package fr.exp.files.text;

public class RunAccessPoint {

	public static void main(String[] args) {

		PrestashopCSVGenerationFromHaxiaCSV projectFacade = new PrestashopCSVGenerationFromHaxiaCSV();

		// 1/ r�cup�ration du tableau ordonn�, depuis le fichier texte, contenant
		// tous les noms de colonne
		String separatorIn = "\",\"";
		String[] charsToDelete = { "\"" };
		String[] haxiaExportColumnNames = projectFacade.extractColumnNames("files/haxia_export.csv", separatorIn,
				charsToDelete);

		// 3/ Enregistrement du mapping entre les deux tables, ie table de
		// correspondance cl� valeur (cl�: nom dans la table d'entr�e; valeur: nom dans
		// la table de sortie) UI facile � faire?
		// Prend quelque chose dans la table d'entr�e pour le mettre dans la table de
		// sortie
		// => Construction du tableau contenant les index des colonnes du tableau
		// d'entr�e qui m'int�ressent, dont la longueur �gale celle du tableau de sortie
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

		// 4/ G�n�ration de la table de sortie contenant toutes les valeurs d�sir�es
		// g�n�ration de la ligne contenant les noms des colonnes
		// g�n�ration des lignes contenant les donn�es dump�es depuis le fichier
		// d'entr�e

		// r�cup�ration du format du fichier de sorti
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
