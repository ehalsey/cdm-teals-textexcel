package textexcel.cells;

public interface ICell {
	public void setValue(String value);
	//gets the value to be printed in the sheet
	//TODO need to remove the formatting from the cell classes and move up
	public String getValue();
	//gets the value to be printed as an individual cell
	public String toString();
}
