package fr.exp.files.pearltrees.metamodels;

import java.util.LinkedList;

import org.slf4j.LoggerFactory;

import fr.exp.files.pearltrees.database.dto.TagsDTO;

public class FoldedTag {
	public ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("fr.exp.files.pearltrees");

	private TagsDTO tag;
	private FoldedTag parentTag;

	public FoldedTag(int id, String tag) {
		super();
		this.tag = new TagsDTO(id, tag);
	}

	public FoldedTag(TagsDTO tag) {
		super();
		this.tag = tag;
	}

	public FoldedTag(String tag) {
		super();
		this.tag = new TagsDTO(0, tag);
		// this.parentTag = new TagsDTO();
	}

	public String getTagName() {
		return tag.getTag();
	}

	public TagsDTO getTag() {
		return tag;
	}

	public void setTagName(String tag) {
		this.tag.setTag(tag);
	}

	public void setTag(TagsDTO tag) {
		this.tag = tag;
	}

	public int getId() {
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
