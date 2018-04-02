package dao.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.entities.Set;
import dao.interfaces.SetDAOInterface;
public class SetDAO implements SetDAOInterface {

	private static final String insertStatement = "INSERT INTO `ping-pong`.set (set_id, score_p1, score_p2) VALUES (?, ?, ?)";
	private static final String selectStatement = "SELECT * FROM `ping-pong`.set WHERE set_id=?";
	private static final String deleteStatement = "DELETE FROM `ping-pong`.set WHERE set_id=?";
	private static final String updateStatement = "UPDATE `ping-pong`.set SET score_p1=?, score_p2=? WHERE set_id=?";

	public Set findById(int id) {
		
		Set result=null;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet resSet = null;
		
		try {
			
			findStatement = connection.prepareStatement(selectStatement);
			findStatement.setInt(1, id);
			resSet = findStatement.executeQuery();
			resSet.next();
			
			int sc1 = resSet.getInt("score_p1");
			int sc2 = resSet.getInt("score_p2");;
			result = new Set(id, sc1,sc2);
		} catch(SQLException e) {

			System.out.println("SQL Error s: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(connection);
		}
		return result;
	}
	
	public int insertSet(Set toBeInserted) {
		
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement insert = null;
		ResultSet resSet = null;
		int index = 0;
		try {
			
			insert = connection.prepareStatement(insertStatement);
			index = IdHelper.getNextId("set", "set_id");
			insert.setInt(1, index);
			insert.setInt(2, toBeInserted.getScore(true));
			insert.setInt(3, toBeInserted.getScore(false));
			insert.executeUpdate();
			
		} catch(SQLException e) {

			System.out.println("SQL Error ss: " + e.getMessage() + e.getStackTrace());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(insert);
			ConnectionFactory.close(connection);
		}
		return index;
	}
	
	public void deleteSet(int id) {
		
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement delete = null;
		ResultSet resSet = null;
		
		try {
			
			delete = connection.prepareStatement(deleteStatement);
			delete.setInt(1, id);
			delete.executeUpdate();
		} catch(SQLException e) {

			System.out.println("SQL Error sss: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(delete);
			ConnectionFactory.close(connection);
		}
	}
	
	public void updateSet(Set toBeUpdated) {
		
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement update = null;
		ResultSet resSet = null;
		
		try {
			
			update = connection.prepareStatement(updateStatement);
			update.setInt(1, toBeUpdated.getScore(true));
			update.setInt(2, toBeUpdated.getScore(false));
			update.setInt(3, toBeUpdated.getId());
			update.executeUpdate();
		} catch(SQLException e) {

			System.out.println("SQL Error: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(update);
			ConnectionFactory.close(connection);
		}
	}
}
