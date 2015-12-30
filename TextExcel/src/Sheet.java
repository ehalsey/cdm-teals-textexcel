import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Sheet {
	/* 
	 * implementation of a spreadsheet
	 */
	private static final char ROW_COL_SEP_CHAR = '+';
	private static final char ROW_SEP_CHAR = '-';
	private static final String COLUMN_SEP = "|";
	public static final int SHEET_MAX_COLUMNS = 7;
	public static final int SHEET_MAX_ROWS = 10;
	public static final int SHEET_MAX_CELLS = SHEET_MAX_ROWS * SHEET_MAX_COLUMNS;
	
	private Map<String, ICell> _cells = new HashMap<String,ICell>();

	Sheet() {
		//TODO try without initializing all of the cells
		for (int row = 1; row <= SHEET_MAX_ROWS; row++) {
			for (int column = 1; column <= SHEET_MAX_COLUMNS; column++) {
				// initialize all cells to TextCells
				_cells.put(getCellKey(row,column), new TextCell());
			}
		}
	}
	
	public void setCell(String key, String value) {
		ICell cell = null;
		if(value.contains("\"")) {
			if(value.startsWith("\"")) {
				value = value.substring(1);
				if(value.endsWith("\"")) {
					value = value.substring(0, value.length()-1);
				}
			}
			cell = new TextCell(value);
		}
		else
		{
			try {
				Double valAsDouble = Double.parseDouble(value);
				cell = new NumberCell(valAsDouble);
			}
			catch (NumberFormatException e) {}
			finally {}
		}
		if(value.contains("/")) {
			DateFormat formatter = DateCell.SIMPLE_DATE_FORMAT;
			try {
				Date valueAsDate = formatter.parse(value);
				cell = new DateCell(valueAsDate);
			} catch (ParseException e) {
			}
		}
		_cells.put(key, cell);
	}

	private String getCellKey(int row, int column) {
		return (char)(64 + column) + "" + row;
	}

	public void print() {
		System.out.println("sheet.print");

		//print out column header row
		System.out.print(Utils.padLeft(COLUMN_SEP, Cell.MAX_LENGTH + 1));
		for (int column = 2; column <= SHEET_MAX_COLUMNS+1; column++) {
			//A = 65
			//char colLetter = (char)(column + 63);
			System.out.print(Utils.center(String.valueOf((char)(column + 63)), Cell.MAX_LENGTH) + COLUMN_SEP);
		}
		System.out.println();
		printRowSeparator();
		
		//print out each row
		for (int row = 1; row <= SHEET_MAX_ROWS; row++) {
			//print row separator
			//------------+------------+------------+------------+------------+------------+------------+------------+
			System.out.print(Utils.center(""+row, Cell.MAX_LENGTH) + COLUMN_SEP);
			for (int column = 1; column <= SHEET_MAX_COLUMNS; column++) {
				ICell cell = (ICell) _cells.get(getCellKey(row, column));
				System.out.print(Utils.center(cell.toString(), Cell.MAX_LENGTH) + COLUMN_SEP);
			}
			System.out.println();
			printRowSeparator();
		}
	}

	private void printRowSeparator() {
		String columnSep = Utils.getStringWithLengthAndFilledWithCharacter(Cell.MAX_LENGTH,ROW_SEP_CHAR) + ROW_COL_SEP_CHAR;
		for (int column = 0; column <= SHEET_MAX_COLUMNS; column++) {
			System.out.print(columnSep);
		}
		System.out.println();
	}

	public void printCell(String cellKey) {
		if(_cells.containsKey(cellKey)) 
		{
			//TODO need to fix so values don't contain padding & enclose with quotes
			System.out.println(cellKey + " = " + _cells.get(cellKey).toString());
		}
		
	}
}
