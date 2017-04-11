package fr.exp.files.treestructure;

public class PearltreesFolder extends PearltreesEntity {

	// Public because I want to use it in the PearltreesEntity class
	public String name;

	public PearltreesFolder() {
		super();
		this.name = "ROOT_FOLDER";
		super.setIsFolder(true);
	}


	/**
	 * ACCESORS
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
