
public class TextCellValue implements ICellValue {

	@Override
	public boolean isMatch(String value) {
		return value.contains("\"");
	}

	@Override
	public String getCellTypeName() {
		return "TextCell";
	}	
}
