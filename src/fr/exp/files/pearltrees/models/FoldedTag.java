package fr.exp.files.pearltrees.models;

import java.util.LinkedList;

public class FoldedTag {

	private int id_tag;
	private String tag;
	private FoldedTag parentTag;

	public FoldedTag(int id, String tag) {
		super();
		this.id_tag = id;
		this.tag = tag;
	}

	public FoldedTag(String tag) {
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

	public FoldedTag getParentTag() {
		return parentTag;
	}

	public void setParentTags(FoldedTag parentTag) {
		this.parentTag = parentTag;
	}

	/**
	 * Only used for the database import. Doesn't work with the result of the
	 * getFoldedTags method.
	 * 
	 * @return
	 */
	public LinkedList<FoldedTag> getFullPath() {

		LinkedList<FoldedTag> parentsTags = new LinkedList<FoldedTag>();
		FoldedTag t;
		t = parentTag;
		while (t != null) {
			parentsTags.add(t);
			t = t.parentTag;
		}

		return parentsTags;
	}

}
