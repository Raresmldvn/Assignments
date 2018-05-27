package dal.hibernate;

import dal.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dal.interfaces.MatchDAOInterface;
import dal.model.Match;
import dal.model.Set;

public class HibernateMatchDAO implements MatchDAOInterface{

	public Match finById(int matchId) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Match match = (Match)currentSession.get(Match.class, matchId);
		transaction.commit();
		return match;
	}

	public int insertMatch(Match toBeInserted) {
		HibernateSetDAO setHelper = new HibernateSetDAO();
		Set[] sets = new Set[5];
		
		for(int i=0; i<5; i++)  {
			Set s = new Set();
			s.setScore1(0);
			s.setScore2(0);
			int sId = setHelper.insertSet(s);
			s.setId(sId);
			sets[i] = s;
		}
		toBeInserted.setSet1(sets[0]);
		toBeInserted.setSet2(sets[1]);
		toBeInserted.setSet3(sets[2]);
		toBeInserted.setSet4(sets[3]);
		toBeInserted.setSet5(sets[4]);
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.persist(toBeInserted);
		transaction.commit();
		return toBeInserted.getId();
	}

	public void updateMatch(Match toBeUpdated) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.update(toBeUpdated);
		transaction.commit();
		
	}

	public void deleteMatch(int id) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();		
		currentSession.createQuery("delete from Match where id= :idParameter").setLong("idParameter", id).executeUpdate();
		transaction.commit();
		
	}

}
