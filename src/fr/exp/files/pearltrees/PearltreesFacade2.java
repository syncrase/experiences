package fr.exp.files.pearltrees;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import fr.exp.files.pearltrees.composite.PearltreesConstructor;
import fr.exp.files.pearltrees.composite.impl.PearltreesComponent;

public class PearltreesFacade2 {

	private PearltreesComponent pearlTreesExportData;

	public PearltreesFacade2(String filePath) {
		// this.pearlTreesExportData = new PearltreesComponent();
		// this.pearlTreesEntity = new PearltreesEntity();
		pearlTreesExportData = PearltreesConstructor.getComponent(filePath);
	}

	public void saveInDataBase() {
		try {
			// Ajout dans la base de données de chaque url associées à son tag
			// composé
			// Dans la base de données
			// * Pour les urls taggées
			// 1 table urls (id, url, label)
			// 1 table tags (id, tag)
			// 1 table de liaison url_tags (id, id_url, id_tagS, --non id_tag_path-- )
			// 1 table de liaison tag_path (id, id_url_tagsS)
			
			// TODO
			// --> pour 1 url, plusieurs tags, 1 path
			// --> pour cette même url, même tag de base pour un autre path
			
			
			// * Pour les folded tags
			// 1 tables de liaison folded_tags pour enregistrer les path de tags
			// id_parent_tag, id_child_tag, id_tag_path
			// id_path permet de rendre unique un path (un certain tag peut
			// avoir plusieurs parents et/ou enfant => permet de rendre unique
			// un path)
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
		// return pearlTreesExportData.toString_asATree(0, true, false);
		return "";
	}

	public String getTree() {
		// return pearlTreesExportData.toString_asATree(0, true, true);
		return "";
	}

	public String getFoldedTags() {
		// return pearlTreesExportData.toString_listOfPaths("");
		return "";
	}

	public String getHtml() {
		return generateHtml();
	}

	/*
	 * PRIVATES METHODS
	 */

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
		sb.append(pearlTreesExportData.printAsHtml(0));
		sb.append("</DL><p>\n");
		return sb.toString();
	}

}
