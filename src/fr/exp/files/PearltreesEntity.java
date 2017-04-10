package fr.exp.files;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class PearltreesEntity {

	private boolean isFolder;
	// Accessible par toutes les sous-classes
	public ArrayList<PearltreesEntity> pearltreesExportData;

	public PearltreesEntity() {
		super();
		this.pearltreesExportData = new ArrayList<PearltreesEntity>();
	}

	public boolean isFolder() {
		return isFolder;
	}

	public void setIsFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	public String toString() {
		String returnedString = "";
		if (this.isFolder()) {
			int folders = 0, urls = 0;
			for (PearltreesEntity entity : this.pearltreesExportData) {
				if (entity.isFolder()) {
					folders++;
				} else {
					urls++;
				}
			}
			returnedString += ((PearltreesFolder) this).name + " contains " + urls + " urls and " + folders
					+ " folders\n";
		} else {
			returnedString += "\t" + ((PearltreesUrl) this).getValue() + "\n";
		}

		for (PearltreesEntity cursor : this.pearltreesExportData) {
			returnedString += cursor.toString();
		}
		return returnedString;
	}

	public void buildObject(Elements allElements) {
		PearltreesEntity entity = null;
		boolean lookForFolderContent = false;
		Element src;
		String url, value;
		for (int i = 0; i < allElements.size(); i++) {
			src = allElements.get(i);
			if (!lookForFolderContent) {
				if (src.tagName().equals("h3")) {
					entity = new PearltreesFolder();
					((PearltreesFolder) entity).setName(src.ownText());
					((PearltreesFolder) entity).setIsFolder(true);
					this.addEntity(entity);
					lookForFolderContent = true;
				}
				if (src.tagName().equals("a")) {
					url = src.attr("href");
					value = src.ownText();
					try {
						entity = new PearltreesUrl(new URL(url));
						((PearltreesUrl) entity).setValue(value);
						((PearltreesUrl) entity).setIsFolder(false);
						this.addEntity(entity);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			} else {
				// If I am here it's because I've found an h3 tag and I'm
				// waiting for the whole content in the dl tag
				if (src.tagName().equals("dl")) {
					// L'objet pearlTreesFolder à déjà été ajouté à la liste des
					// folders
					entity.buildObject(src.getAllElements());
					lookForFolderContent = false;
					// Go after all the previous computed 'wholeContent'
					i += src.getAllElements().size();
				}
			}
		}
	}

	private void addEntity(PearltreesEntity pearlTreesEntity) {
		pearltreesExportData.add(pearlTreesEntity);
	}

	/*
	 * PEARLTREES HANDLERS => Move to another file
	 */
	public String toString_asATree(int depth, boolean addComposite, boolean addLeaf) {
		String returnedString = "";
		String tab = "";
		tab = "";
		for (int i = 0; i < depth; i++) {
			tab += "\t";
		}
		if (this.isFolder() && addComposite) {
			returnedString += tab + ((PearltreesFolder) this).name + "/" + "\n";
		}
		for (PearltreesEntity entity : this.pearltreesExportData) {
			if (entity.isFolder()) {
				returnedString += entity.toString_asATree(depth + 1, addComposite, addLeaf);
			} else if (addLeaf) {
				tab = "";
				for (int i = 0; i < depth + 1; i++) {
					tab += "\t";
				}
				returnedString += tab + ((PearltreesUrl) entity).getUrl().toString() + "\n";
			}
		}
		return returnedString;
	}

	public String toString_htmlFormat(int depth) {
		String returnedString = "";
		String tab = "";
		for (int i = 0; i < depth; i++) {
			tab += "\t";
		}
		for (PearltreesEntity entity : this.pearltreesExportData) {
			if (entity.isFolder()) {
				returnedString += tab + "<DT><H3 FOLDED ADD_DATE=\"1364146937\">" + ((PearltreesFolder) this).name
						+ "</H3>\n";
				returnedString += tab + "<DD><DL><p>\n";
				returnedString += entity.toString_htmlFormat(depth + 1);
				returnedString += tab + "</DL><p>\n";
			} else {
				returnedString += tab;
				returnedString += "<DT><A HREF=\"" + ((PearltreesUrl) entity).getUrl().toString() + "\"";
				returnedString += " ADD_DATE=\"1482700435\">" + ((PearltreesUrl) entity).getValue() + "</A>\n";
				returnedString += tab + ((PearltreesUrl) entity).getUrl() + "\n";
			}
		}

		return returnedString;
	}

	public String toString_listOfPaths(String path) {
		String returnedString = "";
		if (this.isFolder()) {
			path += ((PearltreesFolder) this).name + "/";
			returnedString += ((PearltreesFolder) this).name + "/";
			returnedString += "\n";
		}
		for (PearltreesEntity entity : this.pearltreesExportData) {
			if (entity.isFolder()) {
				returnedString += path + ((PearltreesFolder) entity).toString_listOfPaths(path);
			}
		}
		return returnedString;
	}

}
