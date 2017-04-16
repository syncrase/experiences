package fr.exp.files.pearltrees.database.models;

import java.util.ArrayList;

public class TaggedUrl {

	private int id;
	private Url url;
	private ArrayList<Tag> tags;

	public TaggedUrl(Url url, ArrayList<Tag> tags) {
		super();
		this.url = url;
		this.tags = tags;
	}

	public int getId() {
		return id;
	}

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

	public String getPath() {
		String returnedString = "";
		for (Tag s : tags) {
			returnedString += s.getTag() + "/";
		}
		return returnedString;
	}

}
