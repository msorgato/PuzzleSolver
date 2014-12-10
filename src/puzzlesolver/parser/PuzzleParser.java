package puzzlesolver.parser;

import java.util.List;

import puzzlesolver.piece.Piece;

public class PuzzleParser implements IParser {
	
	//metodo statico utilizzato per controllare che tutti i campi del pezzo in input non siano vuoti
	public static boolean inputNotEmpty(String[] input) {
		for(int i = 0; i < input.length; i++)
			if(input[i].length() == 0)
				return false;
		return true;
	}
	
	public int idCheck(List<Piece> puzzle) {
		for(int i = 0; i < puzzle.size(); i++)
			for(int j = i + 1; j < puzzle.size(); j++)
				if(puzzle.get(i).getId().equals(puzzle.get(j).getId()) || puzzle.get(i).getNorth().equals(puzzle.get(j).getNorth()) ||
						puzzle.get(i).getEast().equals(puzzle.get(j).getEast()) || puzzle.get(i).getSouth().equals(puzzle.get(j).getSouth()) ||
						puzzle.get(i).getWest().equals(puzzle.get(j).getWest())) {
					System.out.println("Sono stati riscontrati dei problemi tra gli ID dei pezzi alla riga " + (i + 1) + " e " + (j + 1));
					return i;
				}
		return -1;
	}
	
	public boolean parseLine(String line) {
		String[] parts = line.split("\t");			//divido gli elementi della stringa splittando sul carattere di tabulazione
		for(int i = 0; i < parts.length; i++) {
			if(i == 1 && parts[i] == " ")			//se il carattere contenuto nel pezzo e' lo spazio, non eseguo il trim()
				continue;
			parts[i] = parts[i].trim();				//elimino eventuali spazi bianchi, ma non nel carattere del pezzo
		}
		if(parts.length != 6)
			return false;							//il numero di elementi della Stringa dev'essere obbligatoriamente 6 (id, char, id_n, id_e, id_s, id_w)
		if(!inputNotEmpty(parts))
			return false;							//nessun campo dell'input puo' essere vuoto
		if(parts[1].length() > 1 || parts[1].equals(System.getProperty("line.separator")))
			return false;							//il carattere del pezzo non puo' essere piu' lungo di una lettera e non puo' essere il carattere caporiga
		return true;
	}
	
	/*
	 * Questo metodo si potrebbe ampliare controllando che non esistano ID uguali nella stessa posizione cardinale
	 * di un dato pezzo. Cosi' poi l'algoritmo di ordinamento non si sminchia.
	 */
	public int parsePuzzle(String[] puzzle) {
		for(int i = 0; i < puzzle.length; i++) 
			if(!parseLine(puzzle[i]))				//se il parsing della stringa corrente ritorna falso,  
				return i;							//ritorno l'indice i della riga che ha dato errore
		return -1;
	}

}
