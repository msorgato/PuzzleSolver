package puzzlesolver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import puzzlesolver.parser.IParser;
import puzzlesolver.parser.PuzzleParser;
import puzzlesolver.piece.Piece;


public class PuzzleSolver {
	
	private static Charset charset = StandardCharsets.UTF_8;	//variabile statica di Classe contenente il Charset da utilizzare

	private static List<String> readContent(Path inputPath) {
		List<String> content = new ArrayList<String>();
		try (BufferedReader reader = Files.newBufferedReader(inputPath,	charset)) {
			String line = null;
			IParser parser = new PuzzleParser();
			while ((line = reader.readLine()) != null) {
				content.add(line);
			}
			int index = parser.parsePuzzle(content.toArray(new String[content.size()])); 
			if(index != -1) {
				System.out.println("Sono stati riscontrati problemi nella sintassi del file alla riga " + (index + 1));
				return null;
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		return content;
	}
		
	private static void writeContent(Path file, String content) {
		try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
			writer.write(content);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	public static void main(String[] args) {
		String inputFile = args[0];
		//String outputFile = args[1];
		
		Path inputPath = Paths.get(inputFile);		
		//Path outputPath = Paths.get(outputFile);
		List<String> puzzle = readContent(inputPath);
		
		List<Piece> pieces = new ArrayList<Piece>();
		if(puzzle != null) 
			for(int i = 0; i < puzzle.size(); i++)
				pieces.add(new Piece(puzzle.get(i)));
		else {
			System.out.println("Sono stati riscontrati dei problemi nella lettura del File.");
			return;
		}
		
		
		System.out.println(pieces.get(1).getEast());
			
				
		//writeContent(outputPath, inputContent);

	}

}

