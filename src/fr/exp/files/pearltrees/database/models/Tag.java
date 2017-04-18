package fr.exp.files.pearltrees.database.models;

public class Tag {

	private int id_tag;
	private String tag;
	private int id_parent_tag;

	public Tag(int id, String tag) {
		super();
		this.id_tag = id;
		this.tag = tag;
	}

	public Tag(String tag) {
		super();
		this.tag = tag;
	}

	public int getId() {
		return id_tag;
	}

	public void setId(int id) {
		this.id_tag = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
