package textexcel.cells;
public interface ICellValue {
	static final String nameSpace = "textexcel.cells."; 
	public boolean isMatch(String value);
	public String getCellTypeName();
}
