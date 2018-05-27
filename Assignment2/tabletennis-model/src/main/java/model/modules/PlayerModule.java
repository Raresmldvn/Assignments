package model.modules;

import java.util.ArrayList;
import java.util.Observable;

import dal.factory.DaoFactory;
import dal.interfaces.PlayerDAOInterface;
import dal.model.Player;
import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.PlayerDTO;
public class PlayerModule extends Observable{

	private static final int admin_code = 0;
	private static final int user_code = 1;
	private static final int error_code_username = -1;
	private static final int error_code_password = -2;
	
	private Player currentPlayer;
	private PlayerDAOInterface playerDAO;
	private static PlayerModule INSTANCE;
	
	public PlayerModule() {
		
		DaoConfiguration config = new DaoConfiguration();
		config.setFactory();
		this.playerDAO = config.getFactory().getPlayerDao();
	}
	
	public void setCurrentPlayer() {
		
		Player resultedPlayer = playerDAO.findByAnything("name",PlayerHelper.currentPlayer.getName());
		currentPlayer = resultedPlayer;
		PlayerHelper.currentPlayer = new PlayerDTO(currentPlayer.getName(), currentPlayer.getEmail(), currentPlayer.getPassword(), currentPlayer.getMoney(), currentPlayer.getIsAdmin());
	}
	public int logIn(String username, String password) {
		
		Player resultedPlayer = playerDAO.findByAnything("email",username);
		if(resultedPlayer==null)
			return error_code_username;
		if(!(resultedPlayer.getPassword().equals(password)))
			return error_code_password;
		else {
			this.currentPlayer = resultedPlayer;
			PlayerHelper.currentPlayer = new PlayerDTO(currentPlayer.getName(), currentPlayer.getEmail(), currentPlayer.getPassword(), currentPlayer.getMoney(), currentPlayer.getIsAdmin());
			this.currentPlayer = resultedPlayer;
			if(resultedPlayer.getIsAdmin()==true) {
				return admin_code;
			}
			else
				return user_code;
		}
	}
	
	public PlayerDTO getCurrentPlayer() {
		
		return new PlayerDTO(currentPlayer.getName(), currentPlayer.getEmail(), currentPlayer.getPassword(), currentPlayer.getMoney(), currentPlayer.getIsAdmin());
	}
	
	public ArrayList<PlayerDTO> getAllPlayers() {
		
		ArrayList<PlayerDTO> players = new ArrayList<PlayerDTO>();
		ArrayList<Player> playersFromDomain  = playerDAO.getAllPlayers();
		for(Player player: playersFromDomain) {
			
			players.add(new PlayerDTO(player.getName(), player.getEmail(), player.getPassword(), player.getMoney(), player.getIsAdmin()));
		}
		return players;
	}
	
	public void createPlayer(String name, String email, String password) {
		
		Player P = new Player();
		P.setName(name);
		P.setEmail(email);
		P.setPassword(password);
		P.setMoney((float)0);
		P.setIsAdmin(false);
		getPlayers();
		playerDAO.insertPlayer(P);
		this.setChanged();
		this.notifyObservers();
	}
	
	public void updatePlayer(PlayerDTO updated) {
		Player P = playerDAO.findByAnything("email", updated.getEmail());
		P.setName(updated.getName());
		P.setEmail(updated.getEmail());
		P.setPassword(updated.getPassword());
		P.setMoney(updated.getMoney());
		playerDAO.updatePlayer(P);
		this.setChanged();
		this.notifyObservers();
	}
	
	public PlayerDTO getPlayerBy(String field, String value) {
		
		Player P = playerDAO.findByAnything(field, value);
		if(P==null)
			return null;
		return new PlayerDTO(P.getName(), P.getEmail(), P.getPassword(), P.getMoney(), P.getIsAdmin());
	}
	
	public void deletePlayer(String name) {

		Player P = playerDAO.findByAnything("name", name);
		playerDAO.deletePlayer(P.getId());
		this.setChanged();
		this.notifyObservers();
	}

	 public static PlayerModule getInstance() {
	        if (INSTANCE == null) INSTANCE = new PlayerModule();
	        return INSTANCE;
	    }
	 
	 
	 private ObservableList<PlayerDTO> players;
	 public ObservableList<PlayerDTO> getPlayers() {
	        players = FXCollections.observableArrayList(getAllPlayers());
	        return players;
	    }
	 
}
