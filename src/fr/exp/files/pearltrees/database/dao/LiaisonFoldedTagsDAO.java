package fr.exp.files.pearltrees.database.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import fr.exp.databases.mysql.DBConnection;
import fr.exp.databases.mysql.DBInfo;
import fr.exp.files.pearltrees.database.dto.LiaisonFoldedTagsDTO;
import fr.exp.files.pearltrees.database.skeleton.DaoMeta;
import fr.exp.files.pearltrees.database.skeleton.DataTransfertObject;

public class LiaisonFoldedTagsDAO extends DaoMeta {

	public LiaisonFoldedTagsDAO(DataTransfertObject model) {
	}

	@Override
	public DataTransfertObject exists(DataTransfertObject model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataTransfertObject insert(DataTransfertObject model) {
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
			this.logger.error("Fail to get or insert", e);
		}
		return dto;
	}

}
