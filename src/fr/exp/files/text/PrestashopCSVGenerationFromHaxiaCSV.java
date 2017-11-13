package fr.exp.files.text;

import java.util.List;

public class PrestashopCSVGenerationFromHaxiaCSV {

	FileBasicsImpl fb = new FileBasicsImpl();
	TextBasicsImpl tb = new TextBasicsImpl();

	public String[] extractColumnNames(String filePath, String separator, String[] charsToDelete) {
		FileBasicsImpl fb = new FileBasicsImpl();
		String firstLine = fb.getFirstLineAsString(filePath);
		return tb.getCleanedValues(firstLine, separator, charsToDelete);
	}

	public int[] findIndexMapping(String[] mapping, String[] haxiaExportColumnNames) {
		int[] tab = new int[mapping.length];
		for (int i = 0; i < tab.length; i++) {
			tab[i] = -1;
		}
		// Je parcours tous les noms
		for (int inI = 0; inI < haxiaExportColumnNames.length; inI++) {
			// Pour chacun des noms
			for (int outI = 0; outI < mapping.length; outI++) {
				// Je regarde si la valeur corresponds à l'une escomptée
				if (haxiaExportColumnNames[inI].equals(mapping[outI])) {
					// map.put(outI, inI);
					tab[outI] = inI;
				}
			}
		}
		if (tab.length != mapping.length)
			return null;
		return tab;
	}

	public void printPrestashopComptatibleCSV(String[] outCols, String filePath, int[] indexMapping, String separatorIn,
			String separatorOut, String[] charsToDelete) throws Exception {

		// Vérification de la taille des tableaux
		if (outCols.length != 10 || outCols.length != indexMapping.length)
			throw new Exception("Both arrays length must be the same");

		List<String> allLines = fb.getAllLines(filePath);
		StringBuilder sb = new StringBuilder();
		String[] rowAsTab;
		int rowAsTabLength = -1;
		// Ajout des noms des colonnes
		for (int j = 0; j < outCols.length; j++) {
			sb.append("\"" + outCols[j] + "\"" + (j < outCols.length - 1 ? separatorOut : ""));
		}
		sb.append("\n");

		// Ajout du contenu
		for (int i = 1; i < allLines.size(); i++) {
			rowAsTab = tb.getCleanedValues(allLines.get(i), separatorIn, charsToDelete);
			// Ne peux plus être changé une fois différent de "-1"
			rowAsTabLength = rowAsTabLength == -1 ? rowAsTab.length : rowAsTabLength;
			if (rowAsTabLength != rowAsTab.length)
				throw new Exception("Row length isn't constant from line " + i);
			for (int j = 0; j < indexMapping.length; j++) {
				sb.append("\"" + (indexMapping[j] > -1 ? rowAsTab[indexMapping[j]] : "") + "\""
						+ (j < indexMapping.length - 1 ? separatorOut : ""));
			}
			sb.append("\n");
		}
		fb.writeCSVFile("files/haxia_export_prestashop_format.csv", sb.toString());
	}

}
