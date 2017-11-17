package fr.exp.files.pearltrees.database.skeleton;

import fr.exp.files.pearltrees.database.dao.LiaisonFoldedTagsDAO;
import fr.exp.files.pearltrees.database.dao.LiaisonTagUrlDAO;
import fr.exp.files.pearltrees.database.dao.PathsDAO;
import fr.exp.files.pearltrees.database.dao.TagsDAO;
import fr.exp.files.pearltrees.database.dao.UrlsDAO;
import fr.exp.files.pearltrees.database.dto.*;

public class DataAccessObject implements DaoMustHave {

	private DaoMustHave dao;

	/**
	 * Instancie le bon dao en fonction du type qu'il doit traiter
	 * @param dto
	 */
	public DataAccessObject(DataTransfertObject dto) {
		if (dto instanceof TagsDTO) {
			dao = new TagsDAO(dto);
		}
		if (dto instanceof UrlsDTO) {
			dao = new UrlsDAO(dto);
		}
		if (dto instanceof PathsDTO) {
			dao = new PathsDAO(dto);
		}
		if (dto instanceof LiaisonTagUrlDTO) {
			dao = new LiaisonTagUrlDAO(dto);
		}
		if (dto instanceof LiaisonFoldedTagsDTO) {
			dao = new LiaisonFoldedTagsDAO(dto);
		}
	}

	@Override
	public DataTransfertObject exists(DataTransfertObject dto) {
		return dao.exists(dto);
	}

	@Override
	public DataTransfertObject insert(DataTransfertObject dto) {
		return dao.insert(dto);
	}

}
