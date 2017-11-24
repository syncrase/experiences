package fr.exp.files.merger;

import fr.exp.files.merger.filetype.CSV;
import fr.exp.files.merger.filetype.XLS;

public class RunAccessPoint {

	public static void main(String[] args) {
		try {
			// TODO supprimer la variable mapping car uniquement utile pour retrouver la
			// correspondance entre index

			// 1/ récupération du tableau ordonné, depuis le fichier texte, contenant
			// tous les noms de colonne
			CSV cvsManipulator = new CSV();
			String separatorIn = "\",\"";
			String[] charsToDelete = { "\"" };
			cvsManipulator.setCharsToDelete(charsToDelete);
			cvsManipulator.setSeparator(separatorIn);
			cvsManipulator.setFilepath("files/in/haxia_export.csv");
			cvsManipulator.loadTitles();
			String[] mappingWithCSV = { "IDART", "IMAGE", "DEF", "", "", "BaseHT", "PRIX_TTC", "", "", "LOCATION" };
			cvsManipulator.setMapping(mappingWithCSV);
			cvsManipulator.initializeIndexing();
			// String[] haxiaExportColumnNames =
			// merger.extractColumnNames("files/in/haxia_export.csv");

			// int[] csvIndexMapping = merger.findIndexMapping(mappingWithCSV,
			// haxiaExportColumnNames);
			// int[] xlsIndexMapping = merger.findIndexMapping(mappingWithXLS,
			// haxiaStockExportColumnNames);

			XLS xlsManipulator = new XLS();
			xlsManipulator.setFilepath("files/in/Article_stock.xls");
			xlsManipulator.loadTitles();
			String[] mappingWithXLS = { "", "", "", "", "", "", "", "Qté en Stock", "", "" };
			xlsManipulator.setMapping(mappingWithXLS);
			xlsManipulator.initializeIndexing();

			// String[] haxiaStockExportColumnNames =
			// xlsManipulator.extractColumnNames("files/in/Article_stock.xls");
			// for (String s : haxiaStockExportColumnNames) {
			// System.out.print(s + ", ");
			// }

			// haxiaStockExportColumnNames.clone();
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

			// récupération du format du fichier de sorti
			Merger merger = new Merger();
			// String separatorOut = ";";
			merger.setSeparator(";");
			String[] outCols = merger.extractColumnNames("files/in/prestashop_product_export.csv");

			merger.printPrestashopComptatibleCSV(outCols, cvsManipulator, xlsManipulator);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
