package sprint2.product;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class GUI extends JFrame implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Board board;
    private int boardSize;
    
    public static final int CELL_SIZE = 40;
    public static final int GRID_WIDTH = 2;
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
    
    public final int CELL_PADDING = CELL_SIZE / 6;
    public final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;
    
    private int CANVAS_WIDTH;
    private int CANVAS_HEIGHT;
    
    private GameBoardCanvas gameBoardCanvas;
    private JTextField currentTurnTextField;
    
    private JRadioButton buttonBlueS;
    private JRadioButton buttonBlueO;
    private ButtonGroup blueSelectionGroup; // For Blue Player’s radio buttons
    
    private JRadioButton buttonRedS;
    private JRadioButton buttonRedO;
    private ButtonGroup redSelectionGroup; // For Red Player’s radio buttons
    
    private ButtonGroup gameModeSelectionGroup; // For game mode radio buttons
    private JSpinner spinnerBoardSize; // to adjust boardSize
 
    private char blueLetter = 'S';
    private char redLetter = 'O';
    
    public GUI(Board board) {
    	getContentPane().setBackground(Color.WHITE);
        setBackground(Color.WHITE);
        this.board = board;
        setContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("SOS GAME");
        setVisible(true);
    }
   
    private void setContentPane() {
    	gameBoardCanvas = new GameBoardCanvas();
    	gameBoardCanvas.setBorder(BorderFactory.createLineBorder(Color.BLACK, GRID_WIDTH));
    	updateCanvasSize();
          
      // gameboard canvas goes in center of these panels
      JPanel panelNorth = new JPanel();
      panelNorth.setBackground(Color.WHITE);
      JPanel panelEast = new JPanel();
      panelEast.setBackground(Color.WHITE);
      JPanel panelSouth = new JPanel();
      panelSouth.setBackground(Color.WHITE);
      JPanel panelWest = new JPanel();
      panelWest.setBackground(Color.WHITE);
      
      // simple or general game mode option
      JLabel labelGameMode = new JLabel("Mode:");
      JRadioButton buttonSimpleGame = new JRadioButton("Simple", true);
      buttonSimpleGame.setBackground(Color.WHITE);
      JRadioButton buttonGeneralGame = new JRadioButton("General");
      buttonGeneralGame.setBackground(Color.WHITE);
      gameModeSelectionGroup = new ButtonGroup();
      gameModeSelectionGroup.add(buttonSimpleGame);
      gameModeSelectionGroup.add(buttonGeneralGame);
      
      JLabel labelBoardSize = new JLabel("         Board Size:");
      labelBoardSize.setBackground(Color.WHITE);

      // blue player's buttons
      JLabel labelBluePlayer = new JLabel("Blue Player:");
      buttonBlueS = new JRadioButton("S", true);
      buttonBlueS.setActionCommand("S");
      buttonBlueS.setBackground(Color.WHITE);
      buttonBlueO = new JRadioButton("O");
      buttonBlueO.setActionCommand("O");
      buttonBlueO.setBackground(Color.WHITE);
 
      blueSelectionGroup = new ButtonGroup();
      blueSelectionGroup.add(buttonBlueS);
      blueSelectionGroup.add(buttonBlueO);
      blueSelectionGroup.clearSelection();

      // red player's buttons
      JLabel labelRedPlayer = new JLabel("Red Player:");
      buttonRedS = new JRadioButton("S", true);
      buttonRedS.setActionCommand("S");
      buttonRedS.setBackground(Color.WHITE);
      buttonRedO = new JRadioButton("O");
      buttonRedO.setActionCommand("O");
      buttonRedO.setBackground(Color.WHITE);
      
      redSelectionGroup = new ButtonGroup();
      redSelectionGroup.add(buttonRedS);
      redSelectionGroup.add(buttonRedO);
      redSelectionGroup.clearSelection();

      buttonBlueS.addActionListener(e -> {
          blueLetter = 'S';
      });
      buttonBlueO.addActionListener(e -> {
          blueLetter = 'O';
      });
      buttonRedS.addActionListener(e -> {
          redLetter = 'S';
      });
      buttonRedO.addActionListener(e -> {
          redLetter = 'O';
      });
      
      panelNorth.add(labelGameMode);
      panelNorth.add(buttonSimpleGame);
      panelNorth.add(buttonGeneralGame);
      panelNorth.add(labelBoardSize);
      
      panelEast.add(labelRedPlayer);
      panelEast.add(buttonRedS);
      panelEast.add(buttonRedO);
      
      currentTurnTextField = new JTextField("Current turn:");
      currentTurnTextField.setBorder(null);
      currentTurnTextField.setBackground(Color.WHITE);
      currentTurnTextField.setColumns(12);
      panelSouth.add(currentTurnTextField);

      panelWest.add(labelBluePlayer);
      panelWest.add(buttonBlueS);
      panelWest.add(buttonBlueO);

      Container contentPane = getContentPane();
      contentPane.setLayout(new BorderLayout());
      contentPane.add(panelNorth, BorderLayout.NORTH);
      contentPane.add(panelSouth, BorderLayout.SOUTH);
      contentPane.add(panelWest, BorderLayout.WEST);
      contentPane.add(panelEast, BorderLayout.EAST);
      contentPane.add(gameBoardCanvas, BorderLayout.CENTER);
      panelNorth.setPreferredSize(new Dimension(100, 100));
      
      // board size options
      spinnerBoardSize = new JSpinner(new SpinnerNumberModel(3, 3, 8, 1));
      panelNorth.add(spinnerBoardSize);
      spinnerBoardSize.addChangeListener(e -> {
          int sizeSelected = (int) spinnerBoardSize.getValue();
          setBoardSize(sizeSelected); // update board size
          gameBoardCanvas.repaint();
      });
      
      panelEast.setPreferredSize(new Dimension(100, 100));
      panelSouth.setPreferredSize(new Dimension(100, 100));
      panelWest.setPreferredSize(new Dimension(100, 100));
    }    
    
    private void updateCanvasSize() {
    	CANVAS_WIDTH = CELL_SIZE * board.getBoardSize();
    	CANVAS_HEIGHT = CELL_SIZE * board.getBoardSize();
    	gameBoardCanvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
    	pack();
    }
    
    public Board getBoard() {
        return board;
    }

    class GameBoardCanvas extends JPanel {
        private char currentPlayerSymbol;
        private char currentPlayerTurn;
        
        GameBoardCanvas() {
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int rowSelected = e.getY() / CELL_SIZE;
                    int columnSelected = e.getX() / CELL_SIZE;
                    currentPlayerTurn = board.getCurrentPlayer();
                    if (isValidMove(rowSelected, columnSelected)) {
                        updateCurrentTurnLabel();
                        updateCurrentPlayerSymbol(rowSelected, columnSelected);
                        board.makeMove(rowSelected, columnSelected, currentPlayerSymbol);
                        repaint();
                    }
                }
            });
        }

        private boolean isValidMove(int row, int column) {
            return row >= 0 && row < board.getBoardSize() && column >= 0 && column < board.getBoardSize() && board.getCell(row, column) == 0;
        }

        private void updateCurrentTurnLabel() {
            currentTurnTextField.setText("Current turn: " + (currentPlayerTurn == 'B' ? "Blue" : "Red"));
        }

        private void updateCurrentPlayerSymbol(int row, int column) {
            ButtonModel selection = currentPlayerTurn == 'B' ? blueSelectionGroup.getSelection() : redSelectionGroup.getSelection();
            currentPlayerSymbol = (selection != null && selection.getActionCommand() != null) ? selection.getActionCommand().charAt(0) : ' ';
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            drawGridLines(g);
            drawBoard(g);
        }
        
        private void drawGridLines(Graphics g) {
            g.setColor(Color.BLACK);
            for (int row = 1; row < board.getBoardSize(); row++) {
                int y = CELL_SIZE * row - GRID_WIDTH_HALF;
                g.fillRect(0, y, CANVAS_WIDTH, GRID_WIDTH);
            }
            for (int column = 1; column < board.getBoardSize(); column++) {
                int x = CELL_SIZE * column - GRID_WIDTH_HALF;
                g.fillRect(x, 0, GRID_WIDTH, CANVAS_HEIGHT);
            }
        }

        private void drawBoard(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int row = 0; row < board.getBoardSize(); row++) {
                for (int column = 0; column < board.getBoardSize(); column++) {
                    int x = column * CELL_SIZE + CELL_PADDING;
                    int y = row * CELL_SIZE + CELL_PADDING;
                    char cellValue = board.getCell(row, column);
                    drawSymbol(g2d, cellValue, x, y);
                }
            }
        }
    }
    
    private void drawSymbol(Graphics2D g2d, char symbol, int x, int y) {
        String letter = String.valueOf(symbol);
        Font font = new Font("Arial", Font.BOLD, SYMBOL_SIZE);
        FontMetrics fm = g2d.getFontMetrics(font);
        int textWidth = fm.stringWidth(letter);
        int textHeight = fm.getHeight();
        int xOffset = (CELL_SIZE - textWidth) / 2;
        int yOffset = (CELL_SIZE - textHeight) / 2 + fm.getAscent();
        g2d.setFont(font);
        g2d.drawString(letter, x + xOffset, y + yOffset);
    }

    public void setBoardSize(int size) {
    	boardSize = size;
    	board = new Board(boardSize, true);
    	updateCanvasSize();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI(new Board(3, true));
            }
        });
    }
}
