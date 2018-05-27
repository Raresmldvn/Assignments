package model;

import java.util.ArrayList;
import java.util.List;

public class UserList {

	private List<User> users;
	private static UserList INSTANCE;
	
	public UserList() {
		
		this.users = new ArrayList<User>();
	}
	
	public static UserList getInstance() {
		
		if(INSTANCE==null) INSTANCE = new UserList();
		return INSTANCE;
	}
	public List<User> getUsers() {
		
		return users;
	}
	
	public void setUsers(List<User> users) {
		
		this.users = users;
	}
	
	public void addUser(User user) {
		
		this.users.add(user);
	}
}
