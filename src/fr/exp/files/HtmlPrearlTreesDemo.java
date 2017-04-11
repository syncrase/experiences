package fr.exp.files;

public class HtmlPrearlTreesDemo {

	public static void main(String[] args) {

		// obtient un objet repr�sentant mon fichier html
		// String fileContent = FilesHandler.getHtmlFileContent(filePath);
		// System.out.println(fileContent != "" ? fileContent : "Unable to get
		// the file content");

		// fileContent = FilesHandler.getPearlTreesExportContent(filePath);
		// System.out.println(fileContent != "" ? fileContent : "Unable to get
		// the file content");

		/*
		 * Les algos
		 *
		 * D�but Je croise un H3 => new HtmlModel() => Ajout du DL correspondant
		 * Go � la fin du DL ajout� (pas possible ). Repeat pour tous les h3 Fin
		 * 
		 * D�but Je croise un H3 => new HtmlModel() => depth ++ ??? Je sors d'un
		 * H3 => depth -- Fin
		 * 
		 * 
		 */
		// HtmlExtractorDemo.test3();
		PearltreesFacade pearltreesFacade = new PearltreesFacade("files/pearltrees_export-25-12-2016.html");

		// PearltreesFolder pearlTreesModel =
		// pearltreesFacade.getPearlTreesModel();
		// System.out.println(pearltreesFacade.toString());
		// System.out.println(pearltreesFacade.getFoldedTags());
		// System.out.println(pearltreesFacade.getTreeWithoutLeafs());
		// System.out.println(pearltreesFacade.getTree());
		System.out.println(pearltreesFacade.getHtml());
		//
		// pearltreesHandler.writeHtmlFile("files/my_pearltrees_export.html");
		// pearltreesHandler.saveInDataBase();

		// Sauvegarde du r�sultat en base de donn�es
		// Structure de dossier -> le tag complexe de chaque url doit �tre son
		// path dans la struture de dossiers
		// 1- Afficher toutes les urls avec son tag complexe
		// 2- Enregistrer toutes les url dans la base de donn�es
		// 3- R�cup�rer les objets dans la BDD ? GetTags()? GetUrls()?
		// GetUrlTaggedWith(Tag tag)? GetUrlTaggedWith(Tag[] tags)?
		// 4- Exporter les donn�es de l'objet vers un fichier html d'export
		// format� � la mani�re de pearltrees

		// fichier pearltrees <-> java objet <-> base donn�es
		// => 4 interactions � coder. Code pour g�n�rer le java en tout ou
		// partie?
		// Sachant que la base de donn�es peut-�tre mongodb, neo4j, ... et
		// fichier! ==>> Bosser sur le pattern ad�quat! (builder, factory?)
	}

	// private static void print(String msg, Object... args) {
	// System.out.println(String.format(msg, args));
	// }
	//
	// private static String trim(String s, int width) {
	// if (s.length() > width)
	// return s.substring(0, width - 1) + ".";
	// else
	// return s;
	// }

}
