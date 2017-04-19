package fr.exp.files.pearltrees;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import fr.exp.files.pearltrees.composite.PearltreesConstructor;
import fr.exp.files.pearltrees.composite.impl.PearltreesComponent;
import fr.exp.files.pearltrees.database.TaggedUrlDatabaseIO;
import fr.exp.files.pearltrees.database.models.FoldedTag;
import fr.exp.files.pearltrees.database.models.TaggedUrl;

public class PearltreesFacade2 {

	private PearltreesComponent pearlTreesExportData;

	public PearltreesFacade2(String filePath) {
		pearlTreesExportData = PearltreesConstructor.getComponent(filePath);
	}

	public void saveInDataBase() {
		try {
			// Ajout dans la base de données de chaque url associées à son tag
			// composé
			// Dans la base de données

			// Get all taggedUrls
			ArrayList<TaggedUrl> taggedUrlList = this.getFoldedTagsList();
			// Write each taggedUrl in db
			TaggedUrlDatabaseIO writer = new TaggedUrlDatabaseIO();
			for (TaggedUrl taggedUrl : taggedUrlList) {
				writer.writeTaggedUrl(taggedUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return pearlTreesExportData.toString();
	}

	/**
	 * @param string
	 * 
	 */
	public void writeHtmlFile(String filePath) {
		Path path = FileSystems.getDefault().getPath(filePath);
		String html = generateHtml();
		Charset charset = Charset.forName("UTF-8");
		// The BufferedWriter use requires to set the project compliance to 1.7
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
		ArrayList<TaggedUrl> taggedUrlList = this.getFoldedTagsList();
		String returnedString = "";
		for (TaggedUrl taggedUrl : taggedUrlList) {
			// TODO Faire la même chose pour tous les tags
			returnedString += taggedUrl.getTags().get(0).getFullPath() + taggedUrl.getUrl().getLabel() + "\n";
		}
		return returnedString;
	}

	public ArrayList<TaggedUrl> getFoldedTagsList() {
		return pearlTreesExportData.getFoldedTags(new ArrayList<FoldedTag>());
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
		sb.append(pearlTreesExportData.getHtmlFormat(0));
		sb.append("</DL><p>\n");
		return sb.toString();
	}

	public String loadFromDataBase() {
		// TODO Construction du pearltreesComponent à partir de la bdd

		// Parcours de toutes les urls pour les ajouter une a une à l'objet
		// pearlTreesExportData

		TaggedUrlDatabaseIO writer = new TaggedUrlDatabaseIO();

		// TODO faire en sorte que je reçoive un PearltreesComponent!!! Pas
		// possible, la nature du PearltreesComponent est une structure de
		// fichier => une url = un path
		// Possible si une url apparaît plusieurs fois. problème quand il n'y a
		// pas de tag de base!!!!
		// Pas possible d'utiliser PearltreesComponent
		// Permettra d'utiliser les autres méthodes de la façade + avoir à
		// disposition tous les IO pour construire l'objet
		ArrayList<TaggedUrl> taggedUrlList;
		taggedUrlList = writer.read();

		StringBuilder sb = new StringBuilder();
		for (TaggedUrl taggedUrl : taggedUrlList) {
			// TODO ici ça ne va pas. Chaque url peut avoir plusieurs folded
			// tags
			// Donc, récupérer le path pour chaque tag
			sb.append(taggedUrl.getTags().get(0).getFullPath() + taggedUrl.getUrl().getLabel() + "\n");
		}
		return sb.toString();
	}

	public void deleteAll() {
		TaggedUrlDatabaseIO writer = new TaggedUrlDatabaseIO();
		writer.deleteAll();
	}

}
