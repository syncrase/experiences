package fr.exp.files.dbmodels;

import java.util.ArrayList;

import fr.exp.files.PearltreesUrl;

public class TaggedUrl {

	private PearltreesUrl url;
	private ArrayList<Tag> tags;

	public TaggedUrl() {
		super();
		this.url = new PearltreesUrl();
		this.tags = new ArrayList<Tag>();
	}

	public TaggedUrl(PearltreesUrl url, ArrayList<Tag> tags) {
		super();
		this.url = url;
		this.tags = tags;
	}

	public PearltreesUrl getUrl() {
		return url;
	}

	public void setUrl(PearltreesUrl url) {
		this.url = url;
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}

	public void addTag(int tagId) {
		this.tags.add(new Tag(tagId));
	}

	/**
	 * Add a tag only if the tag's id doesn't exist
	 * 
	 * @param tagId
	 * @param tagName
	 */
	public void addTag(int tagId, String tagName) {
		int i = 0;
		while (i < this.tags.size()) {
			if (this.tags.get(i).getId() == tagId) {
				return;
			}
			i++;
		}

		this.tags.add(new Tag(tagId, tagName));
	}

}
