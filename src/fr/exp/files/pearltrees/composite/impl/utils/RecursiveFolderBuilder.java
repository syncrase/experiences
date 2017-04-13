package fr.exp.files.pearltrees.composite.impl.utils;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.exp.files.pearltrees.composite.impl.PearltreesComponent;
import fr.exp.files.pearltrees.composite.impl.PearltreesComposite;
import fr.exp.files.pearltrees.composite.impl.PearltreesLeaf;

public abstract class RecursiveFolderBuilder {

	/*
	 * Protected to be available for subclasses , PearltreesComposite
	 * parentFolder
	 */
	protected void buildObject(Elements allElements, PearltreesComposite parent) {
		PearltreesComponent component = null;
		boolean lookForFolderContent = false;
		Element currentElement;
		String url, urlLabel;
		for (int i = 0; i < allElements.size(); i++) {
			currentElement = allElements.get(i);
			if (!lookForFolderContent) {
				if (currentElement.tagName().equals("h3")) {
					// Folder building
					// first occurence the name is null
					if (parent.getFolderName() == null) {
						component = parent;
						((PearltreesComposite) component).setFolderName(currentElement.ownText());
					} else {
						// Add the folder to the current folder
						component = new PearltreesComposite();
						((PearltreesComposite) component).setFolderName(currentElement.ownText());
						parent.addChildComponent(component);
					}
					// See later for the folder filling
					lookForFolderContent = true;
				}
				if (currentElement.tagName().equals("a")) {
					url = currentElement.attr("href");
					urlLabel = currentElement.ownText();
					component = new PearltreesLeaf();
					((PearltreesLeaf) component).setUrl(url);
					((PearltreesLeaf) component).setLabel(urlLabel);
					((PearltreesComposite) this).addChildComponent(component);
				}
			} else {
				// If I am here it's because I've found an h3 tag and I'm
				// waiting for the whole content in the dl tag
				if (currentElement.tagName().equals("dl")) {
					// L'objet entity à déjà été ajouté à la liste des
					// folders
					// L'appel récursif de buildObject oblige
					((PearltreesComposite) component).buildObject(currentElement.getAllElements(),
							((PearltreesComposite) component));
					lookForFolderContent = false;
					// Go after all the previous computed 'wholeContent'
					i += currentElement.getAllElements().size();
				}
			}
		}
	}

}
