package fr.exp.files.pearltrees.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import fr.exp.databases.mysql.DBConnection;
import fr.exp.databases.mysql.DBInfo;
import fr.exp.files.pearltrees.database.dao.DaoContainer;
import fr.exp.files.pearltrees.database.models.IModel;
import fr.exp.files.pearltrees.database.models.LiaisonFoldedTagsDTO;
import fr.exp.files.pearltrees.database.models.LiaisonTagUrlDTO;
import fr.exp.files.pearltrees.database.models.PathsDTO;
import fr.exp.files.pearltrees.database.models.TagsDTO;
import fr.exp.files.pearltrees.database.models.UrlsDTO;
import fr.exp.files.pearltrees.metamodels.FoldedTag;
import fr.exp.files.pearltrees.metamodels.TaggedUrl;

public class TaggedUrlDatabaseIO {
	public static ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("fr.exp.files.pearltrees");

	// TODO pour beaucoup plus tard. Si je veux faire des UI pour manipuler tout ça.
	// Des méthodes pour rassembler des paths. Supprimer des paths entier. recréer
	// le path quand un tag est supprimé
	// Mais en priorité, permettre à une url d'avoir plusieurs foldedTags!!!

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
		// Récupère l'url existante et sinon enregistre une url
		IModel url = new UrlsDTO();
		DaoContainer urldao = new DaoContainer(url);
		// Get or insert the url
		taggedUrl.setUrl((UrlsDTO) urldao.insert(taggedUrl.getUrl()));

