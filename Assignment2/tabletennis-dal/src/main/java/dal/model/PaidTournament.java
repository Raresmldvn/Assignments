package dal.model;

public class PaidTournament {

	int id;
	private Tournament tournament;
	private float fee;
	
	
	public int getId() {
		
		return this.id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
	public Tournament getTournament() {
		
		return this.tournament;
	}
	
	public void setTournament(Tournament t) {
		
		this.tournament = t;
	}
	
	public float getFee() {
		
		return this.fee;
	}
	
	public void setFee(float f) {
		
		this.fee = f;
	}
}
