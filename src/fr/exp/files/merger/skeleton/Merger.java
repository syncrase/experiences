package fr.exp.files.merger.skeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import fr.exp.files.merger.filetype.IFileType;
import fr.exp.files.merger.ioimpl.FileBasicsImpl;
import fr.exp.files.merger.ioimpl.TextBasicsImpl;

public class Merger {

	FileBasicsImpl fb = new FileBasicsImpl();
	TextBasicsImpl tb = new TextBasicsImpl();

	public void printPrestashopComptatibleCSV(IFileType outCols, IFileType fileType1, IFileType fileType)
			throws Exception {
		// TODO pour beaucoup plus tard ou juste pour faire une méthode plus générique:
		// donner une liste de IFileType à la place
		IFileType file1 = fileType1;
		IFileType file2 = fileType;
		// Vérification de la taille des tableaux
		// if (outCols.getFileContent().get(0).length != file1.getIndexMapping().length
		// || outCols.getFileContent().get(0).length != file2.getIndexMapping().length)
		// throw new Exception("Both arrays length must be the same");

		file1.loadFile();
		file2.loadFile();

		List<String[]> csvRows = file1.getFileContent();
		List<String[]> xlsRows = file2.getFileContent();
		// Récupère la taille la plus petite
		int rows = csvRows.size() == xlsRows.size() ? xlsRows.size()
				: csvRows.size() < xlsRows.size() ? csvRows.size() : xlsRows.size();

		if (csvRows.size() != xlsRows.size()) {
			System.err.println(
					"The number of rows of both input files are differents. We will try to rebuilding something based on ids of the first column");
		}

		String[] entireRow1;
		String[] entireRow2;
		List<String[]> finalList = new ArrayList<String[]>();
		// Ajout des noms des colonnes
		finalList.add(outCols.getFileContent().get(0));
		// Ajout du contenu
		String[] rowTmp;
		int idGap1 = 0, idGap2 = 0;
		for (int i = 1; i < rows; i++) {
			rowTmp = new String[outCols.getFileContent().get(0).length];
			entireRow1 = csvRows.get(i - idGap1);
			entireRow2 = xlsRows.get(i - idGap2);
			int id1, id2;
			id1 = (int) Float.parseFloat(entireRow1[0]);
			id2 = (int) Float.parseFloat(entireRow2[0]);
			if (id1 == id2) {
				// Complète la ligne grâce aux infos des deux fichiers d'entrée
				for (int j = 0; j < file1.getIndexMapping().length; j++) {
					if (file1.getIndexMapping()[j] > -1) {
						rowTmp[j] = entireRow1[file1.getIndexMapping()[j]];
					}
					if (file2.getIndexMapping()[j] > -1) {
						rowTmp[j] = entireRow2[file2.getIndexMapping()[j]];
					}
				}
				finalList.add(rowTmp);

			} else {
				// Les identifiants ne sont pas les mêmes => on inscrit la ligne avec l'id le
				// plus petit et on incrémente uniquement celle-ci
				if (id1 > id2) {
					// Alors on inscrit id2 et on voit à la boucle d'après si id2 == id1, sinon on
					// boucle ici tant que ce n'est pas valide
					for (int j = 0; j < file1.getIndexMapping().length; j++) {
						if (file2.getIndexMapping()[j] > -1) {
							rowTmp[j] = entireRow2[file2.getIndexMapping()[j]];
						}
					}
					finalList.add(rowTmp);
					idGap1++;
				}
				if (id2 > id1) {
					for (int j = 0; j < file1.getIndexMapping().length; j++) {
						if (file1.getIndexMapping()[j] > -1) {
							rowTmp[j] = entireRow1[file1.getIndexMapping()[j]];
						}
					}
					finalList.add(rowTmp);
					idGap2++;
				}
			}
		}

		// TODO prioritaire: extraire le nom de la photo pour la mettre dans la colonne
		// références
		// Ici on parcours les lignes et pour chaque ligne, s'il y a une image, on
		// inscrit le nom de l'image comme référence
		String imageUrl = "";
		String imageName = "";
		String[] tab;
		// La ligne 0 est la ligne de titre
		for (int r = 1; r < finalList.size(); r++) {
			imageUrl = finalList.get(r)[1];
			// Si l'image existe
			if (imageUrl != "") {
				// Puisque le format de l'image est de la forme //*/*/name.ext
				// Je split par "\" pour avoir name.ext
				// Puis je split par "."
				tab = imageUrl.split("\\\\");
				imageName = tab[tab.length - 1];
				tab = imageName.split(Pattern.quote("."));
				imageName = tab[0];
				// Ajout de la référence
				finalList.get(r)[3] = imageName;

				// au passage on va modifier l'url de l'image
				// Remplacer \\CAISSE1\Users\Public\Pictures\ par
				// http://instantpresent-cadeaux.com/haxia_img/
				// Supprimer AMADEUS\, NATIVES\, DERRIERE LA PORTE\, CMP\, KROESE\
				imageUrl = imageUrl.replaceAll(Pattern.quote("\\\\CAISSE1\\Users\\Public\\Pictures\\"),
						"http://instantpresent-cadeaux.com/haxia_img/");
				imageUrl = imageUrl.replaceAll(Pattern.quote("AMADEUS\\"), "");
				imageUrl = imageUrl.replaceAll(Pattern.quote("NATIVES\\"), "");
				imageUrl = imageUrl.replaceAll(Pattern.quote("DERRIERE LA PORTE\\"), "");
				imageUrl = imageUrl.replaceAll(Pattern.quote("CMP\\"), "");
				imageUrl = imageUrl.replaceAll(Pattern.quote("KROESE\\"), "");
				finalList.get(r)[1] = imageUrl;
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < finalList.size(); r++) {
			for (int c = 0; c < finalList.get(r).length; c++) {
				sb.append("\"" + finalList.get(r)[c] + "\"" + (c < finalList.get(r).length - 1 ? ";" : ""));
			}
			sb.append("\n");
		}
		fb.write("files/out/haxia_export_prestashop_format.csv", sb.toString());
	}

}
