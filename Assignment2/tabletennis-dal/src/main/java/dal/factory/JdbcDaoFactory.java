package dal.factory;

import dal.interfaces.MatchDAOInterface;
import dal.interfaces.PaidTournamentDAOInterface;
import dal.interfaces.PlayerDAOInterface;
import dal.interfaces.SetDAOInterface;
import dal.interfaces.TournamentDAOInterface;
import dal.jdbc.*;
public class JdbcDaoFactory extends DaoFactory {

	@Override
	public TournamentDAOInterface getTournamentDao() {
		return new TournamentDAO();
	}

	@Override
	public PlayerDAOInterface getPlayerDao() {
		return new PlayerDAO();
	}

	@Override
	public MatchDAOInterface getMatchDao() {
		return new MatchDAO();
	}

	@Override
	public SetDAOInterface getSetDao() {
		return new SetDAO();
	}

	@Override
	public PaidTournamentDAOInterface getPaidTournamentDao() {
		return new PaidTournamentDAO();
	}

	
}
