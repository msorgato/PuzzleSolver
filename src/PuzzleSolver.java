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

import Parser.IParser;
import Parser.PuzzleParser;


public class PuzzleSolver {
	
	private static Charset charset = StandardCharsets.UTF_8;

	private static List<String> readContent(Path inputPath) {
		List<String> content = new ArrayList<String>();
		try (BufferedReader reader = Files.newBufferedReader(inputPath,	charset)) {
			String line = null;
			IParser parser = new PuzzleParser();
			while ((line = reader.readLine()) != null) {
				if(!parser.parseLine(line))
					return null;
				content.add(line);
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
		String outputFile = args[1];
		
		Path inputPath = Paths.get(inputFile);		//PROVALO DA RIGA DI COMANDO
		Path outputPath = Paths.get(outputFile);
		List<String> puzzle = readContent(inputPath);
		//writeContent(outputPath, inputContent);

	}

}
