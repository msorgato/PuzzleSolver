package puzzlesolver.tests;

import java.util.ArrayList;
import java.util.List;

import puzzlesolver.piece.Piece;
import puzzlesolver.sort.ConcurrentSort;

public class FirstColumnTest {

	public static void main(String[] args) {
		Piece p = new Piece("p1	 p 	 VUOTO 	 r1 	 p2 	 VUOTO"), 
				r = new Piece("r1	r	VUOTO 	o1 	r2 	p1"), 
				o = new Piece("o1 	o 	VUOTO 	v1 	o2 	r1"), 
				v = new Piece("v1 	v	VUOTO 	a1 	v2 	o1"), 
				a = new Piece("a1 	a	VUOTO 	VUOTO 	a2 	v1"),
				p1 = new Piece("p2 	t	p1 	 r2 	 VUOTO 	 VUOTO"), 
				r1 = new Piece("r2	e	r1 	o2 	VUOTO 	p2"), 
				o1 = new Piece("o2 	s 	o1 	v2 	VUOTO 	r2"), 
				v1 = new Piece("v2 	t	v1 	a2 	VUOTO 	o2"), 
				a1 = new Piece("a2 	C	a1 	VUOTO 	VUOTO 	v2");
		
		ConcurrentSort sorter = new ConcurrentSort();
		
		//Appunto, dopo la creazione della lista, i vari pezzi vengono inseriti in ordine casuale.
		List<Piece> puzzle = new ArrayList<Piece>();
		puzzle.add(a); puzzle.add(v); puzzle.add(o); puzzle.add(r); puzzle.add(p);
		puzzle.add(r1); puzzle.add(p1); puzzle.add(v1); puzzle.add(o1); puzzle.add(a1);
		

		Piece[] firstColumn = sorter.getLeftBorder(puzzle);
		
		if(firstColumn == null) {
			System.out.println("Problemi nell'ordinamento della prima colonna");
			return;
		}
		
		System.out.println("Risultato dell'ottenimento della prima colonna per l'ordinamento:");
		
		for(int i = 0; i < firstColumn.length; i++)
			System.out.println(firstColumn[i].getCharacter());
		
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Prova con un puzzle di tipo Nx1 (7x1 in questo caso)");
		
		Piece z1 = new Piece("c1	c	VUOTO	VUOTO	o1	VUOTO"),
				z2 = new Piece("o1	o	c1	VUOTO	l1	VUOTO"),
				z3 = new Piece("l1	l	o1	VUOTO	o2	VUOTO"),
				z4 = new Piece("o2	o	l1	VUOTO	n1	VUOTO"),
				z5 = new Piece("n1	n	o2	VUOTO	n2	VUOTO"),
				z6 = new Piece("n2	n	n1	VUOTO	a1	VUOTO"),
				z7 = new Piece("a1	a	n2	VUOTO	VUOTO	VUOTO");
		List<Piece> colonna = new ArrayList<Piece>();
		
		colonna.add(z7); colonna.add(z6); colonna.add(z5); colonna.add(z4); colonna.add(z3); colonna.add(z2); colonna.add(z1);
		
		Piece[] colOrd = sorter.getLeftBorder(colonna);
		
		if(colOrd == null) {
			System.out.println("Problemi nell'ordinamento della prima colonna");
			return;
		}
		
		System.out.println("Risultato dell'ordinamento della colonna:");
		
		for(int i = 0; i < colOrd.length; i++)
			System.out.println(colOrd[i].getCharacter());
		
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Prova con un pezzo illecito che crea un loop nella ricerca");
		
		/*
		 * se ora proviamo a costruire una colonna con un pezzo illecito (che manderebbe in loop la ricerca, ad esempio),
		 * vedremo che il metodo ritorna un valore "null" che verrà opportunamente gestito.
		 */
		
		colonna = new ArrayList<Piece>();
		
		//elemento sostitutivo di z3, con id_sud errato, che punta ad un elemento precedente della colonna.
		Piece w3 = new Piece("l1	l	o1	VUOTO	o1	VUOTO");
		
		colonna.add(z7); colonna.add(z6); colonna.add(z5); colonna.add(z4); colonna.add(w3); colonna.add(z2); colonna.add(z1);
		
		Piece[] colNull = sorter.getLeftBorder(colonna);
		
		if(colNull == null) 
			System.out.println("Come previsto, il metodo ha ritornato 'null', dato che il pezzo avrebbe mandato "
					+ "in loop infinito la ricerca della prima colonna.");
	}

}
