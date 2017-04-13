package fr.exp.files.pearltrees.composite.impl;

import java.util.ArrayList;
import java.util.List;

import fr.exp.files.pearltrees.composite.impl.utils.RecursiveFolderBuilder;

public class PearltreesComposite extends RecursiveFolderBuilder implements PearltreesComponent {

	private String folderName;

	// Collection of child Component
	// This is either a url or a folder
	private List<PearltreesComponent> childComponent = new ArrayList<PearltreesComponent>();

	@Override
	public String printAsHtml(int depth) {
		String returnedString = "";
		String tab = "";
		for (int i = 0; i < depth; i++) {
			tab += "\t";
		}
		returnedString += tab + "<DT><H3 FOLDED ADD_DATE=\"1364146937\">" + this.folderName + "</H3>\n";
		returnedString += tab + "<DD><DL><p>\n";
		for (PearltreesComponent component : childComponent) {
			returnedString += component.printAsHtml(depth + 1);
		}

		returnedString += tab + "</DL><p>\n";
		return returnedString;
	}

	// Adds the graphic to the composition.
	public void addChildComponent(PearltreesComponent component) {
		childComponent.add(component);
	}

	// Removes the graphic from the composition.
	public void removeChildComponent(PearltreesComponent component) {
		childComponent.remove(component);
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

}
