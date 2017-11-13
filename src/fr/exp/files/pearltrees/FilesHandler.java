package fr.exp.files.pearltrees;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import fr.exp.files.basics.FilesBasics;

public class FilesHandler extends FilesBasics {

	/**
	 * 
	 * @param filePath
	 *            File path
	 * @return The complete html file content as a String
	 */
	public static String getHtmlFileContent(String filePath) {
		FilesHandler fh = new FilesHandler();
		String fileContent = "";
//		try {
//			fileContent = fh.getContentAsString(Files.readAllLines(Paths.get(filePath)));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		fileContent = fh.getContentAsString(filePath);
		return fileContent;
	}

	

	public static String getPearlTreesExportContent(String filePath) {
		String fileContent = "";
		try {
			for (String line : Files.readAllLines(Paths.get(filePath))) {
				if (line.contains("FOLDED")) {
					// get the related name
					// line.substring(beginIndex, endIndex)
					// pearlTreesContent = new PearltreesModel(name);
				}
				// fileContent += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}
}
