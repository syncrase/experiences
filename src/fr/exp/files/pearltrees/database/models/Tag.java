package fr.exp.files.pearltrees.database.models;

public class Tag {

	private int id_tag;
	private String tag;
	private Tag parent_tag;

	public Tag(int id, String tag) {
		super();
		this.id_tag = id;
		this.tag = tag;
	}

	public Tag(String tag) {
		super();
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getId_tag() {
		return id_tag;
	}

	public void setId_tag(int id_tag) {
		this.id_tag = id_tag;
	}

	public Tag getParent_tag() {
		return parent_tag;
	}

	public void setParent_tag(Tag parent_tag) {
		this.parent_tag = parent_tag;
	}

}
