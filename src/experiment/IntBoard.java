// Noah Deibert
// Robert Schreibman
package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import clueGame.BoardCell;

public class IntBoard {
	private Set<BoardCell> visitedSet;
	private Set<BoardCell> targetSet;
	private Map<BoardCell, Set<BoardCell>> adjacencyMap;
	private BoardCell[][] myBoard;	// [column][row] or [x][y] or [i][j]
	private static final int BOARD_SIZE = 4;
	
	public IntBoard() {
		visitedSet = new HashSet<BoardCell>();
		targetSet = new HashSet<BoardCell>();
		adjacencyMap = new HashMap<BoardCell, Set<BoardCell>>();
		myBoard = new BoardCell[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				//myBoard[i][j] = new BoardCell(i, j);
			}
		}
	}
	public void calcAdjacencies() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				Set<BoardCell> adjSet = new HashSet<BoardCell>();
				BoardCell thisCell  = myBoard[i][j];
				if (i != 0) {
					adjSet.add(myBoard[i-1][j]);	
				}
				if (i != BOARD_SIZE - 1) {
					adjSet.add(myBoard[i+1][j]);
				}
				if (j != 0) {
					adjSet.add(myBoard[i][j-1]);
				}
				if (j != BOARD_SIZE - 1) {
					adjSet.add(myBoard[i][j+1]);
				}
				adjacencyMap.put(thisCell, adjSet);
			}
		}
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
		Set<BoardCell> tempSet = getAdjList(startCell);
		if (pathLength == 1) {
			for (BoardCell cell : tempSet) {
				if (!targetSet.contains(cell)) {
					targetSet.add(cell);
				}
			}
		}
		else {
			for (BoardCell cell : tempSet) {
				calcTargets(cell, pathLength-1);
			}
		}
		return;
	}
	
	public Set<BoardCell> getTargets() {
		return targetSet;
		
	}
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjacencyMap.get(cell);
	}
	public BoardCell getCell(int i, int j) {
		return myBoard[i][j];
	}
}
