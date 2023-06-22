package sudoku;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Model Class for Multiplayer Sudoku that contains the back end of the game
 * 
 * @author Kalin Johnson
 * @version 1.2
 */

public class SudokuModel {

	/**
	 * Attributes the model is storing to keep track of different parts of the game
	 */

	/**
	 * currentBoard stores the current state of the board where -1 indicates an
	 * empty spot
	 */
	private int[][] currentBoard;

	/**
	 * solutionBoard stores the solution to the current board and is used to compare
	 * with input values
	 */
	private int[][] solutionBoard;

	/**
	 * remainingOpenSpots stores how many empty spots there are in the current board
	 */
	private int remainingOpenSpots;

	/**
	 * These two attributes store the number of points each player has
	 */
	private int player1Points;
	private int player2Points;

	/**
	 * Here are the points for completing a column, row, or square, which can have
	 * different values
	 */
	private int pointsForColumn;
	private int pointsForRow;
	private int pointsForSquare;

	/**
	 * This attributes stores the number of points deducted for a wrong move
	 */
	private int pointsForWrongMove;

	/**
	 * These three attributes store what the last move was so they can provide
	 * feedback for the user
	 */
	private boolean deductedPointsLast;
	private boolean insertNumberLast;
	private boolean invalidSpotLast;

	/**
	 * This attribute stores whose turn it is
	 */
	private boolean turn;

	/**
	 * This attribute stores the size of the game board
	 */
	private int size;

	/**
	 * Property change support for the controller
	 */
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	/**
	 * Highscore of all games played in this session
	 */
	private int highscore;

	/**
	 * Stores the last number entered and the coordinates it was at
	 */

	private int[] lastMove;

	/**
	 * Stores the last game so there aren't duplicates
	 */

	private int lastGame;

	/**
	 * Methods that control the game and logic of the game
	 */

	/**
	 * First is the construct to make the game and generate the current board and
	 * solution board
	 * 
	 * @param the size of the board
	 */

	public SudokuModel(int siz) {
		pointsForRow = 5;
		pointsForColumn = 4;
		pointsForSquare = 6;
		pointsForWrongMove = 3;
		highscore = 0;
		size = siz;
		lastGame = 1;
		newGame();
	}

	/**
	 * Second is all the setters, which set the attributes to given values if valid
	 * This section also has the method that generate a new board and solution board
	 */

	/**
	 * Sets the points for Player 1
	 * 
	 * @param points
	 */

	private void setPlayer1Points(int points) {
		player1Points = points;
	}

	/**
	 * Sets the points for Player 2
	 * 
	 * @param points
	 */

	private void setPlayer2Points(int points) {
		player2Points = points;
	}

	/**
	 * Sets the highscore
	 * 
	 * @param hs
	 */

	public void setHighscore(int hs) {
		highscore = hs;
	}

	/**
	 * Sets the number of points for completing a column if there is not a game in
	 * progress
	 * 
	 * @param c
	 */

	public void setColumnScore(int c) {
		if (isGameInProgress()) {
			pointsForColumn = c;
		}
	}

	/**
	 * Sets the number of points for completing a row if there is not a game in
	 * progress
	 * 
	 * @param r
	 */

	public void setRowScore(int r) {
		if (isGameInProgress()) {
			pointsForRow = r;
		}
	}

	/**
	 * Sets the number of points for completing a square if there is not a game in
	 * progress
	 * 
	 * @param s
	 */

	public void setSquareScore(int s) {
		if (isGameInProgress()) {
			pointsForSquare = s;
		}
	}

	/**
	 * Sets the number of points deducted for a wrong move if there is not a game in
	 * progress
	 * 
	 * @param w
	 */

	public void setWrongMoveScore(int w) {
		if (isGameInProgress()) {
			pointsForWrongMove = w;
		}
	}

	/**
	 * Changes the turn to the other player depending on current state of the turn
	 */

	public void changeTurn() {
		turn = !turn;
	}

	/**
	 * This method checks whether a game is in progress
	 * 
	 * @return whether a game is in progress
	 */

	public boolean isGameInProgress() {
		return !(remainingOpenSpots == 0 || remainingOpenSpots == 51);
	}

	/**
	 * Resets the game and generates a new board
	 */

	public void newGame() {
		turn = true;
		deductedPointsLast = false;
		insertNumberLast = false;
		invalidSpotLast = false;
		setPlayer1Points(0);
		setPlayer2Points(0);
		lastMove = new int[3];
		generateBoard();
		this.pcs.firePropertyChange("new game", null, size);
	}

