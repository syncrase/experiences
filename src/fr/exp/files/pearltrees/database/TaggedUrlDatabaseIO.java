package fr.exp.files.pearltrees.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import fr.exp.databases.mysql.DBConnection;
import fr.exp.databases.mysql.DBInfo;
import fr.exp.files.pearltrees.database.models.Path;
import fr.exp.files.pearltrees.database.models.Url;
import fr.exp.files.pearltrees.metamodels.FoldedTag;
import fr.exp.files.pearltrees.metamodels.TaggedUrl;

public class TaggedUrlDatabaseIO {
	public static ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("fr.exp.files.pearltrees");

	// TODO pour beaucoup plus tard. Si je veux faire des UI pour manipuler tout �a.
	// Des m�thodes pour rassembler des paths. Supprimer des paths entier. recr�er
	// le path quand un tag est supprim�
	// Mais en priorit�, permettre � une url d'avoir plusieurs foldedTags!!!
	
	
	// CREATE TABLE `pearltrees_data`.`liaison_url_tags` ( `id_liaison_url_tags` INT
	// NOT NULL AUTO_INCREMENT , `id_url` INT NOT NULL , `id_tag` INT NOT NULL ,
	// PRIMARY KEY (`id_liaison_url_tags`)) ENGINE = MyISAM;

	// CREATE TABLE `pearltrees_data`.`urls` ( `id_url` INT NOT NULL AUTO_INCREMENT
	// , `url` INT NOT NULL , `label` INT NOT NULL , PRIMARY KEY (`id_url`)) ENGINE
	// = MyISAM;

	// CREATE TABLE `pearltrees_data`.`liaison_folded_tags` ( `id_path` INT NOT NULL
	// , `id_parent_tag` INT NOT NULL , `id_liaison_folded_tags` INT NOT NULL
	// AUTO_INCREMENT , PRIMARY KEY (`id_liaison_folded_tags`)) ENGINE = MyISAM;

	public void taggedUrlInsertion(TaggedUrl taggedUrl) {

		logger.trace("Get or insert {}", taggedUrl.getUrl().toString());
		// R�cup�re l'url existante et sinon enregistre une url
		taggedUrl.setUrl(exists(taggedUrl.getUrl()));
		if (taggedUrl.getUrl().getId_url() == 0) {
			// Enregistrement de l'url
			taggedUrl.setUrl(insert(taggedUrl.getUrl()));
		}
		insert(taggedUrl);
	}

