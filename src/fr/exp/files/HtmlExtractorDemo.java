package fr.exp.files;

public class HtmlExtractorDemo {

	public static void main(String[] args) {

		String filePath = "files/pearltrees_export-25-12-2016.html";

		// obtient un objet représentant mon fichier html
		String fileContent = FilesHandler.getHtmlFileContent(filePath);
//		System.out.println(fileContent != "" ? fileContent : "Unable to get the file content");
		
		fileContent = FilesHandler.getPearlTreesExportContent(filePath);
		System.out.println(fileContent != "" ? fileContent : "Unable to get the file content");

	}

}
