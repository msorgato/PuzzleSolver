package puzzlesolver.sort;

import java.util.ArrayList;
import java.util.List;

import puzzlesolver.piece.Piece;

public class SequentialSort implements ISort {
	
	public Piece[][] sortPuzzle(List<Piece> puzzle) {
		List<Piece> firstLine = new ArrayList<Piece>();
		int puzzleSize = puzzle.size();			//probabilmente la procedura di ricavo della prima riga andrebbe spezzata in una funzione a parte
		for(int i = 0; i < puzzle.size(); i++) {
			if(puzzle.get(i).getWest().equals("VUOTO") && puzzle.get(i).getNorth().equals("VUOTO")) {
				firstLine.add(puzzle.remove(i));
				break;
			}
		}
		Piece currentPiece = firstLine.get(0);
		int currentIndex = 0;
		while(!currentPiece.getEast().equals("VUOTO")) {
			if(puzzle.get(currentIndex).getId().equals(currentPiece.getEast())) {
				currentPiece = puzzle.remove(currentIndex);
				firstLine.add(currentPiece);
				currentIndex = 0;
				continue;
			}
			currentIndex++;
		}
		Piece[][] orderedPuzzle = new Piece[puzzleSize / firstLine.size()][firstLine.size()];	//QUESTA RIGA POTREBBE ESSERE UNA PUTTANATA INCREDIBILE.
		
		/*
		 * da qua in poi, si ordina ogni riga sequenzialmente e si prende il sud di ogni primo elemento
		 * come elemento iniziale della riga successiva. And so on.
		 */
		
		//STAMPA DI PROVA
		for(int i = 0; i < firstLine.size(); i++)
			System.out.println(firstLine.get(i).getCharacter());
		
		return null;
		
	}
	

}