	/**
	 * Set up current board and solution board from one of four pre-set options
	 */

	private void generateBoard() {
		currentBoard = new int[size][size];
		solutionBoard = new int[size][size];

		int[] currentVals;
		int[] solutionVals;

		int min = 1;
		int max = 5;
		int randomNum = lastGame;

		while (randomNum == lastGame) {
			randomNum = min + (int) (Math.random() * ((max - min) + 1));
		}
		lastGame = randomNum;

		int k = 0;

		/**
		 * Options for games of Sudoku
		 */

		int[] currentVals1 = { -1, 5, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, 2, -1, -1, 3, -1, -1, 2, -1, -1, 4, -1, -1,
				-1, -1, -1, 4, -1, 9, 8, -1, -1, 6, 3, 2, 7, -1, -1, -1, -1, 4, -1, 5, -1, -1, -1, -1, 9, -1, 6, -1, -1,
				-1, -1, 2, 6, -1, 1, 8, -1, 7, -1, 5, 4, 3, -1, -1, -1, -1, 1, 9, 8, 7, -1, -1, -1, -1, -1, -1, 5 };
		int[] solutionVals1 = { 6, 5, 8, 7, 9, 3, 1, 2, 4, 1, 9, 4, 2, 6, 5, 3, 8, 7, 2, 3, 7, 4, 8, 1, 5, 9, 6, 4, 1,
				9, 8, 5, 7, 6, 3, 2, 7, 6, 2, 1, 3, 4, 9, 5, 8, 3, 8, 5, 9, 2, 6, 7, 4, 1, 9, 2, 6, 5, 1, 8, 4, 7, 3, 5,
				4, 3, 6, 7, 2, 8, 1, 9, 8, 7, 1, 3, 4, 9, 2, 6, 5 };

		int[] currentVals2 = { -1, 9, 8, -1, -1, 7, -1, -1, 3, -1, -1, -1, -1, 9, -1, -1, 5, 8, 2, 6, -1, -1, 8, -1, -1,
				-1, 1, -1, -1, 4, -1, 3, -1, -1, 2, -1, -1, -1, -1, -1, -1, -1, -1, 3, 4, -1, -1, 6, -1, -1, -1, 9, 7,
				-1, -1, 8, -1, -1, -1, 1, -1, -1, -1, 5, -1, -1, -1, 2, 6, -1, 1, -1, 1, -1, 7, 9, 5, 4, -1, -1, -1 };
		int[] solutionVals2 = { 4, 9, 8, 5, 1, 7, 2, 6, 3, 7, 1, 3, 6, 9, 2, 4, 5, 8, 2, 6, 5, 4, 8, 3, 7, 9, 1, 8, 5,
				4, 7, 3, 9, 1, 2, 6, 9, 7, 1, 2, 6, 5, 8, 3, 4, 3, 2, 6, 1, 4, 8, 9, 7, 5, 6, 8, 2, 3, 7, 1, 5, 4, 9, 5,
				4, 9, 8, 2, 6, 3, 1, 7, 1, 3, 7, 9, 5, 4, 6, 8, 2 };

		int[] currentVals3 = { 5, -1, 4, 6, -1, -1, -1, -1, -1, -1, -1, -1, 1, 5, -1, -1, 9, -1, 2, 1, -1, 7, -1, 3, 6,
				-1, -1, 8, -1, -1, 2, -1, -1, -1, -1, -1, 1, -1, 3, -1, 6, 5, -1, -1, 2, -1, 7, -1, 4, -1, -1, -1, 3,
				-1, 4, -1, -1, -1, -1, -1, 7, -1, -1, -1, -1, -1, -1, -1, 2, 4, 8, 6, -1, -1, -1, -1, -1, -1, 9, 2, 3 };
		int[] solutionVals3 = { 5, 8, 4, 6, 2, 9, 3, 1, 7, 3, 6, 7, 1, 5, 4, 2, 9, 8, 2, 1, 9, 7, 8, 3, 6, 4, 5, 8, 9,
				5, 2, 3, 7, 1, 6, 4, 1, 4, 3, 9, 6, 5, 8, 7, 2, 6, 7, 2, 4, 1, 8, 5, 3, 9, 4, 2, 8, 3, 9, 6, 7, 5, 1, 9,
				3, 1, 5, 7, 2, 4, 8, 6, 7, 5, 6, 8, 4, 1, 9, 2, 3 };

		int[] currentVals4 = { 9, -1, -1, -1, 3, 4, -1, -1, -1, 3, -1, 4, -1, 8, -1, -1, -1, 7, -1, 8, -1, 1, -1, 2, 9,
				-1, -1, -1, 6, -1, 3, 1, 8, -1, 4, -1, 1, 9, 8, -1, -1, -1, -1, -1, -1, -1, -1, -1, 6, -1, -1, 2, -1,
				-1, 5, -1, -1, 2, -1, -1, 6, 1, -1, -1, -1, -1, -1, 6, -1, 7, -1, 5, -1, -1, -1, 5, -1, 7, -1, -1, -1 };
		int[] solutionVals4 = { 9, 5, 1, 7, 3, 4, 8, 6, 2, 3, 2, 4, 9, 8, 6, 1, 5, 7, 6, 8, 7, 1, 5, 2, 9, 3, 4, 7, 6,
				2, 3, 1, 8, 5, 4, 9, 1, 9, 8, 4, 2, 5, 3, 7, 6, 4, 3, 5, 6, 7, 9, 2, 8, 1, 5, 7, 9, 2, 4, 3, 6, 1, 8, 2,
				4, 3, 8, 6, 1, 7, 9, 5, 8, 1, 6, 5, 9, 7, 4, 2, 3 };

		int[] currentVals5 = { -1, 9, 3, -1, 5, -1, -1, 6, -1, 7, 8, 5, 4, -1, -1, -1, -1, -1, -1, 6, -1, 9, -1, 2, -1,
				-1, -1, -1, -1, -1, -1, 7, -1, -1, -1, -1, 5, -1, 8, 6, 9, 1, 4, 3, -1, -1, 4, 7, 2, -1, -1, -1, 8, -1,
				-1, -1, -1, 3, 1, -1, 6, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 6, -1, -1, 5, -1, -1, 2, -1, -1 };
		int[] solutionVals5 = { 2, 9, 3, 1, 5, 7, 8, 6, 4, 7, 8, 5, 4, 6, 3, 9, 1, 2, 4, 6, 1, 9, 8, 2, 7, 5, 3, 3, 1,
				6, 8, 7, 4, 5, 2, 9, 5, 2, 8, 6, 9, 1, 4, 3, 7, 9, 4, 7, 2, 3, 5, 1, 8, 6, 8, 7, 2, 3, 1, 9, 6, 4, 5, 1,
				5, 4, 7, 2, 6, 3, 9, 8, 6, 3, 9, 5, 4, 8, 2, 7, 1 };

		/**
		 * Deciding which one based on the random number
		 */

		if (randomNum == 1) {
			currentVals = currentVals1;
			solutionVals = solutionVals1;
		} else if (randomNum == 2) {
			currentVals = currentVals2;
			solutionVals = solutionVals2;
		} else if (randomNum == 3) {
			currentVals = currentVals3;
			solutionVals = solutionVals3;
		} else if (randomNum == 4) {
			currentVals = currentVals4;
			solutionVals = solutionVals4;
		} else {
			currentVals = currentVals5;
			solutionVals = solutionVals5;
		}

		remainingOpenSpots = 51;

		/**
		 * Putting values into current and solution
		 */

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				currentBoard[i][j] = currentVals[k];
				solutionBoard[i][j] = solutionVals[k];
				k++;
			}
		}

	}

	/**
	 * Third is all the getters, which return the values in different attributes
	 */

	/**
	 * Gets the number of points player 1 has
	 * 
	 * @return player1Points
	 */

	public int getPlayer1Points() {
		return player1Points;
	}

	/**
	 * Gets the number of points player 2 has
	 * 
	 * @return player2Points
	 */

	public int getPlayer2Points() {
		return player2Points;
	}

	/**
	 * Gets the turn
	 * 
	 * @return turn
	 */

	public boolean getTurn() {
		return turn;
	}

	/**
	 * Gets the size of the board
	 * 
	 * @return size
	 */

	public int getSize() {
		return size;
	}

	/**
	 * Gets the high-score for all played games in current session
	 * 
	 * @return high-score
	 */

	public int getHighscore() {
		return highscore;
	}

	/**
	 * Gets the Number of points in an array
	 * 
	 * @return array of point values
	 */

	public int[] getPoints() {
		int[] arr = { pointsForColumn, pointsForRow, pointsForSquare, pointsForWrongMove };
		return arr;
	}

	/**
	 * gets current board and converts into string form
	 * 
	 * @return String 2D array
	 */

	public String[][] getBoard() {
		String[][] outputBoard = new String[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (currentBoard[i][j] >= 0) {
					outputBoard[i][j] = Integer.toString(currentBoard[i][j]);
				} else {
					outputBoard[i][j] = "   ";
				}
			}
		}

		return outputBoard;

	}

	/**
	 * gets current board and returns it in int form
	 * 
	 * @return String 2D array
	 */

	public int[][] getCurrentBoard() {
		return currentBoard;
	}

	/**
	 * Returns a string that contains information about current state of game
	 * 
	 * @return feedback
	 */

	public String getFeedback() {
		int gameWonYet = isGameWon();
		if (gameWonYet == 0) {
			String output;
			if (turn == true) {
				output = ("It is Player 1's turn" + "\n" + "Player 1's Points: " + player1Points + "\n"
						+ "Player 2's Points: " + player2Points);
			} else {
				output = ("It is Player 2's turn" + "\n" + "Player 1's Points: " + player1Points + "\n"
						+ "Player 2's Points: " + player2Points);
			}
			if (insertNumberLast == true) {
				output = output + "\n" + "Great Job! That was correct!";
			} else if (deductedPointsLast == true) {
				output = output + "\n" + "Oh no!  Incorrect number.  Please try again!";
			} else if (invalidSpotLast == true) {
				output = output + "\n" + "Please try again.";
			} else {
				output = output + "\n" + "Welcome to Sudoku";
			}
			if (isGameInProgress()) {
				int row = lastMove[1] + 1;
				int col = lastMove[2] + 1;
				output = output + "\n" + "The last move was putting a " + lastMove[0] + " at row " + row
						+ " and column " + col;
			}
			return output;
		} else if (gameWonYet == 1) {
			return ("Player 1 has won the game!");
		} else if (gameWonYet == 2) {
			return ("Player 2 has won the game!");
		} else if (gameWonYet == 3) {
			return ("It is a tie! Everyone is a loser");
		}
		return ("Cannot Compute :(");
	}

	/**
	 * Gets a fun fact about sudoku for main page
	 * 
	 * @return fun fact about sudoku
	 */

	public String getFunFact() {
		String funFact;

		int min = 1;
		int max = 5;
		int randomNum = min + (int) (Math.random() * ((max - min) + 1));

		// Sources:
		// https://artdaily.com/news/124623/Fun-Facts-About-Sudoku--From-World-Guinness-Records-To-Solo-Fun-At-Home#.ZE60gi-B3Mg
		// https://sudoku2.com/sudoku-tips/sudoku-fun-facts/

		if (randomNum == 1) {
			funFact = "Sudokus are addictive and also distracting, and a flight attendants' job is to pay attention to the passengers. Hence British Airways forbids its attendants from playing Sudoku free puzzles during take-off and landing.";
		} else if (randomNum == 2) {
			funFact = "There are around six sextillion Sudoku free puzzles and you can never run out of them. These are enough for you to do one each day until the next century.";
		} else if (randomNum == 3) {
			funFact = "The largest Sudoku puzzle ever created had a grid size of 9,999 x 9,999. It was created by a team of researchers at the University of Liverpool and took over 100 hours to solve using a computer.";
		} else if (randomNum == 4) {
			funFact = "In 2006, the World Sudoku Championship was held in Lucca, Italy. The winner of the championship was a Japanese man named Junichi Tanaka, who also set the world record for the fastest time at just 3 minutes and 9 seconds.";
		} else {
			funFact = "There is a Sudoku-themed amusement park in Japan called the \"Sudokuland,\" which features a variety of Sudoku-themed attractions, such as a Sudoku-themed roller coaster and a Sudoku-themed maze.";
		}

		return funFact;
	}

	/**
	 * Fourth is logic methods, which determine where someone has won the game,
	 * whether an inputed number is allowed, and whether rows or squares are
	 * completed
	 */

	/**
	 * Checks whether the game is finished, and determines who won is game if it is
	 * finished
	 * 
	 * @return 0 is the game is not finished, 1 if player 1 won the game, and 2 if
	 *         player 2 won the game
	 */

	public int isGameWon() {

		if (remainingOpenSpots == 0) {
			if (player1Points > player2Points) {
				if (player1Points > highscore) {
					highscore = player1Points;
				}
				return 1;
			} else if (player1Points < player2Points) {
				if (player2Points > highscore) {
					highscore = player2Points;
				}
				return 2;
			} else {
				if (player1Points > highscore) {
					highscore = player1Points;
				}
				return 3;
			}
		} else {
			return 0;
		}
	}

	/**
	 * If correct value and location, while insert the number at that spot
	 * 
	 * @param val
	 * @param x
	 * @param y
	 * @return whether number was inserted
	 */

	public boolean insertOrNoteNumber(int val, int x, int y) {
		boolean insert = insertNumber(val, x, y);
		int[] arr = { x, y, val }; // The coordinates of the inserted number or the number that the user attempted
									// to insert
		this.pcs.firePropertyChange("insert number", arr, insert);
		return insert;
	}

	/**
	 * The private method for inserting a number that does all the work for the
	 * process
	 * 
	 * @param val
	 * @param x
	 * @param y
	 * @return whether number was inserted
	 */

	private boolean insertNumber(int val, int x, int y) {
		boolean addedNumber = false;

		if (currentBoard[x][y] == -1) {
			if (solutionBoard[x][y] == val) {
				currentBoard[x][y] = val;

				deductedPointsLast = false;
				invalidSpotLast = false;
				insertNumberLast = true;
				addedNumber = true;

				lastMove[0] = val;
				lastMove[1] = x;
				lastMove[2] = y;

				remainingOpenSpots--;
				if (remainingOpenSpots == 0) {
					if (turn == true) {
						player1Points += 5;
					} else {
						player2Points += 5;
					}
				} else {
					if (isRowCompleted(x)) {
						if (turn == true) {
							player1Points += pointsForRow;
						} else {
							player2Points += pointsForRow;
						}
					} else if (isColumnCompleted(y)) {
						if (turn == true) {
							player1Points += pointsForColumn;
						} else {
							player2Points += pointsForColumn;
						}
					} else if (isSquareCompleted(x, y)) {
						if (turn == true) {
							player1Points += pointsForSquare;
						} else {
							player2Points += pointsForSquare;
						}
					}
				}

				changeTurn();
			} else {
				insertNumberLast = false;
				invalidSpotLast = false;
				deductedPointsLast = true;

				if (turn == true) {
					player1Points -= pointsForWrongMove;
				} else {
					player2Points -= pointsForWrongMove;
				}
			}
		} else {
			insertNumberLast = false;
			deductedPointsLast = false;
			invalidSpotLast = true;
		}
		return addedNumber;
	}

	/**
	 * Checks whether a row has been completed with this move for points system
	 * 
	 * @param row
	 * @return whether a row is completed in a boolean
	 */

	private boolean isRowCompleted(int row) {
		boolean complete = true;
		for (int i = 0; i < size; i++) {
			if (currentBoard[row][i] == -1) {
				complete = false;

			}
		}
		return complete;
	}

	/**
	 * Checks whether a column has been completed with this move for points system
	 * 
	 * @param col
	 * @return whether a column is completed in a boolean
	 */

	private boolean isColumnCompleted(int col) {
		boolean complete = true;
		for (int i = 0; i < size; i++) {
			if (currentBoard[i][col] == -1) {
				complete = false;
			}
		}
		return complete;
	}

	/**
	 * Checks whether a square has been completed with this move for points system
	 * 
	 * @param row
	 * @param col
	 * @return whether a square is completed in a boolean
	 */

	private boolean isSquareCompleted(int row, int col) {
		boolean complete = true;
		double squareSize = Math.sqrt(size);
		int startRow = (int) (row - ((row % squareSize)));
		int startCol = (int) (col - ((col % squareSize)));

		for (int i = startRow; i < (startRow + squareSize); i++) {
			for (int j = startCol; j < (startCol + squareSize); j++) {
				if (currentBoard[i][j] == -1) {
					complete = false;
				}
			}
		}

		return complete;
	}

	/**
	 * Finds similar values so they can be highlighted
	 * 
	 * @param val
	 * @return coordinates in highlighted values locations in the grid
	 */

	public int[][] highlightCoordinates(int val) {
		int[][] highlights = new int[size][2];

		int h = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (currentBoard[i][j] == val) {
					highlights[h][0] = i;
					highlights[h][1] = j;
					h++;
				}
			}
		}

		while (h < size) {
			highlights[h][0] = -1;
			highlights[h][1] = -1;
			h++;
		}

		this.pcs.firePropertyChange("highlight", null, highlights);
		return highlights;
	}

	/**
	 * Fifth is the property change methods
	 */

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

}
