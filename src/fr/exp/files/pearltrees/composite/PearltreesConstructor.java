package fr.exp.files.pearltrees.composite;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import fr.exp.files.pearltrees.composite.impl.PearltreesComponent;
import fr.exp.files.pearltrees.composite.impl.PearltreesComposite;
import fr.exp.files.pearltrees.treestructure.PearltreesEntity;
import fr.exp.files.pearltrees.treestructure.PearltreesFolder;
import fr.exp.files.pearltrees.treestructure.PearltreesUrl;

public class PearltreesConstructor {

	PearltreesComponent content;

	private PearltreesConstructor(String filePath) {
		super();

		File input = new File(filePath);
		Document doc;
		try {
			doc = Jsoup.parse(input, "UTF-8");
			Elements allElements = doc.getAllElements();
			content = new PearltreesComposite();
			this.buildObject(allElements, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static PearltreesComponent getComponent(String filePath) {
		PearltreesConstructor contructor = new PearltreesConstructor(filePath);
		return contructor.getContent();
	}

	private PearltreesComponent getContent() {
		return content;
	}

}
