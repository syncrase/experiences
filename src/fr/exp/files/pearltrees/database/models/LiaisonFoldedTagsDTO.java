package fr.exp.files.pearltrees.database.models;

/**
 * Les dto sont adapt�e � retourner exactement les valeurs � inscrire en bdd.
 * 
 * @author Pierre
 *
 */
public class LiaisonFoldedTagsDTO implements IModel {

	// En soit �a ne servira jamais � rien d'avoir ces objets. Mais �a permet
	// d'avoir une arborescence pr�cise d'o� ces objets sont utilis�s
	int id_liaison_folded_tags;
	PathsDTO path;
	TagsDTO parent;
	TagsDTO tag;

	public LiaisonFoldedTagsDTO(int path_id, int id_tag, int id_tag_parent) {
		super();
		this.path = new PathsDTO(path_id);
		this.parent = new TagsDTO(id_tag_parent);
		this.tag = new TagsDTO(id_tag);
		this.id_liaison_folded_tags = 0;
	}

	public int getId() {
		return id_liaison_folded_tags;
	}

	public void setId(int id) {
		this.id_liaison_folded_tags = id;
	}

	public int getPath() {
		return path.getId();
	}

	public void setPath(int path) {
		this.path.setId(path);
	}

	public int getParent() {
		return parent.getId_tag();
	}

	public void setParent(int parent) {
		this.parent.setId_tag(parent);
	}

	public int getTag() {
		return tag.getId_tag();
	}

	public void setTag(int tag) {
		this.tag.setId_tag(tag);
	}

}