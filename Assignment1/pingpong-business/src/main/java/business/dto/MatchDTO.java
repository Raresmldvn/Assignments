package business.dto;

public class MatchDTO {

	int id;
	private String name1;
	private String name2;
	private int stage;
	
	private SetDTO sets[];
	public MatchDTO(int id,String n1, String n2, SetDTO[] s, int stage) {
		
		this.id = id;
		name1= n1;
		name2 = n2;
		this.sets = s;
		this.stage = stage;
	}
	
	public String getNamePlayer1() {
		
		return name1;
	}
	
	public String getNamePlayer2() {
		
		return name2;
	}
	
	public SetDTO[] getSets() {
		
		return this.sets;
	}
	
	public int getStage() {
		
		return this.stage;
	}
	
	public int getId() {
		
		return id;
	}
}
