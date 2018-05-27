package dal.jdbc;

import dal.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dal.interfaces.PaidTournamentDAOInterface;
import dal.model.PaidTournament;

public class PaidTournamentDAO implements PaidTournamentDAOInterface {
	private static final String insertStatement = "INSERT INTO paidtournament (tournamentId, fee) VALUES (?, ?)";
	private static final String deleteStatement = "DELETE FROM paidtournament WHERE id=?";
	private static final String updateStatement = "UPDATE paidtournament SET tournamentid=?, fee=? WHERE id=?";
	private static final String findAll = "SELECT * FROM paidtournament";
	private static final String findID = "SELECT * FROM paidtournament where tournamentid=?";
	
	public PaidTournament findById(int id) {
		
		PaidTournament result=null;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet resSet = null;
		
		try {
			
			findStatement = connection.prepareStatement(findID);
			findStatement.setInt(1, id);
			resSet = findStatement.executeQuery();
			resSet.next();
			
			int tid = resSet.getInt("id");
			float fee = resSet.getFloat("fee");
			result = new PaidTournament();
			result.setId(tid);
			result.setFee(fee);
			result.setTournament((new TournamentDAO()).findById(id));
		} catch(SQLException e) {

			System.out.println("SQL Error s: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(connection);
		}
		return result;
	}

	public void insert(PaidTournament toBeInserted) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement insert = null;
		ResultSet resSet = null;
		
		try {
			
			insert = connection.prepareStatement(insertStatement);
			insert.setInt(1, toBeInserted.getTournament().getId());
			insert.setFloat(2, toBeInserted.getFee());
			insert.executeUpdate();
		} catch(SQLException e) {

			System.out.println("SQL Error 2: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(insert);
			ConnectionFactory.close(connection);
		}
		
	}

	public void update(PaidTournament toBeUpdated) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement insert = null;
		ResultSet resSet = null;
		
		try {
			
			insert = connection.prepareStatement(updateStatement);
			insert.setInt(1, toBeUpdated.getTournament().getId());
			insert.setFloat(2, toBeUpdated.getFee());
			insert.setInt(3,toBeUpdated.getId());
			insert.executeUpdate();
		} catch(SQLException e) {

			System.out.println("SQL Error 2: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(insert);
			ConnectionFactory.close(connection);
		}
		
	}

	public ArrayList<PaidTournament> getAllTournaments() {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement find = null;
		ResultSet resSet = null;
		ArrayList<PaidTournament> listOfPlayers = new ArrayList<PaidTournament>();
		try {
			
			find = connection.prepareStatement(findAll);
			resSet = find.executeQuery();
			while(resSet.next()) {
				
				
				int tid = resSet.getInt("id");
				float fee = resSet.getFloat("fee");
				int id = resSet.getInt("tournamentId");
				PaidTournament result = new PaidTournament();
				result.setId(tid);
				result.setFee(fee);
				result.setTournament((new TournamentDAO()).findById(id));
				listOfPlayers.add(result);
			}
			
		} catch(SQLException e) {

			System.out.println("SQL Error 6: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(find);
			ConnectionFactory.close(connection);
		}
		return listOfPlayers;
	}

	public void delete(int id) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement delete = null;
		ResultSet resSet = null;
		
		try {
			
			delete = connection.prepareStatement(deleteStatement);
			delete.setInt(1, id);
			delete.executeUpdate();
		} catch(SQLException e) {

			System.out.println("SQL Error 5: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(delete);
			ConnectionFactory.close(connection);
		}
		
	}

	
}
