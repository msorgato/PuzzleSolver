package Parser;

public interface IParser {
	//parser che stabilisce se una stringa sia lecita o meno
	public boolean parseLine(String line);		
	
	//parser che ritorna -1 se il puzzle e' lecito, i in [0 .. puzzle.length] se un pezzo non matcha il pattern voluto
	public int parsePuzzle(String[] puzzle);	
}
