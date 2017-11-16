package fr.exp.files.pearltrees.models;

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

	public String getFullTagPath() {
		// TODO R�cup�rer tous les tags path pour cette url
		return "� impl�menter";
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("url{");
		sb.append(url.getLabel());
		sb.append(";");
		sb.append(url.getUrl());
		sb.append("}");
		sb.append("tags{");
		for (FoldedTag ft : tags) {
			sb.append(ft.getTag());
			sb.append(",");
		}
		sb.append("}");
		return sb.toString();
	}
}