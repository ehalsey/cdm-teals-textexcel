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
	public Object getValue() {
		return _contents;
	}

	public void setValue(String value) {
		_contents = Double.parseDouble(value);
	}
	public void setValue(double value) {
		_contents = value;
	}

	@Override
	public void setValue(Object value) {
		setValue((String)value);
		
	}
}
