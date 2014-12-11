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
	
	//metodo utilizzato per stabilire se ci sia una corrispondenza tra due ID nella stessa posizione cardinale o come ID del pezzo stesso
	public int idCheck(List<Piece> puzzle) {
		for(int i = 0; i < puzzle.size(); i++)
			for(int j = i + 1; j < puzzle.size(); j++) {
				if(puzzle.get(i).equalsId(puzzle.get(j))) {
					System.out.println("Gli ID dei pezzi alle righe " + (i + 1) + " e " + (j + 1) + " sono uguali");
					return i;
				}
				if(puzzle.get(i).equalsNorth(puzzle.get(j)) && !(puzzle.get(i).borderNorth())) {
					System.out.println("Gli ID NORD dei pezzi alle righe " + (i + 1) + " e " + (j + 1) + " sono uguali");
					return i;
				}
				if(puzzle.get(i).equalsEast(puzzle.get(j)) && !(puzzle.get(i).borderEast())) {
					System.out.println("Gli ID EST dei pezzi alle righe " + (i + 1) + " e " + (j + 1) + " sono uguali");
					return i;
				}
				if(puzzle.get(i).equalsSouth(puzzle.get(j)) && !(puzzle.get(i).borderSouth())) {
					System.out.println("Gli ID SUD dei pezzi alle righe " + (i + 1) + " e " + (j + 1) + " sono uguali");
					return i;
				}
				if(puzzle.get(i).equalsWest(puzzle.get(j)) && !(puzzle.get(i).borderWest())) {
					System.out.println("Gli ID OVEST dei pezzi alle righe " + (i + 1) + " e " + (j + 1) + " sono uguali");
					return i;
				}		
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
	
	public int parsePuzzle(String[] puzzle) {
		for(int i = 0; i < puzzle.length; i++) 
			if(!parseLine(puzzle[i]))				//se il parsing della stringa corrente ritorna falso,  
				return i;							//ritorno l'indice i della riga che ha dato errore
		return -1;
	}

}
