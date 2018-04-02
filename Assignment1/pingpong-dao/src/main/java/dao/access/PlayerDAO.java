package dao.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.entities.Player;
import dao.interfaces.PlayerDAOInterface;
public class PlayerDAO implements PlayerDAOInterface {
	
	private static final String insertStatement = "INSERT INTO player (id, name, email, password, is_admin) VALUES (?, ?, ?, ?, ?)";
	private static final String selectStatement = "SELECT * FROM player WHERE email=?";
	private static final String selectStatementById = "SELECT * FROM player WHERE id=?";
	private static final String deleteStatement = "DELETE FROM player WHERE id=?";
	private static final String updateStatement = "UPDATE player SET name = ?, email = ?, password =? WHERE id=?";
	private static final String selectAll = "SELECT * FROM player";
	public Player findByEmail(String email) {
		
		Player result=null;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet resSet = null;
		
		try {
			
			findStatement = connection.prepareStatement(selectStatement);
			findStatement.setString(1, email);
			resSet = findStatement.executeQuery();
			if(!resSet.next())
				return null;
			
			int id = resSet.getInt("id");
			String name = resSet.getString("name");
			String password = resSet.getString("password");
			boolean is_admin = resSet.getBoolean("is_admin");
			result = new Player(id, name, email, password,is_admin);
		} catch(SQLException e) {

			System.out.println("SQL Error p: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(connection);
		}
		return result;
	}
	
	
	public void insertPlayer(Player toBeInserted) {
		
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement insert = null;
		ResultSet resSet = null;
		
		try {
			
			insert = connection.prepareStatement(insertStatement);
			insert.setInt(1, IdHelper.getNextId("player", "id"));
			//insert.setInt(1, 3);
			insert.setString(2, toBeInserted.getName());
			insert.setString(3, toBeInserted.getEmail());
			insert.setString(4, toBeInserted.getPassword());
			insert.setBoolean(5, toBeInserted.isAdmin());
			insert.executeUpdate();
		} catch(SQLException e) {

			System.out.println("SQL Error pp: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(insert);
			ConnectionFactory.close(connection);
		}
	}
	
	public Player findById(int id) {
		
		Player result=null;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet resSet = null;
		
		try {
			
			findStatement = connection.prepareStatement(selectStatementById);
			findStatement.setInt(1,id);
			resSet = findStatement.executeQuery();
			if(!(resSet.next()))
				return null;
			
			String name = resSet.getString("name");
			String email = resSet.getString("email");
			String password = resSet.getString("password");
			boolean isAdmin = resSet.getBoolean("is_admin");
			result = new Player(id, name, email, password, isAdmin);
		} catch(SQLException e) {

			System.out.println("SQL Error ppp: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(connection);
		}
		return result;
	}
	public void updatePlayer(Player toBeUpdated) {
		
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement update = null;
		ResultSet resSet = null;
		
		try {
			
			update = connection.prepareStatement(updateStatement);
			update.setString(1, toBeUpdated.getName());
			update.setString(2, toBeUpdated.getEmail());
			update.setString(3, toBeUpdated.getPassword());
			update.setInt(4, toBeUpdated.getId());
			update.executeUpdate();
		} catch(SQLException e) {

			System.out.println("SQL Error pppp: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(update);
			ConnectionFactory.close(connection);
		}
	}
	
	public void deletePlayer(int id) {
		
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement delete = null;
		ResultSet resSet = null;
		
		try {
			
			delete = connection.prepareStatement(deleteStatement);
			delete.setInt(1, id);
			delete.executeUpdate();
		} catch(SQLException e) {

			System.out.println("SQL Error pppp: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(delete);
			ConnectionFactory.close(connection);
		}
	}
	
	public ArrayList<Player> getAllPlayers() {
		
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement find = null;
		ResultSet resSet = null;
		ArrayList<Player> listOfPlayers = new ArrayList<Player>();
		
		try {
			
			find = connection.prepareStatement(selectAll);
			resSet = find.executeQuery();
			while(resSet.next()) {
				
				int id = resSet.getInt("id");
				String email = resSet.getString("email");
				String name = resSet.getString("name");
				String password = resSet.getString("password");
				boolean is_admin = resSet.getBoolean("is_admin");
				Player result = new Player(id, name, email, password,is_admin);
				listOfPlayers.add(result);
				
			}
			
		} catch(SQLException e) {

			System.out.println("SQL Error ppppp: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(find);
			ConnectionFactory.close(connection);
		}
		return listOfPlayers;
	}
	
	public Player findByAnything(String column, String value) {
		
		String findString = "SELECT * FROM player WHERE " + column + "=" + "?";
		
		Player result=null;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet resSet = null;
		
		try {
			
			findStatement = connection.prepareStatement(findString);
			findStatement.setString(1, value);
			resSet = findStatement.executeQuery();
			if(!resSet.next())
				return null;
			
			int id = resSet.getInt("id");
			String name = resSet.getString("name");
			String email = resSet.getString("email");
			String password = resSet.getString("password");
			boolean is_admin = resSet.getBoolean("is_admin");
			result = new Player(id, name, email, password,is_admin);
		} catch(SQLException e) {

			System.out.println("SQL Error pppppp: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(connection);
		}
		return result;
	}
	
}



