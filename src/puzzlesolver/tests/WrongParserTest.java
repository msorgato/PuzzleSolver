package puzzlesolver.tests;

import puzzlesolver.parser.IParser;
import puzzlesolver.parser.PuzzleParser;

public class WrongParserTest {

	public static void main(String[] args) {
		IParser parser = new PuzzleParser();
		
		String wrong_input = "U1 \t ml \t G1 \t J5 \t U8 \t UH";		
		/*
		 * questo candidato pezzo del puzzle presenta due caratteri al posto di uno,
		 * quindi non deve essere considerato un pezzo valido dal parser sintattico.
		 */
		
		System.out.println("Risultato del parsing della stringa \"U1 \t ml \t G1 \t J5 \t U8 \t UH\": " + parser.parseLine(wrong_input));
		
		System.out.println("--------------------------------------------------------------------------------------------");
		
		String[] wrong_puzzle = {"U1 \t c \t G1 \t J5 \t U8 \t UH", "U1 \t c \t G1 \t J5 \t U8 \t UH", "U1 \t c \t G1 \t J5 \t U8 \t UH", 
				"U1 \t \n \t G1 \t J5 \t U8 \t UH", "U1 \t ml \t G1 \t J5 \t U8 \t UH"};
		/*
		 * questo insieme di stringhe rappresentanti la sintassi di un puzzle, presenta un errore nell'ultimo pezzo, dove 
		 * il carattere è composto da piu' di un singolo carattere
		 */
		
		System.out.println("Risultato del parsing del puzzle errato: " + parser.parsePuzzle(wrong_puzzle));
		
		System.out.println("--------------------------------------------------------------------------------------------");
		
		String string_with_tab = "U1 \t \t \t G1 \t J5 \t U8 \t UH";
		/*
		 * Prova con il carattere di tabulazione usato al posto del carattere contenuto in un pezzo, non accettabile come
		 * carattere.
		 */
		
		System.out.println("Risultato del parsing della stringa \"U1 \t \t \t G1 \t J5 \t U8 \t UH\": " + parser.parseLine(string_with_tab));
		
		System.out.println("--------------------------------------------------------------------------------------------");
		
		String string_with_newline = "U1 \t \n \t G1 \t J5 \t U8 \t UH";
		/*
		 * Prova con il carattere di caporiga usato al posto del carattere contenuto in un pezzo, non accettabile come
		 * carattere.
		 */
		
		System.out.println("Risultato del parsing della stringa \"U1 \t \n \t G1 \t J5 \t U8 \t UH\": " + parser.parseLine(string_with_newline));
	}

}
