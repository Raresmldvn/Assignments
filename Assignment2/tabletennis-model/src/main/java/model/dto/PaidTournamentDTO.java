package model.dto;

public class PaidTournamentDTO {

	private int id;
	private TournamentDTO t;
	private float fee;
	
	public PaidTournamentDTO(float fee) {
		
		this.fee = fee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TournamentDTO getT() {
		return t;
	}

	public void setT(TournamentDTO t) {
		this.t = t;
	}

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

}
