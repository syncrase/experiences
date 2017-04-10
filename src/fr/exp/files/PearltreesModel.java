package fr.exp.files;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PearltreesModel {

	private String name;
	private ArrayList<URL> urlList;
	private ArrayList<PearltreesModel> folderList;

	// private String tab = ""; -> le mieux est d'utiliser une map<int, String>
	// pour garder les tab associées à la profondeur et de n'instancier qu'une
	// fois une certaine tabulation pour ne pas recomposer à chaque ligne la
	// String de tab

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

	public String toString_asATree(int depth, boolean addComposite, boolean addLeaf) {
		String returnedString = "";
		String tab = "";
		tab = getTabulation(depth);
		if (this.folderList.size() != 0) {
			if (addComposite) {
				returnedString += tab + name + "/" + "\n";
			}
			for (PearltreesModel model : this.folderList) {
				returnedString += model.toString_asATree(depth + 1, addComposite, addLeaf);// tab
			}
		} else {
			returnedString += tab + name + "/";
			returnedString += "\n";
		}

		if (addLeaf && this.urlList.size() != 0) {
			tab = getTabulation(depth + 1);
			for (URL url : this.urlList) {
				returnedString += tab + url.toString() + "\n";
			}
		}
		return returnedString;
	}

	private String getTabulation(int depth) {
		String tab = "";
		for (int i = 0; i < depth; i++) {
			tab += "\t";
		}
		return tab;
	}

	/**
	 * Recursive method to construct the tree from the html file
	 * 
	 * @param allElements
	 */
	public void buildObject(Elements allElements) {
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
					pearlTreesModel.buildObject(src.getAllElements());
					lookForFolderContent = false;
					// Go after all the previous computed 'wholeContent'
					i += src.getAllElements().size();
				}
			}
		}
	}

	public String toString_asAnHtml() {
		String returnedString = "";
		String tab = "";
		// for (int i = 0; i < 4; i++) {
		// tab += "\t";
		// }

		/*
		 * Algorithme à implémenter
		 * 
		 */
		if (this.folderList.size() != 0) {
			// path += name + "/";
			// returnedString += returnedString.equals("") ? "" : "\n";
			returnedString += tab + name + "/" + "\n";
			for (PearltreesModel model : this.folderList) {

				// returnedString += "\n";
			}
			// returnedString += "\n";
		} else {
			returnedString += tab + name + "/";
			returnedString += "\n";
		}

		return returnedString;
	}
}
