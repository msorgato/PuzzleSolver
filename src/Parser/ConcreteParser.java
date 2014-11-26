package Parser;

public class ConcreteParser implements IParser {

	@Override
	public boolean parseLine(String line) {
		String[] parts = line.split("\t");			//divido gli elementi della stringa splittando sul carattere di tabulazione
		for(int i = 0; i < parts.length; i++)
			parts[i] = parts[i].trim();				//elimino eventuali spazi bianchi
		if(parts[1].length() > 1)
			return false;							//il carattere del pezzo non può essere più lungo di una lettera
		return true;
		
	}

	@Override
	public int parsePuzzle(String[] puzzle) {
		for(int i = 0; i < puzzle.length; i++) 
			if(!parseLine(puzzle[i]))				//se il parsing della stringa corrente ritorna falso,  
				return i;							//ritorno l'indice i della riga che ha dato errore
		return -1;
	}

}
