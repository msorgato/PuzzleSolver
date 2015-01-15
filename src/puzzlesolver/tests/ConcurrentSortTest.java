package puzzlesolver.tests;

import java.util.ArrayList;
import java.util.List;

import puzzlesolver.piece.Piece;
import puzzlesolver.sort.ISort;
import puzzlesolver.sort.ConcurrentSort;

public class ConcurrentSortTest {

	public static void main(String[] args) {
		/*Piece p = new Piece("p1	 p 	 VUOTO 	 r1 	 p2 	 VUOTO"), 
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
			System.out.println("HA RITORNATO NULL");
			return;
		}
		
		for(int i = 0; i < ordered.length; i++) { 
			for(int j = 0; j < ordered[i].length; j++)
				System.out.print(ordered[i][j].getCharacter() + " ");
			System.out.println(System.getProperty("line.separator"));
		}*/
		
		//prova a singola colonna
		ISort sort = new ConcurrentSort();
		Piece z1 = new Piece("c1	c	VUOTO	VUOTO	o1	VUOTO"),
				z2 = new Piece("o1	o	c1	VUOTO	l1	VUOTO"),
				z3 = new Piece("l1	l	o1	VUOTO	o2	VUOTO"),
				z4 = new Piece("o2	o	l1	VUOTO	n1	VUOTO"),
				z5 = new Piece("n1	n	o2	VUOTO	n2	VUOTO"),
				z6 = new Piece("n2	n	n1	VUOTO	a1	VUOTO"),
				z7 = new Piece("a1	a	n2	VUOTO	VUOTO	VUOTO");
		List<Piece> colonna = new ArrayList<Piece>();
		colonna.add(z7); colonna.add(z6); colonna.add(z5); colonna.add(z4); colonna.add(z3); colonna.add(z2); colonna.add(z1); 
		Piece[][] risultato = sort.sortPuzzle(colonna);
		
		if(risultato == null) {
			System.out.println("RISULTATO E' NULL");
			return;
		}
		
		for(int i = 0; i < risultato.length; i++) { 
			for(int j = 0; j < risultato[i].length; j++)
				System.out.print(risultato[i][j].getCharacter() + " ");
			System.out.println(System.getProperty("line.separator"));
		}
	}

}
