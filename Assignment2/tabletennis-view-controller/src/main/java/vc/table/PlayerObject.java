package vc.table;

public class PlayerObject {

	private String name;
	private String email;
	private Float money;
	
	public PlayerObject(String n, String e, float m) {
		
		this.name = n;
		this.email = e;
		this.money = m;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	
}
