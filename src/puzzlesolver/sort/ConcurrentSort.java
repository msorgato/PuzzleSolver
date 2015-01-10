package puzzlesolver.sort;

import java.util.ArrayList;
import java.util.List;

import puzzlesolver.piece.Piece;

public class ConcurrentSort implements ISort {
	//contatori dei thread utilizzati nell'ordinamento
	private int thread_started = 0;
	private Integer thread_ended = new Integer(0);	//oggetto di sincronizzazione, pertanto uso la classe wrapper
	
	//lista di array di Piece che verrà acceduta concorrentemente nell'ordinamento
	private List<Piece[]> orderedPuzzle;
	
	private class SortLineThread extends Thread {
		private Piece firstRowPiece;
		private int row;
		private List<Piece> puzzle;
		
		public SortLineThread(Piece firstRowPiece, int row, List<Piece> puzzle) { 
			this.firstRowPiece = firstRowPiece; 
			this.row = row;
			this.puzzle = puzzle;
			start(); 
		}
		
		public void run() {
			
		}
	}
	
	//forse queste manco servono, basta inglobarle nel codice della run del SortLineThread
	private void addEnded() {
		synchronized(thread_ended) {
			thread_ended++;
			thread_ended.notify();
		}
	}
	private int getEnded() {
		synchronized(thread_ended) {
			return thread_ended.intValue();
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
	public List<Piece> sortLine(Piece firstPiece, List<Piece> puzzle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Piece[][] sortPuzzle(List<Piece> puzzle) {
		/*
		 * la condizione di attesa per il metodo e':
		 * while(!(thread_started == thread_ended))
		 * 		thread_ended.wait();
		 * 
		 * io pero' ci metterei anche una flag di controllo, in modo che 
		 * l'attesa non rimanga piantata i eterno. La frag dovrebbe essere impostata
		 * dai Thread stessi, che al riscontro di un problema, settano la flag a 
		 * "CAZZI" e notificano il bastardo addormentato.  
		 */
		
		Piece[] leftBorder = getLeftBorder(puzzle);
		if(leftBorder == null) {
			System.out.println("Problemi nella creazione della prima colonna per l'ordinamento.");
			return null;
		}
		
		orderedPuzzle = new ArrayList<Piece[]>(leftBorder.length);
		
		for(int i = 0; i < leftBorder.length; i++) {
			new SortLineThread(leftBorder[i], i, puzzle);
			thread_started++;
		}
		
		return null;
	}

}
