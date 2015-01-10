package puzzlesolver.sort;

import java.util.ArrayList;
import java.util.List;

import puzzlesolver.piece.Piece;

public class ConcurrentSort implements ISort {
	//contatori dei thread utilizzati nell'ordinamento
	private int thread_started = 0;
	private EndedMonitor thread_ended = new EndedMonitor();	//oggetto di sincronizzazione
	
	//lista di array di Piece che verrà acceduta concorrentemente nell'ordinamento
	private List<Piece[]> orderedPuzzle;
	
	private class EndedMonitor {
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
			Piece currentPiece = firstRowPiece;
			int currentIndex = 0;
			List<Piece> puzzleLine = new ArrayList<Piece>();
			puzzleLine.add(firstRowPiece);
			while(currentIndex < puzzle.size() && !currentPiece.borderEast()) {
				if(puzzle.get(currentIndex).getId().equals(currentPiece.getEast())) {
					currentPiece = puzzle.get(currentIndex);
					puzzleLine.add(currentPiece);
					currentIndex = 0;
					continue;
				}
				currentIndex++;
			}
			
			if(!puzzleLine.get(puzzleLine.size() - 1).borderEast())		//vuol dire che il puzzle e' finito prima di completare la riga
				orderedPuzzle.set(row, null);
			else
				orderedPuzzle.set(row, puzzleLine.toArray(new Piece[puzzleLine.size()]));
			
			thread_ended.incrementEnded();
		}
	}
	
	
	private Piece[] getLeftBorder(List<Piece> puzzle) {
		Piece upperLeft = ISort.getUpperLeft(puzzle);
		if(upperLeft == null)
			return null;
		
		List<Piece> firstColumn = new ArrayList();
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


	@Override
	public Piece[][] sortPuzzle(List<Piece> puzzle) {
		/*
		 * la condizione di attesa per il metodo e':
		 * while(!(thread_started == thread_ended))
		 * 		thread_ended.wait();
		 * 
		 * io pero' ci metterei anche una flag di controllo, in modo che 
		 * l'attesa non rimanga piantata in eterno. La frag dovrebbe essere impostata
		 * dai Thread stessi, che al riscontro di un problema, settano la flag a 
		 * "CAZZI" e notificano il bastardo addormentato.  
		 */
		
		Piece[] leftBorder = getLeftBorder(puzzle);
		if(leftBorder == null) {
			System.out.println("Problemi nella creazione della prima colonna per l'ordinamento.");
			return null;
		}
		
		orderedPuzzle = new ArrayList<Piece[]>();		//COSTRUTTORE INUTILE.
		System.out.println("Grandezza di orderedPuzzle: " + orderedPuzzle.size());
		System.out.println("Grandezza di leftBorder: " + leftBorder.length);
		
		for(int i = 0; i < leftBorder.length; i++) {
			orderedPuzzle.add(null);
			new SortLineThread(leftBorder[i], i, puzzle);
			thread_started++;
		}
		
		synchronized(thread_ended) {
			while(!(thread_started == thread_ended.getEnded()))
				try {
					thread_ended.wait();				//<------ occhio che questa istruzione potrebbe avere attesa infinita.
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		System.out.println("OK, fino a dopo l'ordinamento ci e' arrivato.");
		
		/*
		 * qui ci sta il controllo delle righe. Ora, si potrebbe fare che l'ordinamento controlla tutto alla fine,
		 * oppure si imposta il flag di controllo e si modifica la condizione di attesa di cui sopra.
		 */
		
		return null;
	}

}
