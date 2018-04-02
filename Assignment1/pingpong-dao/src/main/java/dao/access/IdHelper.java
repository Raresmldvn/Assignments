package dao.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IdHelper {

	public static int getNextId(String table, String id) {
		
		String selectString = "SELECT max(" + id + ") AS id FROM " + "`ping-pong`." + table;
		
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement find = null;
		ResultSet resSet = null;
		int newId = 0;
		try {
			find = connection.prepareStatement(selectString);
			resSet = find.executeQuery();
			resSet.next();
			newId = resSet.getInt("id")+1;
		} catch(SQLException e) {

			System.out.println("SQL Error i: " + e.getMessage());
		} finally {
			ConnectionFactory.close(resSet);
			ConnectionFactory.close(find);
			ConnectionFactory.close(connection);
		}
		return newId;
	}
}

