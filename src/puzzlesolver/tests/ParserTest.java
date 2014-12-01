package puzzlesolver.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import puzzlesolver.parser.IParser;
import puzzlesolver.parser.PuzzleParser;

public class ParserTest {

	public static void main(String[] args) {
		IParser parser = new PuzzleParser();
		String input = "U1 \t c \t G1 \t J5 \t U8 \t UH";
		System.out.println(input);
		/*String false_input = "U1 \t ml \t G1 \t J5 \t U8 \t UH";
		String[] puzzle = {"U1 \t c \t G1 \t J5 \t U8 \t UH", "U1 \t c \t G1 \t J5 \t U8 \t UH", "U1 \t c \t G1 \t J5 \t U8 \t UH", 
				"U1 \t \n \t G1 \t J5 \t U8 \t UH", "U1 \t ml \t G1 \t J5 \t U8 \t UH"};
		
		System.out.println(parser.parseLine(input));
		System.out.println(parser.parseLine(false_input));
		System.out.println(parser.parsePuzzle(puzzle));
		System.out.println(puzzle[3]);
		
		System.out.println("-----------------------------------------------------------------");
		
		String string_with_tab = "U1 \t \t \t G1 \t J5 \t U8 \t UH";
		System.out.println(parser.parseLine(string_with_tab));*/
		
		System.out.println("-----------------------------------------------------------------");
		System.out.println("    /******Prova per la lettura ed il parsing da file******/");
		String inputFile = args[0];
		//String outputFile = args[1];
		
		Path inputPath = Paths.get(inputFile);		//PROVALO DA RIGA DI COMANDO
		System.out.println(inputPath.toString());
		List<String> content = new ArrayList<String>();
		try (BufferedReader reader = Files.newBufferedReader(inputPath,	StandardCharsets.UTF_8)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				if(!parser.parseLine(line)) {
					System.out.println("Ci sono casini nell'input");
					break;
				}
				content.add(line);
				System.out.println(line);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
