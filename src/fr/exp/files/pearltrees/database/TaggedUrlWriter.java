package fr.exp.files.pearltrees.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.exp.databases.mysql.DBConnection;
import fr.exp.databases.mysql.DBInfo;
import fr.exp.files.pearltrees.database.models.TaggedUrl;

public class TaggedUrlWriter {

	public void write(TaggedUrl taggedUrl) {
		PreparedStatement insertIntoLiaisonUrlTagStatement, insertIntoLiaisonFoldedTagsStatement;

		// Enregistrement de l'url
		PreparedStatement preparedStatement = DBConnection
				.getPreparedStatement("insert into " + DBInfo.DBName + ".urls (url,label) values (?, ?)");
		try {
			preparedStatement.setString(1, taggedUrl.getUrl().getUrl().toString());
			preparedStatement.setString(2, taggedUrl.getUrl().getLabel());
			preparedStatement.executeUpdate();

			taggedUrl.setId(getLastInsertedId("id_url", "urls"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		int id_path = getUniqueIdPath();

		// Enregistrement des folded tags
		preparedStatement = DBConnection
				.getPreparedStatement("insert into " + DBInfo.DBName + ".tags (tag) values (?)");
		insertIntoLiaisonFoldedTagsStatement = DBConnection.getPreparedStatement("insert into " + DBInfo.DBName
				+ ".liaison_folded_tags (id_path,id_tag,id_parent_tag) values (?,?,?)");
		insertIntoLiaisonUrlTagStatement = DBConnection.getPreparedStatement("insert into " + DBInfo.DBName
				+ ".liaison_url_tags (id_url,id_tag,id_path) values (?,?,?)");
		
		for (int i = 0; i < taggedUrl.getTags().size(); i++) {
			taggedUrl.getTags().get(i);

			try {
				preparedStatement.setString(1, taggedUrl.getTags().get(i).getTag());
				preparedStatement.executeUpdate();

				taggedUrl.getTags().get(i).setId(getLastInsertedId("id_tag", "tags"));

				// Enregistrement dans la table folded url pour avoir le parent
				// de ce tag
				if (i > 0) {
					insertIntoLiaisonFoldedTagsStatement.setInt(1, id_path);
					insertIntoLiaisonFoldedTagsStatement.setInt(2, taggedUrl.getTags().get(i).getId());
					insertIntoLiaisonFoldedTagsStatement.setInt(3, taggedUrl.getTags().get(i - 1).getId());
					insertIntoLiaisonFoldedTagsStatement.executeUpdate();
				}

				// Enregistrement dans la table de liaison url tag (pour le
				// dernier uniquement, celui qui est directement associé à
				// l'url)
				if (i == taggedUrl.getTags().size() - 1) {
					insertIntoLiaisonUrlTagStatement.setInt(1, taggedUrl.getId());
					insertIntoLiaisonUrlTagStatement.setInt(2, taggedUrl.getTags().get(i).getId());
					insertIntoLiaisonUrlTagStatement.setInt(3, id_path);
					insertIntoLiaisonUrlTagStatement.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private int getUniqueIdPath() {
		PreparedStatement preparedStatement = DBConnection
				.getPreparedStatement("insert into " + DBInfo.DBName + ".paths (id_path) values (0)");
		try {
			preparedStatement.executeUpdate();
			return getLastInsertedId("id_path", "paths");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return 0;
	}

	private int getLastInsertedId(String column_name, String table_name) {
		// Get the id of the last inserted row
		ResultSet resultSet;
		try {
			resultSet = DBConnection.executeQuery("SELECT " + column_name + " FROM " + DBInfo.DBName + "." + table_name
					+ " ORDER BY " + column_name + " DESC LIMIT 1");
			if (resultSet.next())
				return resultSet.getInt(column_name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
