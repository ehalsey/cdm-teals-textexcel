
public class SheetCell {
	private int _row;
	private int _col;
	private ICell _cell;
	
	SheetCell(int row, int col, ICell cell) {
		this._row = row;
		this._col = col;
		this._cell = cell;
	}
	
	public int getRow() {
		return _row;
	}
	
	public int getColumn() {
		return _col;
	}
	
	public String getText() {
		return _cell.getText();
	}
}
