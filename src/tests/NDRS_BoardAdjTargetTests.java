package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class NDRS_BoardAdjTargetTests {
	
	// Set up / before stuff
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("NDRS_ClueLayout.csv", "NDRS_ClueLegend.txt");
		board.initialize();
	}
	
	// Adjacency tests
	
	// Adjacency inside rooms (orange)
	@Test
	public void testAdjInsideRooms() {
		Set<BoardCell> testList = board.getAdjList(0, 4);
		assertEquals(0, testList.size());
		testList = board.getAdjList(3, 17);
		assertEquals(0, testList.size());
		testList = board.getAdjList(20, 2);
		assertEquals(0, testList.size());
		testList = board.getAdjList(21, 20);
		assertEquals(0, testList.size());
	}
	
	// Adjacency on door exits (purple)
	@Test
	public void testAdjOnExits() {
		//down
		Set<BoardCell> testList = board.getAdjList(2, 9);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(3, 9)));
		//right
		testList = board.getAdjList(13, 6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 7)));
	}
	
	// Adjacency on door entrances (green)
	@Test
	public void testAdjOnEntrances() {
		// entering from below
		Set<BoardCell> testList = board.getAdjList(3, 9);
		assertTrue(testList.contains(board.getCellAt(3, 8)));
		assertTrue(testList.contains(board.getCellAt(2, 9)));
		assertTrue(testList.contains(board.getCellAt(4, 9)));
		// entering from right
		testList = board.getAdjList(13, 7);
		assertTrue(testList.contains(board.getCellAt(13, 6)));
		assertTrue(testList.contains(board.getCellAt(13, 8)));
		assertTrue(testList.contains(board.getCellAt(12, 7)));
		assertTrue(testList.contains(board.getCellAt(14, 7)));
		// entering from left
		testList = board.getAdjList(15, 18);
		assertTrue(testList.contains(board.getCellAt(15, 19)));
		assertTrue(testList.contains(board.getCellAt(15, 17)));
		assertTrue(testList.contains(board.getCellAt(16, 18)));
		// entering from above
		testList = board.getAdjList(18, 10);
		assertTrue(testList.contains(board.getCellAt(18, 9)));
		assertTrue(testList.contains(board.getCellAt(18, 11)));
		assertTrue(testList.contains(board.getCellAt(17, 10)));
		assertTrue(testList.contains(board.getCellAt(19, 10)));
	}

	// Adjacency on walkways in various conditions (red)
	@Test
	public void testAdjOnWalkways() {
		// on edges of board
		Set<BoardCell> testList = board.getAdjList(0, 15);
		assertTrue(testList.contains(board.getCellAt(0, 16)));
		assertTrue(testList.contains(board.getCellAt(0, 14)));
		assertTrue(testList.contains(board.getCellAt(1, 15)));
		testList = board.getAdjList(7, 23);
		assertTrue(testList.contains(board.getCellAt(7, 22)));
		assertTrue(testList.contains(board.getCellAt(6, 23)));
		testList = board.getAdjList(10, 0);
		assertTrue(testList.contains(board.getCellAt(10, 1)));
		assertTrue(testList.contains(board.getCellAt(11, 0)));
		testList = board.getAdjList(23, 6);
		assertTrue(testList.contains(board.getCellAt(23, 5)));
		assertTrue(testList.contains(board.getCellAt(22, 6)));
		
		// surrounded by walkways
		testList = board.getAdjList(4, 7);
		assertTrue(testList.contains(board.getCellAt(4, 8)));
		assertTrue(testList.contains(board.getCellAt(4, 6)));
		assertTrue(testList.contains(board.getCellAt(3, 7)));
		assertTrue(testList.contains(board.getCellAt(5, 7)));
		
		// next to room, but not next to door
		testList = board.getAdjList(6, 14);
		assertTrue(testList.contains(board.getCellAt(6, 15)));
		assertTrue(testList.contains(board.getCellAt(5, 14)));
		assertTrue(testList.contains(board.getCellAt(7, 14)));
		testList = board.getAdjList(20, 14);
		assertTrue(testList.contains(board.getCellAt(20, 15)));
		assertTrue(testList.contains(board.getCellAt(19, 14)));
	}
