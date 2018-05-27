package dal.hibernate;

import dal.util.HibernateUtil;
import dal.interfaces.SetDAOInterface;
import dal.model.Set;
import org.hibernate.*;

public class HibernateSetDAO implements SetDAOInterface{

	public Set findById(int id) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Set set = (Set)currentSession.get(Set.class, id);
		transaction.commit();
		return set;
	}

	public int insertSet(Set toBeInserted) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.save(toBeInserted);
		transaction.commit();
		return toBeInserted.getId();
	}

	public void updateSet(Set toBeUpdated) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.update(toBeUpdated);
		transaction.commit();
		
	}

	public void deleteSet(int id) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();		
		currentSession.createQuery("delete from tabletennis.set where id= :idParameter").setLong("idParameter", id).executeUpdate();
		transaction.commit();
		
	}

}
