package fr.exp.files.pearltrees.database.dao;

import fr.exp.files.pearltrees.database.models.IModel;

public interface DaoMustHave {

	public IModel exists(IModel model);

	public IModel insert(IModel model);

}
