package fr.exp.files.treestructure;

import java.net.URL;

public class PearltreesUrl extends PearltreesEntity {

	private int id;
	private URL url;
	private String value;

	public PearltreesUrl() {
		super();
		this.id = 0;
		this.value = "";
	}

	public PearltreesUrl(URL url) {
		this.id = 0;
		this.url = url;
	}

	public PearltreesUrl(URL url, String value) {
		this.id = 0;
		this.url = url;
		this.value = "";
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