		insert(taggedUrl);
	}

	// /**
	// * Récupère l'id du tag s'il existe, ou insère dans la base de données
	// *
	// * @param foldedTag
	// * @return Un foldedTag avec l'id du tag
	// * @throws SQLException
	// */
	// private FoldedTag insert(FoldedTag foldedTag) throws SQLException {
	// Tag tag;
	// PreparedStatement insertIntoTagsStatement = DBConnection
	// .getPreparedStatement("insert into " + DBInfo.DBName + ".tags (tag) values
	// (?)");
	//
	// foldedTag.setTag(exists(foldedTag.getTag()));
	// if (foldedTag.getTag().getId_tag() == 0) {
	// // ou le créer s'il n'existe pas
	// insertIntoTagsStatement.setString(1, foldedTag.getTag().getTag());
	// insertIntoTagsStatement.executeUpdate();
	// foldedTag.setId_tag(getLastInsertedId("id_tag", "tags"));
	// }
	// return foldedTag;
	// }

	private void insert(TaggedUrl taggedUrl) {
		logger.trace("Process folding tags insertion");

		IModel path = new PathsDTO();
		DaoContainer pathDao = new DaoContainer(path);
		path = pathDao.insert(path);
		taggedUrl.setPath(path);
		// Retirer la condition de taille? Normalement il y a toujours au moins un tag
		// => donc condition inutile... Voir pour ajouter un point bloquant conditionné
		// sur la taille du tableau de tag
		IModel tag = new TagsDTO();
		DaoContainer tagdao = new DaoContainer(tag);
		if (taggedUrl.getTags().size() > 0) {
			tag = taggedUrl.getTags().get(0).getTag();
			// get or insert the tag
			tag = tagdao.insert(tag);
			taggedUrl.getTags().get(0).setTag((TagsDTO) tag);
		}

		for (int i = 0; i < taggedUrl.getTags().size(); i++) {

			// Enregistrement dans la table de liaison url tag (pour le
			// dernier uniquement, celui qui est directement associé à
			// l'url)
			// Même si l'url existe déjà, ainsi que le tag, un nouveau est tout de même créé
			if (i == taggedUrl.getTags().size() - 1) {
				// Enregistre le tag comme directement lié à l'url
				LiaisonTagUrlDTO dto = new LiaisonTagUrlDTO(taggedUrl.getUrl().getId(),
						taggedUrl.getTags().get(i).getId(), taggedUrl.getPath().getId());
				DaoContainer liaisonTagUrldao = new DaoContainer(dto);
				// Je ne récupère pas l'id car inutlie ici.
				liaisonTagUrldao.insert(dto);
			} else {
				// TODO uniquement cette partie nécessaire, la table url_tags ne sers à rien si
				// la table folded tags est bien utilisée. Voir pour commenter et remplacer
				// toutes les
				// références à cette table avant de supprimer le JAVA
				// Si je fais ça, en fin de boucle je n'aurai pas de parent à mettre....
				// 1 mettre l'url en parent? On perdrai l'information de savoir si c'est une url
				// ou un tag dans la liste des 'parents' => temps de traitement supplémentaire
				// Toutes les urls sont collatérales
				// 2 rester comme ça?
				tag = tagdao.insert(taggedUrl.getTags().get(i + 1).getTag());
				taggedUrl.getTags().get(i + 1).setTag((TagsDTO) tag);
				// taggedUrl.getTags().get(i) = insert(taggedUrl.getTags().get(i + 1));
				// Sinon enregistre la succession des tags reliés indirectement à l'url
				// Le parent du premier tag est celui qui est le plus proche de l'url
				LiaisonFoldedTagsDTO dto = new LiaisonFoldedTagsDTO(((PathsDTO) path).getId(),
						taggedUrl.getTags().get(i + 1).getId(), taggedUrl.getTags().get(i).getId());
				DaoContainer liaisonFoldedTagsDao = new DaoContainer(dto);
				// Je ne récupère pas l'id car inutlie ici.
				liaisonFoldedTagsDao.insert(dto);
			}
		}
		logger.trace("Fin de l'inscription de l'url taggées");
	}

	public ArrayList<TaggedUrl> read() {
		logger.trace("Read for all urls");

		ResultSet resultSet;
		TaggedUrlsMap pool = new TaggedUrlsMap();
		try {
			String query = "";
			// TODO Comment récupérer les urls et tous leurs tags dans une seule
			// requête?
			query += "SELECT * " + "FROM urls U, liaison_url_tags L, tags T "
					+ "WHERE U.id_url = L.id_url AND T.id_tag = L.id_tag";
			// AND L.id_path = F.id_path
			// AND F.id_parent = T.id_tag --> pas nécessaire puisque j'ai le
			// path j'obtiens l'information id_parent dans le retour
			resultSet = DBConnection.executeQuery(query);
			int path;
			UrlsDTO url;
			FoldedTag tag;
			TaggedUrl taggedUrl;
			while (resultSet.next()) {
				url = new UrlsDTO(resultSet.getInt("U.id_url"), resultSet.getString("U.url"),
						resultSet.getString("U.label"));
				tag = new FoldedTag(resultSet.getInt("T.id_tag"), resultSet.getString("T.tag"));

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
			for (TaggedUrl obtainedTaggedUrl : pool.getArrayList()) {
				query = "SELECT * "// U.id_url U.url U.label T.tag
						+ "FROM liaison_url_tags L, liaison_folded_tags F, tags T " + "WHERE L.id_url = "
						+ obtainedTaggedUrl.getUrl().getId()
						+ " AND L.id_path = F.id_path AND F.id_parent_tag  = T.id_tag";
				resultSet = DBConnection.executeQuery(query);
				while (resultSet.next()) {
					// Je récupère tous les tags et je les ajoute à mon objet
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
	// // Cette méthode n'a rien à faire dans cette classe. Créer un classe
	// // DatabaseIO? DBConnection?
	// ResultSet resultSet;
	// String query = "";
	// String returnedString = "";
	// ArrayList<String> tableList = new ArrayList<String>();
	// // TODO Comment récupérer les urls et tous leurs tags dans une seule
	// // requête?
	// query += "SELECT TABLE_NAME FROM information_schema.tables "
	// + "where TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA = 'pearltrees_data'";
	// // AND L.id_path = F.id_path
	// // AND F.id_parent = T.id_tag --> pas nécessaire puisque j'ai le
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
