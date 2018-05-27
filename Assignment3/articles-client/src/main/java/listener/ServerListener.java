package listener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Observer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Article;
import model.ArticleList;
import model.User;
import model.UserList;

public class ServerListener extends Thread{

	private static final String saveArticleCommand = "saveArticle";
	private static final String logInCommand = "logIn";
	private static final String getArticlesCommand = "getArticles";
	private static final String updateOneArticleCommand = "updateArticle";
	private static final String deleteArticleCommand = "deleteArticle";
	public static final int portNumber = 9990;
	private static final String getUsersCommand = "getUsers";
	private static final String addUserCommand = "addUser";
	private static final String deleteUserCommand = "deleteUser";
	private static final String updateUserCommand = "updateUser";
	
	
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Socket socket;
	
	private static ServerListener INSTANCE;
	
	private ObjectMapper mapper;
	
	private ArticleList articleList;
	private UserList userList;
	public ServerListener() {
		
		super();
		mapper = new ObjectMapper();
		articleList = ArticleList.getInstance();
		userList = UserList.getInstance();
	}
	
	public static ServerListener getInstance() {
		
		if(INSTANCE==null) INSTANCE = new ServerListener();
		return INSTANCE;
	}
	private String received = "";
	private boolean stop = true;
	@Override
	public void run() {
		
		System.out.println("Starting client...");
		try {
			socket = new Socket("localhost",portNumber);
			System.out.println("Obtained client socket...");
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			System.out.println("Obtained streams" + inputStream + " " + outputStream);
			while(stop) {
				
				try {
				received = (String)inputStream.readObject();
				} catch(java.io.EOFException e) {
					
					continue;
				}
				String[] arguments = received.split("\n");
				if(arguments[0].equals("updateArticles")) {
				List<Article> list = (List<Article>) mapper.readValue(arguments[1], new TypeReference<List<Article>>(){});
				articleList.setArticles(list);
				outputStream.writeObject("OK");
				System.out.println("Received");
				received = "";
				}
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close() {
		
		stop = false;
	}
	public List<Article> getArticlesCommand() {
		
		String command = getArticlesCommand + "\n";
		List<Article> list = null;
		try {
			System.out.println(outputStream + " " + command);
			outputStream.writeObject(command);
			while(received.length()==0) {
				System.out.println("Gere");
			}
			String jsonList = received;
			list = (List<Article>) mapper.readValue(jsonList, new TypeReference<List<Article>>(){});
			articleList.setArticles(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		received = "";
		return list;
	}
	
	public User logInCommand(User user) {
		
		User returnedUser = null;
		String command = logInCommand + "\n";
		String jsonSend ="";
		try {
			jsonSend = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		command += jsonSend;
		try {
			outputStream.writeObject(command);
			while(received.length()==0) {System.out.println("LOGIN");}
			String jsonUser = received;
			returnedUser = mapper.readValue(jsonUser, User.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		received = "";
		return returnedUser;
	}
	
	public Article saveArticle(Article A) {
		
		Article returnedArticle = null;
		String command = saveArticleCommand + "\n";
		String jsonSend = "";
		try {
			jsonSend = mapper.writeValueAsString(A);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		command += jsonSend;
		try {
			outputStream.writeObject(command);
			while(received.length()==0) {System.out.println("SAVE");}
			String jsonArticle = (String)received;
			returnedArticle = mapper.readValue(jsonArticle, Article.class);
			System.out.println("Returnat: " + returnedArticle.getTitle());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		received = "";
		return returnedArticle;
	}
	
	public Article deleteArticle(Article A) {
		
		Article returnedArticle = null;
		String command = deleteArticleCommand + "\n";
		String jsonSend = "";
		try {
			jsonSend = mapper.writeValueAsString(A);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		command += jsonSend;
		try {
			outputStream.writeObject(command);
			while(received.length()==0) {System.out.println("DELETE");}
			String jsonArticle = (String)received;
			returnedArticle = mapper.readValue(jsonArticle, Article.class);
			System.out.println("Returnat: " + returnedArticle.getTitle());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		received = "";
		return returnedArticle;
	}
	
	
	public Article updateArticle(Article A, Article newA) {
		
		Article returnedArticle = null;
		String command = updateOneArticleCommand + "\n";
		String jsonSend = "";
		String jsonSend2 = "";
		try {
			jsonSend = mapper.writeValueAsString(A);
			jsonSend2 = mapper.writeValueAsString(newA);
			
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		command += jsonSend + "\n" + jsonSend2;
		try {
			outputStream.writeObject(command);
			while(received.length()==0) {System.out.println("Update problem");}
			String jsonArticle = (String)received;
			returnedArticle = mapper.readValue(jsonArticle, Article.class);
			System.out.println("Returnat: " + returnedArticle.getTitle());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		received = "";
		return returnedArticle;
	}
	
	public List<User> getUsersCommand() {
		
		String command = getUsersCommand + "\n";
		List<User> list = null;
		try {
			outputStream.writeObject(command);
			while(received.length()==0) {
				

			}
			String jsonList = received;
			list = (List<User>) mapper.readValue(jsonList, new TypeReference<List<User>>(){});
			System.out.println(jsonList);
			userList.setUsers(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		received = "";
		return list;
	}

	public User addUser(User user) {
		
		User returnedUser = null;
		String command = addUserCommand + "\n";
		String jsonSend ="";
		try {
			jsonSend = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		command += jsonSend;
		try {
			outputStream.writeObject(command);
			while(received.length()==0) {System.out.println("ADD USER");}
			String jsonUser = received;
			returnedUser = mapper.readValue(jsonUser, User.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		received = "";
		return returnedUser;
	}
	
	public User deleteUser(User user) {
		
		User returnedUser = null;
		String command = deleteUserCommand + "\n";
		String jsonSend ="";
		try {
			jsonSend = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		command += jsonSend;
		try {
			outputStream.writeObject(command);
			while(received.length()==0) {System.out.println("DELETE USER");}
			String jsonUser = received;
			returnedUser = mapper.readValue(jsonUser, User.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		received = "";
		return returnedUser;
	}
	
	public User updateUser(User user) {
		
		User returnedUser = null;
		String command = updateUserCommand + "\n";
		String jsonSend ="";
		try {
			jsonSend = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		command += jsonSend;
		try {
			outputStream.writeObject(command);
			while(received.length()==0) {System.out.println("UPDATE USER");}
			String jsonUser = received;
			returnedUser = mapper.readValue(jsonUser, User.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		received = "";
		return returnedUser;
	}
	
	public void sendClose() {
		
		String command = "close" + "\n";
		try {
			outputStream.writeObject(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
