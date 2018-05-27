package dal.interfaces;

import java.util.ArrayList;

import dal.model.Tournament;

public interface TournamentDAOInterface {

	public Tournament findByAnything(String column, String value);
	public int insert(Tournament toBeInserted);
	public void insertMatch(int tournamentId, int matchNr, int matchId);
	public void update(Tournament toBeUpdated);
	public ArrayList<Tournament> getAllTournaments();
	public void delete(int id);
}
