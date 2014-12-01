package Tests;

import Parser.PuzzleParser;
import Parser.IParser;

public class ParserTest {

	public static void main(String[] args) {
		IParser parser = new PuzzleParser();
		String input = "U1 \t c \t G1 \t J5 \t U8 \t UH";
		String false_input = "U1 \t ml \t G1 \t J5 \t U8 \t UH";
		String[] puzzle = {"U1 \t c \t G1 \t J5 \t U8 \t UH", "U1 \t c \t G1 \t J5 \t U8 \t UH", "U1 \t c \t G1 \t J5 \t U8 \t UH", 
				"U1 \t \n \t G1 \t J5 \t U8 \t UH", "U1 \t ml \t G1 \t J5 \t U8 \t UH"};
		
		System.out.println(parser.parseLine(input));
		System.out.println(parser.parseLine(false_input));
		System.out.println(parser.parsePuzzle(puzzle));
		System.out.println(puzzle[3]);
		
		System.out.println("---------------------------------------------------------------");
		
		String string_with_tab = "U1 \t \t \t G1 \t J5 \t U8 \t UH";
		System.out.println(parser.parseLine(string_with_tab));
	}

}
