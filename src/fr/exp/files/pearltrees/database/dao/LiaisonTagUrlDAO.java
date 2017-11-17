package fr.exp.files.pearltrees.database.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import fr.exp.databases.mysql.DBConnection;
import fr.exp.databases.mysql.DBInfo;
import fr.exp.files.pearltrees.database.models.*;

public class LiaisonTagUrlDAO extends DAO_absract {
	private ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("fr.exp.files.pearltrees");

	
	public LiaisonTagUrlDAO(IModel model) {
	}

	@Override
	public IModel exists(IModel model) {
		logger.error("Unused method for {}", LiaisonTagUrlDAO.class);
		return null;
	}

	@Override
	public IModel insert(IModel model) {
		LiaisonTagUrlDTO dto = (LiaisonTagUrlDTO) model;
		try {

			PreparedStatement insertIntoLiaisonUrlTagStatement = DBConnection.getPreparedStatement(
					"insert into " + DBInfo.DBName + ".liaison_url_tags (id_url,id_tag,id_path) values (?,?,?)");
			insertIntoLiaisonUrlTagStatement.setInt(1, dto.getUrl());
			insertIntoLiaisonUrlTagStatement.setInt(2, dto.getTag());
			insertIntoLiaisonUrlTagStatement.setInt(3, dto.getPath());
			insertIntoLiaisonUrlTagStatement.executeUpdate();
			dto.setId(getLastInsertedId("id_liaison_url_tags", "liaison_url_tags"));
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Fail to get or insert", e);
		}
		return dto;
	}
}
