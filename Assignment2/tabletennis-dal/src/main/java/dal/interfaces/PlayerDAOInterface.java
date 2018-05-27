package dal.interfaces;

import java.util.ArrayList;

import dal.model.Player;

public interface PlayerDAOInterface {

	public Player findByAnything(String column, String value);
	public void insertPlayer(Player toBeInserted);
	public void updatePlayer(Player toBeUpdated);
	public void deletePlayer(int id);
	public ArrayList<Player> getAllPlayers();
}

