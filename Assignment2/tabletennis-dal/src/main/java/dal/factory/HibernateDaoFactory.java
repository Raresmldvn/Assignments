package dal.factory;

import dal.interfaces.MatchDAOInterface;
import dal.interfaces.PaidTournamentDAOInterface;
import dal.interfaces.PlayerDAOInterface;
import dal.interfaces.SetDAOInterface;
import dal.interfaces.TournamentDAOInterface;

import dal.hibernate.*;
public class HibernateDaoFactory extends DaoFactory {

	@Override
	public TournamentDAOInterface getTournamentDao() {
		return new HibernateTournamentDAO();
	}

	@Override
	public PlayerDAOInterface getPlayerDao() {
		return new HibernatePlayerDAO();
	}

	@Override
	public MatchDAOInterface getMatchDao() {
		return new HibernateMatchDAO();
	}

	@Override
	public SetDAOInterface getSetDao() {
		return new HibernateSetDAO();
	}

	@Override
	public PaidTournamentDAOInterface getPaidTournamentDao() {
		return new HibernatePaidTournamentDAO();
	}

}
