package fr.exp.files.pearltrees.database.models;

public class TagsDTO implements IModel {

	int id_tag;
	String tag;

	public TagsDTO(int id_tag, String tag) {
		super();
		this.id_tag = id_tag;
		this.tag = tag;
	}

	public TagsDTO() {
	}

	public TagsDTO(int id_tag_parent) {
		this.id_tag = id_tag_parent;
		this.tag = "";
	}

	public int getId_tag() {
		return id_tag;
	}

	public void setId_tag(int id_tag) {
		this.id_tag = id_tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String toString() {
		return this.getId_tag() + ":" + this.getTag();
	}
}
