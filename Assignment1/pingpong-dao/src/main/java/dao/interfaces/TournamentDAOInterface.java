package dao.interfaces;

import java.util.ArrayList;
import dao.entities.Tournament;

public interface TournamentDAOInterface {

	public Tournament findByAnything(String column, String value);
	public void insert(Tournament toBeInserted);
	public void insertMatch(int matchNr, int matchId);
	public void update(Tournament toBeUpdated);
	public ArrayList<Tournament> getAllTournaments();
	public void delete(int id);
}