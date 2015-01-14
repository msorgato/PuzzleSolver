package puzzlesolver.sort;

import java.util.List;

import puzzlesolver.piece.Piece;

public interface ISort {	
	
	boolean compareSize(int originalSize, Piece[][] orderedPuzzle);
	
	Piece getUpperLeft(List<Piece> puzzle);
	
	Piece[][] sortPuzzle(List<Piece> puzzle);
	
	static class DefaultSort {
		public boolean compareSize(int originalSize, Piece[][] orderedPuzzle) {
			int orderedSize = 0;
			for(int i = 0; i < orderedPuzzle.length; i++)
				for(int j = 0; j < orderedPuzzle[i].length; j++)
					orderedSize++;
			
			return orderedSize == originalSize;
		}
		
		public Piece getUpperLeft(List<Piece> puzzle) {
			Piece upperLeft = null;
			for(int i = 0; i < puzzle.size(); i++) {
				if(puzzle.get(i).borderWest() && puzzle.get(i).borderNorth()) {
					upperLeft = puzzle.get(i);
					break;
				}
			}	
			return upperLeft;
		}
	}
}
