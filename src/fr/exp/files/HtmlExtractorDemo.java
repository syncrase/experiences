package fr.exp.files;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlExtractorDemo {

	public static void main(String[] args) {

		String filePath = "files/pearltrees_export-25-12-2016.html";

		// obtient un objet représentant mon fichier html
		// String fileContent = FilesHandler.getHtmlFileContent(filePath);
		// System.out.println(fileContent != "" ? fileContent : "Unable to get
		// the file content");

		// fileContent = FilesHandler.getPearlTreesExportContent(filePath);
		// System.out.println(fileContent != "" ? fileContent : "Unable to get
		// the file content");

		/*
		 * Les algos
		 *
		 * Début Je croise un H3 => new HtmlModel() => Ajout du DL correspondant
		 * Go à la fin du DL ajouté (pas possible ). Repeat pour tous les h3 Fin
		 * 
		 * Début Je croise un H3 => new HtmlModel() => depth ++ ??? Je sors d'un
		 * H3 => depth -- Fin
		 * 
		 * 
		 */

		/*
		 *************************************************************************************************
		 */
		File input = new File(filePath);
		try {

			Document doc = Jsoup.parse(input, "UTF-8");
			Elements allElements = doc.getAllElements();
			print("\nLines: %d", allElements.size());
			Element src;
			PearltreesHtmlExtractor extractor = new PearltreesHtmlExtractor();
			String outerHTML = "";
			boolean lookForContent = false;

			for (int i = 0; i < allElements.size(); i++) {
				src = allElements.get(i);
//				if (outerHTML.equals("")) {
				/*
				 * J'ai récupéré la dernière ligne de tout le dernier contenu. Tant que je ne suis pas arrivé à cette ligne, je ne touche à rien
				 */
					if (src.tagName().equals("h3") && !lookForContent) {
						extractor.setName(src.ownText());
						lookForContent = true;
						// print(" ************* %s **************",
						// src.ownText());
						// print("cssSelector: %s", src.cssSelector());
					}
					if (src.tagName().equals("dl") && lookForContent) {
						outerHTML = src.outerHtml();
						String[] tab = outerHTML.split("\n");
						int a = tab.length;

						extractor.setWholeContent(outerHTML);
						lookForContent = false;
						outerHTML = tab[tab.length - 1];
						// Je récupère la dernière ligne du html et tant que je
						// ne
						// l'ai pas croisé je ne suis pas à la recherche d'un H3

						// Dès que j'ai pris la liste je m'arrête car la liste
						// contient déjà déjà tous les éléments simples ainsi
						// que
						// les listes internes
						// break;
					}
//				}
			}
			PearltreesModel pearlTrees = extractor.perform();
			System.out.println(pearlTrees.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

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
