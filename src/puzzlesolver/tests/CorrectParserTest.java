package puzzlesolver.tests;

import puzzlesolver.parser.IParser;
import puzzlesolver.parser.PuzzleParser;

public class CorrectParserTest {

	public static void main(String[] args) {
		IParser parser = new PuzzleParser();
		String correct_line = "U1 \t c \t G1 \t J5 \t U8 \t UH";
		/*
		 * questa stringa rappresenta la sintassi di un pezzo corretto, con campi ID 
		 * del pezzo e degli adiacenti non vuoti e con il carattere contenuto nel pezzo
		 * di lunghezza massima 1. Infatti il risultato del parsing di una stringa con questa sintassi
		 * da' risultato "true".
		 */
		
		System.out.println("Risultato del parsing della stringa \"U1 \t c \t G1 \t J5 \t U8 \t UH\": " + parser.parseLine(correct_line));
		
		System.out.println("--------------------------------------------------------------------------------------------");
		
		String[] correct_puzzle = {"v2 	v	v1 	a2 	VUOTO 	o2", "o1 	o 	VUOTO 	v1 	o2 	r1", "v1 	v	VUOTO 	a1 	v2 	o1", 
				"o2 	o 	o1 	v2 	VUOTO 	r2", "a1 	a	VUOTO 	VUOTO 	a2 	v1"};
		/*
		 * questo Array di stringhe rappresenta dei pezzi del puzzle con sintassi corretta. Tra un campo e l'altro sono stati inseriti 
		 * dei caratteri di tabulazione, come descritto nella specifica del progetto. Ora, se si prova ad eseguire il parsing del 
		 * puzzle intero, questo procedimento ritornera' l'indice di una riga non idonea, oppure l'intero "-1" se il puzzle e' corretto.
		 */
		
		System.out.println("Risultato del parsing del Puzzle: " + parser.parsePuzzle(correct_puzzle));

	}

}
