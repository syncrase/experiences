package fr.exp.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import fr.exp.files.treestructure.PearltreesFolder;

public class FilesHandler {

	/**
	 * 
	 * @param filePath
	 *            File path
	 * @return The complete html file content as a String
	 */
	public static String getHtmlFileContent(String filePath) {
		String fileContent = "";
		try {
			for (String line : Files.readAllLines(Paths.get(filePath))) {
				fileContent += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}

	public static String getPearlTreesExportContent(String filePath) {
		String fileContent = "";
		PearltreesFolder pearlTreesContent;
		String name;
		try {
			for (String line : Files.readAllLines(Paths.get(filePath))) {
				if(line.contains("FOLDED")){
					// get the related name
//					line.substring(beginIndex, endIndex)
					name = "";
//					pearlTreesContent = new PearltreesModel(name);
				}
//				fileContent += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}
}
