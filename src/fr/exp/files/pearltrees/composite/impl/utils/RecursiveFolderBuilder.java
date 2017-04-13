package fr.exp.files.pearltrees.composite.impl.utils;

import java.net.MalformedURLException;

import fr.exp.files.pearltrees.composite.Element;
import fr.exp.files.pearltrees.composite.Elements;
import fr.exp.files.pearltrees.composite.impl.PearltreesComponent;
import fr.exp.files.pearltrees.composite.impl.PearltreesComposite;
import fr.exp.files.pearltrees.composite.impl.PearltreesLeaf;

public abstract class RecursiveFolderBuilder {

	private void buildObject(Elements allElements, PearltreesComposite currentFolder) {
		PearltreesComponent component = null;
		boolean lookForFolderContent = false;
		Element currentElement;
		String url, value;
		for (int i = 0; i < allElements.size(); i++) {
			currentElement = allElements.get(i);
			if (!lookForFolderContent) {
				if (currentElement.tagName().equals("h3")) {
					// Folder building
					component = new PearltreesComposite();
					((PearltreesComposite) component).setFolderName(currentElement.ownText());
					// Add the folder to the current folder
					currentFolder.addChildComponent(component);
					// See later for the folder filling
					lookForFolderContent = true;
				}
				if (currentElement.tagName().equals("a")) {
					url = currentElement.attr("href");
					value = currentElement.ownText();
					try {
						component = new PearltreesLeaf();
						((PearltreesLeaf) component).setUrl(url);
						currentFolder.addChildComponent(component);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			} else {
				// If I am here it's because I've found an h3 tag and I'm
				// waiting for the whole content in the dl tag
				if (currentElement.tagName().equals("dl")) {
					// L'objet entity à déjà été ajouté à la liste des
					// folders
					// L'appel récursif de buildObject oblige
					component.buildObject(currentElement.getAllElements());
					lookForFolderContent = false;
					// Go after all the previous computed 'wholeContent'
					i += currentElement.getAllElements().size();
				}
			}
		}
	}

}
