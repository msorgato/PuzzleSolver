package Parser;

public class ConcreteParser implements IParser {

	@Override
	public boolean parseLine(String line) {
		String[] parts = line.split("\t");			//divido gli elementi della stringa splittando sul carattere di tabulazione
		for(int i = 0; i < parts.length; i++)
			if(i != 1)
				parts[i] = parts[i].trim();			//elimino eventuali spazi bianchi, ma non nel carattere del pezzo
		if(parts[1].length() > 1)
			return false;							//il carattere del pezzo non può essere più lungo di una lettera
		if(parts.length != 6)
			return false;							//il numero di elementi della Stringa dev'essere obbligatoriamente 6 (id, char, id_n, id_e, id_s, id_w)
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
