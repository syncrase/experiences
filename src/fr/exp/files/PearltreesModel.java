package fr.exp.files;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class PearltreesModel {

	private String name;
	private ArrayList<URL> urlList;
	private ArrayList<PearltreesModel> folderList;

	public PearltreesModel() {
		super();
	}

	public PearltreesModel(File pearlTreesExportToHtml) {

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

	public ArrayList<PearltreesModel> getFolderList() {
		return folderList;
	}

	public void setFolderList(ArrayList<PearltreesModel> folderList) {
		this.folderList = folderList;
	}

	public String toString() {
		return name + " contains " + urlList.size() + " urls and " + folderList.size() + " folders\n";
	}
}
