package fr.exp.files.pearltrees.database.models;

import java.util.ArrayList;

public class TaggedUrl {

	private Url url;
	private ArrayList<FoldedTag> tags;
	private int path;

	public TaggedUrl(Url url, ArrayList<FoldedTag> tags) {
		super();
		this.url = url;
		this.tags = tags;
	}

	public TaggedUrl(Url url, FoldedTag tag, int path) {
		super();
		this.url = url;
		this.tags = new ArrayList<FoldedTag>();
		this.tags.add(tag);
		this.path = path;
	}

	public Url getUrl() {
		return url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}

	public ArrayList<FoldedTag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<FoldedTag> tags) {
		this.tags = tags;
	}

	public void setPath(int path) {
		this.path = path;
	}

	public int getPath() {
		return path;
	}

	/**
	 * Ajoute un tag dans la liste de tag.
	 * 
	 * @param tag
	 * @param id_parent_tag
	 */
	public void addTag(FoldedTag tag, int id_parent_tag) {
		tags.add(tag);

	}

}
