package fr.exp.files;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PearltreesModel {

	private String name;
	private ArrayList<URL> urlList;
	private ArrayList<PearltreesModel> folderList;

	public PearltreesModel() {
		super();
		this.name = "ROOT_FOLDER";
		this.urlList = new ArrayList<URL>();
		this.folderList = new ArrayList<PearltreesModel>();

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<URL> getUrlList() {
		return urlList;
	}

	public void setUrlList(ArrayList<URL> urlList) {
		this.urlList = urlList;
	}

	public void addUrl(URL url) {
		if (this.urlList == null) {
			this.urlList = new ArrayList<URL>();
		}
		this.urlList.add(url);
	}

	public ArrayList<PearltreesModel> getFolderList() {
		return folderList;
	}

	public void addFolder(PearltreesModel pearltreesModel) {
		if (this.folderList == null) {
			this.folderList = new ArrayList<PearltreesModel>();
		}
		this.folderList.add(pearltreesModel);
	}

	public void setFolderList(ArrayList<PearltreesModel> folderList) {
		this.folderList = folderList;
	}

	public String toString() {
		String returnedString = "";
		returnedString += name + " contains " + urlList.size() + " urls and " + folderList.size() + " folders\n";
		for (PearltreesModel model : this.folderList) {
			returnedString += model.toString();
		}
		return returnedString;
	}

	public String toString_listOfPaths(String path) {
		String returnedString = "";

		if (this.folderList.size() != 0) {
			path += name + "/";
			// returnedString += returnedString.equals("") ? "" : "\n";

			returnedString += "\n";
			for (PearltreesModel model : this.folderList) {
				returnedString += path + model.toString_listOfPaths(path);
			}
		} else {
			returnedString += name + "/";
			returnedString += "\n";
		}

		return returnedString;
	}

	public String toString_asATree(int depth) {
		String returnedString = "";
		String tab = "";
		for (int i = 0; i < depth; i++) {
			tab += "\t";
		}
		if (this.folderList.size() != 0) {
			// path += name + "/";
			// returnedString += returnedString.equals("") ? "" : "\n";
			returnedString += tab + name + "/" + "\n";
			for (PearltreesModel model : this.folderList) {
				returnedString += model.toString_asATree(depth + 1);// tab +
				// returnedString += "\n";
			}
			// returnedString += "\n";
		} else {
			returnedString += tab + name + "/";
			returnedString += "\n";
		}

		return returnedString;
	}

	/**
	 * Recursive method to construct the tree from the html file
	 * 
	 * @param allElements
	 */
	public void setWholeContent(Elements allElements) {
		PearltreesModel pearlTreesModel = new PearltreesModel();
		boolean lookForFolderContent = false;
		Element src;
		String url;
		URL formattedURL;
		for (int i = 0; i < allElements.size(); i++) {
			src = allElements.get(i);
			if (!lookForFolderContent) {
				if (src.tagName().equals("h3")) {
					pearlTreesModel = new PearltreesModel();
					pearlTreesModel.setName(src.ownText());
					this.addFolder(pearlTreesModel);
					lookForFolderContent = true;
				}
				if (src.tagName().equals("a")) {
					url = src.attr("href");
					try {
						formattedURL = new URL(url);
						this.addUrl(formattedURL);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			} else {
				// If I am here it's because I've found an h3 tag and I'm
				// waiting for the whole content
				if (src.tagName().equals("dl")) {
					pearlTreesModel.setWholeContent(src.getAllElements());
					lookForFolderContent = false;
					// Go after all the previous computed 'wholeContent'
					i += src.getAllElements().size();
				}
			}
		}
	}

}
