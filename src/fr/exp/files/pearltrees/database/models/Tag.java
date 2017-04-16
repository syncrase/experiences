package fr.exp.files.pearltrees.database.models;

public class Tag {

	private int id;
	private String tag;

	public Tag(int id, String tag) {
		super();
		this.id = id;
		this.tag = tag;
	}

	public Tag(String tag) {
		super();
		this.tag = tag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
