package dal.model;


public class Match {

	private int id;
	private Player player1;
	private Player player2;
	private Set set1;
	private Set set2;
	private Set set3;
	private Set set4;
	private Set set5;
	private Tournament tournament;
	
	public int getId() {
		
		return this.id;
	}
	
	public void setId(int toBeSet) {
		
		this.id = toBeSet;
	}
	
	public Player getPlayer1() {
		
		return this.player1;
	}
	
	public void setPlayer1(Player P) {
		
		this.player1 = P;
	}
	
	public Player getPlayer2() {
		
		return this.player2;
	}
	
	public void setPlayer2(Player P) {
		
		this.player2= P;
	}
	
	public Set getSet1() {
		
		return this.set1;
	}
	
	public void setSet1(Set S) {
		
		this.set1 = S;
	}
	
	public Set getSet2() {
		
		return this.set2;
	}
	
	public void setSet2(Set S) {
		
		this.set2 = S;
	}
	
	public Set getSet3() {
		
		return this.set3;
	}
	
	public void setSet3(Set S) {
		
		this.set3 = S;
	}
	
	public Set getSet4() {
		
		return this.set4;
	}
	
	public void setSet4(Set S) {
		
		this.set4 = S;
	}
	
	public Set getSet5() {
		
		return this.set5;
	}
	
	public void setSet5(Set S) {
		
		this.set5 = S;
	}
	
	public Tournament getTournament() {
		
		return this.tournament;
	}
	
	public void setTournament(Tournament t) {
		
		this.tournament = t;
	}
	
	public String toString() {
		String result="";
		result += Integer.toString(id);
		result += player1.toString() + "\n" + player2.toString();
		result += "\n" + set1.toString() + "\n" + set2.toString() + "\n" + set3.toString() + "\n" + set4.toString() + "\n" + set5.toString();
		return result;
	}
}
