package puzzlesolver.sort;

import java.util.ArrayList;
import java.util.List;

import puzzlesolver.piece.Piece;

public class SequentialSort implements ISort {

	public Piece[][] sortPuzzle(List<Piece> puzzle) {
		List<Piece> firstLine = new ArrayList<Piece>();
		int puzzleSize = puzzle.size();
		for(int i = 0; i < puzzle.size(); i++) {
			if(puzzle.get(i).getWest().equals("VUOTO") && puzzle.get(i).getNorth().equals("VUOTO")) {
				System.out.println(puzzle.get(i).getCharacter());
				firstLine.add(puzzle.remove(i));
				break;
			}
		}
		Piece currentPiece = firstLine.get(0);
		int currentIndex = 0;
		while(!currentPiece.getEast().equals("VUOTO")) {
			if(puzzle.get(currentIndex).getId().equals(currentPiece.getEast())) {
				currentPiece = puzzle.remove(currentIndex);
				currentIndex = 0;
				continue;
			}
			currentIndex++;
		}
		
		//STAMPA DI PROVA
		for(int i = 0; i < firstLine.size(); i++)
			System.out.println(firstLine.get(i).getCharacter());
		
		return null;
		
	}

}
