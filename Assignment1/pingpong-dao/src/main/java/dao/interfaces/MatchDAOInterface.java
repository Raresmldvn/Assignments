package dao.interfaces;

import dao.entities.Match;

public interface MatchDAOInterface {

	public Match finById(int matchId);
	public int insertMatch(Match toBeInserted);
	public void updateMatch(Match toBeUpdated);
	public void deleteMatch(int id);
}
