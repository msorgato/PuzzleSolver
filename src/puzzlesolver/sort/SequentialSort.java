package puzzlesolver.sort;

import java.util.ArrayList;
import java.util.List;

import puzzlesolver.piece.Piece;

public class SequentialSort implements ISort {
	
	public Piece removePiece(String id, List<Piece> puzzle) {
		for(int i = 0; i < puzzle.size(); i++)
			if(puzzle.get(i).getId().equals(id)) 
				return puzzle.remove(i);
		return null;											//se non trovo il pezzo, ritorno null
	}
	
	
	public List<Piece> sortLine(Piece firstPiece, List<Piece> puzzle) {
		Piece currentPiece = firstPiece;
		int currentIndex = 0;
		List<Piece> puzzleLine = new ArrayList<Piece>();
		puzzleLine.add(firstPiece);
		while(!currentPiece.getEast().equals("VUOTO") && puzzle.size() != 0) {
			if(puzzle.get(currentIndex).getId().equals(currentPiece.getEast())) {
				currentPiece = puzzle.remove(currentIndex);
				puzzleLine.add(currentPiece);
				currentIndex = 0;
				continue;
			}
			currentIndex++;
		}
		if(!puzzleLine.get(puzzleLine.size() - 1).getEast().equals("VUOTO"))		//vuol dire che il puzzle e' finito prima di completare la riga
			return null;
		return puzzleLine;
	}
	
	
	public Piece[][] sortPuzzle(List<Piece> puzzle) {
		int puzzleSize = puzzle.size();			//probabilmente la procedura di ricavo della prima riga andrebbe spezzata in una funzione a parte
		Piece upperLeft = null;
		for(int i = 0; i < puzzle.size(); i++) {
			if(puzzle.get(i).getWest().equals("VUOTO") && puzzle.get(i).getNorth().equals("VUOTO")) {
				upperLeft = puzzle.remove(i);
				break;
			}
		}
		if(upperLeft == null)			//non e' stato trovato l'angolo in alto a sinistra -> manca (almeno) un pezzo
			return null;	
		
		List<Piece> firstLine = this.sortLine(upperLeft, puzzle);
		if(firstLine == null)	//mancano pezzi nella prima riga, i suppose
			return null;
		
		int rows = puzzleSize / firstLine.size();	
		int cols = firstLine.size();									//QUESTE RIGHE POTREBBERO
		Piece[][] orderedPuzzle = new Piece[rows][cols];	//ESSERE UNA PUTTANATA INCREDIBILE
		for(int i = 0; i < cols; i++)
			orderedPuzzle[0][i] = firstLine.get(i);
		
		/*
		 * da qua in poi, si ordina ogni riga sequenzialmente e si prende il sud di ogni primo elemento
		 * come elemento iniziale della riga successiva. And so on.
		 */
		
		for(int i = 1; i < rows; i++) {
			Piece firstRowPiece = this.removePiece(orderedPuzzle[i - 1][0].getSouth(), puzzle);
			if(firstRowPiece == null)
				return null;
			List<Piece> rowPieces = this.sortLine(firstRowPiece, puzzle);
			if(rowPieces == null)
				return null;
			for(int j = 0; j < cols; j++)
				orderedPuzzle[i][j] = rowPieces.get(j);
		}
		
		
		
		
		
		
		return null;
		
	}
	

}
