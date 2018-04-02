package business.modules;

import dao.entities.Player;
import dao.interfaces.PlayerDAOInterface;
import dao.access.PlayerDAO;

import java.util.ArrayList;

import business.dto.PlayerDTO;

public class PlayerModule {

	private static final int admin_code = 0;
	private static final int user_code = 1;
	private static final int error_code_username = -1;
	private static final int error_code_password = -2;
	
	private Player currentPlayer;
	private PlayerDAOInterface playerDAO;
	
	public PlayerModule() {
		
		this.playerDAO = new PlayerDAO();
	}
	
	public int logIn(String username, String password) {
		
		Player resultedPlayer = playerDAO.findByAnything("email",username);
		if(resultedPlayer==null)
			return error_code_username;
		if(!(resultedPlayer.getPassword().equals(password)))
			return error_code_password;
		else {
			PlayerHelper.playerName = resultedPlayer.getName();
			this.currentPlayer = resultedPlayer;
			if(resultedPlayer.isAdmin()==true) {
				PlayerHelper.playerName = "Admin";
				return admin_code;
			}
			else
				return user_code;
		}
	}
	
	public PlayerDTO getCurrentPlayer() {
		
		return new PlayerDTO(currentPlayer.getName(), currentPlayer.getEmail(), currentPlayer.getPassword());
	}
	
	public ArrayList<PlayerDTO> getAllPlayers() {
		
		ArrayList<PlayerDTO> players = new ArrayList<PlayerDTO>();
		ArrayList<Player> playersFromDomain  = playerDAO.getAllPlayers();
		for(Player player: playersFromDomain) {
			
			players.add(new PlayerDTO(player.getName(), player.getEmail(), player.getPassword()));
		}
		return players;
	}
	
	public void createPlayer(String name, String email, String password) {
		
		playerDAO.insertPlayer(new Player(1, name, email, password, false));
	}
	
	public void updatePlayer(PlayerDTO updated) {
		Player P = playerDAO.findByAnything("email", updated.getEmail());
		playerDAO.updatePlayer(new Player(P.getId(), updated.getName(), updated.getEmail(), updated.getPassword(), P.isAdmin()));
	}
	
	public PlayerDTO getPlayerBy(String field, String value) {
		
		Player P = playerDAO.findByAnything(field, value);
		if(P==null)
			return null;
		return new PlayerDTO(P.getName(), P.getEmail(), P.getPassword());
	}
	
	public void deletePlayer(String name) {
		
		Player P = playerDAO.findByAnything("name", name);
		playerDAO.deletePlayer(P.getId());
	}

}
