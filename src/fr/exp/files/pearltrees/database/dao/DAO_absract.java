package fr.exp.files.pearltrees.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import fr.exp.databases.mysql.DBConnection;
import fr.exp.databases.mysql.DBInfo;

abstract class DAO_absract implements DAO_interface {

	private ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("fr.exp.files.pearltrees");

	protected int getLastInsertedId(String column_name, String table_name) {
		// Get the id of the last inserted row
		ResultSet resultSet;
		try {
			resultSet = DBConnection.executeQuery("SELECT " + column_name + " FROM " + DBInfo.DBName + "." + table_name
					+ " ORDER BY " + column_name + " DESC LIMIT 1");
			if (resultSet.next()) {
				int lastInserted = resultSet.getInt(column_name);
				logger.trace("Last path inserted id: {}", lastInserted);
				return lastInserted;

			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Unable to request for the last inserted id", e);
		}
		return 0;
	}

}
