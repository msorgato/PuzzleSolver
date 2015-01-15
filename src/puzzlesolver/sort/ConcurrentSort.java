package puzzlesolver.sort;

import java.util.ArrayList;
import java.util.List;

import puzzlesolver.piece.Piece;

public class ConcurrentSort implements ISort {
	
	//oggetto utilizzato per il forwarding dei metodi dell'interfaccia ISort
	private ISort.DefaultSort forward = new ISort.DefaultSort();
	
	@Override
	public boolean compareSize(int originalSize, Piece[][] orderedPuzzle) {
		return forward.compareSize(originalSize, orderedPuzzle);
	}

	@Override
	public Piece getUpperLeft(List<Piece> puzzle) {
		return forward.getUpperLeft(puzzle);
	}
	
	//contatore dei thread utilizzati nell'ordinamento e oggetto di sincronizzazione
	private EndedMonitor thread_ended = new EndedMonitor();	
	//flag che viene impostata a false soltanto al riscontro di problemi nell'ordinamento
	private boolean allOk = true;
	
	//lista di array di Piece che verra' acceduta concorrentemente nell'ordinamento
	private List<Piece[]> orderedPuzzle = new ArrayList<Piece[]>();
	
	private class EndedMonitor {
		private int endedCounter = 0;
		
		public synchronized void incrementEnded() {
			endedCounter++;
			notifyAll();
		}
		
		public synchronized int getEnded() {
			return endedCounter;
		}
		
		public synchronized void resetEnded() {
			endedCounter = 0;
		}
	}
	
	private class SortLineThread extends Thread {
		private Piece firstRowPiece;
		private int row;
		//marcatura final del riferimento per evitare race condition tra i vari SortLineThread
		private final List<Piece> puzzle;
		
		public SortLineThread(Piece firstRowPiece, int row, List<Piece> puzzle) { 
			this.firstRowPiece = firstRowPiece; 
			this.row = row;
			this.puzzle = puzzle;
			start();
		}
		
		public void run() {
			//stampa di monitoraggio dello stato del thread
			System.out.println("Thread riga " + (row + 1) + " partito.");
			
			/*
			 * MAX_ITER esprime il numero MASSIMO di iterazioni che un thread puo' compiere per
			 * ordinare una riga di un puzzle, in ordine inverso. Ovvero, si calcola la percorrenza di tutta la lista 
			 * fino all'ultimo elemento per ogni elemento, ogni volta diminuendo di 1 la dimensione della lista (idealmente, 
			 * togliamo un pezzo ad ogni passata).
			 * Quindi, il numero massimo di iterazioni si riconduce al problema della somma dei primi n numeri naturali,
			 * la cui formula e' la sottostante.
			 */
			final int MAX_ITER = (puzzle.size() * (puzzle.size() + 1)) / 2;	 	
			//qui dovrebbe essere (puzzle.size() * (puzzle.size() + 1)) / (2 * (row + 1)) 
			
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
			
			//stampa di monitoraggio dello stato del thread
			System.out.println("Thread riga " + (row + 1) + " terminato.");
			
		}
	}
	
	public Piece[] getLeftBorder(List<Piece> puzzle) {
		Piece upperLeft = getUpperLeft(puzzle);
		if(upperLeft == null)
			return null;
		
		final int MAX_ITER = (puzzle.size() * (puzzle.size() + 1)) / 2;	
		List<Piece> firstColumn = new ArrayList<Piece>();
		firstColumn.add(upperLeft);
		String nextPieceID = upperLeft.getSouth();
		int currentIndex = 0, currentIter = 0;
		
		while(!nextPieceID.equals("VUOTO") && currentIndex < puzzle.size() && currentIter <= MAX_ITER) {
			currentIter++;
			if(puzzle.get(currentIndex).getId().equals(nextPieceID)) {
				firstColumn.add(puzzle.get(currentIndex));
				nextPieceID = puzzle.get(currentIndex).getSouth();
				currentIndex = 0;
			} else 
				currentIndex++;
		}
		
		if(!firstColumn.get(firstColumn.size() - 1).borderSouth())
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
	public synchronized Piece[][] sortPuzzle(List<Piece> puzzle) {
		
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
			while(!(thread_ended.getEnded() == leftBorder.length) && allOk)		
				try {
					thread_ended.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Il main Thread e' stato interrotto mentre era in attesa dei Thread di"
							+ " ordinamento");
					return null;
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
		
		/*
		 * blocco di istruzioni utile solamente nel caso di utilizzi consecutivi di una stessa
		 * istanza di ConcurrentSort 
		 */
		synchronized(thread_ended) {
			while(!(thread_ended.getEnded() == leftBorder.length))		
				try {
					thread_ended.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Il main Thread e' stato interrotto mentre era in attesa dei Thread di"
							+ " ordinamento");
					return null;
				}
		}
		
		//reinizializzazione dei campi per il nuovo possibile ordinamento
		thread_ended.resetEnded();
		allOk = true;
		orderedPuzzle = new ArrayList<Piece[]>();
		
		if(!compareSize(puzzle.size(), orderedMatrix))
			return null;
		return orderedMatrix;
	}

}
