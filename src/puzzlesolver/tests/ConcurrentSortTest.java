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
				p1 = new Piece("p2 	 	p1 	 r2 	 VUOTO 	 VUOTO"), 
				r1 = new Piece("r2	t	r1 	o2 	VUOTO 	p2"), 
				o1 = new Piece("o2 	e 	o1 	v2 	VUOTO 	r2"), 
				v1 = new Piece("v2 	s	v1 	a2 	VUOTO 	o2"), 
				a1 = new Piece("a2 	t	a1 	VUOTO 	VUOTO 	v2");
		
		
		//Appunto, dopo la creazione della lista, i vari pezzi vengono inseriti in ordine casuale.
		List<Piece> puzzle = new ArrayList<Piece>();
		puzzle.add(a); puzzle.add(v); puzzle.add(o); puzzle.add(r); puzzle.add(p);
		puzzle.add(r1); puzzle.add(p1); puzzle.add(v1); puzzle.add(o1); puzzle.add(a1);
		
		List<Piece> lista = new ArrayList<Piece>(5);
		System.out.println("Grandezza di lista: " + lista.size());
		
		//A questo punto si procede con l'ordinamento e la stampa del puzzle ordinato
		ISort sort = new ConcurrentSort();
		Piece[][] ordered = sort.sortPuzzle(puzzle);

	}

}
