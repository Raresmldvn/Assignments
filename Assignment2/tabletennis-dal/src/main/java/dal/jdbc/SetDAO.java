package dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dal.util.ConnectionFactory;
import dal.interfaces.SetDAOInterface;
import dal.model.Set;

public class SetDAO implements SetDAOInterface{

	private static final String insertStatement = "INSERT INTO `tabletennis`.set  (score1, score2) VALUES (?, ?)";
	private static final String selectStatement = "SELECT * FROM `tabletennis`.set WHERE id=?";
	private static final String deleteStatement = "DELETE FROM `tabletennis`.set WHERE id=?";
	private static final String updateStatement = "UPDATE `tabletennis`.set SET score1=?, score2=? WHERE id=?";
	
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
			
			int sc1 = resSet.getInt("score1");
			int sc2 = resSet.getInt("score2");;
			result = new Set();
			result.setId(id);
			result.setScore1(sc1);
			result.setScore2(sc2);
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
			insert.setInt(1, toBeInserted.getScore1());
			insert.setInt(2, toBeInserted.getScore2());
			insert.executeUpdate();
			ResultSet rs = insert.getGeneratedKeys();
			rs.next();
			index = rs.getInt(1);
		} catch(SQLException e) {

			System.out.println("SQL Error ss: " + e.getMessage() + e.getStackTrace());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(insert);
			ConnectionFactory.close(connection);
		}
		return index;
	}

	public void updateSet(Set toBeUpdated) {
		
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement update = null;
		ResultSet resSet = null;
		
		try {
			
			update = connection.prepareStatement(updateStatement);
			update.setInt(1, toBeUpdated.getScore1());
			update.setInt(2, toBeUpdated.getScore2());
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

}
