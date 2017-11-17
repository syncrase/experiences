package fr.exp.files.pearltrees.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.exp.databases.mysql.DBConnection;
import fr.exp.databases.mysql.DBInfo;
import fr.exp.files.pearltrees.database.dto.UrlsDTO;
import fr.exp.files.pearltrees.database.skeleton.DaoMeta;
import fr.exp.files.pearltrees.database.skeleton.DataTransfertObject;

public class UrlsDAO extends DaoMeta {

	private DataTransfertObject url;

	public UrlsDAO(DataTransfertObject model) {
		this.url = model;
	}

	public DataTransfertObject insert(DataTransfertObject url) {
		this.url = (UrlsDTO) url;
		PreparedStatement insertIntoTagsStatement = DBConnection
				.getPreparedStatement("insert into " + DBInfo.DBName + ".urls (url,label) values (?, ?)");
		try {
			insertIntoTagsStatement.setString(1, ((UrlsDTO) this.url).getUrl().toString());
			insertIntoTagsStatement.setString(2, ((UrlsDTO) this.url).getLabel());
			logger.trace("Execute update {}", insertIntoTagsStatement);
			insertIntoTagsStatement.executeUpdate();

			((UrlsDTO) this.url).setId_url(getLastInsertedId("id_url", "urls"));
			logger.trace("Update success {}", this.url.toString());
			return this.url;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Fail to update", e);
		}
		return this.url;
	}

	/**
	 * Récupère une Url en base de données à partir de l'url et du label
	 * 
	 * @param url
	 *            Url contenant url ET label
	 * @return L'objet url complété de son id en base de données, ou pas s'il
	 *         n'existe pas.
	 */
	public DataTransfertObject exists(DataTransfertObject url) {
		ResultSet resultSet;
		String query = "";
		query += "SELECT * FROM urls WHERE url = \"" + ((UrlsDTO) this.url).getUrl() + "\" & label = \""
				+ ((UrlsDTO) this.url).getLabel() + "\"";
		try {
			resultSet = DBConnection.executeQuery(query);
			if (resultSet.next()) {
				int id_url = (int) resultSet.getInt("id_url");
				// logger.trace("tag id for {} is {}", tagName, tag_id);
				((UrlsDTO) this.url).setId_url(id_url);
				return url;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Unable to check if {} exists", ((UrlsDTO) this.url));
		}
		return this.url;
	}
}
