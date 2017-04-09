package fr.exp.files;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlExtractorDemo {

	public static void main(String[] args) {

		// obtient un objet repr�sentant mon fichier html
		// String fileContent = FilesHandler.getHtmlFileContent(filePath);
		// System.out.println(fileContent != "" ? fileContent : "Unable to get
		// the file content");

		// fileContent = FilesHandler.getPearlTreesExportContent(filePath);
		// System.out.println(fileContent != "" ? fileContent : "Unable to get
		// the file content");

		/*
		 * Les algos
		 *
		 * D�but Je croise un H3 => new HtmlModel() => Ajout du DL correspondant
		 * Go � la fin du DL ajout� (pas possible ). Repeat pour tous les h3 Fin
		 * 
		 * D�but Je croise un H3 => new HtmlModel() => depth ++ ??? Je sors d'un
		 * H3 => depth -- Fin
		 * 
		 * 
		 */
		// HtmlExtractorDemo.test3();
		PearltreesHandler pearltreesHandler = new PearltreesHandler();
		PearltreesModel pearlTreesModel = pearltreesHandler
				.pearltreesTreeExtractor("files/pearltrees_export-25-12-2016.html");
		 System.out.println(pearlTreesModel.toString());
		// System.out.println(pearlTreesModel.toString_listOfPaths(""));
//		System.out.println(pearlTreesModel.toString_asATree(0));

		// Sauvegarde du r�ultat en base de donn�es
		// Structure de dossier -> le tag complexe de chaque url doit �tre son
		// path dans la struture de dossiers
		// 1- Afficher toutes les urls avec son tag complexe
		// 2- Enregistrer toutes les url dans la base de donn�es
		// 3- R�cup�rer les objets dans la BDD ? GetTags()? GetUrls()?
		// GetUrlTaggedWith(Tag tag)? GetUrlTaggedWith(Tag[] tags)?
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}

}
