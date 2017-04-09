package fr.exp.files;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class PearltreesHandler {

	/**
	 * Based on a file path, this method compute the file to extract the needed
	 * data
	 */
	public PearltreesModel pearltreesTreeExtractor(String filePath) {

		PearltreesModel pearlTreesModel = new PearltreesModel();
		// String filePath = "files/pearltrees_export-25-12-2016.html";
		File input = new File(filePath);
		Document doc;
		try {
			doc = Jsoup.parse(input, "UTF-8");
			Elements allElements = doc.getAllElements();
			pearlTreesModel.setWholeContent(allElements);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pearlTreesModel;

	}

	
	
}
