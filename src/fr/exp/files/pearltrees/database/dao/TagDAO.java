package fr.exp.files.pearltrees.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import fr.exp.databases.mysql.DBConnection;
import fr.exp.databases.mysql.DBInfo;
import fr.exp.files.pearltrees.database.models.IModel;
import fr.exp.files.pearltrees.database.models.TagsDTO;

public class TagDAO extends DaoMeta {

	private IModel tag;

	protected TagDAO(IModel model) {
		this.tag = model;
	}

	/**
	 * Check in the table 'tags' if the tagName exists. If it does, return the id.
	 * If it doesn't, return 0.
	 * 
	 * @param tagName
	 * @return if( tagName exists in 'tags') tagId else 0
	 */
	@Override
	public IModel exists(IModel tag) {
		this.tag = tag;
		logger.trace("Request tag id for : {}", tag);
		ResultSet resultSet;
		String query = "";
		query += "SELECT * FROM tags WHERE tag = \"" + ((TagsDTO) this.tag).getTag() + "\"";
		try {
			resultSet = DBConnection.executeQuery(query);
			if (resultSet.next()) {
				int id_tag = (int) resultSet.getInt("id_tag");
				((TagsDTO) this.tag).setId_tag(id_tag);
				logger.trace("tag id for {} is {}", ((TagsDTO) this.tag).getTag(), id_tag);
				return tag;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Unable to request tag id for : {}", ((TagsDTO) this.tag).getTag());
		}
		return this.tag;
	}

	/**
	 * Récupère l'id du tag s'il existe, ou insère dans la base de données. Garanti
	 * sans doublon
	 * 
	 * @param tag
	 * @return Un tag avec l'id du tag
	 * @throws SQLException
	 */
	@Override
	public IModel insert(IModel tag) {
		try {

			this.tag = exists(((TagsDTO) tag));
			if (((TagsDTO) this.tag).getId_tag() == 0) {
				// ou le créer s'il n'existe pas
				PreparedStatement insertIntoTagsStatement = DBConnection
						.getPreparedStatement("insert into " + DBInfo.DBName + ".tags (tag) values (?)");
				insertIntoTagsStatement.setString(1, ((TagsDTO) this.tag).getTag());
				insertIntoTagsStatement.executeUpdate();

				((TagsDTO) this.tag).setId_tag(getLastInsertedId("id_tag", "tags"));
			}
		} catch (SQLException e) {
			logger.error("Unable to insert the tag {}", tag.toString());
			e.printStackTrace();
		}
		return this.tag;
	}
}
