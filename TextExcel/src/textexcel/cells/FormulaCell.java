package textexcel.cells;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.core.IsInstanceOf;

import textexcel.Sheet;
import utils.Utils;

public class FormulaCell extends Cell implements ICell {
	private static final String BLANK_VALUE = "<empty>";
	private String _contents = BLANK_VALUE;

	public FormulaCell(Sheet sheet, String value) {
		super(sheet);
		_contents = utils.Utils.stripOuter(value, "(", ")");
	}

	public FormulaCell(Sheet sheet) {
		this(sheet, BLANK_VALUE);
	}

	public String toString() {
		return "(" + _contents + ")";
	}

	// ( 2 + 3 )
	private double getCalculatedValue() {
		ArrayList<String> parts = new ArrayList<String>();
		if(_contents.matches("sum [A-Za-z]\\d+ \\- [A-Za-z]\\d+")) {
			ArrayList<ICell> cells = getCellsForFunction();
			return calculateSum(cells);
		}
		if(_contents.matches("avg [A-Za-z]\\d+ \\- [A-Za-z]\\d+")) {
			ArrayList<ICell> cells = getCellsForFunction();
			return calculateAvg(cells);
		}

		Pattern pattern = Pattern
				.compile("(([A-Za-z]\\d+)|(\\-\\d*\\.\\d+)|(\\-\\d+)|(\\d*\\.\\d+)|(\\d+)|([\\+\\-\\*/\\(\\)]))");
		Matcher m = pattern.matcher(_contents);
		while (m.find()) {
			parts.add(m.group());
		}

		//replace all references with values, recursively
		for (int i = 0; i < parts.size(); i++) {
			String part = parts.get(i);
			if(part.matches("[A-Za-z]\\d+")) {
 				ICell cell = this.getSheet().getCell(part);
 				if(cell instanceof FormulaCell) {
 					parts.set(i, "" + ((FormulaCell)cell).getCalculatedValue());	
 				}
 				else
 				{
 					parts.set(i, "" + ((NumberCell)cell).getValue());
 				}
				
			}
		}
		double total = Double.parseDouble(parts.get(0));
		for (int i = 1; i <= parts.size() - 2; i = i + 2) {
			double nextVal = Double.parseDouble(parts.get(i + 1));
			switch (parts.get(i)) {
			case "+":
				total += nextVal;
				break;
			case "-":
				total -= nextVal;
				break;
			case "*":
				total *= nextVal;
				break;
			case "/":
				total /= nextVal;
				break;
			default:
				break;
			}
		}
		return total;
	}

	private static ArrayList<String> GetCellKeyParts(String cellKey) {
		return utils.Utils.breakString("(([A-Za-z]+)|(\\d+))",cellKey);
	}

	private double calculateSum(ArrayList<ICell> cells) {
		double total = 0.0;
		for (ICell cell : cells) {
			if(cell instanceof NumberCell) {
				total += Double.parseDouble(((NumberCell)cell).toString());
			}
			else if (cell instanceof FormulaCell) {
				total += ((FormulaCell)cell).getCalculatedValue();
			}
		}
		return total;
	}

	private double calculateAvg(ArrayList<ICell> cells) {
		return calculateSum(cells)/cells.size();
	}
	
	private ArrayList<ICell> getCellsForFunction() {
		String[] parts = _contents.split(" ");
		String beginCell = parts[1];
		String endCell = parts[3];
		
		ArrayList<String> cellKeyParts;		
		cellKeyParts = FormulaCell.GetCellKeyParts(beginCell);		
		char startColumn = cellKeyParts.get(0).toCharArray()[0];
		int startRow = Integer.parseInt(cellKeyParts.get(1));
		
		cellKeyParts = FormulaCell.GetCellKeyParts(endCell);		
		char endColumn = cellKeyParts.get(0).toCharArray()[0];
		int endRow = Integer.parseInt(cellKeyParts.get(1));
		
		ArrayList<ICell> cells = new ArrayList<ICell>();
		Sheet sheet = this.getSheet();
		for (int rowIndex = startRow; rowIndex <= endRow; rowIndex++) {
			for (char colChar = startColumn; colChar <= endColumn; colChar++) {
				cells.add(sheet.getCell(String.valueOf(colChar)+rowIndex));
			}
		}
		return cells;
	}

	@Override
	public String getValue() {
		if (_contents.equals(BLANK_VALUE)) {
			return Utils.getStringWithLengthAndFilledWithCharacter(
					Cell.MAX_LENGTH, ' ');
		} else {
			final String valueAsString = Utils.stripZeroDecimal("" + getCalculatedValue());
			if (valueAsString.length() <= Cell.MAX_LENGTH) {
				return Utils.center(valueAsString, Cell.MAX_LENGTH);
			} else {
				return valueAsString.substring(0,
						Cell.MAX_LENGTH - 1) + ">";
			}
		}
	}

	@Override
	public void setValue(String value) {
		_contents = utils.Utils.stripOuter(value, "(", ")").trim();
	}
}
