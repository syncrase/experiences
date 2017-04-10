package fr.exp.files;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class PearltreesHandler {

	private PearltreesModel pearlTreesModel;

	public PearltreesHandler(String filePath) {
		this.pearlTreesModel = new PearltreesModel();
		extractData(filePath);
	}

	/**
	 * Extrait les données avec jsoup
	 * 
	 * @param filePath
	 */
	private void extractData(String filePath) {
		File input = new File(filePath);
		Document doc;
		try {
			doc = Jsoup.parse(input, "UTF-8");
			Elements allElements = doc.getAllElements();
			pearlTreesModel.setWholeContent(allElements);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Based on a file path, this method compute the file to extract the needed
	 * data
	 */
	public PearltreesModel getPearlTreesModel() {
		return pearlTreesModel;
	}

	public File getHtmlFile() {
		try {
			throw new Exception("getHtmlFile isn't yet implemented");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveInDataBase() {
		try {
			throw new Exception("saveInDataBase isn't yet implemented");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
