package textexcel.cells;

import textexcel.Sheet;
import utils.Utils;

public class TextCell extends Cell implements ICell {
	private static final String BLANK_VALUE = "<empty>";
	/**
	 * Implementation for a cell containing text
	 */
	private String _contents = BLANK_VALUE;

	public TextCell(Sheet sheet, String value) {
		super(sheet);
		_contents = utils.Utils.stripOuter(value, "\"");
	}

	public TextCell(Sheet sheet) {
		this(sheet, BLANK_VALUE);
	}

	public String toString() {
		return "\"" + _contents + "\"";
	}

	public String getValue() {
		return _contents;
	}

	public void setValue(String value) {
		_contents=utils.Utils.stripOuter(value, "\"");
	}

	@Override
	public void setValue(Object value) {
		setValue((String)value);
		
	}
}
