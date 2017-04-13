package fr.exp.files.pearltrees;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import fr.exp.files.pearltrees.treestructure.PearltreesFolder;

public class PearltreesFacade {

	private PearltreesFolder pearlTreesExportData;

	public PearltreesFacade(String filePath) {
		this.pearlTreesExportData = new PearltreesFolder();
		// this.pearlTreesEntity = new PearltreesEntity();
		updateData(filePath);
	}

	/**
	 * Based on a file path, this method compute the file to extract the needed
	 * data
	 */
	public PearltreesFolder getPearlTreesModel() {
		return pearlTreesExportData;
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

	public String toString() {
		return pearlTreesExportData.toString();
		// return pearlTreesEntity.toString();
		// return pearltreesStructureBuilder.toString();
	}

	/**
	 * @param string
	 * 
	 */
	public void writeHtmlFile(String filePath) {
		Path path = FileSystems.getDefault().getPath(filePath);
		String html = generateHtml();
		Charset charset = Charset.forName("UTF-8");
		// The BufferedWriter use require to set the project compliance to 1.7
		try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
			writer.write(html, 0, html.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

	public String getTreeWithoutLeafs() {
		return pearlTreesExportData.toString_asATree(0, true, false);
	}

	public String getTree() {
		return pearlTreesExportData.toString_asATree(0, true, true);
	}

	public String getFoldedTags() {
		return pearlTreesExportData.toString_listOfPaths("");
	}

	/*
	 * PRIVATES METHODS
	 */

	// /**
	// * Extrait les données avec jsoup
	// *
	// * @param filePath
	// */
	// private void updateData(String filePath) {
	// // TODO L'implémenter avec w3c puis voir pour implémenter le pattern
	// // pour ajouter un comportement
	// //
	// https://docs.oracle.com/javase/7/docs/api/org/w3c/dom/package-summary.html
	// File input = new File(filePath);
	// Document doc;
	// try {
	// doc = Jsoup.parse(input, "UTF-8");
	// Elements allElements = doc.getAllElements();
	// pearlTreesExportData.buildObject(allElements);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * Generate the html in order to be as near as possible of the pearltrees
	 * export html files
	 * 
	 * @return html file source code
	 */
	private String generateHtml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html>\n");
		sb.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n");
		sb.append("<!--This is an automatically generated file.\n");
		sb.append("It will be read and overwritten.\n");
		sb.append("Do Not Edit! -->\n");
		sb.append("<Title>Pearltrees Export</Title>\n");
		sb.append("<H1>Pearltrees Export</H1>\n");
		sb.append("<DL><p>\n");
		sb.append(pearlTreesExportData.toString_htmlFormat(0));
		sb.append("</DL><p>\n");
		return sb.toString();
	}

	public String getHtml() {
		return generateHtml();
	}

}
