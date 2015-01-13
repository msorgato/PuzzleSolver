package puzzlesolver.sort;

import java.util.ArrayList;
import java.util.List;

import puzzlesolver.piece.Piece;

public class ConcurrentSort implements ISort {
	//contatore dei thread utilizzati nell'ordinamento e oggetto di sincronizzazione
	private EndedMonitor thread_ended = new EndedMonitor();	
	//flag che viene impostata a false soltanto al riscontro di problemi nell'ordinamento
	private boolean allOk = true;
	
	//lista di array di Piece che verra' acceduta concorrentemente nell'ordinamento
	private List<Piece[]> orderedPuzzle = new ArrayList<Piece[]>();
	
	public class EndedMonitor {
		private int endedCounter = 0;
		
		public synchronized void incrementEnded() {
			endedCounter++;
			notify();
		}
		
		public synchronized int getEnded() {
			return endedCounter;
		}
	}
	
	private class SortLineThread extends Thread {
		private Piece firstRowPiece;
		private int row;
		private final List<Piece> puzzle;
		
		public SortLineThread(Piece firstRowPiece, int row, List<Piece> puzzle) { 
			this.firstRowPiece = firstRowPiece; 
			this.row = row;
			this.puzzle = puzzle;
			start(); 
		}
		
		public void run() {
			/*
			 * MAX_ITER esprime il numero MASSIMO di iterazioni che un thread puo' compiere per
			 * ordinare una riga di un puzzle. Ovvero, si calcola la percorrenza di tutta la lista fino all'ultimo
			 * elemento per ogni elemento, ogni volta diminuendo di 1 la dimensione della lista (idealmente, 
			 * togliamo un pezzo ad ogni passata).
			 * Quindi, il numero massimo si riconduce al problema della somma dei primi n numeri naturali, la
			 * cui formula e' la sottostante.
			 */
			final int MAX_ITER = (puzzle.size() * (puzzle.size() + 1)) / 2;	
			
			Piece currentPiece = firstRowPiece;
			int currentIndex = 0, currentIter = 0;
			
			List<Piece> puzzleLine = new ArrayList<Piece>();
			puzzleLine.add(firstRowPiece);
			
			while(currentIndex < puzzle.size() && !currentPiece.borderEast() && (currentIter <= MAX_ITER)) {
				currentIter++;
				if(puzzle.get(currentIndex).getId().equals(currentPiece.getEast())) {
					currentPiece = puzzle.get(currentIndex);
					puzzleLine.add(currentPiece);
					currentIndex = 0;
					continue;
				}
				currentIndex++;
			}
			
			if(!puzzleLine.get(puzzleLine.size() - 1).borderEast())	{	
				//vuol dire che il puzzle e' finito prima di completare la riga
				orderedPuzzle.set(row, null);
				allOk = false;
				System.out.println("L'ordinamento della riga " + (row + 1) + " e' terminato "
						+ "prima di giungere al bordo est.");
			}
			else
				orderedPuzzle.set(row, puzzleLine.toArray(new Piece[puzzleLine.size()]));
			
			thread_ended.incrementEnded();
		}
	}
	
	
	private static Piece[] getLeftBorder(List<Piece> puzzle) {
		Piece upperLeft = ISort.getUpperLeft(puzzle);
		if(upperLeft == null)
			return null;
		
		List<Piece> firstColumn = new ArrayList<Piece>();
		firstColumn.add(upperLeft);
		String nextPieceID = upperLeft.getSouth();
		int currentIndex = 0;
		
		while(!nextPieceID.equals("VUOTO") && currentIndex < puzzle.size()) {
			if(puzzle.get(currentIndex).getId().equals(nextPieceID)) {
				firstColumn.add(puzzle.get(currentIndex));
				nextPieceID = puzzle.get(currentIndex).getSouth();
				currentIndex = 0;
			} else 
				currentIndex++;
		}
		
		if(!firstColumn.get(firstColumn.size() -1).getSouth().equals("VUOTO"))
			return null;
		
		return firstColumn.toArray(new Piece[firstColumn.size()]);
	}
	
	private int rowCheck() {
		int rowSize;
		try {
			rowSize = orderedPuzzle.get(0).length;
		} catch(NullPointerException e) {
			//la prima riga ha presentato problemi nell'ordinamento
			return 0;
		}
		for(int i = 0; i < orderedPuzzle.size(); i++) {
			if(orderedPuzzle.get(i) == null)
				return i;
			if(i > 0 && (orderedPuzzle.get(i).length != rowSize)) {
				System.out.println("La riga " + (i + 1) + " ha una lunghezza diversa dalla prima riga.");
				return i; 
			}
		}
		return -1;
	}


	@Override
	public Piece[][] sortPuzzle(List<Piece> puzzle) {
		/* 
		 * io pero' ci metterei anche una flag di controllo, in modo che 
		 * l'attesa non rimanga piantata in eterno. La flag dovrebbe essere impostata
		 * dai Thread stessi, che al riscontro di un problema, settano la flag a 
		 * "CAZZI" e notificano il bastardo addormentato.  
		 */
		
		Piece[] leftBorder = getLeftBorder(puzzle);
		if(leftBorder == null) {
			System.out.println("Problemi nella creazione della prima colonna per l'ordinamento.");
			return null;
		}
		
		
		for(int i = 0; i < leftBorder.length; i++) {
			orderedPuzzle.add(null);
			new SortLineThread(leftBorder[i], i, puzzle);
		}
		
		synchronized(thread_ended) {
			while(!(leftBorder.length == thread_ended.getEnded()) && allOk)		//da aggiornare con la flag dei thread
				try {
					thread_ended.wait();		//questa wait non attende mai infinitamente
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		
		if(!allOk) {
			System.out.println("Il processo di ordinamento delle righe ha riscontrato dei problemi.");
			return null;
		}
		
		int rowCheckResult = rowCheck();
		if(rowCheckResult != -1) {
			//la notazione "rowCheckResult + 1" e' dovuta al fatto di numerare le righe a partire da 1
			System.out.println("L'ordinamento della riga " + (rowCheckResult + 1) + " ha riscontrato dei problemi.");
			return null;
		}
		
		Piece[][] orderedMatrix = new Piece[orderedPuzzle.size()][orderedPuzzle.get(0).length];
		
		try {
			for(int i = 0; i < orderedPuzzle.size(); i++)
				for(int j = 0; j < orderedPuzzle.get(i).length; j++)
					orderedMatrix[i][j] = orderedPuzzle.get(i)[j];
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("La dimensione del puzzle ordinato non e' NxM.");
			return null;
		}
		
		return orderedMatrix;
	}

}
