package fr.exp.files.pearltrees.database.models;

public class Tag {

	int id_tag;
	String tag;

	public Tag(int id_tag, String tag) {
		super();
		this.id_tag = id_tag;
		this.tag = tag;
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
