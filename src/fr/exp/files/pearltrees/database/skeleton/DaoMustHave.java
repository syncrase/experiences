package fr.exp.files.pearltrees.database.skeleton;

public interface DaoMustHave {

	public DataTransfertObject exists(DataTransfertObject dto);

	public DataTransfertObject insert(DataTransfertObject dto);

	public DataTransfertObject getOrInsert(DataTransfertObject dto);

}
