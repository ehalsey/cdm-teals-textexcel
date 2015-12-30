
public interface ICell {
	public void setValue(String value);
	//gets the value to be printed in the sheet
	public String getValue();
	//gets the value to be printed as an individual cell
	public String toString();
}
