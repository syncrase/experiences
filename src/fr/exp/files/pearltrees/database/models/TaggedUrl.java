package fr.exp.files.pearltrees.database.models;

import java.util.ArrayList;

public class TaggedUrl {

	private Url url;
	private ArrayList<String> tags;

	public TaggedUrl(Url url, ArrayList<String> tags) {
		super();
		this.url = url;
		this.tags = tags;
	}

	public Url getUrl() {
		return url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public String getPath() {
		String returnedString = "";
		for (String s : tags) {
			returnedString += s + "/";
		}
		return returnedString;
	}

}
