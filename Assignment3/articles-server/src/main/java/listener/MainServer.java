package listener;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import business.ArticleList;
import business.ArticleModule;
import business.UserList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MainServer extends Thread{

	public static final int portNumber = 9990;
	public List<ClientConnection> connections;
	
	public static MainServer INSTANCE;
	
	public MainServer() {
		
		super();
		connections = new ArrayList<ClientConnection>();
		ArticleList.getInstance().setPersistentArticles();
		UserList.getInstance().setPersistentUsers();
	}
	
	public static MainServer getInstance() {
		
		if(INSTANCE==null) INSTANCE = new MainServer();
		return INSTANCE;
	}

	@Override
	public void run() {
		
		int clientNumber = 0;
		ServerSocket serverSocket = null;
		System.out.println("Starting server...");
		try {
			
			serverSocket = new ServerSocket(portNumber);
			System.out.println("Obtained server socket...");
			
			while(true) {
				
				Socket clientSocket = serverSocket.accept();
				clientNumber++;
				ClientConnection clientConnection = new ClientConnection(clientSocket, clientNumber);
				connections.add(clientConnection);
				System.out.println("Added client connection " + clientNumber + "...");
				clientConnection.start();
				
			}
		}catch(Exception e) {
			
			e.printStackTrace();
		} finally {
			
			if(serverSocket!=null) {
				
				try {
					serverSocket.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
	}
	
	public void notifyAllClients() {
		
		for(ClientConnection connection : connections) {
			
				System.out.println(connection.getID());
				connection.sendArticlesCommand();

		}
	}
	
	public void removeClientConnection(int ID) {
		
		ClientConnection c = null;
		for(ClientConnection connection : connections) {
			
			if(connection.getID()==ID) {
				c = connection;
				break;
			}
		}
		connections.remove(c);
	}
	
	public List<ClientConnection> getConnections() {
		
		return this.connections;
	}
	
}
