package dal.model;

public class Set {
	

	private int id;
	private int score1;
	private int score2;
	public int getId() {
		
		return this.id;
	}
	
	public void setId(int toBeSet) {
		
		this.id = toBeSet;
	}
	
	public int getScore1() {
		
		return this.score1;
	}
	
	public void setScore1(int score) {
		
		this.score1 = score;
	}
	
	public int getScore2() {
		
		return this.score2;
	}
	
	public void setScore2(int score) {
		
		this.score2 = score;
	}
	
	@Override
	public String toString() {
		
		return Integer.toString(id) + ": " + Integer.toString(score1) + " - " + Integer.toString(score2);
	}
}
