package vc.table;

public class TournamentObject {

	private String name;
	private String type;
	private Float fee;
	private String startDate;
	
	
	public TournamentObject(String name, String type, float fee, String startDate) {
		super();
		this.name = name;
		this.type = type;
		this.fee = fee;
		this.startDate = startDate;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Float getFee() {
		return fee;
	}


	public void setFee(Float fee) {
		this.fee = fee;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	
}
