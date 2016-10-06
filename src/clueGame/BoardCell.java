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
	private int column;
	private int row;
	private char initial;
	private DoorDirection direction = DoorDirection.NONE;
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
		if (initial == 'X') {
			return true;
		}
		return false;
		
	}
	public boolean isRoom() {
		return false;
		
	}
	public boolean isDoorway() {
		if (direction != DoorDirection.NONE) {
			return true;
		}
		return false;
		
	}
	public DoorDirection getDoorDirection() {
		return direction;
	}
	public char getInitial() {
		return initial;
	}
	
	public void setDoor(char direction) {
		switch (direction) {
		case 'U':
			this.direction = DoorDirection.UP;
			break;
		case 'L':
			this.direction = DoorDirection.LEFT;
			break;
		case 'R':
			this.direction = DoorDirection.RIGHT;
			break;
		case 'D':
			this.direction = DoorDirection.DOWN;
			break;
		default:
			this.direction = DoorDirection.NONE;
			break;
		}
	}
}
