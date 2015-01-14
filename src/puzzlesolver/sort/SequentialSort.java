package puzzlesolver.sort;

import java.util.ArrayList;
import java.util.List;

import puzzlesolver.piece.Piece;

public class SequentialSort implements ISort {
	
	/*
	 * Metodo che ricerca il pezzo di id voluto, lo rimuove dal puzzle e lo ritorna.
	 * Se il pezzo non viene trovato, viene ritornato null.
	 */
	private Piece removePiece(String id, List<Piece> puzzle) {
		for(int i = 0; i < puzzle.size(); i++)
			if(puzzle.get(i).getId().equals(id)) 
				return puzzle.remove(i);
		return null;											//se non trovo il pezzo, ritorno null
	}
	
	public static boolean compareSize(int originalSize, Piece[][] orderedPuzzle) {
		int orderedSize = 0;
		for(int i = 0; i < orderedPuzzle.length; i++)
			for(int j = 0; j < orderedPuzzle[i].length; j++)
				orderedSize++;
		
		return orderedSize == originalSize;
	}
	
	public List<Piece> sortLine(Piece firstPiece, List<Piece> puzzle) {
		Piece currentPiece = firstPiece;
		int currentIndex = 0;
		List<Piece> puzzleLine = new ArrayList<Piece>();
		puzzleLine.add(firstPiece);
		while(currentIndex < puzzle.size() && !currentPiece.borderEast()) {
			if(puzzle.get(currentIndex).getId().equals(currentPiece.getEast())) {
				currentPiece = puzzle.remove(currentIndex);
				puzzleLine.add(currentPiece);
				currentIndex = 0;
				continue;
			}
			currentIndex++;
		}
		if(!puzzleLine.get(puzzleLine.size() - 1).borderEast())		//vuol dire che il puzzle e' finito prima di completare la riga
			return null;
		return puzzleLine;
	}
	
	
	public Piece[][] sortPuzzle(List<Piece> puzzle) {
		int puzzleSize = puzzle.size();			//probabilmente la procedura di ricavo della prima riga andrebbe spezzata in una funzione a parte
		Piece upperLeft = null;
		for(int i = 0; i < puzzle.size(); i++) {
			if(puzzle.get(i).borderWest() && puzzle.get(i).borderNorth()) {
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
		int cols = firstLine.size();									
		Piece[][] orderedPuzzle = new Piece[rows][cols];	
		for(int i = 0; i < cols; i++)
			orderedPuzzle[0][i] = firstLine.get(i);
		
		for(int i = 1; i < rows; i++) {
			Piece firstRowPiece = this.removePiece(orderedPuzzle[i - 1][0].getSouth(), puzzle);
			if(firstRowPiece == null)
				return null;
			List<Piece> rowPieces = sortLine(firstRowPiece, puzzle);
			if(rowPieces == null)
				return null;
			for(int j = 0; j < cols; j++)
				orderedPuzzle[i][j] = rowPieces.get(j);
		}
		
		if(compareSize(puzzleSize, orderedPuzzle))
			return orderedPuzzle;
		
		System.out.println("La dimensione del puzzle dopo l'ordinamento e' minore di quella di partenza.");
		return null;
	}
	
}
