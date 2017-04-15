package fr.exp.files.pearltrees.database.models;

import java.net.URL;

public class Url {

	private URL url;
	private String label;

	public Url(URL url, String label) {
		super();
		this.url = url;
		this.label = label;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
