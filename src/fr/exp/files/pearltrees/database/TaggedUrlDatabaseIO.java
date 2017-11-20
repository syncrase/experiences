package fr.exp.files.pearltrees.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import fr.exp.databases.mysql.DBConnection;
import fr.exp.files.pearltrees.database.dto.LiaisonFoldedTagsDTO;
import fr.exp.files.pearltrees.database.dto.LiaisonTagUrlDTO;
import fr.exp.files.pearltrees.database.dto.PathsDTO;
import fr.exp.files.pearltrees.database.dto.TagsDTO;
import fr.exp.files.pearltrees.database.dto.UrlsDTO;
import fr.exp.files.pearltrees.database.skeleton.DataAccessObject;
import fr.exp.files.pearltrees.database.skeleton.DataTransfertObject;
import fr.exp.files.pearltrees.metamodels.FoldedTag;
import fr.exp.files.pearltrees.metamodels.TaggedUrl;

public class TaggedUrlDatabaseIO {
	public static ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("fr.exp.files.pearltrees");

	// TODO pour beaucoup plus tard. Si je veux faire des UI pour manipuler tout �a.
	// Des m�thodes pour rassembler des paths. Supprimer des paths entier. recr�er
	// le path quand un tag est supprim�
	// Mais en priorit�, permettre � une url d'avoir plusieurs foldedTags!!!

	public void taggedUrlInsertion(TaggedUrl taggedUrl) {

		logger.trace("Get or insert {}", taggedUrl.getUrl().toString());
		DataAccessObject dao = new DataAccessObject();

		// Get or insert the url
		taggedUrl.setUrl((UrlsDTO) dao.insert(taggedUrl.getUrl()));

		insert(taggedUrl);
	}

	private void insert(TaggedUrl taggedUrl) {
		logger.trace("Process folding tags insertion");

		DataAccessObject dao = new DataAccessObject();
		taggedUrl.setPath(dao.insert(new PathsDTO()));
		// Retirer la condition de taille? Normalement il y a toujours au moins un tag
		// => donc condition inutile... Voir pour ajouter un point bloquant conditionn�
		// sur la taille du tableau de tag
		DataTransfertObject tag = new TagsDTO();
		if (taggedUrl.getTags().size() > 0) {
			tag = taggedUrl.getTags().get(0).getTag();
			// get or insert the tag
			tag = dao.insert(tag);
			taggedUrl.getTags().get(0).setTag((TagsDTO) tag);
		}

		for (int i = 0; i < taggedUrl.getTags().size(); i++) {

			// Enregistrement dans la table de liaison url tag (pour le
			// dernier uniquement, celui qui est directement associ� �
			// l'url)
			// M�me si l'url existe d�j�, ainsi que le tag, un nouveau est tout de m�me cr��
			if (i == taggedUrl.getTags().size() - 1) {
				// Enregistre le tag comme directement li� � l'url
				LiaisonTagUrlDTO dto = new LiaisonTagUrlDTO(taggedUrl.getUrl().getId(),
						taggedUrl.getTags().get(i).getId(), taggedUrl.getPath().getId());
				// Je ne r�cup�re pas l'id car inutlie ici.
				dao.insert(dto);
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
				tag = dao.insert(taggedUrl.getTags().get(i + 1).getTag());
				taggedUrl.getTags().get(i + 1).setTag((TagsDTO) tag);
				// taggedUrl.getTags().get(i) = insert(taggedUrl.getTags().get(i + 1));
				// Sinon enregistre la succession des tags reli�s indirectement � l'url
				// Le parent du premier tag est celui qui est le plus proche de l'url
				LiaisonFoldedTagsDTO dto = new LiaisonFoldedTagsDTO(taggedUrl.getPath().getId(),
						taggedUrl.getTags().get(i + 1).getId(), taggedUrl.getTags().get(i).getId());
				// Je ne r�cup�re pas l'id car inutlie ici.
				dao.insert(dto);
			}
		}
		logger.trace("Fin de l'inscription de l'url tagg�es");
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
						+ obtainedTaggedUrl.getUrl().getId()
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
