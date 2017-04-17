package fr.exp.files.pearltrees.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.exp.databases.mysql.DBConnection;
import fr.exp.databases.mysql.DBInfo;
import fr.exp.files.pearltrees.database.models.Tag;
import fr.exp.files.pearltrees.database.models.TaggedUrl;
import fr.exp.files.pearltrees.database.models.Url;

public class TaggedUrlWriter {

	public void write(TaggedUrl taggedUrl) {
		PreparedStatement insertIntoLiaisonUrlTagStatement, insertIntoLiaisonFoldedTagsStatement;

		// Enregistrement de l'url
		PreparedStatement insertIntoTagsStatement = DBConnection
				.getPreparedStatement("insert into " + DBInfo.DBName + ".urls (url,label) values (?, ?)");
		try {
			insertIntoTagsStatement.setString(1, taggedUrl.getUrl().getUrl().toString());
			insertIntoTagsStatement.setString(2, taggedUrl.getUrl().getLabel());
			insertIntoTagsStatement.executeUpdate();

			taggedUrl.setId(getLastInsertedId("id_url", "urls"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		int id_path = getUniqueIdPath(), id_tag;

		// Enregistrement des folded tags
		insertIntoTagsStatement = DBConnection
				.getPreparedStatement("insert into " + DBInfo.DBName + ".tags (tag) values (?)");
		insertIntoLiaisonFoldedTagsStatement = DBConnection.getPreparedStatement(
				"insert into " + DBInfo.DBName + ".liaison_folded_tags (id_path,id_tag,id_parent_tag) values (?,?,?)");
		insertIntoLiaisonUrlTagStatement = DBConnection.getPreparedStatement(
				"insert into " + DBInfo.DBName + ".liaison_url_tags (id_url,id_tag,id_path) values (?,?,?)");

		for (int i = 0; i < taggedUrl.getTags().size(); i++) {
			try {
				id_tag = getTagId(taggedUrl.getTags().get(i).getTag());
				if (id_tag == 0) {
					insertIntoTagsStatement.setString(1, taggedUrl.getTags().get(i).getTag());
					insertIntoTagsStatement.executeUpdate();
					id_tag = getLastInsertedId("id_tag", "tags");
				}
				taggedUrl.getTags().get(i).setId(id_tag);

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

	private int getTagId(String tagName) {
		ResultSet resultSet;
		String query = "";
		// TODO Comment récupérer les urls et tous leurs tags dans une seule
		// requête?
		query += "SELECT * "// U.id_url U.url U.label T.tag
				+ "FROM tags "
				// " + DBInfo.DBName + ".
				+ "WHERE tag = \"" + tagName + "\"";
		// AND L.id_path = F.id_path
		// AND F.id_parent = T.id_tag --> pas nécessaire puisque j'ai le
		// path j'obtiens l'information id_parent dans le retour
		try {
			resultSet = DBConnection.executeQuery(query);
			if (resultSet.next())
				return resultSet.getInt("id_tag");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Créé une ligne dans la table path et renvoie son Id. Permet d'identifier
	 * un parent unique alors qu'il y en a plusieurs qui le sont
	 * potentiellement. Utile pour n'avoir qu'une inscription du tag en base de
	 * données quand il y a plusieurs fois son occurence
	 * 
	 * @return
	 */
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

	public ArrayList<TaggedUrl> read() {

		ResultSet resultSet;
		Pool pool = new Pool();
		try {
			String query = "";
			// TODO Comment récupérer les urls et tous leurs tags dans une seule
			// requête?
			// TODO Faire un fichier d'export simple pour tester
			// l'enregistrement en bdd
			query += "SELECT * "// U.id_url U.url U.label T.tag
					+ "FROM urls U, liaison_url_tags L, tags T "
					// " + DBInfo.DBName + ".
					+ "WHERE U.id_url = L.id_url AND T.id_tag = L.id_tag";
			// AND L.id_path = F.id_path
			// AND F.id_parent = T.id_tag --> pas nécessaire puisque j'ai le
			// path j'obtiens l'information id_parent dans le retour
			resultSet = DBConnection.executeQuery(query);
			int path;
			Url url;
			Tag tag;
			TaggedUrl taggedUrl;
			while (resultSet.next()) {
				url = new Url(resultSet.getInt("U.id_url"), resultSet.getString("U.url"),
						resultSet.getString("U.label"));
				tag = new Tag(resultSet.getInt("T.id_tag"), resultSet.getString("T.tag"));

				path = resultSet.getInt("L.id_path");

				taggedUrl = new TaggedUrl(url, tag, path);
				pool.add(taggedUrl);
			}

			// Maintenant que l'on a récupéré toutes les urls taggées, on
			// récupère les tags parents

			// Je parcours la liste des taggedUrl pour checker le path et
			// requêter (
			// si le path n'existe pas déjà dans la map je le crée)
			// Sinon je crée

			// Parcours récursif de l'arbre et ajout du tag parent récursivement
			// Map accessible par singleton
			query += "SELECT * "// U.id_url U.url U.label T.tag
					+ "FROM urls U, liaison_url_tags L, tags T "
					// " + DBInfo.DBName + ".
					+ "WHERE L.id_url = U.id_url AND T.id_tag = L.id_tag";
			resultSet = DBConnection.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pool.getArrayList();
	}
}
