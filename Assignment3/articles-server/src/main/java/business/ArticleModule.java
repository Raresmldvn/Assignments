package business;

import java.util.List;

import model.Article;
import model.User;
import persistance.ArticlesPersistance;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ArticleModule {

	private ArticleList list;
	
	private static ArticleModule INSTANCE;
	
	private ObjectMapper mapper;
	public ArticleModule() {
		
		list = ArticleList.getInstance();
		mapper = new ObjectMapper();
	}
	
	public static ArticleModule getInstance() {
		
		if(INSTANCE==null) INSTANCE = new ArticleModule();
		return INSTANCE;
	}
	
	public Article articleSave(String serializedArticle) {
		
		Article article = null;
		
		try {
			article = mapper.readValue(serializedArticle, Article.class);
			saveArticle(article);
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return article;
	}
	public void saveArticle(Article article) {
		
		list.addArticle(article);
		ArticlesPersistance.persistArticles(list.getArticles());
	}
	
	public List<Article> getAllArticles() {
		
		return list.getArticles();
	}
	
	public Article deleteArticle(String serializedArticle) {
		

		Article article = null;
		try {
			article = mapper.readValue(serializedArticle, Article.class);
			Article toBeDeleted = findByTitle(article);
			list.getArticles().remove(toBeDeleted);
			deleteInAllRelated(article, new Article());
			ArticlesPersistance.persistArticles(list.getArticles());
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		return article;
		
	}
	
	public Article updateArticle(String serializedArticle, String serializedUpdated) {
		

		Article article = null;
		Article newArticle = null;
		try {
			article = mapper.readValue(serializedArticle, Article.class);
			newArticle = mapper.readValue(serializedUpdated, Article.class);
			Article toBeDeleted = findByTitle(article);
			newArticle.setRelatedArticles(article.getRelatedArticles());
			list.getArticles().remove(toBeDeleted);
			list.getArticles().add(newArticle);
			
			updateInAllRelated(article, newArticle);
			ArticlesPersistance.persistArticles(list.getArticles());
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		return article;
		
	}
	
	private void deleteInAllRelated(Article toBeUpdated, Article updated) {
		
		for(Article article : list.getArticles()) {
			
			deleteFromRelatedList(false, article.getRelatedArticles(), toBeUpdated, updated);
		}
	}
	
	private void updateInAllRelated(Article toBeUpdated, Article updated) {
		
		for(Article article : list.getArticles()) {
			
			deleteFromRelatedList(true, article.getRelatedArticles(), toBeUpdated, updated);
		}
	}
	
	private void deleteFromRelatedList(boolean updateOrDelete,List<Article> related, Article toBeUpdated, Article updated) {
		
		for(Article article : related) {
			
			if(article.getTitle().equals(toBeUpdated.getTitle())) {
				
				related.remove(article);
				if(updateOrDelete==true) {
					
					related.add(updated);
				}
			}
			break;
		}
	}
	public Article findByTitle(Article A) {
		
		for(Article article : list.getArticles()) {
			
			if(article.getTitle().equals(A.getTitle()))
				return article;
		}
		return null;
	}
	
	public void changeUser(User oldUser, User newUser) {
		
		for(Article article : list.getArticles()) {
			if(article.getAuthor().getEmail()!=null&&article.getAuthor().getEmail().equals(oldUser.getEmail())) {
				System.out.println(article.getAuthor().getEmail());
				System.out.println(newUser.getName());
				article.setAuthor(newUser);
			}
		}
		ArticlesPersistance.persistArticles(list.getArticles());
	}
}


