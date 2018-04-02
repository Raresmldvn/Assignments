package business.dto;

public class SetDTO {

	private int id;
	private int scoreP1;
	private int scoreP2;
	
	public SetDTO(int id,int s1, int s2) {
		
		this.id = id;
		this.scoreP1 = s1;
		this.scoreP2 = s2;
	}
	
	public int getScore1() {
		
		return scoreP1;
	}
	
	public int getScore2() {
		
		return scoreP2;
	}
	
	public int getId() {
		
		return id;
	}
}