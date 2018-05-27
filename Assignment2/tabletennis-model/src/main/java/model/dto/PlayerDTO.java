package model.dto;

public class PlayerDTO {

	private int id;
	private String name;
	private String email;
	private String password;
	private boolean isAdmin;
	private float money;
	
	
	public PlayerDTO(String n, String e, String p, float m, boolean admin) {
		
		this.name = n;
		this.email = e;
		this.password = p;
		this.money = m;
		this.isAdmin = admin;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
}
