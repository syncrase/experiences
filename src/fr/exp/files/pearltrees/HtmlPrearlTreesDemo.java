package fr.exp.files.pearltrees;

import org.slf4j.LoggerFactory;

public class HtmlPrearlTreesDemo {

	public static ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("fr.exp.files.pearltrees");
	
	public static void main(String[] args) {

		logger.trace("D�but d'ex�cution de HtmlPrearlTreesDemo");
		
		// obtient un objet repr�sentant mon fichier html
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
		 pearltreesFacade2.writeHtmlFile("files/my_pearltrees_export.html");

		// pearltreesFacade2.deleteAll();
		// pearltreesFacade2.saveInDataBase();

		// print(pearltreesFacade2.getTablesName());
		// print(pearltreesFacade2.loadFromDataBase());

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
		logger.trace("Fin d'ex�cution de HtmlPrearlTreesDemo");
	}

	private static void print(String msg, Object... args) {
		if (args.length == 0) {
			System.out.println(msg);
		} else {
			System.out.println(String.format(msg, args));
		}
	}
	//
	// private static String trim(String s, int width) {
	// if (s.length() > width)
	// return s.substring(0, width - 1) + ".";
	// else
	// return s;
	// }

}
