// Noah Deibert
// Robert Schreibman
package clueGame;

public class BoardCell {
	
	public BoardCell(int row, int column, char initial) {
		super();
		this.column = column;
		this.row = row;
		this.initial = initial;
	}
	public BoardCell(int row, int column) { //this constructor used for IntBoard
		super();
		this.column = column;
		this.row = row;
	}
	private int column;
	private int row;
	private char initial;
	private char initial2;
	
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
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
	public boolean isWalkway() {
		if (initial == 'X'){
			return true;
		}
		return false;
		
	}
	public boolean isRoom() {
		if (initial != 'X' && !(initial2 == 'U' || initial2 == 'D' || initial2 == 'L' || initial2 == 'R')){
			return true;
		}
		return false;
		
	}
	public boolean isDoorway() {
		if (initial2 == 'U' || initial2 == 'D' || initial2 == 'L' || initial2 == 'R') {
			return true;
		}
		return false;
		
	}
	public DoorDirection getDoorDirection() {
		switch (initial2) {
			case 'D':
				return DoorDirection.DOWN;
			case 'U':
				return DoorDirection.UP;
			case 'R':
				return DoorDirection.RIGHT;
			case 'L':
				return DoorDirection.LEFT;
			default:
				return DoorDirection.NONE;
		}
	}
	public char getInitial() {
		return initial;
	}
	
	public void setInitial2(char initial2) {
		this.initial2 = initial2;
	}
	public char getInitial2() {
		return initial2;
	}
}
