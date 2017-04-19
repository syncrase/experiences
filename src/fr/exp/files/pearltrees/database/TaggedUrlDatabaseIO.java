package fr.exp.files.pearltrees.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.exp.databases.mysql.DBConnection;
import fr.exp.databases.mysql.DBInfo;
import fr.exp.files.pearltrees.database.models.FoldedTag;
import fr.exp.files.pearltrees.database.models.TaggedUrl;
import fr.exp.files.pearltrees.database.models.Url;

public class TaggedUrlDatabaseIO {

	public void writeTaggedUrl(TaggedUrl taggedUrl) {
		PreparedStatement insertIntoLiaisonUrlTagStatement, insertIntoLiaisonFoldedTagsStatement;

		// Enregistrement de l'url
		PreparedStatement insertIntoTagsStatement = DBConnection
				.getPreparedStatement("insert into " + DBInfo.DBName + ".urls (url,label) values (?, ?)");
		try {
			insertIntoTagsStatement.setString(1, taggedUrl.getUrl().getUrl().toString());
			insertIntoTagsStatement.setString(2, taggedUrl.getUrl().getLabel());
			insertIntoTagsStatement.executeUpdate();

			taggedUrl.getUrl().setId_url(getLastInsertedId("id_url", "urls"));

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

		// TODO Dans l'id�e �a ne va pas, la liste de tags de taggedUrl
		// repr�sente tous les tags de 1er niveau ou tous les �l�ments de
		// l'arborescence d'un folded tag? Sachant que dans le cas PearlTrees,
		// une url n'a qu'un folded tag de 1er niveau.

		// TODO Cas particulier : Toutes les urls ont un root_tag = syncrase.
		// Comment
		// cela se passe quand il y en a plusieurs?
		for (int i = 0; i < taggedUrl.getTags().size(); i++) {
			try {
				// R�cup�re l'id du tag s'il existe ou le cr��
				id_tag = getTagId(taggedUrl.getTags().get(i).getTag());
				if (id_tag == 0) {
					insertIntoTagsStatement.setString(1, taggedUrl.getTags().get(i).getTag());
					insertIntoTagsStatement.executeUpdate();
					id_tag = getLastInsertedId("id_tag", "tags");
				}
				taggedUrl.getTags().get(i).setId_tag(id_tag);

				// Enregistrement dans la table folded url pour avoir le parent
				// de ce tag
				// TODO � utiliser dans chaque cas, pour chaque tag de 1er
				// niveau contenu dans la liste taggedUrl.getTags()

				// taggedUrl.getTags().get(i) : tag de 1er niveau

				// LinkedList<FoldedTag> foldedTagsList =
				// taggedUrl.getTags().get(i).getFullPath();
				// Avec cette liste je peux traiter tous les tags parents
				if (i > 0) {
					insertIntoLiaisonFoldedTagsStatement.setInt(1, id_path);
					insertIntoLiaisonFoldedTagsStatement.setInt(2, taggedUrl.getTags().get(i).getId_tag());
					insertIntoLiaisonFoldedTagsStatement.setInt(3, taggedUrl.getTags().get(i - 1).getId_tag());
					insertIntoLiaisonFoldedTagsStatement.executeUpdate();
				}

				// Enregistrement dans la table de liaison url tag (pour le
				// dernier uniquement, celui qui est directement associ� �
				// l'url)
				// TODO la condition n'est pas n�cessaire puisqu'il faut que
				// tous les tags soient des tags de 1er niveau
				if (i == taggedUrl.getTags().size() - 1) {
					insertIntoLiaisonUrlTagStatement.setInt(1, taggedUrl.getUrl().getId_url());
					insertIntoLiaisonUrlTagStatement.setInt(2, taggedUrl.getTags().get(i).getId_tag());
					insertIntoLiaisonUrlTagStatement.setInt(3, id_path);
					insertIntoLiaisonUrlTagStatement.executeUpdate();
				}

				// Nouvel algo
				// Enregistrement du tag de 1er niveau et du lien dans url_tags
				// Enregistrement des tags parents et de leur lien dans
				// folded_tags
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Check in the table 'tags' if the tagName exists. If it does, return the id. If it doesn't, return 0.
	 * @param tagName
	 * @return if( tagName exists in 'tags') tagId else 0
	 */
	private int getTagId(String tagName) {
		ResultSet resultSet;
		String query = "";
		query += "SELECT * FROM tags WHERE tag = \"" + tagName + "\"";
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
	 * Cr�� une ligne dans la table path et renvoie son Id. Permet d'identifier
	 * un parent unique alors qu'il y en a plusieurs qui le sont
	 * potentiellement. Utile pour n'avoir qu'une inscription du tag en base de
	 * donn�es quand il y a plusieurs fois son occurence
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
			// TODO Comment r�cup�rer les urls et tous leurs tags dans une seule
			// requ�te?
			query += "SELECT * " + "FROM urls U, liaison_url_tags L, tags T "
					+ "WHERE U.id_url = L.id_url AND T.id_tag = L.id_tag";
			// AND L.id_path = F.id_path
			// AND F.id_parent = T.id_tag --> pas n�cessaire puisque j'ai le
			// path j'obtiens l'information id_parent dans le retour
			resultSet = DBConnection.executeQuery(query);
			int path;
			Url url;
			FoldedTag tag;
			TaggedUrl taggedUrl;
			while (resultSet.next()) {
				url = new Url(resultSet.getInt("U.id_url"), resultSet.getString("U.url"),
						resultSet.getString("U.label"));
				tag = new FoldedTag(resultSet.getInt("T.id_tag"), resultSet.getString("T.tag"));

				path = resultSet.getInt("L.id_path");

				taggedUrl = new TaggedUrl(url, tag, path);
				pool.add(taggedUrl);
			}

			// Maintenant que l'on a r�cup�r� toutes les urls tagg�es, on
			// r�cup�re les tags parents

			// Je parcours la liste des taggedUrl pour checker le path et
			// requ�ter (
			// si le path n'existe pas d�j� dans la map je le cr�e)
			// Sinon je cr�e

			// Parcours r�cursif de l'arbre et ajout du tag parent r�cursivement
			// Map accessible par singleton
			for (TaggedUrl obtainedTaggedUrl : pool.getArrayList()) {
				query = "SELECT * "// U.id_url U.url U.label T.tag
						+ "FROM liaison_url_tags L, liaison_folded_tags F, tags T " + "WHERE L.id_url = "
						+ obtainedTaggedUrl.getUrl().getId_url()
						+ " AND L.id_path = F.id_path AND F.id_parent_tag  = T.id_tag";
				resultSet = DBConnection.executeQuery(query);
				while (resultSet.next()) {
					// Je r�cup�re tous les tags et je les ajoute � mon objet
					obtainedTaggedUrl.addTag(new FoldedTag(resultSet.getInt("id_tag"), resultSet.getString("tag")),
							resultSet.getInt("id_parent_tag"));
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pool.getArrayList();
	}

	public void deleteAll() {
		PreparedStatement delete;
		try {
			delete = DBConnection.getPreparedStatement("DELETE FROM `liaison_folded_tags` WHERE 1");
			delete.executeUpdate();
			delete = DBConnection.getPreparedStatement("DELETE FROM `liaison_url_tags` WHERE 1");
			delete.executeUpdate();
			delete = DBConnection.getPreparedStatement("DELETE FROM `tags` WHERE 1");
			delete.executeUpdate();
			delete = DBConnection.getPreparedStatement("DELETE FROM `urls` WHERE 1");
			delete.executeUpdate();
			delete = DBConnection.getPreparedStatement("DELETE FROM `paths` WHERE 1");
			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}