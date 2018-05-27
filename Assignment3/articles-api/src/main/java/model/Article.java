package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Article implements Serializable{

	private static final long serialVersionUID = 1L;
	private String title;
	private String articleAbstract;
	private User author;
	private String body;
	private List<Article> relatedArticles;
	
	public Article() {
		
		this.relatedArticles = new ArrayList<Article>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArticleAbstract() {
		return articleAbstract;
	}

	public void setArticleAbstract(String articleAbstract) {
		this.articleAbstract = articleAbstract;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<Article> getRelatedArticles() {
		return relatedArticles;
	}

	public void setRelatedArticles(List<Article> relatedArticles) {
		this.relatedArticles = relatedArticles;
	}
	
	public void addRelatedArticle(Article article) {
		
		this.relatedArticles.add(article);
	}
	
	@Override
	public String toString() {
		
		return title + " " + articleAbstract + " " + author.getName();
	}
}
