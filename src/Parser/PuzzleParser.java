﻿package Parser;

public class PuzzleParser implements IParser {

	@Override
	public boolean parseLine(String line) {
		String[] parts = line.split("\t");			//divido gli elementi della stringa splittando sul carattere di tabulazione
		for(int i = 0; i < parts.length; i++) {
			if(i == 1 && parts[i] == " ")			//se il carattere contenuto nel pezzo e' lo spazio, non eseguo il trim()
				continue;
			parts[i] = parts[i].trim();				//elimino eventuali spazi bianchi, ma non nel carattere del pezzo
		}
		if(parts.length != 6)
			return false;							//il numero di elementi della Stringa dev'essere obbligatoriamente 6 (id, char, id_n, id_e, id_s, id_w)
		if(parts[1].length() > 1 || parts[1].equals("\n"))
			return false;							//il carattere del pezzo non puo' essere piu' lungo di una lettera e non puo' essere il carattere caporiga
		System.out.println("Carattere nel pezzo: " + parts[1] + ";");
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
