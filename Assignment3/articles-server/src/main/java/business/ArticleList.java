package business;

import java.util.ArrayList;
import java.util.List;

import model.Article;
import persistance.ArticlesPersistance;

public class ArticleList {

	private List<Article> articles;
	private static ArticleList INSTANCE;
	
	public ArticleList() {
		
		this.articles = new ArrayList<Article>();
	}
	public static ArticleList getInstance() {
		
		if(INSTANCE==null) INSTANCE= new ArticleList();
		return INSTANCE;
	}
	
	public void setPersistentArticles() {
		
		this.articles = ArticlesPersistance.getPersistantArticles();
	}
	public List<Article> getArticles() {
		
		return this.articles;
	}
	
	public void setArticles(List<Article> articles) {
		
		this.articles = articles;
	}
	
	public void addArticle(Article article) {
		
		this.articles.add(article);
	}
}
