import persistence.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Sheet implements Savable {
	private static final String DATA_DELIMETER = String.valueOf(((char)31));
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
	private ArrayList<Map<String, ICell>> _history = new ArrayList<Map<String, ICell>>(); 
	
	private static final ArrayList<ICellValue> _cellValueTypes = new ArrayList<ICellValue>(Arrays.asList(new FormulaCellValue(),new TextCellValue(),
			new NumberCellValue(), new DateCellValue()));

	Sheet() {
	}


	public void pushHistory() {
		HashMap<String,ICell> latest = new HashMap<>(_cells);
		_history.add(latest);
	}
	
	public void popHistory() {
		_cells = _history.remove(_history.size()-1);
	}
	
	public void setCell(String key, String value) {
		ICell cell = null;
		for (ICellValue cellvalueType : _cellValueTypes) {
			if(cellvalueType.isMatch(value)) {
				try {
					cell = (ICell) Class.forName(cellvalueType.getCellTypeName()).newInstance();
					cell.setValue(value);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		_cells.put(key, cell);
	}

	private String getCellKey(int row, int column) {
		return (char)(64 + column) + "" + row;
	}

	public void print() {
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
				String cellKey = getCellKey(row, column);
				String val = "";
				if(_cells.containsKey(cellKey)){
					val = ((ICell) _cells.get(cellKey)).getValue();
				}
				System.out.print(Utils.center(val, Cell.MAX_LENGTH) + COLUMN_SEP);
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
		else
		{
			System.out.println("<empty>");
		}
		
	}

	public void clear(String userInput) {
		String[] options = userInput.split(" ");
		if(options.length>1) {
			String cellKey = options[1].toUpperCase();
			if(_cells.containsKey(cellKey)) {
				_cells.remove(cellKey);
			}
		}
		else
		{
			_cells.clear();
		}
		
	}

	@Override
	public String[] getSaveData() {
		String[] ret = new String[_cells.size()];
		try {
			Iterator<Entry<String, ICell>> it = _cells.entrySet().iterator();
			int index = 0;
		    while (it.hasNext()) {
		        Map.Entry<String, ICell> pair = (Map.Entry<String, ICell>)it.next();
		        Object cell = pair.getValue();
		        String cellType = "";
		        if(cell instanceof TextCell) {
		        	cellType = "TextCell";
		        }
		        else if (cell instanceof NumberCell) {
		        	cellType = "NumberCell";
		        }
		        else if (cell instanceof DateCell) {
		        	cellType = "DateCell";
		        }
		        String data =pair.getKey() + DATA_DELIMETER + cellType + DATA_DELIMETER + cell.toString(); 
		        ret[index] = data;
		        //it.remove(); // avoids a ConcurrentModificationException
		        index++;
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public void loadFrom(String[] saveData) {
		//TODO should use Program clear confirm and save if want
		_cells.clear();
		for (int i = 0; i < saveData.length; i++) {
			String[] data = saveData[i].split(DATA_DELIMETER);
			switch (data[1]) {
			case "TextCell":
				_cells.put(data[0], new TextCell(data[2]));
				break;
			case "NumberCell":
				_cells.put(data[0], new NumberCell(data[2]));
				break;
			case "DateCell":
				try {
					_cells.put(data[0], new DateCell(data[2]));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				break;				
			default:
				break;
			}
		}
		
	}

	public int getCellCount() {
		return _cells.size();
	}
}
