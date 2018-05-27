package business;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;
import persistance.UsersPersistance;

public class UserModule {

	private UserList list;
	private ObjectMapper mapper;
	
	private static UserModule INSTANCE;
	public UserModule() {
		
		list = UserList.getInstance();
		mapper = new ObjectMapper();
	}
	
	public static UserModule getInstance() {
		
		if(INSTANCE==null) INSTANCE = new UserModule();
		return INSTANCE;
	}
	
	public User logIn(String serializedUser) {
		
		User user = new User();
		
		try {
			
			User readUser = mapper.readValue(serializedUser, User.class);
			User foundByEmail = findWithEmail(readUser.getEmail());
			if(foundByEmail==null) {
				
			} else {
				if(!foundByEmail.getPassword().equals(readUser.getPassword())) {
					user.setName(foundByEmail.getName());
					user.setPassword(null);
				} else {
				user = foundByEmail;
				}
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		return user;
	}
	private User findWithEmail(String email) {
		
		User user = null;
		
		List<User> users = list.getUsers();
		for(User U : users) {
			
			if(U.getEmail().equals(email))
				return U;
		}
		return user;
	}
	
	public List<User> getAllUsers() {
		
		return list.getUsers();
	}
	
	public User addUser(String serialized) {
		
		User u = null;
		try {
			u = mapper.readValue(serialized, User.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list.addUser(u);
		UsersPersistance.persistArticles(list.getUsers());
		return u;
	}
	
	public User deleteUser(String serialized) {
		
		User u = null;
		try {
			u = mapper.readValue(serialized, User.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User foundByEmail = findWithEmail(u.getEmail());
		list.getUsers().remove(foundByEmail);
		User emptyUser = new User();
		emptyUser.setName("Deleted");
		emptyUser.setEmail("Deleted");
		ArticleModule.getInstance().changeUser(foundByEmail, emptyUser);
		UsersPersistance.persistArticles(list.getUsers());
		return u;
	}
	
	public User updateUser(String serialized) {
		
		User u = null;
		try {
			u = mapper.readValue(serialized, User.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User foundByEmail = findWithEmail(u.getEmail());
		list.getUsers().remove(foundByEmail);
		list.getUsers().add(u);
		ArticleModule.getInstance().changeUser(foundByEmail, u);
		UsersPersistance.persistArticles(list.getUsers());
		return u;
	}
	
}
