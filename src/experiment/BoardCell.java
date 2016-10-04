// Noah Deibert
// Robert Schreibman
package experiment;

public class BoardCell {
	
	public BoardCell(int column, int row) {
		super();
		this.column = column;
		this.row = row;
	}
	private int column;
	private int row;
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	@Override
	public String toString() {
		return "BoardCell [column=" + column + ", row=" + row + "]";
	}
	
}
