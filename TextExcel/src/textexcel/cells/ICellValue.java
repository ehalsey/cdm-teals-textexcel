package textexcel.cells;
public interface ICellValue {
	public boolean isMatch(String value);
	public String getCellTypeName();
}
