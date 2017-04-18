package fr.exp.files.pearltrees.database.models;

import java.util.ArrayList;

public class TaggedUrl {

	@Deprecated
	private int id;
	private Url url;
	private ArrayList<Tag> tags;
	private int path;

	public TaggedUrl(Url url, ArrayList<Tag> tags) {
		super();
		this.url = url;
		this.tags = tags;
	}

	public TaggedUrl(Url url, Tag tag, int path) {
		super();
		this.url = url;
		// if (this.tags == null)
		this.tags = new ArrayList<Tag>();
		this.tags.add(tag);
		this.path = path;
	}

	@Deprecated
	public int getId() {
		return id;
	}

	@Deprecated
	public void setId(int id) {
		this.id = id;
	}

	public Url getUrl() {
		return url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}

	public String getFullPath() {
		String returnedString = "";
		for (Tag s : tags) {
			returnedString += s.getTag() + "/";
		}
		return returnedString;
	}

	public void setPath(int path) {
		this.path = path;
	}

	public int getPath() {
		return path;
	}

	/**
	 * Ajoute un tag dans la liste de tag.
	 * @param tag
	 * @param id_parent_tag
	 */
	public void addTag(Tag tag, int id_parent_tag) {
		tags.add(tag);
		
	}

}
