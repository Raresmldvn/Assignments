package dal.hibernate;

import dal.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dal.interfaces.TournamentDAOInterface;
import dal.jdbc.MatchDAO;
import dal.model.Match;
import dal.model.Player;
import dal.model.Tournament;

public class HibernateTournamentDAO implements TournamentDAOInterface {

	public Tournament findByAnything(String column, String value) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Query query = currentSession.createQuery("from Tournament where " + column + "=:value");
		query.setString("value", value);
		Tournament tournament = (Tournament)query.uniqueResult();
		transaction.commit();
		return tournament;
	}

	public int insert(Tournament toBeInserted) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.persist(toBeInserted);
		transaction.commit();
		return 0;
		
	}

	public void insertMatch(int tournamentId, int matchNr, int matchId) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Query query = currentSession.createQuery("update Tournament set m" + Integer.toString(matchNr) + " =:id1 where id=:id2");
		query.setInteger("id1", matchId);
		query.setInteger("id2", tournamentId);
		query.executeUpdate();
		transaction.commit();
	}

	public void update(Tournament toBeUpdated) {
		
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.update(toBeUpdated);
		transaction.commit();
	}

	public ArrayList<Tournament> getAllTournaments() {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();	
		Query query = currentSession.createQuery("from Tournament");
		List<Tournament> tournaments = (List<Tournament>)query.list();
		ArrayList<Tournament> finalList = new ArrayList<Tournament>();
		for(Tournament T : tournaments) {
			
			finalList.add(T);
		}
		transaction.commit();
		return finalList;
	}
	
	public Tournament findById(int id) {
		
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Tournament tournament = (Tournament)currentSession.get(Tournament.class, id);
		transaction.commit();
		return tournament;
	}
	
	public void delete(int id) {
		Tournament t = findById(id);
		if(t.getIsPaid()==true)
			(new HibernatePaidTournamentDAO()).delete(id);
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		MatchDAO matchHelper = new MatchDAO();
		if(t.getM1()!=null)
			matchHelper.deleteMatch(t.getM1().getId());
		if(t.getM2()!=null)
			matchHelper.deleteMatch(t.getM2().getId());
		if(t.getM3()!=null)
			matchHelper.deleteMatch(t.getM3().getId());
		if(t.getM4()!=null)
			matchHelper.deleteMatch(t.getM4().getId());
		if(t.getM5()!=null)
			matchHelper.deleteMatch(t.getM5().getId());
		if(t.getM6()!=null)
			matchHelper.deleteMatch(t.getM6().getId());
		if(t.getM7()!=null)
			matchHelper.deleteMatch(t.getM7().getId());
		currentSession.createQuery("delete from Tournament where id=:idParameter").setLong("idParameter", id).executeUpdate();
		transaction.commit();
		
	}

}
