package puzzlesolver.piece;

public class Piece {
	private final String id; 			//ID del pezzo
	private final char character;		//carattere contenuto
	
	private final String n_id;			//\
	private final String e_id;			//- ID
	private final String s_id;			//- CARDINALI
	private final String w_id;			///
	
	//Costruttore completo
	public Piece(String id, char character, String n_id, String e_id, String s_id, String w_id) {	
		this.id = id; this.character = character; this.n_id = n_id; this.e_id = e_id; this.s_id = s_id; this.w_id = w_id;
	}
	
	//Costruttore con stringa unica, gia' controllata dal Parser
	public Piece(String fields) {
		String[] splitted = fields.split("\t");
		for(int i = 0; i < splitted.length; i++) {
			if(i == 1 && splitted[i] == " ")			
				continue;
			splitted[i] = splitted[i].trim();			
		}
		
		id = splitted[0];
		character = splitted[1].toCharArray()[0];
		n_id = splitted[2];
		e_id = splitted[3];
		s_id = splitted[4];
		w_id = splitted[5];
	}
	
	//Getter per ogni campo dato
	public String getId() {	return id; }
	public char getCharacter() { return character; }
	
	public String getNorth() { return n_id;	}
	public String getEast()  { return e_id; }
	public String getSouth() { return s_id;	}
	public String getWest()  { return w_id;	}
	
	//equals per ogni id
	public boolean equalsId(Piece other) {
		if(this.id.equals(other.id))
			return true;
		return false;
	}
	
	public boolean equalsNorth(Piece other) {
		if(this.n_id.equals(other.n_id))
			return true;
		return false;
	}
	public boolean equalsEast(Piece other) {
		if(this.e_id.equals(other.e_id))
			return true;
		return false;
	}
	public boolean equalsSouth(Piece other) {
		if(this.s_id.equals(other.s_id))
			return true;
		return false;
	}
	public boolean equalsWest(Piece other) {
		if(this.w_id.equals(other.w_id))
			return true;
		return false;
	}
	
	//boolean che ritornano true se l'ID adiacente nella posizione cardinale e' "VUOTO"
	public boolean borderNorth() {
		if(n_id.equals("VUOTO"))
			return true;
		return false;
	}
	public boolean borderEast() {
		if(e_id.equals("VUOTO"))
			return true;
		return false;
	}
	public boolean borderSouth() {
		if(s_id.equals("VUOTO"))
			return true;
		return false;
	}
	public boolean borderWest() {
		if(w_id.equals("VUOTO"))
			return true;
		return false;
	}
	
}