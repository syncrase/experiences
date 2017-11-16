package fr.exp.files.pearltrees.composite.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import fr.exp.files.pearltrees.models.FoldedTag;
import fr.exp.files.pearltrees.models.TaggedUrl;
import fr.exp.files.pearltrees.models.Url;

public class PearltreesLeaf implements INode {

	private String url;
	private String label;

	@Override
	public String getHtmlFormat(int depth) {
		String returnedString = "";
		// String tab = "";
		// for (int i = 0; i < depth; i++) {
		// tab += "\t";
		// }
		// returnedString += tab;
		// returnedString += "<DT><A HREF=\"" + this.getUrl() + "\"";
		// returnedString += " ADD_DATE=\"1482700435\">" + this.getLabel() + "</A>\n";
		// // returnedString += tab + this.getUrl() + "\n";

		return returnedString;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	// @Override
	// public String getFoldedTags(String path) {
	// return path + this.label + "\n";// path + "\n"
	// // + this.label;
	// }

	@Override
	public ArrayList<TaggedUrl> getFoldedTags(ArrayList<FoldedTag> path) {
		Url url = null;
		try {
			url = new Url(new URL(this.url), this.label);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		TaggedUrl taggedUrl = new TaggedUrl(url, path);
		ArrayList<TaggedUrl> returnedList = new ArrayList<TaggedUrl>();
		returnedList.add(taggedUrl);
		return returnedList;
	}

}
