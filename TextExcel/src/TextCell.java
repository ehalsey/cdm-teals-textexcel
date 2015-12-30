public class TextCell extends Cell implements ICell {
	private static final String BLANK_VALUE = "<blank>";
	/**
	 * Implementation for a cell containing text
	 */
	private String _contents = BLANK_VALUE;

	public String getText() {
		if (_contents.equals(BLANK_VALUE)) {
			return Utils.getStringWithLengthAndFilledWithCharacter(
					this.MAX_LENGTH, ' ');
		} else if (_contents.length() <= this.MAX_LENGTH) {
			return Utils.center(_contents, this.MAX_LENGTH);
		} else {
			return _contents.substring(0, this.MAX_LENGTH - 1) + ">";
		}
	}
}
