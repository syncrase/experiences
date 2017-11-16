package fr.exp.files.pearltrees.models;

import java.net.MalformedURLException;
import java.net.URL;

public class Url {

	private int id_url;
	private URL url;
	private String label;

	public Url(URL url, String label) {
		super();
		this.url = url;
		this.label = label;
	}

	public Url(int id_url, String url, String label) {
		super();

		this.id_url = id_url;
		this.label = label;
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public int getId_url() {
		return id_url;
	}

	public void setId_url(int id_url) {
		this.id_url = id_url;
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
