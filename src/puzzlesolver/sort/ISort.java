package puzzlesolver.sort;

import java.util.List;

import puzzlesolver.piece.Piece;

public interface ISort {
	
	/*
	 * Metodo di comparazione tra la dimensione originaria del puzzle e la dimensione
	 * del puzzle ordinato. Viene calcolata la dimensione del puzzle ordinato e 
	 * si ritorna il risultato.
	 */
	static boolean compareSize(int originalSize, Piece[][] orderedPuzzle) {
		int orderedSize = 0;
		for(int i = 0; i < orderedPuzzle.length; i++)
			for(int j = 0; j < orderedPuzzle[i].length; j++)
				orderedSize++;
		
		return orderedSize == originalSize;
	}
	
	List<Piece> sortLine(Piece firstPiece, List<Piece> puzzle);
	
	Piece[][] sortPuzzle(List<Piece> puzzle);
}
