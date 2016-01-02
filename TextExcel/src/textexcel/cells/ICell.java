package textexcel.cells;

public interface ICell {
	public void setValue(Object value);
	//gets the value to be printed as an individual cell
	public String toString();
	public Object getValue();
}
