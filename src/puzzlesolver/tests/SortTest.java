package puzzlesolver.tests;

import java.util.ArrayList;
import java.util.List;

import puzzlesolver.piece.Piece;
import puzzlesolver.sort.ISort;
import puzzlesolver.sort.SequentialSort;

public class SortTest {

	public static void main(String[] args) {
		Piece p = new Piece("p1 	 p 	 VUOTO 	 r1 	 VUOTO 	 VUOTO"), 
				r = new Piece("r1	r	VUOTO 	o1 	VUOTO 	p1"), 
				o = new Piece("o1 	o 	VUOTO 	v1 	VUOTO 	r1"), 
				v = new Piece("v1 	v	VUOTO 	a1 	VUOTO 	o1"), 
				a = new Piece("a1 	a	VUOTO 	VUOTO 	VUOTO 	v1");
		System.out.println(p.getCharacter());
		/*List<Piece> puzzle = new ArrayList<Piece>();
		puzzle.add(p); puzzle.add(r); puzzle.add(o); puzzle.add(v); puzzle.add(a);
		ISort sort = new SequentialSort();
		sort.sortPuzzle(puzzle);*/

	}

}
