package fr.exp.files.merger.filetype;

import java.util.List;

public interface IFileType {

	public String[] extractColumnNames(String filePath);

	public void loadTitles();
	
	public List<String> getAllLines();

	
	public int[] getIndexMapping();

	public String getSeparator();

	public void loadFile();

	public List<String[]> getFileContent();
}
