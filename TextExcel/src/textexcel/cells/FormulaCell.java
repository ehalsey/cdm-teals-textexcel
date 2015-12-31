package textexcel.cells;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.Utils;

public class FormulaCell extends Cell implements ICell {
	private static final String BLANK_VALUE = "<empty>";
	private String _contents = BLANK_VALUE;

	FormulaCell(String value) {
		_contents = stripParens(value);
	}

	private String stripParens(String value) {
		if (value.startsWith("(")) {
			value = value.substring(1);
			if (value.endsWith(")")) {
				value = value.substring(0, value.length() - 1);
			}
		}
		return value;
	}

	FormulaCell() {
		this(BLANK_VALUE);
	}

	public String toString() {
		return "(" + _contents + ")";
	}

	// ( 2 + 3 )
	private double getCalculatedValue() {
		ArrayList<String> parts = new ArrayList<String>();

		Pattern pattern = Pattern
				.compile("((\\d*\\.\\d+)|(\\d+)|([\\+\\-\\*/\\(\\)]))");
		Matcher m = pattern.matcher(_contents);
		while (m.find()) {
			parts.add(m.group());
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

	@Override
	public String getValue() {
		if (_contents.equals(BLANK_VALUE)) {
			return Utils.getStringWithLengthAndFilledWithCharacter(
					Cell.MAX_LENGTH, ' ');
		} else {
			final String valueAsString = "" + getCalculatedValue();
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
		_contents = stripParens(value);
	}
}
