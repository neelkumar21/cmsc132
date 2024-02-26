/* Group Members
 * 	1. Neel Kumar - nkumar21
 * 	2. Ayushmaan Khurana - ayukhur
 * 	3. Xiwei Tong - xtong12
 * 	4. Dev Patel - dpatel54
 */

package model;

import java.util.Random;

/**
 * This class extends GameModel and implements the logic of strategy #1. A
 * description of this strategy can be found in the javadoc for the processCell
 * method.
 * 
 * We define an empty cell as BoardCell.EMPTY. An empty row is defined as one
 * where every cell corresponds to BoardCell.EMPTY.
 * 
 * @author Department of Computer Science, UMCP
 */

public class ProcessCellGame extends Game {
	private int maxCols, strategy, score;
	private Random random;
	private BoardCell cc;
	private boolean click = false;

	/**
	 * Defines a board with empty cells. It relies on the super class constructor to
	 * define the board. The random parameter is used for the generation of random
	 * cells. The strategy parameter defines which processing cell strategy to use
	 * (for this project the default will be 1).
	 * 
	 * @param maxRows
	 * @param maxCols
	 * @param random
	 * @param strategy
	 */
	public ProcessCellGame(int maxRows, int maxCols, Random random, int strategy) {
		super(maxRows, maxCols);
		this.maxCols = maxCols;
		this.random = random;
		this.strategy = strategy;

	}

	/**
	 * The game is over when the last board row (row with index board.length -1) is
	 * different from empty row.
	 */
	public boolean isGameOver() {
		int empty = 0;
		for (int col = 0; col < maxCols; col++) {
			if (getBoardCell(board.length - 1, col) == BoardCell.EMPTY) {
				empty++;
			}
		}
		if (maxCols == empty) {
			return false;
		}
		return true;
	}

	public int getScore() {
		return score;
	}

	/**
	 * This method will attempt to insert a row of random BoardCell objects if the
	 * last board row (row with index board.length -1) corresponds to the empty row;
	 * otherwise no operation will take place.
	 */
	public void nextAnimationStep() {
		if (this.isGameOver() == false) {
			for (int row = getMaxRows() - 2; row >= 0; row--) {
				for (int col = 0; col < getMaxCols(); col++) {
					if (getBoardCell(row, col) != BoardCell.EMPTY) {
						setBoardCell(row + 1, col, this.getBoardCell(row, col));
					}

					if (row == 0) {
						setBoardCell(row, col, BoardCell.getNonEmptyRandomBoardCell(random));
					}

				}
			}
		}
	}

	/**
	 * The default processing associated with this method is that for strategy #1.
	 * If you add a new strategy, make sure you add a conditional so the processing
	 * described below is associated with strategy #1. <br>
	 * <br>
	 * Strategy #1 Description.<br>
	 * <br>
	 * This method will turn to BoardCell.EMPTY the cell selected and any adjacent
	 * surrounding cells in the vertical, horizontal, and diagonal directions that
	 * have the same color. The clearing of adjacent cells will continue as long as
	 * cells have a color that corresponds to the selected cell. Notice that the
	 * clearing process does not clear every single cell that surrounds a cell
	 * selected (only those found in the vertical, horizontal or diagonal
	 * directions). <br>
	 * IMPORTANT: Clearing a cell adds one point to the game's score.<br>
	 * <br>
	 * 
	 * If after processing cells, any rows in the board are empty,those rows will
	 * collapse, moving non-empty rows upward. For example, if we have the following
	 * board (an * represents an empty cell):<br />
	 * <br />
	 * RRR<br />
	 * GGG<br />
	 * YYY<br />
	 * * * *<br/>
	 * <br />
	 * then processing each cell of the second row will generate the following
	 * board<br />
	 * <br />
	 * RRR<br />
	 * YYY<br />
	 * * * *<br/>
	 * * * *<br/>
	 * <br />
	 * IMPORTANT: If the game has ended no action will take place.
	 * 
	 * 
	 */
	public void processCell(int rowIndex, int colIndex) {
		if (this.strategy == 1) {
			cc = board[rowIndex][colIndex];
			processCellMovement(rowIndex, colIndex);
			for (int counter = -1; counter < 2; counter++) {
				if ((rowIndex + counter) < board.length && rowIndex + counter > -1 && clearRow(rowIndex + counter)) {
					for (int x = rowIndex + counter; x < board.length - 1; x++) {
						for (int y = 0; y < board[0].length; y++) {
							board[x][y] = board[x + 1][y];
						}
					}
					for (int x = 0; x < board[0].length; x++) {
						board[board.length - 1][x] = BoardCell.EMPTY;
					}
				}
			}
		} else if (this.strategy == 2) {
			if(!click) {
				click = true;
				return;
			}
			cc = board[rowIndex][colIndex];
			processCellMovement(rowIndex, colIndex);
			for (int counter = -1; counter < 2; counter++) {
				if ((rowIndex + counter) < board.length && rowIndex + counter > -1 && clearRow(rowIndex + counter)) {
					for (int x = rowIndex + counter; x < board.length - 1; x++) {
						for (int y = 0; y < board[0].length; y++) {
							board[x][y] = board[x + 1][y];
						}
					}
					for (int x = 0; x < board[0].length; x++) {
						board[board.length - 1][x] = BoardCell.EMPTY;
					}
				}
			}
			click = false;
		} else {
			System.err.println("Invalid Strategy");
			System.exit(1);
		}
	}

	private void processCellMovement(int rowIndex, int colIndex) {
		boolean validRow = rowIndex > -1 && rowIndex < board.length;
		boolean validCol = colIndex > -1 && colIndex < board[0].length;
		if (validRow && validCol && (board[rowIndex][colIndex] == cc)) {
			board[rowIndex][colIndex] = BoardCell.EMPTY;
			score++;
			processCellMovement(rowIndex - 1, colIndex + 1);
			processCellMovement(rowIndex - 1, colIndex);
			processCellMovement(rowIndex - 1, colIndex - 1);
			processCellMovement(rowIndex, colIndex - 1);
			processCellMovement(rowIndex, colIndex + 1);
			processCellMovement(rowIndex + 1, colIndex - 1);
			processCellMovement(rowIndex + 1, colIndex);
			processCellMovement(rowIndex + 1, colIndex + 1);
		} else {
			return;
		}

	}

	private boolean clearRow(int rowIndex) {
		for (BoardCell cell : board[rowIndex]) {
			if (!cell.equals(BoardCell.EMPTY)) {
				return false;
			}
		}
		return true;
	}

}