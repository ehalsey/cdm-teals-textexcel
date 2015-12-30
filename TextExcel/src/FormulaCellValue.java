
public class FormulaCellValue implements ICellValue {

	@Override
	public boolean isMatch(String value) {
		return value.contains("(");
	}

	@Override
	public String getCellTypeName() {
		return "FormulaCell";
	}

}
