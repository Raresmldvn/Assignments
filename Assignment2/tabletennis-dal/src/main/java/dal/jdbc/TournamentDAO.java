package dal.jdbc;

import dal.util.ConnectionFactory;

import java.util.ArrayList;
import java.util.Date;

import dal.interfaces.TournamentDAOInterface;
import dal.model.Match;
import dal.model.Tournament;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class TournamentDAO implements TournamentDAOInterface{

	private static final String insertStatement = "INSERT INTO tournament (name, startDate, isPaid) VALUES (?, ?, ?)";
	private static final String deleteStatement = "DELETE FROM tournament WHERE id=?";
	private static final String updateStatement = "UPDATE tournament SET name=?, startDate=?, isPaid=?,  m1=?, m2=?, m3=?, m4=?, m5=?, m6=?, m7=? WHERE id=?";
	private static final String findAll = "SELECT * FROM tournament";
	
	public Tournament findByAnything(String column, String value) {
		String selectStatement = "SELECT * FROM tournament WHERE " + column + "=?";
		
		Tournament result = null;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet resSet = null;
		MatchDAO matchHelper = new MatchDAO();
		try {
			
			findStatement = connection.prepareStatement(selectStatement);
			findStatement.setString(1, value);
			resSet = findStatement.executeQuery();
			resSet.next();
			
			String name = resSet.getString("name");
			int id = resSet.getInt("id");
			Date d = resSet.getDate("startDate");
			boolean isPaid = resSet.getBoolean("isPaid");
			
			
			int[] matches = new int[7];
			for(int i=0; i<7; i++) {
				
				matches[i] = resSet.getInt("m" + Integer.toString(i+1));
			}
			
			result =  new Tournament();
			result.setId(id);
			result.setIsPaid(isPaid);
			result.setStartDate(d);
			result.setName(name);
			result.setM1(matchHelper.finById(matches[0]));
			result.setM2(matchHelper.finById(matches[1]));
			result.setM3(matchHelper.finById(matches[2]));
			result.setM4(matchHelper.finById(matches[3]));
			result.setM5(matchHelper.finById(matches[4]));
			result.setM6(matchHelper.finById(matches[5]));
			result.setM7(matchHelper.finById(matches[6]));
			
		} catch(SQLException e) {

			System.out.println("SQL Error 1: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(connection);
		}
		return result;
	}

	public Tournament findById(int id) {
		
		String selectStatement = "SELECT * FROM tournament WHERE id=?";
		
		Tournament result = null;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet resSet = null;
		MatchDAO matchHelper = new MatchDAO();
		try {
			
			findStatement = connection.prepareStatement(selectStatement);
			findStatement.setInt(1, id);
			resSet = findStatement.executeQuery();
			resSet.next();
			
			String name = resSet.getString("name");
			id = resSet.getInt("id");
			Date d = resSet.getDate("startDate");
			boolean isPaid = resSet.getBoolean("isPaid");
			
			
			int[] matches = new int[7];
			for(int i=0; i<7; i++) {
				
				matches[i] = resSet.getInt("m" + Integer.toString(i+1));
			}
			
			result =  new Tournament();
			result.setId(id);
			result.setIsPaid(isPaid);
			result.setStartDate(d);
			result.setName(name);
			result.setM1(matchHelper.finById(matches[0]));
			result.setM2(matchHelper.finById(matches[1]));
			result.setM3(matchHelper.finById(matches[2]));
			result.setM4(matchHelper.finById(matches[3]));
			result.setM5(matchHelper.finById(matches[4]));
			result.setM6(matchHelper.finById(matches[5]));
			result.setM7(matchHelper.finById(matches[6]));
			
		} catch(SQLException e) {

			System.out.println("SQL Error 1: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(connection);
		}
		return result;
	}
	public int insert(Tournament toBeInserted) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement insert = null;
		ResultSet resSet = null;
		int id = 0;
		try {
			
			insert = connection.prepareStatement(insertStatement);
			insert.setString(1,toBeInserted.getName());
			insert.setDate(2, new java.sql.Date(toBeInserted.getStartDate().getTime()));
			insert.setBoolean(3, toBeInserted.getIsPaid());
			insert.executeUpdate();
			ResultSet rs = insert.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
		} catch(SQLException e) {

			System.out.println("SQL Error 2: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(insert);
			ConnectionFactory.close(connection);
		}
		return id;
		
	}

	public void insertMatch(int tournamentId, int matchNr, int matchId) {
		String insertString  = "UPDATE tournament SET m" + Integer.toString(matchNr) + "=? + WHERE id=?";
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement insert = null;
		ResultSet resSet = null;
		
		try {
			
			insert = connection.prepareStatement(insertString);
			insert.setInt(1, matchId);
			insert.setInt(2, tournamentId);
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
			update.setTimestamp(2, new java.sql.Timestamp(toBeUpdated.getStartDate().getTime()));
			update.setBoolean(3, toBeUpdated.getIsPaid());
			
			Match M1 = toBeUpdated.getM1();
			if(M1==null)
				update.setNull(4, Types.INTEGER);
			else
				update.setInt(4, M1.getId());
			
			Match M2 = toBeUpdated.getM2();
			if(M2==null)
				update.setNull(5, Types.INTEGER);
			else
				update.setInt(5, M2.getId());
			
			Match M3 = toBeUpdated.getM3();
			if(M3==null)
				update.setNull(6, Types.INTEGER);
			else
				update.setInt(6, M3.getId());
			
			Match M4 = toBeUpdated.getM4();
			if(M4==null)
				update.setNull(7, Types.INTEGER);
			else
				update.setInt(7, M4.getId());
			
			Match M5 = toBeUpdated.getM5();
			if(M5==null)
				update.setNull(8, Types.INTEGER);
			else
				update.setInt(8, M5.getId());
			
			Match M6 = toBeUpdated.getM6();
			if(M6==null)
				update.setNull(9, Types.INTEGER);
			else
				update.setInt(9, M6.getId());
			
			Match M7 = toBeUpdated.getM7();
			if(M7==null)
				update.setNull(10, Types.INTEGER);
			else
				update.setInt(10, M7.getId());
			update.setInt(11, toBeUpdated.getId());
			update.executeUpdate();
		}catch(SQLException e) {
			
			System.out.println("SQL Error 4: " + e.getMessage());
		}
		
	}

	public ArrayList<Tournament> getAllTournaments() {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement find = null;
		ResultSet resSet = null;
		ArrayList<Tournament> listOfPlayers = new ArrayList<Tournament>();
		MatchDAO matchHelper = new MatchDAO();
		try {
			
			find = connection.prepareStatement(findAll);
			resSet = find.executeQuery();
			while(resSet.next()) {
				
				
				String name = resSet.getString("name");
				int id = resSet.getInt("id");
				Date d = resSet.getDate("startDate");
				boolean isPaid = resSet.getBoolean("isPaid");
				
				
				int[] matches = new int[7];
				for(int i=0; i<7; i++) {
					
					matches[i] = resSet.getInt("m" + Integer.toString(i+1));
				}
				
				Tournament result =  new Tournament();
				result.setId(id);
				result.setIsPaid(isPaid);
				result.setStartDate(d);
				result.setName(name);
				result.setM1(matchHelper.finById(matches[0]));
				result.setM2(matchHelper.finById(matches[1]));
				result.setM3(matchHelper.finById(matches[2]));
				result.setM4(matchHelper.finById(matches[3]));
				result.setM5(matchHelper.finById(matches[4]));
				result.setM6(matchHelper.finById(matches[5]));
				result.setM7(matchHelper.finById(matches[6]));
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
