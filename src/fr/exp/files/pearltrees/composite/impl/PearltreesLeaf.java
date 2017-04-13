package fr.exp.files.pearltrees.composite.impl;

public class PearltreesLeaf implements PearltreesComponent {

	private String url;
	private String label;

	@Override
	public String printAsHtml(int depth) {
		String returnedString = "";
		String tab = "";
		for (int i = 0; i < depth; i++) {
			tab += "\t";
		}
		returnedString += tab;
		returnedString += "<DT><A HREF=\"" + this.getUrl() + "\"";
		returnedString += " ADD_DATE=\"1482700435\">" + this.getLabel() + "</A>\n";
		// returnedString += tab + this.getUrl() + "\n";

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

}
