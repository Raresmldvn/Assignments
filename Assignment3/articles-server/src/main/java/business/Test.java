package business;

import model.Article;
import model.User;
import persistance.ArticlesPersistance;
import persistance.UsersPersistance;

import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import listener.MainServer;

public class Test {

	public static void main(String args[]) {
		
		MainServer server = MainServer.getInstance();
		server.start();
	}
}
