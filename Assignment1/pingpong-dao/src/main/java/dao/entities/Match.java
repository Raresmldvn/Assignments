package dao.entities;

public class Match {

	private int id;
	private Player player1;
	private Player player2;
	private Set[] set_list;
	private Stage stage;
	
	public Match(int matchId, Player firstPlayer, Player secondPlayer, Stage stageV) {
		
		this.id = matchId;
		this.player1 = firstPlayer;
		this.player2 = secondPlayer;
		this.stage = stageV;
		this.set_list = new Set[5];
	}
	
	public Match(int matchId, Player firstPlayer, Player secondPlayer, Set[] set_list,Stage stageV) {
		
		this.id = matchId;
		this.player1 = firstPlayer;
		this.player2 = secondPlayer;
		this.stage = stageV;
		this.set_list = set_list;
	}
	public int getId() {
		
		return this.id;
	}
	
	public Player getFirstPlayer() {
		
		return this.player1;
	}
	
	public Player getSecondPlayer() {
		
		return this.player2;
	}
	public Set[] getSetList() {
		
		return this.set_list;
	}
	
	public Stage getStage() {
		
		return stage;
	}
	
	public void attachSet(Set toBeAttached, int number) {
		
		set_list[number-1] = toBeAttached;
	}
	
	@Override
	public String toString() {
		
		String result = Integer.toString(this.id) + " " + this.player1.toString() + " " + this.player2.toString() + "\n";
		for(int i=0; i<5; i++) 
			result = result + this.set_list[i].toString() + "\n";
		result = result + this.stage.toString();
		return result;
	}
}

