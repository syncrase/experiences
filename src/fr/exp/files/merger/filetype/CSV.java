package fr.exp.files.merger.filetype;

import java.util.List;

import fr.exp.files.merger.FileBasicsImpl;
import fr.exp.files.merger.TextBasicsImpl;

public class CSV extends AFileType {

	protected String separator;
	protected String[] charsToDelete;

	public String[] extractColumnNames(String filePath) {
		FileBasicsImpl fb = new FileBasicsImpl();

		TextBasicsImpl tb = new TextBasicsImpl();
		String firstLine = fb.getFirstLineAsString(filePath);
		return tb.getCleanedValues(firstLine, separator, charsToDelete);
	}



	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String[] getCharsToDelete() {
		return charsToDelete;
	}

	public void setCharsToDelete(String[] charsToDelete) {
		this.charsToDelete = charsToDelete;
	}



	@Override
	public List<String> getAllLines() {
		FileBasicsImpl fb = new FileBasicsImpl();
		return fb.getAllLines(this.getFilepath());
	}

}
