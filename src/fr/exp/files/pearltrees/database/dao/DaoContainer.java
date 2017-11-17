package fr.exp.files.pearltrees.database.dao;

import fr.exp.files.pearltrees.database.models.*;

public class DaoContainer implements DAO_interface {

	private DAO_interface dao;

	public DaoContainer(IModel model) {
		if (model instanceof TagsDTO) {
			dao = new TagDAO(model);
		}
		if (model instanceof UrlsDTO) {
			dao = new UrlDAO(model);
		}
		if (model instanceof PathsDTO) {
			dao = new PathDAO(model);
		}
		if (model instanceof LiaisonTagUrlDTO) {
			dao = new LiaisonTagUrlDAO(model);
		}
		if (model instanceof LiaisonFoldedTagsDTO) {
			dao = new LiaisonFoldedTagsDAO(model);
		}
	}

	@Override
	public IModel exists(IModel model) {
		return dao.exists(model);
	}

	@Override
	public IModel insert(IModel model) {
		return dao.insert(model);
	}

}
