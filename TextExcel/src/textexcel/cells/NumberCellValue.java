package textexcel.cells;

public class NumberCellValue implements ICellValue {

	@Override
	public boolean isMatch(String value) {
		try {
			Double.parseDouble(value);
			return true;
		}
		catch (NumberFormatException e) {}
		return false;
	}

	@Override
	public String getCellTypeName() {
		return ICellValue.nameSpace + "NumberCell";
	}

}
