package textexcel;
import persistence.*;
import textexcel.cells.*;
import utils.Utils;

import java.lang.reflect.Constructor;
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

	private int _maxColumns;
	private int _maxRows;
	
	private Map<String, ICell> _cells = new HashMap<String,ICell>();
	private ArrayList<Map<String, ICell>> _history = new ArrayList<Map<String, ICell>>(); 
	
	private static final ArrayList<ICellValue> _cellValueTypes = new ArrayList<ICellValue>(Arrays.asList(new FormulaCellValue(),new TextCellValue(),
			new NumberCellValue(), new DateCellValue()));

	public Sheet(int maxRows, int maxColumns) {
		_maxRows = maxRows;
		_maxColumns = maxColumns;
	}

	private void pushHistory() {
		_history.add(new HashMap<>(_cells));
	}
	
	private void popHistory() {
		_cells = _history.remove(_history.size()-1);
	}
	
	public ICell getCell(String key) {
		return _cells.get(key);
	}
	
	public void setCell(String key, String value) {
		ICell cell = null;
		for (ICellValue cellvalueType : _cellValueTypes) {
			if(cellvalueType.isMatch(value)) {
				try {
					Constructor c = Class.forName(cellvalueType.getCellTypeName()).getConstructor(Sheet.class);
					cell = (ICell) c.newInstance(this);
					pushHistory();
					cell.setValue(value);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		pushHistory();
		_cells.put(key, cell);
	}

	private String getCellKey(int row, int column) {
		return (char)(64 + column) + "" + row;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		//add column header row
		buffer.append(Utils.padLeft(COLUMN_SEP, Cell.MAX_LENGTH + 1));
		for (int column = 2; column <= _maxColumns+1; column++) {
			//A = 65
			//char colLetter = (char)(column + 63);
			buffer.append(Utils.center(String.valueOf((char)(column + 63)), Cell.MAX_LENGTH) + COLUMN_SEP);
		}
		buffer.append("\r\n");
		buffer.append(getRowSeparator());
		
		//add each row
		for (int row = 1; row <= _maxRows; row++) {
			//get row separator
			//------------+------------+------------+------------+------------+------------+------------+------------+
			buffer.append(Utils.center(""+row, Cell.MAX_LENGTH) + COLUMN_SEP);
			for (int column = 1; column <= _maxColumns; column++) {
				String cellKey = getCellKey(row, column);
				String val = "";
				if(_cells.containsKey(cellKey)){
					val = ((ICell) _cells.get(cellKey)).getValue();
				}
				buffer.append(Utils.center(val, Cell.MAX_LENGTH) + COLUMN_SEP);
			}
			buffer.append("\r\n");
			buffer.append(getRowSeparator());
		}
		return buffer.toString();
	}

	private String getRowSeparator() {
		StringBuffer buffer = new StringBuffer();
		String columnSep = Utils.getStringWithLengthAndFilledWithCharacter(Cell.MAX_LENGTH,ROW_SEP_CHAR) + ROW_COL_SEP_CHAR;
		for (int column = 0; column <= _maxColumns; column++) {
			buffer.append(columnSep);
		}
		buffer.append("\r\n");
		return buffer.toString();
	}

	public void printCell(String key) {
		if(_cells.containsKey(key)) 
		{
			//TODO need to fix so values don't contain padding & enclose with quotes
			System.out.println(key + " = " + _cells.get(key).toString());
		}
		else
		{
			System.out.println("<empty>");
		}
		
	}

	public void clear(String userInput) {
		pushHistory();
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
		        else if (cell instanceof FormulaCell) {
		        	cellType = "FormulaCell";
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
		pushHistory();
		_cells.clear();
		for (int i = 0; i < saveData.length; i++) {
			String[] data = saveData[i].split(DATA_DELIMETER);
			switch (data[1]) {
			case "FormulaCell":
				_cells.put(data[0], new FormulaCell(this,data[2]));
				break;
			case "TextCell":
				_cells.put(data[0], new TextCell(this,data[2]));
				break;
			case "NumberCell":
				_cells.put(data[0], new NumberCell(this,data[2]));
				break;
			case "DateCell":
				try {
					_cells.put(data[0], new DateCell(this,data[2]));
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

	public void undo() {
		popHistory();
	}
}
