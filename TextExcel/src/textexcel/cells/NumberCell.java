package textexcel.cells;

import textexcel.Sheet;
import utils.Utils;

public class NumberCell extends Cell implements ICell {
	private static final double BLANK_VALUE = (double) 0;
	/**
	 * Implementation for a cell containing a Number
	 */
	private double _contents = BLANK_VALUE;
	
	public NumberCell(Sheet sheet) {
		super(sheet);
	}

	public NumberCell(Sheet sheet, Double value) {
		this(sheet);
		_contents = value;
	}

	public NumberCell(Sheet sheet, String value) {
		this(sheet,Double.parseDouble(value));
	}
	
	public String toString() {
		return Utils.stripZeroDecimal(""+_contents);
	}

	@Override
	public String getValue() {
		String contents = "" + _contents;
		contents = Utils.stripZeroDecimal(contents);
		if (_contents == BLANK_VALUE) {
			return Utils.getStringWithLengthAndFilledWithCharacter(
					Cell.MAX_LENGTH, ' ');
		} else if (contents.length() <= Cell.MAX_LENGTH) {
			return Utils.center(contents, Cell.MAX_LENGTH);
		} else {
			return contents.substring(0, Cell.MAX_LENGTH - 1) + ">";
		}
	}

	@Override
	public void setValue(String value) {
		_contents = Double.parseDouble(value);
	}
}
