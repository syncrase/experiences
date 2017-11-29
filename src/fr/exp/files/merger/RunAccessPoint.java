package fr.exp.files.merger;

import org.slf4j.LoggerFactory;

import fr.exp.files.merger.filetype.IFileType;
import fr.exp.files.merger.skeleton.MergeableFileFactory;
import fr.exp.files.merger.skeleton.Merger;
import fr.exp.logimpl.Counter;

public class RunAccessPoint {
	
	public static ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("fr.exp.files.*");

	public static void main(String[] args) {
		logger.warn("Main lauched");
		Counter counter = Counter.getCounter();
		counter.start();
		
		try {
			// 1/ récupération du tableau ordonné, depuis le fichier texte, contenant
			// tous les noms de colonne
			logger.warn("Initialization of 'files/in/export.xls'");
			IFileType cvsManipulator = MergeableFileFactory.getMergeableFile("files/in/export.xls");
			String[] newCSVColumnOrder = { "IDART", "IMAGE", "DEF", "", "", "BaseHT", "PRIX_TTC", "", "", "LOCATION" };
			cvsManipulator.setMapping(newCSVColumnOrder);

			logger.warn("Initialization of 'files/in/ETAT_DU_STOCK.xls'");
			IFileType xlsManipulator = MergeableFileFactory.getMergeableFile("files/in/ETAT_DU_STOCK.xls");
			String[] newXLSColumnOrder = { "", "", "", "", "", "", "", "Qté en Stock", "", "" };
			xlsManipulator.setMapping(newXLSColumnOrder);

			// 3/ Enregistrement du mapping entre les deux tables, ie table de
			// correspondance clé valeur (clé: nom dans la table d'entrée; valeur: nom dans
			// la table de sortie) UI facile à faire?
			// Prend quelque chose dans la table d'entrée pour le mettre dans la table de
			// sortie
			// => Construction du tableau contenant les index des colonnes du tableau
			// d'entrée qui m'intéressent, dont la longueur égale celle du tableau de sortie
			//
			// correspondance entre le format prestashop et le format haxia
			// "Product ID"; IDART
			// Image; IMAGE
			// Name; DEF
			// Reference;
			// Category;
			// "Base price"; BaseHT
			// "Final price"; PRIX_TTC
			// Quantity; Unite
			// Status;
			// Position LOCATION
			// index will be the index of the OUT file, value is the index in the IN file
			// where the real value is. The value is a kind of pointer
			// 4/ Génération de la table de sortie contenant toutes les valeurs désirées
			// génération de la ligne contenant les noms des colonnes
			// génération des lignes contenant les données dumpées depuis le fichier
			// d'entrée

			logger.warn("Initialize the merger");
			// récupération du format du fichier de sorti
			Merger merger = new Merger();
			IFileType desiredLayout = MergeableFileFactory.getMergeableFile("files/in/prestashop_product_export.csv");
			desiredLayout.loadTitles();
			logger.warn("Start merging");
			merger.printPrestashopComptatibleCSV(desiredLayout, cvsManipulator, xlsManipulator);
			counter.stop();
			logger.warn("Time elapsed: {}", counter.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
