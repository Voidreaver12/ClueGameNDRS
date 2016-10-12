// Noah Deibert
// Robert Schreibman
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private int numRows;
	private int numColumns;
	public final static int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private Map<Character, String> roomTypes = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private BoardCell lastVisited;
	private ArrayList<BoardCell> visited = new ArrayList<BoardCell>();
	private int totalSteps = 0;
	private BoardCell startingCell;
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
			calcAdjacencies();
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
				if (!line[2].equals("Card") && !line[2].equals("Other")) {
					// throw error
					scanner.close();
					throw new BadConfigFormatException();
				}
				rooms.put(line[0].charAt(0), line[1]);
				roomTypes.put(line[0].charAt(0), line[2]);
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
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				Set<BoardCell>  adj = new HashSet<BoardCell>();
				if (board[i][j].isDoorway()) {
					switch(board[i][j].getDoorDirection()) {
					case UP:
						adj.add(board[i-1][j]);
						break;
					case DOWN:
						adj.add(board[i+1][j]);
						break;
					case RIGHT:
						adj.add(board[i][j+1]);
						break;
					case LEFT:
						adj.add(board[i][j-1]);
						break;
					default:
						break;
					}
					adjMatrix.put(board[i][j], adj);
				}
				else if (board[i][j].isRoom()) {
					adjMatrix.put(board[i][j], adj);
				}
				else if (board[i][j].isWalkway()) {
					if (i != 0) {
						if (board[i-1][j].isDoorway() && board[i-1][j].getDoorDirection() == DoorDirection.DOWN) {
							adj.add(board[i-1][j]);
						}
						if (board[i-1][j].isWalkway()) {
							adj.add(board[i-1][j]);
						}
					}
					if (i != numRows - 1) {
						if (board[i+1][j].isDoorway() && board[i+1][j].getDoorDirection() == DoorDirection.UP) {
							adj.add(board[i+1][j]);
						}
						if (board[i+1][j].isWalkway()) {
							adj.add(board[i+1][j]);
						}
					}
					if (j != 0) {
						if (board[i][j-1].isDoorway() && board[i][j-1].getDoorDirection() == DoorDirection.RIGHT) {
							adj.add(board[i][j-1]);
						}
						if (board[i][j-1].isWalkway()) {
							adj.add(board[i][j-1]);
						}
					}
					if (j != numColumns - 1) {
						if (board[i][j+1].isDoorway() && board[i][j+1].getDoorDirection() == DoorDirection.LEFT) {
							adj.add(board[i][j+1]);
						}
						if (board[i][j+1].isWalkway()) {
							adj.add(board[i][j+1]);
						}
					}
					adjMatrix.put(board[i][j], adj);
				}
			}
		}
	}	
	public Set<BoardCell> getAdjList(int row, int column) {
		return adjMatrix.get(board[row][column]);
	}
	public void calcTargets(int row, int column, int numSteps) {
		
		if (numSteps > totalSteps) {
			startingCell = board[row][column];
			totalSteps = numSteps;
		}
		Set<BoardCell> tempSet = new HashSet<BoardCell>();
		for (BoardCell cell : getAdjList(row, column)) {
			tempSet.add(cell);
		}
		if (numSteps ==1 ) {
			//System.out.println(tempSet);
		}
		if (!visited.isEmpty()) {
			lastVisited = visited.get(visited.size()-1);
			//System.out.println("last visited " + lastVisited);
			if (tempSet.contains(lastVisited)) {
				//System.out.println("at cell " + board[row][column]);
				//System.out.println("adj = " + tempSet);
				tempSet.remove(lastVisited);
				//System.out.println("adj = " + tempSet);
			}
		}
		if (numSteps == 1) {
			//System.out.println("at cell " + board[row][column]);
			//System.out.println(tempSet);
			for (BoardCell cell : tempSet) {
				if (!targets.contains(cell) && cell != startingCell) {
					//System.out.println("adding " + cell);
					targets.add(cell);
				}
			}
		}
		else {
			//System.out.println(tempSet);
			for (BoardCell cell : tempSet) {
				//System.out.println("at cell " + board[row][column]);
				visited.add(board[row][column]);
				if (cell.isDoorway() && !targets.contains(cell) && cell != startingCell) {
					targets.add(cell);
				}
				if (cell != startingCell) {
					calcTargets(cell.getRow(), cell.getColumn(), numSteps - 1);
				}
				
			}
		}
		return;
	}
	public Set<BoardCell> getTargets() {
		Set<BoardCell> temp = new HashSet<BoardCell>();
		for (BoardCell cell : targets) {
			temp.add(cell);
		}
		targets.clear();
		visited.clear();
		totalSteps = 0;
		return temp;
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
