package dal.factory;

import dal.interfaces.*;
public abstract class DaoFactory {


	public enum Type {
		HIBERNATE,
		JDBC;
	}


	protected DaoFactory(){

	}
	
	public static DaoFactory getInstance(Type factoryType) {
		switch (factoryType) {
			case HIBERNATE:
				return new HibernateDaoFactory();
			case JDBC:
				return new JdbcDaoFactory();
			default:
				throw new IllegalArgumentException("Invalid factory");
		}
	}

	public abstract TournamentDAOInterface getTournamentDao();

	public abstract PlayerDAOInterface getPlayerDao();
	
	public abstract MatchDAOInterface getMatchDao();
	
	public abstract SetDAOInterface getSetDao();
	
	public abstract PaidTournamentDAOInterface getPaidTournamentDao();
}
