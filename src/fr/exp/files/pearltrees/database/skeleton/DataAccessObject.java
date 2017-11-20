package fr.exp.files.pearltrees.database.skeleton;

import fr.exp.files.pearltrees.database.dao.LiaisonFoldedTagsDAO;
import fr.exp.files.pearltrees.database.dao.LiaisonTagUrlDAO;
import fr.exp.files.pearltrees.database.dao.PathsDAO;
import fr.exp.files.pearltrees.database.dao.TagsDAO;
import fr.exp.files.pearltrees.database.dao.UrlsDAO;
import fr.exp.files.pearltrees.database.dto.*;

public class DataAccessObject implements DaoMustHave {

	private DaoMustHave dao;

	public DataAccessObject() {

	}

	@Override
	public DataTransfertObject exists(DataTransfertObject dto) {
		selectDao(dto);
		return dao.exists(dto);
	}

	@Override
	public DataTransfertObject insert(DataTransfertObject dto) {
		selectDao(dto);
		return dao.insert(dto);
	}

	@Override
	public DataTransfertObject getOrInsert(DataTransfertObject dto) {
		selectDao(dto);
		return dao.getOrInsert(dto);
	}

	/**
	 * Instancie le bon dao en fonction du type qu'il doit traiter
	 * 
	 * @param dto
	 */
	private void selectDao(DataTransfertObject dto) {
		if (dto instanceof TagsDTO) {
			dao = new TagsDAO(dto);
			return;
		}
		if (dto instanceof UrlsDTO) {
			dao = new UrlsDAO(dto);
			return;
		}
		if (dto instanceof PathsDTO) {
			dao = new PathsDAO(dto);
			return;
		}
		if (dto instanceof LiaisonTagUrlDTO) {
			dao = new LiaisonTagUrlDAO(dto);
			return;
		}
		if (dto instanceof LiaisonFoldedTagsDTO) {
			dao = new LiaisonFoldedTagsDAO(dto);
			return;
		}
	}

}
