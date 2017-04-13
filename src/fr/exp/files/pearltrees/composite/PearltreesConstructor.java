package fr.exp.files.pearltrees.composite;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import fr.exp.files.pearltrees.composite.impl.PearltreesComponent;
import fr.exp.files.pearltrees.composite.impl.PearltreesComposite;
import fr.exp.files.pearltrees.composite.impl.utils.RecursiveFolderBuilder;

public class PearltreesConstructor extends RecursiveFolderBuilder {

	PearltreesComponent content;

	private PearltreesConstructor(String filePath) {
		super();

		File input = new File(filePath);
		Document doc;
		try {
			doc = Jsoup.parse(input, "UTF-8");
			Elements allElements = doc.getAllElements();
			content = new PearltreesComposite();
//			((PearltreesComposite) content).setFolderName("root");
			this.buildObject(allElements, (PearltreesComposite) content);
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
