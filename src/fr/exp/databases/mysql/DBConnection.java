package fr.exp.databases.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {

	private static Connection connection = null;
	private static Map<String, Statement> statementsPool = new HashMap<String, Statement>();
	private static Map<String, PreparedStatement> preparedStatementsPool = new HashMap<String, PreparedStatement>();

	/**
	 * 
	 * @return
	 */
	private static Connection getConnection() {
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

	private static void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (Exception e) {
			// TODO Logger: Unable to close the database
		}
	}

	public static void closeCurrentStatement() {
		try {
			if (statementsPool.get("current") != null) {
				statementsPool.get("current").close();
				statementsPool.remove("current");
			}
		} catch (Exception e) {
			// TODO Logger: Unable to close the database
		}
	}

	public static void close() {
		for (Statement statement : statementsPool.values()) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		statementsPool = new HashMap<String, Statement>();
		DBConnection.closeConnection();
	}

	public static Statement getCurrentStatement() {
		Statement currentStatement = statementsPool.get("current");
		if (currentStatement == null) {
			currentStatement = DBConnection.getStatement("current");
		}
		return currentStatement;
	}

	public static Statement getStatement(String statementName) {
		Statement statementTEMP = statementsPool.get(statementName);
		Statement currentStatement = null;
		if (statementTEMP == null) {
			try {
				currentStatement = DBConnection.getNewStatement(statementName);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			currentStatement = statementTEMP;
		}
		return currentStatement;
	}

	private static Statement getNewStatement(String statementName) throws SQLException {
		return DBConnection.addStatement(statementName, DBConnection.getConnection().createStatement());
	}

	private static PreparedStatement getNewPreparedStatement(String statementName, String query) throws SQLException {
		return (PreparedStatement) DBConnection.addPreparedStatement(statementName,
				DBConnection.getConnection().prepareStatement(query));
	}

	private static Statement addStatement(String name, Statement createStatement) {
		statementsPool.put(name, createStatement);
		return createStatement;
	}

	private static Statement addPreparedStatement(String name, PreparedStatement createStatement) {
		preparedStatementsPool.put(name, createStatement);
		return createStatement;
	}

	public static ResultSet executeQuery(String query) throws SQLException {
		return DBConnection.getCurrentStatement().executeQuery(query);
	}

	public static PreparedStatement getPreparedStatement(String query) {
		// PreparedStatement statementTEMP = null;
		// = preparedStatementsPool.get("current");
		PreparedStatement currentStatement = null;
		// if (statementTEMP == null) {
		try {
			currentStatement = DBConnection.getNewPreparedStatement("current", query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// } else {
		// currentStatement = statementTEMP;
		// }
		return currentStatement;
	}
}
