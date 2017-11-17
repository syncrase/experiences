package fr.exp.files.pearltrees.database.models;

public class PathsDTO implements IModel {

	private int id_path;

	public PathsDTO(int path_id) {
		this.id_path = path_id;
	}

	public PathsDTO() {
	}

	public void setId(int id_path) {
		this.id_path = id_path;
	}

	public int getId() {
		return this.id_path;
	}

}
