package puzzlesolver.parser;

import java.util.List;

import puzzlesolver.piece.Piece;

public interface IParser {
	
	//controllo su ogni campo ID dei pezzi contenuti del puzzle
	public int idCheck(List<Piece> puzzle);
	
	//parser che stabilisce se una stringa sia lecita o meno
	public boolean parseLine(String line);		
	
	//parser che ritorna -1 se il puzzle e' lecito, i in [0 .. puzzle.length] se un pezzo non matcha il pattern voluto
	public int parsePuzzle(String[] puzzle);	
}
