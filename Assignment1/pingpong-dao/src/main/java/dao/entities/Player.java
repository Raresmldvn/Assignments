package dao.entities;

public class Player {

	private int id;
	private String name;
	private String email;
	private String password;
	private boolean isAdmin;
	
	public Player(int playerId, String playerName, String playerEmail, String playerPassword, boolean admin) {
		
		this.id = playerId;
		this.name = playerName;
		this.email = playerEmail;
		this.password = playerPassword;
		this.isAdmin = admin;
	}
	
	public int getId() {
		
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		
		return email;
	}
	
	public boolean isAdmin() {
		
		return this.isAdmin;
	}
	public String getPassword() {
		
		return password;
	}
	
	
	@Override
	public String toString() {
		
		return Integer.valueOf(id).toString() + " " + name + " " + email + " " + password;
	}
	
}


