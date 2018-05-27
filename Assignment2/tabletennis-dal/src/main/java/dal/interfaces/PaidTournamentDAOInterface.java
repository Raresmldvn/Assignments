package dal.interfaces;

import java.util.ArrayList;

import dal.model.*;

public interface PaidTournamentDAOInterface {

	public PaidTournament findById(int id);
	public void insert(PaidTournament toBeInserted);
	public void update(PaidTournament toBeUpdated);
	public ArrayList<PaidTournament> getAllTournaments();
	public void delete(int id);
}
