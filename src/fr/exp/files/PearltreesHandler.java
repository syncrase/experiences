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
		updateData(filePath);
	}

	/**
	 * Extrait les données avec jsoup
	 * 
	 * @param filePath
	 */
	private void updateData(String filePath) {
		File input = new File(filePath);
		Document doc;
		try {
			doc = Jsoup.parse(input, "UTF-8");
			Elements allElements = doc.getAllElements();
			pearlTreesModel.buildObject(allElements);
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

	public void saveInDataBase() {
		try {
			// Ajout dans la base de données de chaque url associées à son tag
			// composé
			throw new Exception("saveInDataBase isn't yet implemented");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param string
	 * 
	 */
	public void writeHtmlFile(String filePath) {
		// File input = new File(filePath);
		// if (!input.exists()) {
		// try {
		// input.createNewFile();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// input.

		String html = generateHtml();
		// HERE, write this content to a file in the file system
		// How to?
		try {
			throw new Exception("saveInDataBase isn't yet implemented");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String generateHtml() {
		return "";
	}

	public String getTreeWithoutLeafs() {
		return pearlTreesModel.toString_asATree(0, true, false);
	}

	public String getTree() {
		return pearlTreesModel.toString_asATree(0, true, true);
	}

	public String getFoldedTags() {
		return pearlTreesModel.toString_listOfPaths("");
	}
}
