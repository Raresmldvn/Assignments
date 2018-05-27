package persistance;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;

public class UsersPersistance {

	private static ObjectMapper mapper = new ObjectMapper();
	public static void persistArticles(List<User> userList) {
		
		try {
			mapper.writeValue(new File("users.json"), userList);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<User>  getPersistantUsers() {
		
		List<User> users  = null;
		try {
			users = (List<User>) mapper.readValue(new File("users.json"), new TypeReference<List<User>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return users;
	}
}
