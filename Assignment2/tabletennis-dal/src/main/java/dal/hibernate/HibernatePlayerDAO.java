package dal.hibernate;

import dal.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import dal.interfaces.PlayerDAOInterface;
import dal.model.Player;

import org.hibernate.Query;
import org.hibernate.Session;

import org.hibernate.Transaction;
public class HibernatePlayerDAO implements PlayerDAOInterface{

	public Player findByAnything(String column, String value) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		Query query = currentSession.createQuery("from Player where " + column + "=:value");
		query.setString("value", value);
		Player player = (Player)query.uniqueResult();
		transaction.commit();
		return player;
	}

	public void insertPlayer(Player toBeInserted) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.save(toBeInserted);
		transaction.commit();
		
	}

	public void updatePlayer(Player toBeUpdated) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.update(toBeUpdated);
		transaction.commit();
	}

	public void deletePlayer(int id) {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();		
		currentSession.createQuery("delete from Player where id=:idParameter").setLong("idParameter", id).executeUpdate();
		transaction.commit();
		//currentSession.close();
		
	}

	public ArrayList<Player> getAllPlayers() {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = currentSession.beginTransaction();	
		Query query = currentSession.createQuery("from Player");
		List<Player> players = query.list();
		ArrayList<Player> finalList = new ArrayList<Player>();
		for(Player P : players) {
			
			finalList.add(P);
		}
		transaction.commit();
		return finalList;
	}

}
