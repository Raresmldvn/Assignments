package dao.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import dao.entities.Match;
import dao.entities.Player;
import dao.entities.Set;
import dao.entities.Stage;
import dao.interfaces.MatchDAOInterface;
public class MatchDAO implements MatchDAOInterface {

	private static final String insertStatement = "INSERT INTO `ping-pong`.match (match_id, player1_id, player2_id, set1_id, set2_id, set3_id, set4_id, set5_id, stage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String selectStatement = "SELECT * FROM `ping-pong`.match WHERE match_id=?";
	private static final String deleteStatement = "DELETE FROM `ping-pong`.match WHERE match_id=?";
	private static final String updateStatement = "UPDATE `ping-pong`.match SET player1_id = ?, player2_id = ? WHERE match_id =?";
	
	
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
			
			int p1 = resSet.getInt("player1_id");
			int p2 = resSet.getInt("player2_id");
			int stage = resSet.getInt("stage");
			Stage stageType;
			if(stage==1) 
				stageType = Stage.QF;
			else if (stage==2)
				stageType = Stage.SF;
			else
				stageType = Stage.F;
			
			int s1 = resSet.getInt("set1_id");
			int s2 = resSet.getInt("set2_id");
			int s3 = resSet.getInt("set3_id");
			int s4 = resSet.getInt("set4_id");
			int s5 = resSet.getInt("set5_id");
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
			result = new Match(id,first, second, stageType);
			SetDAO setHelper = new SetDAO();
			result.attachSet(setHelper.findById(s1), 1);
			result.attachSet(setHelper.findById(s2), 2);
			result.attachSet(setHelper.findById(s3), 3);
			result.attachSet(setHelper.findById(s4), 4);
			result.attachSet(setHelper.findById(s5), 5);
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
			id = IdHelper.getNextId("match", "match_id");
			insert.setInt(1, id);
			if(toBeInserted.getFirstPlayer()==null)	{
				
				insert.setNull(2, Types.INTEGER);
			} else {
				
				insert.setInt(2, toBeInserted.getFirstPlayer().getId());
			}
			if(toBeInserted.getSecondPlayer()==null) {
				insert.setNull(3, Types.INTEGER);
			} else {
				
				insert.setInt(3, toBeInserted.getSecondPlayer().getId());
			}
			SetDAO setHelper = new SetDAO();
			Set[] sets = new Set[5];
			for(int i=0; i<5; i++)
				sets[i] = new Set(setHelper.insertSet(new Set(1, 0, 0)), 0, 0);
			
			for(int i=4; i<=8; i++) {
				
				insert.setInt(i, sets[i-4].getId());
			}
			if(toBeInserted.getStage()==Stage.QF) {
				insert.setInt(9, 1);
			}else if (toBeInserted.getStage()==Stage.SF)
				insert.setInt(9, 2);
			else
				insert.setInt(9, 3);
			insert.executeUpdate();
		} catch(SQLException e) {

			System.out.println("SQL Error zz: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(insert);
			ConnectionFactory.close(connection);
		}
		return id;
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
	
	public void updateMatch(Match toBeUpdated) {
		
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement update = null;
		ResultSet resSet = null;
		
		try {
			
			update = connection.prepareStatement(updateStatement);
			update.setInt(1, toBeUpdated.getFirstPlayer().getId());
			update.setInt(2, toBeUpdated.getSecondPlayer().getId());
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
}