	private void insert(TaggedUrl taggedUrl) {
		logger.trace("Process folding tags insertion");

		PreparedStatement insertIntoLiaisonUrlTagStatement, insertIntoLiaisonFoldedTagsStatement;
		Path path = new Path();
		path = getNewPath(path);
		insertIntoLiaisonFoldedTagsStatement = DBConnection.getPreparedStatement(
				"insert into " + DBInfo.DBName + ".liaison_folded_tags (id_path,id_tag,id_parent_tag) values (?,?,?)");
		insertIntoLiaisonUrlTagStatement = DBConnection.getPreparedStatement(
				"insert into " + DBInfo.DBName + ".liaison_url_tags (id_url,id_tag,id_path) values (?,?,?)");

		// TODO Cas particulier : Toutes les urls ont un root_tag = syncrase.
		// Comment
		// cela se passe quand il y en a plusieurs?
		try {

			// Retirer la condition de taille? Normalement il y a toujours au moins un tag
			// => donc condition inutile... Voir pour ajouter un point bloquant conditionn�
			// sur la taille du tableau de tag
			if (taggedUrl.getTags().size() > 0) {
				taggedUrl.getTags().set(0, insert(taggedUrl.getTags().get(0)));
			}

			for (int i = 0; i < taggedUrl.getTags().size(); i++) {

				// Enregistrement dans la table de liaison url tag (pour le
				// dernier uniquement, celui qui est directement associ� �
				// l'url)
				// M�me si l'url existe d�j�, ainsi que le tag, un nouveau est tout de m�me cr��
				if (i == taggedUrl.getTags().size() - 1) {
					// Enregistre le tag comme directement li� � l'url
					insertIntoLiaisonUrlTagStatement.setInt(1, taggedUrl.getUrl().getId_url());
					insertIntoLiaisonUrlTagStatement.setInt(2, taggedUrl.getTags().get(i).getId_tag());
					insertIntoLiaisonUrlTagStatement.setInt(3, path.getId());
					insertIntoLiaisonUrlTagStatement.executeUpdate();
				} else {
					// TODO uniquement cette partie n�cessaire, la table url_tags ne sers � rien si
					// la table folded tags est bien utilis�e. Voir pour commenter et remplacer
					// toutes les
					// r�f�rences � cette table avant de supprimer le JAVA
					// Si je fais �a, en fin de boucle je n'aurai pas de parent � mettre....
					// 1 mettre l'url en parent? On perdrai l'information de savoir si c'est une url
					// ou un tag dans la liste des 'parents' => temps de traitement suppl�mentaire
					// Toutes les urls sont collat�rales
					// 2 rester comme �a?
					taggedUrl.getTags().set(i + 1, insert(taggedUrl.getTags().get(i + 1)));
					// taggedUrl.getTags().get(i) = insert(taggedUrl.getTags().get(i + 1));
					// Sinon enregistre la succession des tags reli�s indirectement � l'url
					insertIntoLiaisonFoldedTagsStatement.setInt(1, path.getId());
					insertIntoLiaisonFoldedTagsStatement.setInt(2, taggedUrl.getTags().get(i + 1).getId_tag());
					// Le parent du premier tag est celui qui est le plus proche de l'url
					insertIntoLiaisonFoldedTagsStatement.setInt(3, taggedUrl.getTags().get(i).getId_tag());
					insertIntoLiaisonFoldedTagsStatement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Fail to insert tags in the database", e);
		}
	}

	private Url insert(Url url) {
		PreparedStatement insertIntoTagsStatement = DBConnection
				.getPreparedStatement("insert into " + DBInfo.DBName + ".urls (url,label) values (?, ?)");
		try {
			insertIntoTagsStatement.setString(1, url.getUrl().toString());
			insertIntoTagsStatement.setString(2, url.getLabel());
			logger.trace("Execute update {}", insertIntoTagsStatement);
			insertIntoTagsStatement.executeUpdate();

			url.setId_url(getLastInsertedId("id_url", "urls"));
			logger.trace("Update success {}", url.toString());
			return url;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Fail to update", e);
		}
		return url;
	}

	/**
	 * R�cup�re l'id du tag s'il existe
	 * 
	 * @param foldedTag
	 * @return
	 * @throws SQLException
	 */
	private FoldedTag insert(FoldedTag foldedTag) throws SQLException {
		int id_tag;
		PreparedStatement insertIntoTagsStatement = DBConnection
				.getPreparedStatement("insert into " + DBInfo.DBName + ".tags (tag) values (?)");

		id_tag = tagExists(foldedTag.getTag());
		if (id_tag == 0) {
			// ou le cr�er s'il n'existe pas
			insertIntoTagsStatement.setString(1, foldedTag.getTag());
			insertIntoTagsStatement.executeUpdate();
			id_tag = getLastInsertedId("id_tag", "tags");
		}
		foldedTag.setId_tag(id_tag);
		return foldedTag;
	}

	/**
	 * R�cup�re une Url en base de donn�es � partir de l'url et du label
	 * 
	 * @param url
	 *            Url contenant url ET label
	 * @return L'objet url compl�t� de son id en base de donn�es, ou pas s'il
	 *         n'existe pas.
	 */
	private Url exists(Url url) {
		// logger.trace("Request tag id for : {}", tagName);
		ResultSet resultSet;
		String query = "";
		query += "SELECT * FROM urls WHERE url = \"" + url.getUrl() + "\" & label = \"" + url.getLabel() + "\"";
		try {
			resultSet = DBConnection.executeQuery(query);
			if (resultSet.next()) {
				int id_url = (int) resultSet.getInt("id_url");
				// logger.trace("tag id for {} is {}", tagName, tag_id);
				url.setId_url(id_url);
				return url;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Unable to check if {} exists", url);
		}
		return url;
	}

	/**
	 * Check in the table 'tags' if the tagName exists. If it does, return the id.
	 * If it doesn't, return 0.
	 * 
	 * @param tagName
	 * @return if( tagName exists in 'tags') tagId else 0
	 */
	private int tagExists(String tagName) {
		logger.trace("Request tag id for : {}", tagName);
		ResultSet resultSet;
		String query = "";
		query += "SELECT * FROM tags WHERE tag = \"" + tagName + "\"";
		try {
			resultSet = DBConnection.executeQuery(query);
			if (resultSet.next()) {
				int tag_id = (int) resultSet.getInt("id_tag");
				logger.trace("tag id for {} is {}", tagName, tag_id);
				return tag_id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Unable to request tag id for : {}", tagName);
		}
		return 0;
	}

	/**
	 * Cr�e une ligne dans la table path et renvoie son Id. Permet d'identifier un
	 * parent unique alors qu'il y en a plusieurs qui le sont potentiellement. Utile
	 * pour n'avoir qu'une inscription du tag en base de donn�es quand il y a
	 * plusieurs fois son occurence
	 * 
	 * @return
	 */
	private Path getNewPath(Path path) {
		logger.trace("Request for a new id path to the database");
		PreparedStatement preparedStatement = DBConnection
				.getPreparedStatement("insert into " + DBInfo.DBName + ".paths (id_path) values (0)");
		try {
			preparedStatement.executeUpdate();
			path.setId(getLastInsertedId("id_path", "paths"));
			return path;
		} catch (SQLException e1) {
			e1.printStackTrace();
			logger.error("Unable to have a new id path, It's weird bro...", e1);
		}
		return path;
	}

	private int getLastInsertedId(String column_name, String table_name) {
		logger.trace("Request for the last inserted id");
		// Get the id of the last inserted row
		ResultSet resultSet;
		try {
			resultSet = DBConnection.executeQuery("SELECT " + column_name + " FROM " + DBInfo.DBName + "." + table_name
					+ " ORDER BY " + column_name + " DESC LIMIT 1");
			if (resultSet.next())
				return resultSet.getInt(column_name);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Unable to request for the last inserted id", e);
		}
		return 0;
	}

	public ArrayList<TaggedUrl> read() {
		logger.trace("Read for all urls");

		ResultSet resultSet;
		TaggedUrlsMap pool = new TaggedUrlsMap();
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
			logger.error("Read for all urls", e);
		}

		return pool.getArrayList();
	}

	public void deleteAll() {
		logger.trace("Delete the whole database content");
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
			logger.error("Unable to delete the whole database content", e);
		}

	}

	// public String getTablesName() {
	// logger.trace("Get database table names");
	// // Cette m�thode n'a rien � faire dans cette classe. Cr�er un classe
	// // DatabaseIO? DBConnection?
	// ResultSet resultSet;
	// String query = "";
	// String returnedString = "";
	// ArrayList<String> tableList = new ArrayList<String>();
	// // TODO Comment r�cup�rer les urls et tous leurs tags dans une seule
	// // requ�te?
	// query += "SELECT TABLE_NAME FROM information_schema.tables "
	// + "where TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA = 'pearltrees_data'";
	// // AND L.id_path = F.id_path
	// // AND F.id_parent = T.id_tag --> pas n�cessaire puisque j'ai le
	// // path j'obtiens l'information id_parent dans le retour
	// try {
	// resultSet = DBConnection.executeQuery(query);
	// while (resultSet.next()) {
	// // TODO need some parse
	// tableList.add(resultSet.getString("TABLE_NAME"));
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// logger.error("Unable to get database table names");
	// }
	//
	// for (String tableName : tableList) {
	// returnedString += tableName + "\n";
	// }
	// return null;
	// }
}
