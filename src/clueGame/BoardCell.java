// Noah Deibert
// Robert Schreibman
package clueGame;

public class BoardCell {
	
	public BoardCell(int column, int row) {
		super();
		this.column = column;
		this.row = row;
	}
	private int column;
	private int row;
	private char initial;
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
	public boolean isWalkway() {
		return false;
		
	}
	public boolean isRoom() {
		return false;
		
	}
	public boolean isDoorway() {
		return false;
		
	}
	public DoorDirection getDoorDirection() {
		return null;
	}
	public char getInitial() {
		return initial;
	}
}
