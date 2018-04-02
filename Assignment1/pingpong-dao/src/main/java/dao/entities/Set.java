package dao.entities;

public class Set {

	private int id;
	private int scorePlayer1;
	private int scorePlayer2;
	
	public Set(int id) {
		
		this.id = id;
		this.scorePlayer1 = 0;
		this.scorePlayer2 = 0;
	}
	
	public Set(int id, int p1, int p2) {
		
		this.id = id;
		this.scorePlayer1 = p1;
		this.scorePlayer2 = p2;
	}
	
	public void increaseScore(boolean first) {
		
		if(first==true) 
			this.scorePlayer1++;
		else
			this.scorePlayer2++;
	}
	
	public int getId() {
		
		return this.id;
	}
	public int getScore(boolean first) {
		if(first==true)
			return this.scorePlayer1;
		else
			return this.scorePlayer2;
	}
	
	@Override
	public String toString() {
		return Integer.toString(id) + " " + Integer.toString(scorePlayer1) + " " + Integer.toString(scorePlayer2);
	}
	
}

