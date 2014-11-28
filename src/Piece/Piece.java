package Piece;

public class Piece {
	private String id;
	private char character;
	
	private String n_id;
	private String e_id;
	private String s_id;
	private String w_id;
	
	//Costruttore completo
	public Piece(String id, char character, String n_id, String e_id, String s_id, String w_id) {	
		this.id = id; this.character = character; this.n_id = n_id; this.e_id = e_id; this.s_id = s_id; this.w_id = w_id;
	}
	
	//Getter per ogni campo dato
	public String getId() {	return id; }
	public char getCharacter() { return character; }
	
	public String getNorth() { return n_id;	}
	public String getEast()  { return e_id; }
	public String getSouth() { return s_id;	}
	public String getWest()  { return w_id;	}
	
}
