package maze.masks;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import maze.parent.Cell;

public class TextMask extends Mask {

	public TextMask(String fileName) throws URISyntaxException {
		URI fileUri = getClass().getResource(fileName).toURI();
		Path filePath = Paths.get(fileUri);
		readMask(filePath);
	}
	
	public TextMask(File file) {
		Path filePath = file.toPath();
		readMask(filePath);
	}
	
	protected void readMask(Path filePath) {
		try {
			List<String> rows = Files.readAllLines(filePath);
			int rowCount = rows.size();
			int colCount = 0;
			for (int i=0; i<rows.size(); i++) {
				String row = rows.get(i);
				if (colCount < row.length())
					colCount = row.length();
				for (int j=0; j<row.length(); j++) {
					if (row.charAt(j) == 'X'){
						Cell cell = new Cell(rows.size()-1-i, j);
						addMaskedCell(cell);
					}
				}
			}
			setRows(rowCount);
			setCols(colCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
