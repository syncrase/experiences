package fr.exp.files.pearltrees.database.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import fr.exp.databases.mysql.DBConnection;
import fr.exp.databases.mysql.DBInfo;
import fr.exp.files.pearltrees.database.models.IModel;
import fr.exp.files.pearltrees.database.models.LiaisonFoldedTagsDTO;

public class LiaisonFoldedTagsDAO extends DAO_absract {
	private ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("fr.exp.files.pearltrees");

	public LiaisonFoldedTagsDAO(IModel model) {
	}

	@Override
	public IModel exists(IModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModel insert(IModel model) {
		LiaisonFoldedTagsDTO dto = (LiaisonFoldedTagsDTO) model;
		try {

			PreparedStatement insertIntoLiaisonFoldedTagsStatement = DBConnection.getPreparedStatement("insert into "
					+ DBInfo.DBName + ".liaison_folded_tags (id_path,id_tag,id_parent_tag) values (?,?,?)");
			insertIntoLiaisonFoldedTagsStatement.setInt(1, dto.getPath());
			insertIntoLiaisonFoldedTagsStatement.setInt(2, dto.getTag());
			insertIntoLiaisonFoldedTagsStatement.setInt(3, dto.getParent());
			insertIntoLiaisonFoldedTagsStatement.executeUpdate();
			dto.setId(getLastInsertedId("id_liaison_folded_tags", "liaison_folded_tags"));
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Fail to get or insert", e);
		}
		return dto;
	}

}
