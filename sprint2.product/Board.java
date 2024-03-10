package sprint2.product;

public class Board {
	private char[][] board;
	private char currentPlayer;
	private int boardSize;
	private boolean simpleGame;

	// Default constructor initializes a 3x3 board in simple game mode with blue player starting
	public Board() {
		this(3, true);
	}

	// Constructor to initialize a board with a specified size in simple game mode with blue player starting
	public Board(int size) {
		this(size, true);
	}

	// Constructor to initialize a board with specified size and game mode with blue player starting
	public Board(int size, boolean mode) {
		this.boardSize = size;
		this.simpleGame = mode;
		this.currentPlayer = 'B';
		this.board = new char[size][size];
	}

  public void setBoardSize(int size) {
        this.boardSize = size;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = 0;
            }
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public void printBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void setGameMode(boolean mode) {
        this.simpleGame = mode; // true for simple game, false for general game
    }

    public boolean getGameMode() {
        return simpleGame;
    }

    public void setCurrentPlayer(char player) {
        this.currentPlayer = player;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public char getCell(int row, int column) {
        if (row >= 0 && row < boardSize && column >= 0 && column < boardSize)
            return board[row][column];
        else
            return 'X';
    }

    public char getLetter(int row, int column) {
        return board[row][column];
    }
    
    public boolean makeMove(int row, int column, char letter) {
        boolean isValidMove = isValidMove(row, column);
        if (isValidMove) {
            board[row][column] = letter;
            switchPlayer();
        }
        return isValidMove;
    }

    private boolean isValidMove(int row, int column) {
        return row >= 0 && row < boardSize && column >= 0 && column < boardSize && board[row][column] == 0;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'B') ? 'R' : 'B';
    }
}
