package sprint2.test;

import sprint2.product.Board;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {

	private Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
	}
	
	@Test
    public void testDefaultBoardSize() {
        // Assert that the default board size is 3
        assertEquals(3, board.getBoardSize());
    }
	
	@Test
	public void testDefaultConstructor() {
		assertEquals(3, board.getBoardSize());
		assertTrue(board.getGameMode());
		assertEquals('B', board.getCurrentPlayer());
	}
	
	@Test
	public void testConstructorWithSize() {
		int size = 5;
		board = new Board(size);
		assertEquals(size, board.getBoardSize());
		assertTrue(board.getGameMode());
		assertEquals('B', board.getCurrentPlayer());
	}
	
	@Test
	public void testConstructorWithSizeAndMode() {
		int size = 7;
		boolean mode = false;
		board = new Board(size, mode);
		assertEquals(size, board.getBoardSize());
		assertEquals(mode, board.getGameMode());
		assertEquals('B', board.getCurrentPlayer());
	}
	
	@Test
	public void testInitializeBoard() {
		board.initializeBoard();
		char[][] expectedBoard = {
			{0, 0, 0},
			{0, 0, 0},
			{0, 0, 0},			
		};
		assertArrayEquals(expectedBoard, board.getBoard());
	}
	
	@Test
	public void testMakeMove() {
		board.makeMove(0, 0, 'X');
		assertEquals('X', board.getCell(0, 0));
		assertEquals('R', board.getCurrentPlayer());
		assertTrue(board.makeMove(1, 1, 'O'));
		assertEquals('O', board.getCell(1, 1));
		assertEquals('B', board.getCurrentPlayer());
	}
	
	@Test
    public void testMakeMoveOnInvalidCell() {
        // Set up the board
        int size = 3;
        board.setBoardSize(size);
        board.initializeBoard();

        // Make a move on an invalid cell (out of bounds)
        boolean result = board.makeMove(-1, 0, 'X'); // Row is out of bounds
        assertFalse(result);

        // Make a move on an invalid cell (already occupied)
        board.makeMove(0, 0, 'X');
        result = board.makeMove(0, 0, 'O'); // Cell is already occupied
        assertFalse(result);
    }

	@Test
    public void testSetAndGetGameMode() {
        // Default game mode is simple (true)
        assertTrue(board.getGameMode());

        // Set game mode to general (false)
        board.setGameMode(false);
        assertFalse(board.getGameMode());

        // Set game mode back to simple (true)
        board.setGameMode(true);
        assertTrue(board.getGameMode());
    }
	
	@Test
	public void testSetAndGetBoardSize() {
		int size = 5;
		board.setBoardSize(size);
		assertEquals(size, board.getBoardSize());
	}
}
