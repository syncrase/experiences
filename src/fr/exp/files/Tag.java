package fr.exp.files;

import java.util.ArrayList;

public class Tag {

	private int id;
	private String tag;
	private ArrayList<Tag> parents;
	private ArrayList<Tag> enfants;

	// public Tag() {
	// super();
	// this.id = 0;
	// this.tag = "";
	// }

	public Tag(String tag) {
		super();
		this.id = 0;
		this.tag = tag;
	}

	public Tag(int id) {
		super();
		this.id = id;
		this.tag = "bug id " + id;
	}

	public Tag(int id, String tag) {
		super();
		this.id = id;
		this.tag = tag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public ArrayList<Tag> getParents() {
		return parents;
	}

	public void setParents(ArrayList<Tag> parents) {
		this.parents = parents;
	}

	public ArrayList<Tag> getEnfants() {
		return enfants;
	}

	public void setEnfants(ArrayList<Tag> enfants) {
		this.enfants = enfants;
	}

}