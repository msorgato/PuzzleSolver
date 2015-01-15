package puzzlesolver.tests;

import java.util.ArrayList;
import java.util.List;

import puzzlesolver.piece.Piece;
import puzzlesolver.sort.ISort;
import puzzlesolver.sort.ConcurrentSort;

public class ConcurrentSortTest {

	public static void main(String[] args) {
		Piece p = new Piece("p1	 p 	 VUOTO 	 r1 	 p2 	 VUOTO"), 
				r = new Piece("r1	r	VUOTO 	o1 	r2 	p1"), 
				o = new Piece("o1 	o 	VUOTO 	v1 	o2 	r1"), 
				v = new Piece("v1 	v	VUOTO 	a1 	v2 	o1"), 
				a = new Piece("a1 	a	VUOTO 	VUOTO 	a2 	v1"),
				p1 = new Piece("p2 	 	p1 	 r2 	 p3 	 VUOTO"), 
				r1 = new Piece("r2	t	r1 	o2 	r3 	p2"), 
				o1 = new Piece("o2 	e 	o1 	v2 	o3 	r2"), 
				v1 = new Piece("v2 	s	v1 	a2 	v3 	o2"), 
				a1 = new Piece("a2 	t	a1 	VUOTO 	a3 	v2"),
				p2 = new Piece("p3 	t	p2	r3 	 VUOTO 	 VUOTO"), 
				r2 = new Piece("r3	e	r2 	o3 	VUOTO 	p3"), 
				o2 = new Piece("o3 	s 	o2 	v3 	VUOTO 	r3"), 
				v2 = new Piece("v3 	t	v2 	a3 	VUOTO 	o3"), 
				a2 = new Piece("a3 	2	a2 	VUOTO 	VUOTO 	v3");
		
		
		//Appunto, dopo la creazione della lista, i vari pezzi vengono inseriti in ordine casuale.
		List<Piece> puzzle = new ArrayList<Piece>();
		puzzle.add(o); puzzle.add(r2); puzzle.add(a2); puzzle.add(p2); puzzle.add(v1); 
		puzzle.add(a1); puzzle.add(v); puzzle.add(o2); puzzle.add(r); puzzle.add(p);
		puzzle.add(r1); puzzle.add(p1); puzzle.add(v2); puzzle.add(o1); puzzle.add(a);
		
		
		//A questo punto si procede con l'ordinamento e la stampa del puzzle ordinato
		ISort sort = new ConcurrentSort();
		Piece[][] ordered = sort.sortPuzzle(puzzle);
		
		if(ordered == null) {
			System.out.println("Ci sono stati problemi nell'ordinamento.");
			return;
		}
		
		System.out.println("Stampa del puzzle ordinato:");
		for(int i = 0; i < ordered.length; i++) { 
			for(int j = 0; j < ordered[i].length; j++)
				System.out.print(ordered[i][j].getCharacter() + " ");
			System.out.println(System.getProperty("line.separator"));
		}
		
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Prova con un pezzo che formerebbe un loop di ricerca");
		
		/*
		 * questo pezzo viene sostituito a v1, con id_est cambiato in modo da puntare ad un pezzo
		 * precedente della stessa riga
		 */
		v1 = new Piece("v2 	s	v1 	r2 	v3 	o2");
		
		//riempimento del puzzle con il pezzo errato
		puzzle = new ArrayList<Piece>();
		puzzle.add(o); puzzle.add(r2); puzzle.add(a2); puzzle.add(p2); puzzle.add(v1); 
		puzzle.add(a1); puzzle.add(v); puzzle.add(o2); puzzle.add(r); puzzle.add(p);
		puzzle.add(r1); puzzle.add(p1); puzzle.add(v2); puzzle.add(o1); puzzle.add(a);
		
		//Stampe di errore ad indicare la localizzazione del problema
		Piece[][] nullPuzzle = sort.sortPuzzle(puzzle);
		
		if(nullPuzzle == null)
			System.out.println("Si possono notare appunto, oltre alle stampe di monitoraggio dei Thread, "
					+ "le stampe di segnalazione di errore.");
	}

}
