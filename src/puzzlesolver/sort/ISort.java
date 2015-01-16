package puzzlesolver.sort;

import java.util.List;

import puzzlesolver.piece.Piece;

public interface ISort {	
	
	//metodo di comparazione della dimensione iniziale del puzzle con quella del puzzle ordinato
	boolean compareSize(int originalSize, Piece[][] orderedPuzzle);
	
	//metodo che ritorna un array di Piece contenente i pezzi della prima colonna del puzzle
	Piece getUpperLeft(List<Piece> puzzle);
	
	//metodo di ordinamento del puzzle
	Piece[][] sortPuzzle(List<Piece> puzzle);
	
	//classe statica contenente l'implementazione di default dell'interfaccia
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
