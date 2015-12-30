public class NumberCell extends Cell implements ICell {
	private static final double BLANK_VALUE = (double) 0;
	/**
	 * Implementation for a cell containing a Number
	 */
	private double _contents = BLANK_VALUE;

	public NumberCell(Double value) {
		_contents = value;
	}

	public NumberCell(String value) {
		this(Double.parseDouble(value));
	}
	
	public String toString() {
		return ""+_contents;
	}

	@Override
	public String getValue() {
		String contents = "" + _contents;
		if (_contents == BLANK_VALUE) {
			return Utils.getStringWithLengthAndFilledWithCharacter(
					Cell.MAX_LENGTH, ' ');
		} else if (contents.length() <= Cell.MAX_LENGTH) {
			return Utils.center(contents, Cell.MAX_LENGTH);
		} else {
			return contents.substring(0, Cell.MAX_LENGTH - 1) + ">";
		}
	}
}
