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
		
		public SortLineThread(Piece firstRowPiece) { this.firstRowPiece = firstRowPiece; start(); }
		
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
		// TODO Auto-generated method stub
		return null;
	}

}
