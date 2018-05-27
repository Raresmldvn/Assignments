package dal.model;

import java.util.List;

public class Player {
	
	private int id;
	private String name;
	private String email;
	private String password;
	private boolean isAdmin;
	private float money;
	
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int pId) {
		
		this.id = pId;
	}
	
	public String getName() {
		
		return name;
	}
	
	public void setName(String pName) {
		
		this.name = pName;
	}
	
	public String getEmail() {
		
		return this.email;
	}
	
	public void setEmail(String pEmail) {
		
		this.email = pEmail;
	}
	
	public String getPassword() {
		
		return this.password;
	}
	
	public void setPassword(String pPassword) {
		
		this.password = pPassword;
	}
	
	public boolean getIsAdmin() {
		
		return this.isAdmin;
	}
	
	public void setIsAdmin(boolean i) {
		
		this.isAdmin = i;
	}
	
	public float getMoney() {
		
		return this.money;
	}
	
	public void setMoney(float amount) {
		
		this.money = amount;
	}
	
	
	@Override
	public String toString() {
		
		return Integer.toString(id) + ": " + name + " " + email + " " + password + " " + Boolean.toString(isAdmin) + " " + Float.toString(money) + "\n";
	}
}
