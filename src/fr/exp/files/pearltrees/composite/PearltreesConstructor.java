package fr.exp.files.pearltrees.composite;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;

import fr.exp.files.pearltrees.composite.impl.PearltreesComponent;
import fr.exp.files.pearltrees.composite.impl.PearltreesComposite;
import fr.exp.files.pearltrees.composite.impl.utils.RecursiveFolderBuilder;

public class PearltreesConstructor extends RecursiveFolderBuilder {

	PearltreesComponent content;

	public static ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("fr.exp.files.pearltrees");

	private PearltreesConstructor(String filePath) {
		super();
		logger.trace("Parse the pearltrees file: {}", filePath);

		File input = new File(filePath);
		Document doc;
		try {
			doc = Jsoup.parse(input, "UTF-8");
			Elements allElements = doc.getAllElements();
			content = new PearltreesComposite();
			logger.trace("Begin to construct the content representative objet");
			this.buildObject(allElements, (PearltreesComposite) content);
			logger.trace("Finish to construct the content representative objet");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Unable to parse the file: {}", filePath);
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
