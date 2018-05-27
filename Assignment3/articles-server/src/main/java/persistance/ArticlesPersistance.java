package persistance;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Article;

public class ArticlesPersistance {

	
	private static ObjectMapper mapper = new ObjectMapper();
	public static void persistArticles(List<Article> articleList) {
		
		try {
			mapper.writeValue(new File("articles.json"), articleList);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Article>  getPersistantArticles() {
		
		List<Article> articles  = null;
		try {
			articles = (List<Article>) mapper.readValue(new File("articles.json"), new TypeReference<List<Article>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return articles;
	}
}
