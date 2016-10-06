package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;


public class NDRS_FileInitTests {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 24;
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("NDRS_ClueLayout.csv", "NDRS_ClueLegend.txt");		
		board.initialize();
	}
	@Test
	public void testRooms() {
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		assertEquals("BrownBuilding", legend.get('B'));
		assertEquals("GreenCenter", legend.get('G'));
		assertEquals("MarquezHall", legend.get('M'));
		assertEquals("CTLM", legend.get('C'));
		assertEquals("IntramuralFields", legend.get('I'));
		assertEquals("Kafadar", legend.get('K'));
		assertEquals("Streets", legend.get('X'));
		
	}
	
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(3, 4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(6, 12);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(9, 19);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(19, 11);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = board.getCellAt(1, 1);
		assertFalse(room.isDoorway());	
		BoardCell cell = board.getCellAt(7, 7);
		assertFalse(cell.isDoorway());		

	}
	
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(15, numDoors);
	}

	@Test
	public void testRoomInitials() {
		assertEquals('B', board.getCellAt(0, 0).getInitial());
		assertEquals('M', board.getCellAt(0, 8).getInitial());
		assertEquals('G', board.getCellAt(0, 17).getInitial());
		assertEquals('A', board.getCellAt(14, 6).getInitial());
		assertEquals('C', board.getCellAt(16, 23).getInitial());
		assertEquals('X', board.getCellAt(7, 7).getInitial());
		assertEquals('K', board.getCellAt(12,12).getInitial());
	}
	

}