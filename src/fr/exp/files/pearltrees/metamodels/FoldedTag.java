package fr.exp.files.pearltrees.metamodels;

import java.util.LinkedList;

import fr.exp.files.pearltrees.database.models.Tag;

public class FoldedTag {

	// private int id_tag;
	// private String tag;
	private Tag tag;
	private FoldedTag parentTag;

	public FoldedTag(int id, String tag) {
		super();
		this.tag = new Tag(id, tag);
	}

	public FoldedTag(Tag tag) {
		super();
		this.tag = tag;
	}

	public FoldedTag(String tag) {
		super();
		this.tag = new Tag(0, tag);
	}

	public String getTagName() {
		return tag.getTag();
	}
	public Tag getTag() {
		return tag;
	}

	public void setTagName(String tag) {
		this.tag.setTag(tag);
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
	public int getId_tag() {
		return tag.getId_tag();
	}

	public void setId_tag(int id_tag) {
		tag.setId_tag(id_tag);
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
