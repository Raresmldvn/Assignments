package model.dto;

public class MatchDTO {

	private int id;
	private PlayerDTO player1;
	private PlayerDTO player2;
	private SetDTO set1;
	private SetDTO set2;
	private SetDTO set3;
	private SetDTO set4;
	private SetDTO set5;
	
	public MatchDTO(PlayerDTO p1, PlayerDTO p2, SetDTO s1, SetDTO s2, SetDTO s3, SetDTO s4, SetDTO s5) {
		
		this.player1 = p1;
		this.player2 = p2;
		this.set1 = s1;
		this.set2 = s2;
		this.set3 = s3;
		this.set4 = s4;
		this.set5 = s5;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PlayerDTO getPlayer1() {
		return player1;
	}
	public void setPlayer1(PlayerDTO player1) {
		this.player1 = player1;
	}
	public PlayerDTO getPlayer2() {
		return player2;
	}
	public void setPlayer2(PlayerDTO player2) {
		this.player2 = player2;
	}
	public SetDTO getSet1() {
		return set1;
	}
	public void setSet1(SetDTO set1) {
		this.set1 = set1;
	}
	public SetDTO getSet2() {
		return set2;
	}
	public void setSet2(SetDTO set2) {
		this.set2 = set2;
	}
	public SetDTO getSet3() {
		return set3;
	}
	public void setSet3(SetDTO set3) {
		this.set3 = set3;
	}
	public SetDTO getSet4() {
		return set4;
	}
	public void setSet4(SetDTO set4) {
		this.set4 = set4;
	}
	public SetDTO getSet5() {
		return set5;
	}
	public void setSet5(SetDTO set5) {
		this.set5 = set5;
	}
}
