import java.util.ArrayList;
import java.util.Iterator;

public class Sheet {
	/* 
	 * implementation of a spreadsheet
	 */
	private static final char ROW_COL_SEP_CHAR = '+';
	private static final char ROW_SEP_CHAR = '-';
	private static final String COLUMN_SEP = "|";
	public static final int SHEET_MAX_COLUMNS = 7;
	public static final int SHEET_MAX_ROWS = 10;

	private ArrayList<SheetCell> _sheetCells = new ArrayList<SheetCell>();

	Sheet() {
		for (int row = 1; row <= SHEET_MAX_ROWS; row++) {
			for (int column = 1; column <= SHEET_MAX_COLUMNS; column++) {
				// initialize all cells to TextCells
				_sheetCells.add(new SheetCell(row, column, new TextCell()));
			}
		}
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
		Iterator iterator = _sheetCells.iterator();
		for (int row = 1; row <= SHEET_MAX_ROWS; row++) {
			//print row separator
			//------------+------------+------------+------------+------------+------------+------------+------------+
			
			System.out.print(Utils.center(""+row, Cell.MAX_LENGTH) + COLUMN_SEP);
			for (int column = 1; column <= SHEET_MAX_COLUMNS; column++) {
				SheetCell sc = (SheetCell) iterator.next();
				System.out.print(Utils.center(sc.getText(), Cell.MAX_LENGTH) + COLUMN_SEP);
			}
			System.out.println();
			printRowSeparator();
		}
		
		
		// for (Iterator iterator = _sheetCells.iterator(); iterator.hasNext();)
		// {
		// SheetCell sc = (SheetCell) iterator.next();
		// System.out.println("row:"+sc.getRow()
		// +", col:"+sc.getColumn()+", val:"+sc.getText());
		// }
	}

	private void printRowSeparator() {
		String columnSep = Utils.getStringWithLengthAndFilledWithCharacter(Cell.MAX_LENGTH,ROW_SEP_CHAR) + ROW_COL_SEP_CHAR;
		for (int column = 0; column <= SHEET_MAX_COLUMNS; column++) {
			System.out.print(columnSep);
		}
		System.out.println();
	}
}
