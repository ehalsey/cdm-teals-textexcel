package textexcel.cells;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import textexcel.Sheet;
import utils.Utils;

public class DateCell extends Cell implements ICell {
	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	private static final Date BLANK_VALUE = null;
	/**
	 * Implementation for a cell containing a Number
	 */
	private Date _contents = BLANK_VALUE;
	
	public DateCell(Sheet sheet) {
		super(sheet);
	}

	public DateCell(Sheet sheet, Date value) {
		this(sheet);
		_contents = value;
	}

	public DateCell(Sheet sheet, String value) throws ParseException {
		this(sheet, DateCell.SIMPLE_DATE_FORMAT.parse(value));
	}

	public String toString() {
		return "" + SIMPLE_DATE_FORMAT.format(_contents);
	}

	@Override
	public String getValue() {
		String contents = "" + SIMPLE_DATE_FORMAT.format(_contents);
		if (_contents == BLANK_VALUE) {
			return Utils.getStringWithLengthAndFilledWithCharacter(
					DateCell.MAX_LENGTH, ' ');
		} else if (contents.length() <= Cell.MAX_LENGTH) {
			return Utils.center(contents, Cell.MAX_LENGTH);
		} else {
			return contents.substring(0, Cell.MAX_LENGTH - 1) + ">";
		}
	}

	@Override
	public void setValue(String value) {
		try {
			_contents = DateCell.SIMPLE_DATE_FORMAT.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
