package fr.exp.databases.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLDemo {

	public static void main(String[] args) {

	}

	/**
	 * @param taggedUrlsList
	 * @param statement
	 * @throws SQLException
	 */
	private void pullAllDemo() {
		ArrayList<Model> modelList = new ArrayList<Model>();
		Query query = new Query();
		query.setSelect("*");
		query.setFrom(DBInfo.DBName + ".model");
		ResultSet resultSet;
		try {
			resultSet = DBConnection.executeQuery(query.getQuery());
			Model model;
			// Get url
			while (resultSet.next()) {
				model = new Model();
				model.setId(resultSet.getInt("id"));
				model.setName(resultSet.getString("name"));
				modelList.add(model);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close();
		}

	}

}
