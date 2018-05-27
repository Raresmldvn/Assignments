package listener;

import java.net.Socket;
import java.net.SocketException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import business.ArticleModule;
import business.UserModule;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ClientConnection extends Thread {

	private static final String saveArticleCommand = "saveArticle";
	private static final String logInCommand = "logIn";
	private static final String getArticlesCommand = "getArticles";
	private static final String updateArticlesCommand = "updateArticles";
	private static final String updateOneArticleCommand = "updateArticle";
	private static final String deleteArticleCommand = "deleteArticle";
	private static final String closeCommand = "close";
	private static final String getUsersCommand = "getUsers";
	private static final String addUserCommand = "addUser";
	private static final String deleteUserCommand = "deleteUser";
	private static final String updateUserCommand = "updateUser";
	
	private Socket socket;
	private int ID;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private UserModule userModule;
	private ArticleModule articleModule;
	private ObjectMapper mapper;
	
	public ClientConnection(Socket connection, int ID) {
		
		super();
		this.socket = connection;
		this.ID = ID;
		this.userModule = UserModule.getInstance();
		this.articleModule = ArticleModule.getInstance();
		mapper = new ObjectMapper();
	}

	private String received = "";
	private boolean stop = true;
	@Override
	public void run() {
		
		try {
			
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			
			while(stop) {
				
				System.out.println("Astept...");
				try {
				received = (String)inputStream.readObject();
				}catch(java.net.SocketException e) {
					
					stop = false;
					MainServer.getInstance().removeClientConnection(this.ID);
				}
				System.out.println(received);
				if(received.equals("OK")==false) {
					boolean x = decodeMessageAndAction(received);
					if(x==true) 
						MainServer.getInstance().notifyAllClients();
				} 
			}
			
		} catch(Exception e) {
			
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
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public int getID() {
		return ID;
	}

	public void setId(int id) {
		this.ID = id;
	}

	public ObjectInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ObjectInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ObjectOutputStream getOutStream() {
		return outputStream;
	}

	public void setOutStream(ObjectOutputStream outStream) {
		this.outputStream = outStream;
	}
	
	public boolean decodeMessageAndAction(String message) {
		
		try {
		String[] arguments = message.split("\n");
		if(arguments[0].equals(logInCommand)) {
			
			String serialized = mapper.writeValueAsString(userModule.logIn(arguments[1]));
			outputStream.writeObject(serialized);
			return false;
		}
		if(arguments[0].equals(getArticlesCommand)) {
			
			String serialized = mapper.writeValueAsString(articleModule.getAllArticles());
			outputStream.writeObject(serialized);
			return false;
		}
		if(arguments[0].equals(getUsersCommand)) {
			
			String serialized = mapper.writeValueAsString(userModule.getAllUsers());
			outputStream.writeObject(serialized);
			return false;
		}
		if(arguments[0].equals(saveArticleCommand)) {
			String serialized = mapper.writeValueAsString(articleModule.articleSave(arguments[1]));
			outputStream.writeObject(serialized);
			return true;
		}
		if(arguments[0].equals(deleteArticleCommand)) {
			
			String serialized = mapper.writeValueAsString(articleModule.deleteArticle(arguments[1]));
			outputStream.writeObject(serialized);
			return true;
		}
		if(arguments[0].equals(updateOneArticleCommand)) {
			
			String serialized = mapper.writeValueAsString(articleModule.updateArticle(arguments[1], arguments[2]));
			outputStream.writeObject(serialized);
			return true;
		}
		if(arguments[0].equals(addUserCommand)) {
			
			String serialized = mapper.writeValueAsString(userModule.addUser(arguments[1]));
			outputStream.writeObject(serialized);
			return false;
		}
		if(arguments[0].equals(deleteUserCommand)) {
			
			String serialized = mapper.writeValueAsString(userModule.deleteUser(arguments[1]));
			outputStream.writeObject(serialized);
			return true;
		}
		if(arguments[0].equals(updateUserCommand)) {
			
			String serialized = mapper.writeValueAsString(userModule.updateUser(arguments[1]));
			outputStream.writeObject(serialized);
			return true;
		}
		
		if(arguments[0].equals("close")) {
			stop = false;
			MainServer.getInstance().removeClientConnection(this.ID);
		}
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	public void sendArticlesCommand() {
		
		String command = updateArticlesCommand + "\n";
		String serialized="";
		try {
			serialized = mapper.writeValueAsString(articleModule.getAllArticles());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		command += serialized;
		try {
			outputStream.writeObject(command);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		received = "";
	}
	
}
