package fr.exp.files.pearltrees.composite;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import fr.exp.files.pearltrees.composite.impl.PearltreesComponent;
import fr.exp.files.pearltrees.composite.impl.PearltreesComposite;
import fr.exp.files.pearltrees.treestructure.Element;
import fr.exp.files.pearltrees.treestructure.Elements;
import fr.exp.files.pearltrees.treestructure.PearltreesEntity;
import fr.exp.files.pearltrees.treestructure.PearltreesFolder;
import fr.exp.files.pearltrees.treestructure.PearltreesUrl;

public class PearltreesConstructor {

	PearltreesComponent content;

	public PearltreesConstructor(String filePath) {
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

	private void buildObject(Elements allElements, PearltreesComposite currentFolder) {
		PearltreesComponent component = null;
		boolean lookForFolderContent = false;
		Element src;
		String url, value;
		for (int i = 0; i < allElements.size(); i++) {
			src = allElements.get(i);
			if (!lookForFolderContent) {
				if (src.tagName().equals("h3")) {
					component = new PearltreesComposite();
					component.setFolderName(src.ownText());
					currentFolder.add(component);

					// ((PearltreesFolder) entity).setIsFolder(true);
					// this.addEntity(entity);
					lookForFolderContent = true;
				}
				if (src.tagName().equals("a")) {
					url = src.attr("href");
					value = src.ownText();
					try {
						// entity = new PearltreesUrl(new URL(url));
						// ((PearltreesUrl) entity).setValue(value);
						// ((PearltreesUrl) entity).setIsFolder(false);
						// this.addEntity(entity);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			} else {
				// If I am here it's because I've found an h3 tag and I'm
				// waiting for the whole content in the dl tag
				if (src.tagName().equals("dl")) {
					// L'objet entity à déjà été ajouté à la liste des
					// folders
					this.buildObject(src.getAllElements());
					lookForFolderContent = false;
					// Go after all the previous computed 'wholeContent'
					i += src.getAllElements().size();
				}
			}
		}
	}
}
