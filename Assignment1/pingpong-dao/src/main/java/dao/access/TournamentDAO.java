package dao.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import dao.entities.Match;
import dao.entities.Player;
import dao.entities.Tournament;
import dao.interfaces.TournamentDAOInterface;

public class TournamentDAO implements TournamentDAOInterface {

	private static final String insertStatement = "INSERT INTO tournament (tournament_id, name) VALUES (?, ?)";
	private static final String deleteStatement = "DELETE FROM tournament WHERE tournament_id=?";
	private static final String updateStatement = "UPDATE tournament SET name=?, m1=?, m2=?, m3=?, m4=?, m5=?, m6=?, m7=? WHERE tournament_id=?";
	private static final String findAll = "SELECT * FROM tournament";
	
	public Tournament findByAnything(String column, String name) {
		
		String selectStatement = "SELECT * FROM tournament WHERE " + column + "=?";
		
		Tournament result = null;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet resSet = null;
		
		try {
			
			findStatement = connection.prepareStatement(selectStatement);
			findStatement.setString(1, name);
			resSet = findStatement.executeQuery();
			resSet.next();
			
			int id = resSet.getInt("tournament_id");
			result = new Tournament(id, name);
			int[] matches = new int[7];
			for(int i=0; i<7; i++) {
			
					matches[i] = resSet.getInt("m" + Integer.toString(i+1));
			}
			MatchDAO matchFinder = new MatchDAO();
			for(int i=0; i<7; i++) {
				
				if(matches[i]!=0) {
					Match m = matchFinder.finById(matches[i]);
					result.addMatch(m, i);
				} 
			}
			
		} catch(SQLException e) {

			System.out.println("SQL Error 1: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(connection);
		}
		return result;
	}
	
	public void insert(Tournament toBeInserted) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement insert = null;
		ResultSet resSet = null;
		
		try {
			
			insert = connection.prepareStatement(insertStatement);
			insert.setInt(1, IdHelper.getNextId("tournament", "tournament_id"));
			insert.setString(2, toBeInserted.getName());
			insert.executeUpdate();
		} catch(SQLException e) {

			System.out.println("SQL Error 2: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(insert);
			ConnectionFactory.close(connection);
		}
	}
	
	public void insertMatch(int matchNr, int matchId) {
		
		String insertString  = "UPDATE tournament SET m" + Integer.toString(matchNr) + "=?";
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement insert = null;
		ResultSet resSet = null;
		
		try {
			
			insert = connection.prepareStatement(insertString);
			insert.setInt(1, matchId);
			insert.executeUpdate();
		} catch(SQLException e) {

			System.out.println("SQL Error 3: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(insert);
			ConnectionFactory.close(connection);
		}
		
	}
	public void update(Tournament toBeUpdated) {
		
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement update = null;
		
		try {
			update = connection.prepareStatement(updateStatement);
			update.setString(1, toBeUpdated.getName());
			Match[] matches = toBeUpdated.getMatches();
			for(int i=0; i<7; i++) {
				if(matches[i]!=null) {
				update.setInt(i+2, matches[i].getId());
				} else {
					
					update.setNull(i+2, Types.INTEGER);
				}
			}
			update.setInt(9, toBeUpdated.getId());
			update.executeUpdate();
		}catch(SQLException e) {
			
			System.out.println("SQL Error 4: " + e.getMessage());
		}
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
	
	
	
	public ArrayList<Tournament> getAllTournaments() {
		
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement find = null;
		ResultSet resSet = null;
		ArrayList<Tournament> listOfPlayers = new ArrayList<Tournament>();
		
		try {
			
			find = connection.prepareStatement(findAll);
			resSet = find.executeQuery();
			while(resSet.next()) {
				
				
				String name = resSet.getString("name");
				int id = resSet.getInt("tournament_id");
				int[] matches = new int[7];
				for(int i=0; i<7; i++) {
					
					matches[i] = resSet.getInt("m" + Integer.toString(i+1));
				}
				Tournament result = new Tournament(id, name);
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
}

