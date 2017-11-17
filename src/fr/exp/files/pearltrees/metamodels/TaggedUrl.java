package fr.exp.files.pearltrees.metamodels;

import java.util.ArrayList;

import fr.exp.files.pearltrees.database.models.*;

/**
 * Equivalent Java correspondant au model 'Url'
 * 
 * @author Pierre
 *
 */
public class TaggedUrl {

	private UrlsDTO url;
	private ArrayList<FoldedTag> tags;
	private PathsDTO path;

	public TaggedUrl(UrlsDTO url, ArrayList<FoldedTag> tags) {
		super();
		this.url = url;
		this.tags = tags;
	}

	public TaggedUrl(UrlsDTO url, FoldedTag tag, int path) {
		super();
		this.url = url;
		this.tags = new ArrayList<FoldedTag>();
		this.tags.add(tag);
		this.path = new PathsDTO(path);
	}

	public UrlsDTO getUrl() {
		return url;
	}

	public void setUrl(UrlsDTO url) {
		this.url = url;
	}

	public ArrayList<FoldedTag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<FoldedTag> tags) {
		this.tags = tags;
	}

	public void setPath(PathsDTO path) {
		this.path = path;
	}

	public void setPathId(int path) {
		this.path.setId(path);
	}

	public PathsDTO getPath() {
		return path;
	}

	public void setPath(IModel path) {
		this.path = (PathsDTO) path;
	}

	/**
	 * Ajoute un tag dans la liste de tag.
	 * 
	 * @param tag
	 * @param id_parent_tag
	 */
	public void addTag(FoldedTag tag, int id_parent_tag) {
		tags.add(tag);

	}

	public String getFullTagPath() {
		// TODO Récupérer tous les tags path pour cette url
		return "À implémenter";
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("url{");
		sb.append(url.getLabel());
		sb.append(";");
		sb.append(url.getUrl());
		sb.append("}");
		sb.append("tags{");
		for (FoldedTag ft : tags) {
			sb.append(ft.getTag());
			sb.append(",");
		}
		sb.append("}");
		return sb.toString();
	}

}
