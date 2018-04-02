package business.dto;

public class PlayerDTO {

	private String name;
	private String email;
	private String password;
	
	public PlayerDTO(String name, String email, String password) {
		
		this.name= name;
		this.email = email;
		this.password = password;
	}
	
	public String getPassword() {
		
		return password;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public String getEmail() {
		
		return this.email;
	}
	
}
