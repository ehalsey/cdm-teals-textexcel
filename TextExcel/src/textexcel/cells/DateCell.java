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

	public Date getValue() {
		return _contents;
	}
	
	@Override
	public void setValue(Object value) {
		if(value instanceof String) {
			setValue((String)value);
		}
		else if(value instanceof Date) {
			setValue((Date)value);
		}
	};

	public void setValue(Date value) {
		_contents = value;
	}

	public void setValue(String value) {
		try {
			setValue(DateCell.SIMPLE_DATE_FORMAT.parse(value));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
