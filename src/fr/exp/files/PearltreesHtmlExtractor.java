package fr.exp.files;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PearltreesHtmlExtractor {

	private String name;
	private String wholeContent;

	public PearltreesHtmlExtractor(String name) {
		this.name = name;
	}

	public PearltreesHtmlExtractor() {
	}

	// public String getName() {
	// return name;
	// }

	public void setName(String name) {
		this.name = name;
	}

	// public String getWholeContent() {
	// return wholeContent;
	// }

	public void setWholeContent(String wholeContent) {
		this.wholeContent = wholeContent;
	}

	/**
	 * Dès que je tombe sur un folder, j'instancie un nouvel objet pour exécuter
	 * cette méthode
	 * 
	 * @return
	 */
	public PearltreesModel perform() {
		PearltreesModel pearlTrees = new PearltreesModel();
		pearlTrees.setName(name);
		
		Document doc = Jsoup.parse(wholeContent, "UTF-8");
		Elements allElements = doc.getAllElements();
		Element src;
		boolean lookForContent = false;
		PearltreesHtmlExtractor extractor = new PearltreesHtmlExtractor();
		
		for (int i = 0; i < allElements.size(); i++) {
			src = allElements.get(i);
			if (src.tagName().equals("h3") && !lookForContent) {
				lookForContent = true;
				extractor = new PearltreesHtmlExtractor();
				extractor.setName(src.ownText());
				// print(" ************* %s **************", src.ownText());
			}
			if (src.tagName().equals("dl") && lookForContent) {
				lookForContent = false;
				extractor.setWholeContent(src.outerHtml());
//				break;
			}
		}
		pearlTrees = extractor.perform();

		return pearlTrees;
	}

}
