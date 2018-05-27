package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.Article;

public class ArticleList extends Observable {

	private List<Article> articles;
	private static ArticleList INSTANCE;
	
	public ArticleList() {
		
		this.articles = new ArrayList<Article>();
	}
	
	public static ArticleList getInstance() {
		
		if(INSTANCE==null) INSTANCE= new ArticleList();
		return INSTANCE;
	}
	
	public List<Article> getArticles() {
		
		return this.articles;
	}
	
	public void setArticles(List<Article> articles) {
		
		this.articles = articles;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void addArticle(Article article) {
		
		this.articles.add(article);
		this.setChanged();
		this.notifyObservers();
		
	}
}

