public class TextCell extends Cell implements ICell {
	private static final String BLANK_VALUE = "<empty>";
	/**
	 * Implementation for a cell containing text
	 */
	private String _contents = BLANK_VALUE;

	public TextCell(String value) {
		if(value.startsWith("\"")) {
			value = value.substring(1);
			if(value.endsWith("\"")) {
				value = value.substring(0, value.length()-1);
			}
		}		
		_contents = value;
	}

	public TextCell() {
		this(BLANK_VALUE);
	}

	public String toString() {
		return "\"" + _contents + "\"";
	}

	@Override
	public String getValue() {
		if (_contents.equals(BLANK_VALUE)) {
			return Utils.getStringWithLengthAndFilledWithCharacter(
					Cell.MAX_LENGTH, ' ');
		} else if (_contents.length() <= Cell.MAX_LENGTH) {
			return Utils.center(_contents, Cell.MAX_LENGTH);
		} else {
			return _contents.substring(0, Cell.MAX_LENGTH - 1) + ">";
		}
	}
}
