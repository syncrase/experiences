package fr.exp.files.dbmodels;

public class Url {

	private int id;
	private String url;

	public Url() {
		super();
		this.id = 0;
		this.url = "";
	}

	public Url(String url) {
		this.id = 0;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
