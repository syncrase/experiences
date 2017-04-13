package fr.exp.files.pearltrees;

public class HtmlPrearlTreesDemo {

	public static void main(String[] args) {

		// obtient un objet représentant mon fichier html
		// String fileContent = FilesHandler.getHtmlFileContent(filePath);
		// Sprint(fileContent != "" ? fileContent : "Unable to get
		// the file content");

		// fileContent = FilesHandler.getPearlTreesExportContent(filePath);
		// print(fileContent != "" ? fileContent : "Unable to get
		// the file content");

		/**
		 * 1er jet
		 */
		// PearltreesFacade pearltreesFacade = new
		// PearltreesFacade("files/pearltrees_export-25-12-2016.html");

		// PearltreesFolder pearlTreesModel =
		// pearltreesFacade.getPearlTreesModel();
		// print(pearltreesFacade.toString());
		// print(pearltreesFacade.getFoldedTags());
		// print(pearltreesFacade.getTreeWithoutLeafs());
		// print(pearltreesFacade.getTree());
		// print(pearltreesFacade.getHtml());
		//
		// pearltreesHandler.writeHtmlFile("files/my_pearltrees_export.html");
		// pearltreesHandler.saveInDataBase();

		/**
		 * 2nd jet
		 */
		PearltreesFacade2 pearltreesFacade2 = new PearltreesFacade2("files/pearltrees_export-25-12-2016.html");
		// print(pearltreesFacade2.getFoldedTags());
		// print(pearltreesFacade2.getHtml());
		System.out.println(pearltreesFacade2.getHtml());
		// Sauvegarde du résultat en base de données
		// Structure de dossier -> le tag complexe de chaque url doit être son
		// path dans la struture de dossiers
		// 1- Afficher toutes les urls avec son tag complexe
		// 2- Enregistrer toutes les url dans la base de données
		// 3- Récupérer les objets dans la BDD ? GetTags()? GetUrls()?
		// GetUrlTaggedWith(Tag tag)? GetUrlTaggedWith(Tag[] tags)?
		// 4- Exporter les données de l'objet vers un fichier html d'export
		// formaté à la manière de pearltrees

		// fichier pearltrees <-> java objet <-> base données
		// => 4 interactions à coder. Code pour générer le java en tout ou
		// partie?
		// Sachant que la base de données peut-être mongodb, neo4j, ... et
		// fichier! ==>> Bosser sur le pattern adéquat! (builder, factory?)
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}
	//
	// private static String trim(String s, int width) {
	// if (s.length() > width)
	// return s.substring(0, width - 1) + ".";
	// else
	// return s;
	// }

}
