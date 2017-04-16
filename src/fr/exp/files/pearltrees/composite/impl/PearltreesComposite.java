package fr.exp.files.pearltrees.composite.impl;

import java.util.ArrayList;
import java.util.List;

import fr.exp.files.pearltrees.composite.impl.utils.RecursiveFolderBuilder;
import fr.exp.files.pearltrees.database.models.Tag;
import fr.exp.files.pearltrees.database.models.TaggedUrl;

public class PearltreesComposite extends RecursiveFolderBuilder implements PearltreesComponent {

	private String folderName;

	// Collection of child Component
	// This is either a url or a folder
	private List<PearltreesComponent> childComponent = new ArrayList<PearltreesComponent>();

	@Override
	public String getHtmlFormat(int depth) {
		String returnedString = "";
		String tab = "";
		for (int i = 0; i < depth; i++) {
			tab += "\t";
		}
		returnedString += tab + "<DT><H3 FOLDED ADD_DATE=\"1364146937\">" + this.folderName + "</H3>\n";
		returnedString += tab + "<DD><DL><p>\n";
		for (PearltreesComponent component : childComponent) {
			returnedString += component.getHtmlFormat(depth + 1);
		}

		returnedString += tab + "</DL><p>\n";
		return returnedString;
	}

	// @Override
	// public String getFoldedTags(String path) {
	// String returnedString = "";
	// path += this.folderName + "/";
	//// returnedString += path;// + this.folderName + "/"
	//// returnedString += "\n";
	// for (PearltreesComponent entity : this.childComponent) {
	// returnedString += entity.getFoldedTags(path);// + this.folderName +
	// // "/"
	// }
	// return returnedString;
	// }

	@Override
	public ArrayList<TaggedUrl> getFoldedTags(ArrayList<Tag> path) {
		ArrayList<TaggedUrl> taggedUrlList = new ArrayList<TaggedUrl>();
		ArrayList<Tag> tempPath;
		for (PearltreesComponent entity : this.childComponent) {
			tempPath = new ArrayList<Tag>();
			tempPath.addAll(path);
			tempPath.add(new Tag(folderName));
			taggedUrlList.addAll(entity.getFoldedTags(tempPath));
		}
		return taggedUrlList;
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
