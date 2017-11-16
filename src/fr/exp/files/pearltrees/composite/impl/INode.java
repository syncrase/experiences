package fr.exp.files.pearltrees.composite.impl;

import java.util.ArrayList;

import fr.exp.files.pearltrees.metamodels.FoldedTag;
import fr.exp.files.pearltrees.metamodels.TaggedUrl;

/**
 * This interface gathers methods that must be implemented by tree elements
 * 
 * @author Pierre
 *
 */
public interface INode {

	public String getHtmlFormat(int depth);

	// public String getFoldedTags(String path);

	public ArrayList<TaggedUrl> getFoldedTags(ArrayList<FoldedTag> path);
}
