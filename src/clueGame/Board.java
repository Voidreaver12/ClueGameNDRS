package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
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
	private Map<Character, String> legend = new HashMap<Character, String>();
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
	
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void loadRoomConfig() throws BadConfigFormatException {
		try {
			FileReader reader = new FileReader(roomConfigFile);
			Scanner scanner = new Scanner(reader);
			while (scanner.hasNextLine()) {
				String line[] = scanner.nextLine().split(", ");
				rooms.put(line[0].charAt(0), line[1]);
				if (line[2] != "Card" && line[2] != "Other") {
					throw new BadConfigFormatException();
				}
				legend.put(line[0].charAt(0), line[2]);
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
			int col = 0;
			String line[] = scanner.nextLine().split(",");
			for (col = 0; col < col; col++) {
				board[row][col] = new BoardCell(row, col, line[col].charAt(0));
				if (line[col].length() > 1) {
					board[row][col].setDoor(line[col].charAt(1));
				}
			}
			numColumns = col;
			row++;
			while (scanner.hasNextLine()) {
				line = scanner.nextLine().split(",");
				if (line.length != numColumns) {
					throw new BadConfigFormatException();
				}
				for (col = 0; col < col; col++) {
					board[row][col] = new BoardCell(row, col, line[col].charAt(0));
					if (line[col].length() > 1) {
						board[row][col].setDoor(line[col].charAt(1));
					}
				}
				row++;
				
			}
			numRows = row;
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void calcAdjacencies() {
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
	}

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

	public void setConfigFiles(String csv, String txt) {
		boardConfigFile = csv;
		roomConfigFile = txt;
	}
	
	public Map<Character, String> getLegend() {
		return rooms;
	}
	
	public BoardCell getCellAt(int row, int column) {
		return board[row][column];
	}
}
