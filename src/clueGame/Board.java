package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private int numRows;
	private int numColumns;
	public final static int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE]; // contains board chars
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private String boardConfigFile;
	private String roomConfigFile;

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	private Board() {
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	// Initialization and file reading
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}

	}
	public void setConfigFiles(String csv, String txt) {
		boardConfigFile = csv;
		roomConfigFile = txt;
	}
	public void loadRoomConfig() throws BadConfigFormatException {
		try {
			FileReader reader = new FileReader(roomConfigFile);
			Scanner scanner = new Scanner(reader);
			while (scanner.hasNextLine()) {
				String line[] = scanner.nextLine().split(", ");
				rooms.put(line[0].charAt(0), line[1]);
				if (!line[2].equals("Card") && !line[2].equals("Other")) {
					// throw error
					scanner.close();
					throw new BadConfigFormatException();
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void loadBoardConfig() throws BadConfigFormatException {
		try {
			FileReader reader = new FileReader(boardConfigFile);
			Scanner scanner = new Scanner(reader);
			int row = 0;
			int columns = 0;
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(",");
				if (row == 0) {
					columns = line.length;
				}
				else {
					if (columns != line.length) {
						// throw error
						scanner.close();
						throw new BadConfigFormatException();
					}
				}
				for (int col = 0; col < line.length; col++) {
					if (!rooms.containsKey(line[col].charAt(0))) {
						// throw error
						scanner.close();
						throw new BadConfigFormatException();
					}
					board[row][col] = new BoardCell(row, col, line[col].charAt(0));
					if (line[col].length() > 1) {
						board[row][col].setInitial2(line[col].charAt(1));
					}
				}
				row++;
			}
			numRows = row;
			numColumns = columns;
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Adjacencies and targets
	public void calcAdjacencies() {
	}	
	public Set<BoardCell> getAdjList(int row, int column) {
		Set<BoardCell> temp = new HashSet<BoardCell>();

		if (board[row][column].isRoom() == true){
			return temp;
		}
		if (board[row][column].isDoorway() == true){

			if (row != 0 && board[row-1][column].isWalkway() ) {
				temp.add(board[row-1][column]);
			}
			if (row != board.length - 1 && board[row+1][column].isWalkway() ) {
				temp.add(board[row+1][column]);
			}
			if (column != 0 && board[row][column-1].isWalkway() ) {
				temp.add(board[row][column-1]);
			}
			if (column != board[0].length && board[row][column+1].isWalkway()) {
				temp.add(board[row][column+1]);
			}
		}
		if (board[row][column].isWalkway() == true){
			if (row != 0 && (board[row-1][column].isWalkway() || board[row-1][column].isDoorway()) ) {
				temp.add(board[row-1][column]);
			}
			if (row != numRows - 1 && (board[row+1][column].isWalkway() || board[row+1][column].isDoorway()) ) {
				temp.add(board[row+1][column]);
			}
			if (column != 0){// && (board[row][column-1].isWalkway() || board[row][column-1].isDoorway()) ) {
				temp.add(board[row][column-1]);
			}
			if (column != numColumns-1 && (board[row][column+1].isWalkway() || board[row][column+1].isDoorway())) {
				temp.add(board[row][column+1]);
			}
		}

		adjMatrix.put(board[row][column], temp);
		return adjMatrix.get(board[row][column]);
	}
	public void calcTargets(int row, int column, int numSteps) {
//		int x, y, z;
//		for (int i = 0; i < numRows; i++){
//			for (int j = 0; j < numColumns; j++){
//				if (row == i && column == j) continue; // makes sure the original cell doesn't get added 
//				x = 0;
//				y = 0;
//				z = 0;
//				x = Math.abs(column - j);
//				y = Math.abs(row - i);
//				z = x + y;
//				if (numSteps % 2 == 0){
//					if (z % 2 == 0 && z <= numSteps){
//						if (board[i][j].isWalkway() || board[i][j].isDoorway()){
//						targets.add(board[i][j]);
//						System.out.println("EVENcell: " + i + " " + j);
//						}
//					}
//				}
//				if (numSteps % 2 == 1){
//					if (board[i][j].isWalkway() || board[i][j].isDoorway()){
//					if (z % 2 == 1 && z <= numSteps){
//						targets.add(board[i][j]);
//						System.out.println("ODDcell: " + i + " " + j);
//					}
//					}
//				}
//			}
//		}	

//		Set<BoardCell> tempSet = getAdjList(row, column);		
//
//		if (numSteps == 1) {
//			for (BoardCell cell : tempSet) {
//				if (!targets.contains(cell)) {
//					targets.add(cell);
//				}
//			}
//		}
//		else {
//			for (BoardCell cell : tempSet) {
//				calcTargets(cell.getRow(), cell.getColumn(), numSteps-1);
//			}
//		}
		
		if (numSteps == 0){ 
			targets.add(board[row][column]);
			return; 
		}
		//Set<BoardCell> tempSet = getAdjList(row, column);
		if (row != 0) calcTargets(row - 1, column, numSteps - 1);
		if (column != 0) calcTargets(row, column - 1, numSteps - 1);
		if (row != numRows - 1) calcTargets(row + 1, column, numSteps - 1);
		if (column != numColumns - 1) calcTargets(row, column + 1, numSteps - 1);
		
//		for (BoardCell cell : tempSet) {
//			if (!targets.contains(cell)){
//				targets.add(cell);
//				System.out.println("Row: " + cell.getRow() + " Col: " + cell.getColumn());
//				calcTargets(cell.getRow(), cell.getColumn(), numSteps - 1);
//			}
//		}
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	// Misc getters and setters
	public int getNumRows() {
		return numRows;
	}
	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}
	public Map<Character, String> getLegend() {
		return rooms;
	}
	public BoardCell getCellAt(int row, int column) {
		return board[row][column];
	}
}
