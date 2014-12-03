package puzzlesolver.sort;

import java.util.List;

import puzzlesolver.piece.Piece;

public interface ISort {
	Piece removePiece(String id, List<Piece> puzzle);
	
	List<Piece> sortLine(Piece firstPiece, List<Piece> puzzle);
	
	Piece[][] sortPuzzle(List<Piece> puzzle);
}
