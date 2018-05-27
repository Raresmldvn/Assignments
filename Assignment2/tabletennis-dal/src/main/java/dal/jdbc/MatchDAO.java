package dal.jdbc;

import dal.util.ConnectionFactory;
import dal.interfaces.MatchDAOInterface;
import dal.model.Match;
import dal.model.Player;
import dal.model.Set;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class MatchDAO implements MatchDAOInterface{

	private static final String insertStatement = "INSERT INTO `tabletennis`.match (player1, player2, set1, set2, set3, set4, set5) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String selectStatement = "SELECT * FROM `tabletennis`.match WHERE id=?";
	private static final String deleteStatement = "DELETE FROM `tabletennis`.match WHERE id=?";
	private static final String updateStatement = "UPDATE `tabletennis`.match SET player1 = ?, player2 = ? WHERE id =?";
	
	public Match finById(int id) {
		Match result=null;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet resSet = null;
		
		try {
			
			findStatement = connection.prepareStatement(selectStatement);
			findStatement.setInt(1, id);
			resSet = findStatement.executeQuery();
			
			if(resSet.next()) {
			
			int p1 = resSet.getInt("player1");
			int p2 = resSet.getInt("player2");
			
			int s1 = resSet.getInt("set1");
			int s2 = resSet.getInt("set2");
			int s3 = resSet.getInt("set3");
			int s4 = resSet.getInt("set4");
			int s5 = resSet.getInt("set5");
			Player first, second;
			if(p1==0) {
				first = null;
			}
			else {
				first =  (new PlayerDAO()).findById(p1);
			}
			if(p2==0)
				second = null;
			else
				second = (new PlayerDAO()).findById(p2);
			result = new Match();
			result.setId(id);
			result.setPlayer1(first);
			result.setPlayer2(second);
			SetDAO setHelper = new SetDAO();
			result.setSet1(setHelper.findById(s1));
			result.setSet2(setHelper.findById(s2));
			result.setSet3(setHelper.findById(s3));
			result.setSet4(setHelper.findById(s4));
			result.setSet5(setHelper.findById(s5));
			}
		} catch(SQLException e) {

			System.out.println("SQL Error z: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(connection);
		}
		return result;
	}

	public int insertMatch(Match toBeInserted) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement insert = null;
		ResultSet resSet = null;
		int id = 0;
		try {
			
			insert = connection.prepareStatement(insertStatement);
			if(toBeInserted.getPlayer1()==null)	{
				
				insert.setNull(1, Types.INTEGER);
			} else {
				
				insert.setInt(1, toBeInserted.getPlayer1().getId());
			}
			if(toBeInserted.getPlayer2()==null) {
				insert.setNull(2, Types.INTEGER);
			} else {
				
				insert.setInt(2, toBeInserted.getPlayer2().getId());
			}
			SetDAO setHelper = new SetDAO();
			Set[] sets = new Set[5];
			
			for(int i=0; i<5; i++)  {
				Set s = new Set();
				s.setScore1(0);
				s.setScore2(0);
				int sId = setHelper.insertSet(s);
				s.setId(sId);
				sets[i] = s;
			}
			for(int i=3; i<=7; i++) {
				
				insert.setInt(i, sets[i-3].getId());
			}
			insert.executeUpdate();
			ResultSet rs = insert.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
		} catch(SQLException e) {

			System.out.println("SQL Error zz: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(insert);
			ConnectionFactory.close(connection);
		}
		return id;
	}

	public void updateMatch(Match toBeUpdated) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement update = null;
		ResultSet resSet = null;
		
		try {
			
			update = connection.prepareStatement(updateStatement);
			update.setInt(1, toBeUpdated.getPlayer1().getId());
			update.setInt(2, toBeUpdated.getPlayer2().getId());
			update.setInt(3, toBeUpdated.getId());
			update.executeUpdate();
		} catch(SQLException e) {

			System.out.println("SQL Error zzz: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(update);
			ConnectionFactory.close(connection);
		}
		
	}

	public void deleteMatch(int id) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement delete = null;
		ResultSet resSet = null;
		
		try {
			
			delete = connection.prepareStatement(deleteStatement);
			delete.setInt(1, id);
			delete.executeUpdate();
		} catch(SQLException e) {

			System.out.println("SQL Error zz: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(delete);
			ConnectionFactory.close(connection);
		}
		
	}

}
