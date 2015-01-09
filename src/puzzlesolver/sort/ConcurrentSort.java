package puzzlesolver.sort;

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
