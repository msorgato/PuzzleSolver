package puzzlesolver.sort;

import java.util.List;

import puzzlesolver.piece.Piece;

public interface ISort {
	
	List<Piece> sortLine(Piece firstPiece, List<Piece> puzzle);
	
	Piece[][] sortPuzzle(List<Piece> puzzle);
}
