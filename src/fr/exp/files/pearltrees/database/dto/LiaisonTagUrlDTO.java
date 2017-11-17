package fr.exp.files.pearltrees.database.dto;

import fr.exp.files.pearltrees.database.skeleton.DataTransfertObject;

public class LiaisonTagUrlDTO implements DataTransfertObject {

	int id_liaison_url_tags;
	UrlsDTO url;
	TagsDTO tag;
	PathsDTO path;

	public LiaisonTagUrlDTO(int url, int tag, int path) {
		super();
		this.url = new UrlsDTO(url);
		this.tag = new TagsDTO(tag);
		this.path = new PathsDTO(path);
		this.id_liaison_url_tags = 0;
	}

	public int getId() {
		return id_liaison_url_tags;
	}

	public void setId(int id) {
		this.id_liaison_url_tags = id;
	}

	public int getUrl() {
		return url.getId();
	}

	public void setUrl(int url) {
		this.url.setId_url(url);
	}

	public int getTag() {
		return tag.getId_tag();
	}

	public void setTag(int tag) {
		this.tag.setId_tag(tag);
	}

	public int getPath() {
		return path.getId();
	}

	public void setPath(int path) {
		this.path.setId(path);
	}

}
