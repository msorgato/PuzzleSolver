package remote.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import puzzlesolver.parser.IParser;
import puzzlesolver.parser.PuzzleParser;
import puzzlesolver.piece.Piece;
import remote.RemoteSolver;

public class PuzzleSolverClient {
	
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
			return null;
		}
		return content;
	}
	
	private static void writeContent(Path file, String content) {
		try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {		//cerca di scrivere nel file di Path "file",
			writer.write(content);													//il contenuto della stringa "content",
		} catch (IOException e) {													//altrimenti gestisco localmente l'eccezione
		System.err.println(e);														//ed il programma termina con un messaggio di errore
			System.out.println("Non e' stato possibile scrivere sul file di output.");
		}
	}
	
	/*
	 * metodo privato statico che prepara un puzzle per la scrittura su file, creando un'unica stringa
	 * per l'output
	 */
	private static String puzzleStringBuilder(Piece[][] puzzle) {
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < puzzle.length; i++)
			for(int j = 0; j < puzzle[i].length; j++)
				builder.append(puzzle[i][j].getCharacter());	//scrive su un'unica riga i caratteri dei pezzi del puzzle
		
		builder.append(System.getProperty("line.separator"));	//vado a capo e lascio una riga vuota
		builder.append(System.getProperty("line.separator"));
		
		for(int i = 0; i < puzzle.length; i++) {				//cicli di costruzione della forma tabellare
			for(int j = 0; j < puzzle[i].length; j++) {
				builder.append(puzzle[i][j].getCharacter());
				if(!(j == (puzzle[i].length - 1)))
					builder.append("\t");
			}
			builder.append(System.getProperty("line.separator"));
		}
		
		builder.append(System.getProperty("line.separator"));	//riga vuota
		
		builder.append("Il puzzle e' formato da " + puzzle.length + " righe e " + puzzle[0].length + " colonne");
		
		return builder.toString();
	}

	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("Mancano i tre argomenti di invocazione.");
			System.out.println("Il metodo di invocazione corretto e': java PuzzleSolver "
					+ "path\\input path\\output nome_del_server");
			return;
		}
		if(args.length == 1) {
			System.out.println("Mancano due dei tre argomenti di invocazione.");
			System.out.println("Il metodo di invocazione corretto e': java PuzzleSolver "
					+ "path\\input path\\output nome_del_server");
			return;
		}
		if(args.length == 2) {
			System.out.println("Manca un argomento di invocazione");
			System.out.println("Il metodo di invocazione corretto e': java PuzzleSolver "
					+ "path\\input path\\output nome_del_server");
			return;
		}
		if(args.length > 3) {
			System.out.println("Sono presenti troppi argomenti di invocazione.");
			System.out.println("Il metodo di invocazione corretto e': java PuzzleSolver "
					+ "path\\input path\\output nome_del_server");
			return;
		}
		
		String inputFile = args[0];
		String outputFile = args[1];
		
		Path inputPath = null;
		try {
			inputPath = Paths.get(inputFile);
		} catch(InvalidPathException e) {
			System.out.println("Il percorso del file di input non e' corretto.");
			return;
		}
		
		Path outputPath = null;
		try {
			outputPath = Paths.get(outputFile);
		} catch(InvalidPathException e) {
			System.out.println("Il percorso del file di output non e' corretto.");
			return;
		}
		
		List<String> input = readContent(inputPath);
		
		List<Piece> pieces = new ArrayList<Piece>();
		if(input != null) 
			for(int i = 0; i < input.size(); i++)
				pieces.add(new Piece(input.get(i)));
		else {
			System.out.println("Sono stati riscontrati dei problemi nella lettura del File.");
			return;
		}
		
		IParser parser = new PuzzleParser();
		if(parser.idCheck(pieces) != -1)
			return;
		
		RemoteSolver solver = null;
		try {
			solver = (RemoteSolver) Naming.lookup(args[2]);
		} catch (MalformedURLException e) {
			System.out.println("L'URL del Server immessa non e' corretta.");
			e.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("Si sono riscontrati problemi con l'invocazione del metodo al Server.");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("All'indirizzo del Server specificato non e' presente alcun servizio.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(solver == null)
			return;
		
		Piece[][] orderedPuzzle = null;
		try {
			orderedPuzzle = solver.sortPuzzle(pieces);
		} catch (RemoteException e) {
			System.out.println("Il Server si e' disconnesso prima della fine dell'ordinamento.");
			e.printStackTrace();
		}
		
		if(orderedPuzzle == null)
			return;
		
		String output = puzzleStringBuilder(orderedPuzzle);
		writeContent(outputPath, output);

	}

}
