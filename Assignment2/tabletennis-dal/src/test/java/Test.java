
import dal.model.*;
import dal.interfaces.*;
import dal.jdbc.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dal.hibernate.*;

public class Test {

	public static void main(String args[]) {
		
		PlayerDAOInterface playerDAO = new HibernatePlayerDAO();
		MatchDAOInterface matchDAO = new HibernateMatchDAO();
		TournamentDAOInterface tournamentDAO = new HibernateTournamentDAO();
		PaidTournamentDAOInterface pDAO = new HibernatePaidTournamentDAO();
		Tournament t = tournamentDAO.findByAnything("name", "Name");
		tournamentDAO.delete(t.getId());
	}
	
}
