package puzzlesolver.remote.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import puzzlesolver.parser.IParser;
import puzzlesolver.parser.PuzzleParser;
import puzzlesolver.piece.Piece;
import puzzlesolver.remote.RemoteSolver;

public class PuzzleSolverClient {

	private static List<String> readContent(String inputPath) {
		List<String> content = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				content.add(line);
			}
			IParser parser = new PuzzleParser();
			int index = parser.parsePuzzle(content.toArray(new String[content.size()])); 
			if(index != -1) {
				System.out.println("Sono stati riscontrati problemi nella sintassi del file alla riga " + (index + 1));
				content = null;
			}
		} catch (IOException e) {
			System.out.println("Si sono riscontrati problemi nella lettura del file di input.");
			content = null;
		} finally {
			try {
				if(reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
	
	private static void writeContent(String file, String content) {
		BufferedWriter writer = null;
		try {																		//cerca di scrivere nel file di percorso "file",
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			writer.write(content);													//il contenuto della stringa "content",																		
		} catch (IOException e) {													//altrimenti gestisco localmente l'eccezione
		System.err.println(e);														//ed il programma termina con un messaggio di errore
			System.out.println("Non e' stato possibile scrivere sul file di output.");
		} finally {
			try {
				if(writer != null) 
					writer.close();
			} catch (IOException e) {
					e.printStackTrace();
			}
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
		//controlli degli argomenti di invocazione del main di PuzzleSolverClient
		if(args.length == 0) {
			System.out.println("Mancano i tre argomenti di invocazione.");
			System.out.println("Il metodo di invocazione corretto e': java PuzzleSolverClient "
					+ "path\\input path\\output nome_del_server");
			return;
		}
		if(args.length == 1) {
			System.out.println("Mancano due dei tre argomenti di invocazione.");
			System.out.println("Il metodo di invocazione corretto e': java PuzzleSolverClient "
					+ "path\\input path\\output nome_del_server");
			return;
		}
		if(args.length == 2) {
			System.out.println("Manca un argomento di invocazione");
			System.out.println("Il metodo di invocazione corretto e': java PuzzleSolverClient "
					+ "path\\input path\\output nome_del_server");
			return;
		}
		if(args.length > 3) {
			System.out.println("Sono presenti troppi argomenti di invocazione.");
			System.out.println("Il metodo di invocazione corretto e': java PuzzleSolverClient "
					+ "path\\input path\\output nome_del_server");
			return;
		}
		
		String inputPath = args[0];
		String outputPath = args[1];
		
		List<String> input = readContent(inputPath);
		
		List<Piece> pieces = new ArrayList<Piece>();
		if(input != null) 
			for(int i = 0; i < input.size(); i++)
				pieces.add(new Piece(input.get(i)));
		else {
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
		} catch (RemoteException e) {
			System.out.println("Si sono riscontrati problemi con l'ottenimento del riferimento al server remoto.");
		} catch (NotBoundException e) {
			System.out.println("All'indirizzo del Server specificato non e' presente alcun servizio.");
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
