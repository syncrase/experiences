package fr.exp.files.pearltrees.composite.impl;

public class PearltreesLeaf implements PearltreesComponent {

	private String url;

	@Override
	public String printAsHtml() {
		return null;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
