package fr.exp.databases.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DBConnection {

	private static Connection connection = null;
	private static Statement statement = null;
	private static Map<String, Statement> statementsPool = null;

	/**
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		if (connection == null) {
			// This will load the MySQL driver, each DB has its own driver
			try {
				Class.forName("com.mysql.jdbc.Driver");

				// Setup the connection with the DB
				try {
					String databaseUrl = "jdbc:mysql://localhost/" + DBInfo.DBName + "?" + "user=" + DBInfo.DBUser
							+ "&password=" + DBInfo.DBPassword;
					connection = DriverManager.getConnection(databaseUrl);
				} catch (SQLException e) {
					// TODO Logger: Unable to establish the connection with
					// $databaseUrl
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				// TODO Logger: Unable to find the driver: com.mysql.jdbc.Driver
				e.printStackTrace();
			}

		}
		return connection;
	}

	public static void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (Exception e) {
			// TODO Logger: Unable to close the database
		}
	}

	public static void closeStatement() {
		try {
			if (statement != null) {
				statement.close();
				statement = null;
			}
		} catch (Exception e) {
			// TODO Logger: Unable to close the database
		}
	}

	public static void close() {
		DBConnection.closeStatement();
		DBConnection.closeConnection();
		for (Statement statement : statementsPool.values()) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Statement getCurrentStatement() {
		if (statement == null) {
			try {
				statement = DBConnection.getNewStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return statement;
	}

	public static Statement getStatement(String statementName) {
		Statement statementTEMP = statementsPool.get(statementName);
		if (statementTEMP == null) {
			try {
				statement = DBConnection.getNewStatement();
				statementsPool.put(statementName, statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			statement = statementTEMP;
		}
		return statement;
	}

	private static Statement getNewStatement() throws SQLException {
		return DBConnection.getConnection().createStatement();
	}

	public static ResultSet executeQuery(String query) throws SQLException {
		return DBConnection.getCurrentStatement().executeQuery(query);
	}
}
