package fr.exp.files.pearltrees.composite.impl;

import java.util.ArrayList;

import fr.exp.files.pearltrees.database.models.TaggedUrl;

public interface PearltreesComponent {

	public String getHtmlFormat(int depth);

	// public String getFoldedTags(String path);

	public ArrayList<TaggedUrl> getFoldedTags(ArrayList<String> path);
}
