package dal.hibernate;

import dal.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dal.interfaces.PaidTournamentDAOInterface;
import dal.interfaces.TournamentDAOInterface;
import dal.model.PaidTournament;
import dal.model.Tournament;

public class HibernatePaidTournamentDAO  implements PaidTournamentDAOInterface{

	public PaidTournament findById(int id) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Query query = currentSession.createQuery("from PaidTournament where tournamentId=:idParameter").setLong("idParameter", id);
		
		PaidTournament tournament = (PaidTournament) query.uniqueResult();
		transaction.commit();
		return tournament;
	}

	public void insert(PaidTournament toBeInserted) {

		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.persist(toBeInserted);
		transaction.commit();
	}

	public void update(PaidTournament toBeUpdated) {

		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.update(toBeUpdated);
		transaction.commit();
	}

	public ArrayList<PaidTournament> getAllTournaments() {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();	
		Query query = currentSession.createQuery("from PaidTournament");
		List<PaidTournament> tournaments = (List<PaidTournament>)query.list();
		ArrayList<PaidTournament> finalList = new ArrayList<PaidTournament>();
		for(PaidTournament T : tournaments) {
			
			finalList.add(T);
		}
		transaction.commit();
		return finalList;
	}

	public void delete(int id) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();		
		currentSession.createQuery("delete from PaidTournament where tournamentId=:idParameter").setLong("idParameter", id).executeUpdate();
		transaction.commit();
	}



}
