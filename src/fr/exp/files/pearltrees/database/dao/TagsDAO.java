package fr.exp.files.pearltrees.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.exp.databases.mysql.DBConnection;
import fr.exp.databases.mysql.DBInfo;
import fr.exp.files.pearltrees.database.dto.TagsDTO;
import fr.exp.files.pearltrees.database.skeleton.DaoMeta;
import fr.exp.files.pearltrees.database.skeleton.DataTransfertObject;

public class TagsDAO extends DaoMeta {

	private DataTransfertObject tag;

	public TagsDAO(DataTransfertObject model) {
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
	public DataTransfertObject exists(DataTransfertObject tag) {
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
	public DataTransfertObject insert(DataTransfertObject tag) {
		try {

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

	@Override
	public DataTransfertObject getOrInsert(DataTransfertObject dto) {
		return insert(exists(((TagsDTO) tag)));
	}
}
