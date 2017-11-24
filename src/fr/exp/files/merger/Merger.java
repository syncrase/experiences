package fr.exp.files.merger;

import java.util.Arrays;
import java.util.List;

import fr.exp.files.merger.filetype.AFileType;
import fr.exp.files.merger.filetype.CSV;
import fr.exp.files.merger.filetype.XLS;

public class Merger extends CSV {

	FileBasicsImpl fb = new FileBasicsImpl();
	TextBasicsImpl tb = new TextBasicsImpl();

	public void printPrestashopComptatibleCSV(String[] outCols, AFileType csvFile, AFileType xlsFile) throws Exception {
		StringBuilder sb = new StringBuilder();
		CSV csvF = (CSV) csvFile;
		XLS xlsF = (XLS) xlsFile;
		// Vérification de la taille des tableaux
		if (outCols.length != 10 || outCols.length != csvF.getIndexMapping().length
				|| outCols.length != xlsF.getIndexMapping().length)
			throw new Exception("Both arrays length must be the same");

		List<String> csvRows = csvF.getAllLines();
		List<String> xlsRows = xlsF.getAllLines();
		// Récupère la taille la plus petite
		int rows = csvRows.size() == xlsRows.size() ? xlsRows.size()
				: csvRows.size() < xlsRows.size() ? csvRows.size() : xlsRows.size();

		if (csvRows.size() != xlsRows.size()) {
			// throw new Exception(
			// "The number of rows isn't the same. CSV: " + csvRows.size() + "; XLS: " +
			// xlsRows.size());
			System.err.println(
					"The number of rows of both input files are differents. The end of the larger file will be ignored.");
		}

		String[] csvRowAsTab;
		String[] xlsRowAsTab;
		int csvRowAsTabLength = -1;
		int xlsRowAsTabLength = -1;
		// Ajout des noms des colonnes
		for (int j = 0; j < outCols.length; j++) {
			sb.append("\"" + outCols[j] + "\"" + (j < outCols.length - 1 ? this.getSeparator() : ""));
		}
		sb.append("\n");

		// Ajout du contenu
		String[] rowTmp;
		for (int i = 1; i < rows; i++) {
			rowTmp = new String[outCols.length];
			Arrays.fill(rowTmp, "");

			csvRowAsTab = tb.getCleanedValues(csvRows.get(i), csvF.getSeparator(), charsToDelete);
			xlsRowAsTab = tb.getCleanedValues(xlsRows.get(i), xlsF.getSeparator(), charsToDelete);
			// Ne peux plus être changé une fois différent de "-1"
			csvRowAsTabLength = csvRowAsTabLength == -1 ? csvRowAsTab.length : csvRowAsTabLength;
			xlsRowAsTabLength = xlsRowAsTabLength == -1 ? xlsRowAsTab.length : xlsRowAsTabLength;

			// Si le nombre de colonnes n'est pas constant d'une ligne à l'autre
			if (csvRowAsTabLength != csvRowAsTab.length) {
				throw new Exception("Corrupted CSV file. Row length isn't constant from line " + i);
			}
			if (xlsRowAsTabLength != xlsRowAsTab.length) {
				System.err.println("row: " + i);
				throw new Exception("Corrupted XLS file. Row length isn't constant from line " + i);
			}

			// Complète la ligne grâce aux infos des deux fichiers d'entrée
			for (int j = 0; j < csvF.getIndexMapping().length; j++) {
				if (csvF.getIndexMapping()[j] > -1) {
					rowTmp[j] = csvRowAsTab[csvF.getIndexMapping()[j]];
				}
			}
			for (int j = 0; j < xlsF.getIndexMapping().length; j++) {
				if (xlsF.getIndexMapping()[j] > -1) {
					rowTmp[j] = xlsRowAsTab[xlsF.getIndexMapping()[j]];
				}
			}
			if (Integer.parseInt(csvRowAsTab[0]) == (int) Float.parseFloat(xlsRowAsTab[0])) {
				// System.err.println("ids are validate");
			} else {
				// TODO problème de non correspondance d'ids
				System.err.println("ids mismatch. CSV id: " + csvRowAsTab[0] + " & XLS id: " + xlsRowAsTab[0]);
			}

			for (int j = 0; j < outCols.length; j++) {
				sb.append("\"" + rowTmp[j] + "\"" + (j < rowTmp.length - 1 ? this.getSeparator() : ""));
			}
			sb.append("\n");
		}
		fb.writeCSVFile("files/out/haxia_export_prestashop_format.csv", sb.toString());
	}

}
