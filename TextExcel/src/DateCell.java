import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCell extends Cell implements ICell {
	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	private static final Date BLANK_VALUE = null;
	/**
	 * Implementation for a cell containing a Number
	 */
	private Date _contents = BLANK_VALUE;

	public DateCell(Date value) {
		_contents = value;
	}

	public DateCell(String value) throws ParseException {
		this(DateCell.SIMPLE_DATE_FORMAT.parse(value));
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
}
