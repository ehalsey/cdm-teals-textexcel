package textexcel.cells;

import textexcel.Sheet;

public abstract class Cell {
	public static final int MAX_LENGTH = 12;
	private Sheet _sheet;
	public String getText() {
		return "cell.getText";
	}
	Cell(Sheet sheet) {
		_sheet = sheet;
	}
	
	public Sheet getSheet() {
		return _sheet;
	}
}