/*	
	// Target Tests
	
	// targets from walkways with 1,2,3,4 spaces
	@Test
	public void testTargetsOnWalkways() {
		// one space
		board.calcTargets(17, 7, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 8)));
		assertTrue(targets.contains(board.getCellAt(17, 6)));
		assertTrue(targets.contains(board.getCellAt(16, 7)));
		assertTrue(targets.contains(board.getCellAt(18, 7)));
		// two spaces
		board.calcTargets(6, 16, 2);
		targets = board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 14)));
		assertTrue(targets.contains(board.getCellAt(6, 18)));
		assertTrue(targets.contains(board.getCellAt(4, 16)));
		assertTrue(targets.contains(board.getCellAt(8, 16)));
		assertTrue(targets.contains(board.getCellAt(5, 15)));
		assertTrue(targets.contains(board.getCellAt(5, 17)));
		assertTrue(targets.contains(board.getCellAt(7, 15)));
		assertTrue(targets.contains(board.getCellAt(7, 17)));
		// three spaces
		board.calcTargets(7, 8, 3);
		targets = board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 8)));
		assertTrue(targets.contains(board.getCellAt(5, 7)));
		assertTrue(targets.contains(board.getCellAt(5, 9)));
		assertTrue(targets.contains(board.getCellAt(6, 8)));
		assertTrue(targets.contains(board.getCellAt(6, 6)));
		assertTrue(targets.contains(board.getCellAt(7, 7)));
		assertTrue(targets.contains(board.getCellAt(7, 9)));
		assertTrue(targets.contains(board.getCellAt(7, 11)));
		assertTrue(targets.contains(board.getCellAt(8, 6)));
		assertTrue(targets.contains(board.getCellAt(8, 8)));
		assertTrue(targets.contains(board.getCellAt(8, 10)));
		assertTrue(targets.contains(board.getCellAt(9, 7)));
		assertTrue(targets.contains(board.getCellAt(10, 8)));
		// four spaces
		board.calcTargets(15, 0, 4);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 2)));
		assertTrue(targets.contains(board.getCellAt(15, 4)));
		assertTrue(targets.contains(board.getCellAt(16, 1)));
		assertTrue(targets.contains(board.getCellAt(16, 3)));	
	}
	
	// targets on walkways that are close enough to enter a room
	@Test
	public void testTargetsEnteringRoom() {
		// does not need all spaces to enter room
		board.calcTargets(10, 3, 4);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 2)));
		assertTrue(targets.contains(board.getCellAt(7, 3)));
		assertTrue(targets.contains(board.getCellAt(8, 3)));
		assertTrue(targets.contains(board.getCellAt(9, 2)));
		assertTrue(targets.contains(board.getCellAt(9, 6)));
		assertTrue(targets.contains(board.getCellAt(10, 1)));
		assertTrue(targets.contains(board.getCellAt(10, 5)));
		assertTrue(targets.contains(board.getCellAt(10, 7)));
		assertTrue(targets.contains(board.getCellAt(11, 0)));
		assertTrue(targets.contains(board.getCellAt(11, 2)));
		assertTrue(targets.contains(board.getCellAt(11, 4)));
		assertTrue(targets.contains(board.getCellAt(11, 6)));
		// needs all 2 spaces to enter room
		board.calcTargets(20, 16, 2);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 16)));
		assertTrue(targets.contains(board.getCellAt(19, 15)));
		assertTrue(targets.contains(board.getCellAt(19, 17)));
		assertTrue(targets.contains(board.getCellAt(20, 14)));
		assertTrue(targets.contains(board.getCellAt(21, 15)));
		assertTrue(targets.contains(board.getCellAt(22, 16)));	
	}
	
	// targets from doorway leaving room
	@Test
	public void testTargetsExitingRoom() {
		board.calcTargets(15, 19, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 18)));	
		board.calcTargets(19, 10, 2);
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 10)));	
	}
	*/
}
