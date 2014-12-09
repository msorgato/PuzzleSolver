package puzzlesolver.piece;

public class Piece {
	private final String id;
	private final char character;
	
	private final String n_id;
	private final String e_id;
	private final String s_id;
	private final String w_id;
	
	//Costruttore completo
	public Piece(String id, char character, String n_id, String e_id, String s_id, String w_id) {	
		this.id = id; this.character = character; this.n_id = n_id; this.e_id = e_id; this.s_id = s_id; this.w_id = w_id;
	}
	
	//Costruttore con stringa unica, gi� controllata dal Parser
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
	
}
