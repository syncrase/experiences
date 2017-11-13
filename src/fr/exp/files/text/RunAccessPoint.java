package fr.exp.files.text;

public class RunAccessPoint {

	public static void main(String[] args) {

		PrestashopCSVGenerationFromHaxiaCSV projectFacade = new PrestashopCSVGenerationFromHaxiaCSV();

		// 1/ récupération du tableau ordonné, depuis le fichier texte, contenant
		// tous les noms de colonne
		String separatorIn = "\",\"";
		String[] charsToDelete = { "\"" };
		String[] haxiaExportColumnNames = projectFacade.extractColumnNames("files/haxia_export.csv", separatorIn,
				charsToDelete);

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
		String[] mapping = { "IDART", "IMAGE", "DEF", "", "", "BaseHT", "PRIX_TTC", "N_Unite", "", "LOCATION" };

		// index will be the index of the OUT file, value is the index in the IN file
		// where the real value is. The value is a kind of pointer
		int[] indexMapping = projectFacade.findIndexMapping(mapping, haxiaExportColumnNames);

		// 4/ Génération de la table de sortie contenant toutes les valeurs désirées
		// génération de la ligne contenant les noms des colonnes
		// génération des lignes contenant les données dumpées depuis le fichier
		// d'entrée

		// récupération du format du fichier de sorti
		String separatorOut = ";";
		String[] outCols = projectFacade.extractColumnNames("files/prestashop_product_export.csv", separatorOut,
				charsToDelete);
		try {
			projectFacade.printPrestashopComptatibleCSV(outCols, "files/haxia_export.csv", indexMapping, separatorIn,
					separatorOut, charsToDelete);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
