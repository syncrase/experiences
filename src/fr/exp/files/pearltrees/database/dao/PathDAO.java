package fr.exp.files.pearltrees.database.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import fr.exp.databases.mysql.DBConnection;
import fr.exp.databases.mysql.DBInfo;
import fr.exp.files.pearltrees.database.models.IModel;
import fr.exp.files.pearltrees.database.models.PathsDTO;

public class PathDAO extends DAO_absract {
	private ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("fr.exp.files.pearltrees");
	IModel path;

	protected PathDAO(IModel model) {
		this.path = model;
	}

	@Override
	public IModel exists(IModel model) {
		logger.error("Unused method for {}", PathDAO.class);
		return null;
	}

	/**
	 * Crée une ligne dans la table path et renvoie son Id. Permet d'identifier un
	 * parent unique alors qu'il y en a plusieurs qui le sont potentiellement. Utile
	 * pour n'avoir qu'une inscription du tag en base de données quand il y a
	 * plusieurs fois son occurence
	 * 
	 * @return
	 */
	@Override
	public IModel insert(IModel model) {
		this.path = model;
		logger.trace("Request for a new id path to the database");
		PreparedStatement preparedStatement = DBConnection
				.getPreparedStatement("insert into " + DBInfo.DBName + ".paths (id_path) values (0)");
		try {
			preparedStatement.executeUpdate();
			((PathsDTO) this.path).setId(getLastInsertedId("id_path", "paths"));
			return this.path;
		} catch (SQLException e1) {
			e1.printStackTrace();
			logger.error("Unable to have a new id path, It's weird bro...", e1);
		}
		return this.path;
	}
}
