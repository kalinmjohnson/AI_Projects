package sudoku;

import java.util.LinkedList;

/**
 * The Sudoku AI player
 * 
 * @author kalinjohnson
 * @version 1.1
 */

public class SudokuPlayer {

	/**
	 * Current Board stores the current state of the board to be analyzed and find a
	 * good move from
	 */
	private int[][] currentBoard;

	/**
	 * Possible Moves stores the moves that we know from the information provided on
	 * current board
	 */
	private int[][] possibleMoves;

	/**
	 * These linked lists store the spot coordinates the are in rows, columns, or
	 * square with a specific number of spots left They are used to help decide
	 * which move to make
	 */
	private LinkedList<Location> oneOpenSpot;
	private LinkedList<Location> twoOpenSpots;
	private LinkedList<Location> threeOpenSpots;
	private LinkedList<Location> fourOpenSpots;
	private LinkedList<Location> severalOpenSpots;

	/**
	 * move stores that value and coordinates of the move the AI player is making
	 */
	private int[] move;

	/**
	 * These three attributes store the number of points
	 */
	private int rowPoints;
	private int columnPoints;
	private int squarePoints;

	/**
	 * The Constructor of Sudoku Player
	 * 
	 * @param cb
	 * @param points
	 */

	SudokuPlayer(int[][] cb, int[] points) {
		newGame(cb, points);
	}

	/**
	 * Called to reset the game for the AI player
	 * 
	 * @param cb
	 * @param points
	 */
	public void newGame(int[][] cb, int[] points) {
		currentBoard = cb;
		rowPoints = points[1];
		columnPoints = points[0];
		squarePoints = points[2];
		possibleMoves = new int[currentBoard.length][currentBoard.length];
	}

	/**
	 * The method used for the AI player to make a move
	 * 
	 * @param cb
	 * @return an array of the value and coordinates for the move
	 */

	public int[] makeAMove(int[][] cb) {

		/**
		 * The first return value is the value to put into the spot, the second value is
		 * the row, and the third value is the column
		 */
		move = new int[3];
		currentBoard = cb;

		/**
		 * I attempted to make an array of the linked lists and use a for loop to
		 * initialize them, but it didn't allow me to do that Because of this and the
		 * fact that there are only five of them, I initialized them manually
		 * 
		 * I also attempted to keep the Linked Lists between moves, but there was too
		 * much shuffling between Linked List to make it efficient and easy It was also
		 * hard to do because I delete elements from the linked lists if I don't know
		 * the values at that spot, and I might know the values of them by the next turn
		 * 
		 * There were too many changing elements to make it worth the small efficiency
		 * benefit
		 * 
		 * I did keep the potential moves board between moves and I just update it with
		 * new potential moves
		 */

		oneOpenSpot = new LinkedList<Location>();
		twoOpenSpots = new LinkedList<Location>();
		threeOpenSpots = new LinkedList<Location>();
		fourOpenSpots = new LinkedList<Location>();
		severalOpenSpots = new LinkedList<Location>();

		/**
		 * Sort the open spots into the linked lists
		 */
		sortOpenSpaces();

		/**
		 * find out which spots we know the value for
		 */
		findPossibleSpots();

		/**
		 * Find a move if there are places that can score points
		 */
		if (oneOpenSpot.size() != 0) {
			findBestSpot(oneOpenSpot, 1);
			return move;
		}

		/**
		 * Continue moving through the linked lists to fins a good move to make
		 */

		eliminateUnknownSpots(threeOpenSpots);

		if (threeOpenSpots.size() != 0) {

			/**
			 * find the best of the three open spot options
			 */

			findBestSpot(threeOpenSpots, 3);
			return move;
		}

		eliminateUnknownSpots(fourOpenSpots);

		if (fourOpenSpots.size() != 0) {
			findBestSpot(fourOpenSpots, 4);
			return move;
		}

		eliminateUnknownSpots(severalOpenSpots);

		if (severalOpenSpots.size() != 0) {
			findBestSpot(severalOpenSpots, 9);
			return move;
		}

		eliminateUnknownSpots(twoOpenSpots);

		if (twoOpenSpots.size() != 0) {
			findBestSpot(twoOpenSpots, 2);
			return move;
		}

		/**
		 * it should never get to here, but the method requires a return statement
		 */
		return move;

	}

	/**
	 * Sort the open spots into the linked lists First, loop through the
	 * currentBoard, and assign each open spot to one of three Linked Lists
	 */

	public void sortOpenSpaces() {

		int s = currentBoard.length;

		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				if (currentBoard[i][j] == -1) {
					/**
					 * Find the minimum number of spots left using that location
					 */
					int howCloseToCompletion = Math.min(Math.min(spotsInRow(i, j), spotsInColumn(i, j)),
							spotsInSquare(i, j));
					if (howCloseToCompletion == 1) {
						oneOpenSpot.add(new Location(i, j));
					} else if (howCloseToCompletion == 2) {
						twoOpenSpots.add(new Location(i, j));
					} else if (howCloseToCompletion == 3) {
						threeOpenSpots.add(new Location(i, j));
					} else if (howCloseToCompletion == 4) {
						fourOpenSpots.add(new Location(i, j));
					} else {
						severalOpenSpots.add(new Location(i, j));
					}
				}
			}
		}
	}

	/**
	 * Find the number of spots in the row
	 * 
	 * @param x
	 * @param y
	 * @return spots in the locations row
	 */

	private int spotsInRow(int x, int y) {
		int spotsLeft = 0;

		for (int i = 0; i < currentBoard.length; i++) {
			if (currentBoard[x][i] == -1) {
				spotsLeft++;
			}
		}

		return spotsLeft;
	}

	/**
	 * Find the number of spots in the column
	 * 
	 * @param x
	 * @param y
	 * @return spots in the locations column
	 */

	private int spotsInColumn(int x, int y) {
		int spotsLeft = 0;

		for (int i = 0; i < currentBoard.length; i++) {
			if (currentBoard[i][y] == -1) {
				spotsLeft++;
			}
		}

		return spotsLeft;
	}

	/**
	 * Find the number of spots in the square
	 * 
	 * @param x
	 * @param y
	 * @return spots in the locations square
	 */

	private int spotsInSquare(int x, int y) {
		int spotsLeft = 0;

		double squareSize = Math.sqrt(currentBoard.length);
		int startRow = (int) (x - ((x % squareSize)));
		int startCol = (int) (y - ((y % squareSize)));

		for (int i = startRow; i < (startRow + squareSize); i++) {
			for (int j = startCol; j < (startCol + squareSize); j++) {
				if (currentBoard[i][j] == -1) {
					spotsLeft++;
				}
			}
		}

		return spotsLeft;
	}

	/**
	 * Put values into possible spots array This will just update the current values
	 * in possible spots array and it will not reset existing knowledge between
	 * turns
	 */

	private void findPossibleSpots() {
		int s = currentBoard.length;
		double squareSize = Math.sqrt(s);
		int numberOfOptions;
		int startRow;
		int startCol;

		/**
		 * Loop through the current board to find open spots
		 */

		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {

				/**
				 * If there is nothing in the spot and we don't already know the value, find out
				 * what it could be
				 */
				if (currentBoard[i][j] == -1 && possibleMoves[i][j] == 0) {

					/**
					 * Starts out by possibly being any of the 9 options
					 */
					int[] options = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
					numberOfOptions = 9;

					/**
					 * Loop through the spot's row and set any numbers we find to 0
					 */
					for (int row = 0; row < s; row++) {
						if (currentBoard[row][j] != -1 && options[currentBoard[row][j] - 1] != 0) {
							options[currentBoard[row][j] - 1] = 0;
							numberOfOptions--;
						}
					}

					/**
					 * Loop through the spot's column and set any numbers we find to 0
					 */
					for (int column = 0; column < s; column++) {
						if (currentBoard[i][column] != -1 && options[currentBoard[i][column] - 1] != 0) {
							options[currentBoard[i][column] - 1] = 0;
							numberOfOptions--;
						}
					}

					/**
					 * Loop through the spot's square and set any numbers we find to 0
					 */
					startRow = (int) (i - ((i % squareSize)));
					startCol = (int) (j - ((j % squareSize)));

					for (int x = startRow; x < (startRow + squareSize); x++) {
						for (int y = startCol; y < (startCol + squareSize); y++) {
							if (currentBoard[x][y] != -1 && options[currentBoard[x][y] - 1] != 0) {
								options[currentBoard[x][y] - 1] = 0;
								numberOfOptions--;
							}
						}
					}

					/**
					 * If the number of options is 1, put the value it should be with its
					 * coordinates
					 */

					if (numberOfOptions == 1) {
						int solution = 0;

						for (int k = 0; k < s; k++) {
							if (options[k] != 0) {
								solution = options[k];
							}
						}

						/**
						 * Now we can use this information in another method (eliminate locations)
						 */
						possibleMoves[i][j] = solution;
					}
				}
			}
		}
	}

	/**
	 * Eliminates the elements in the inputed linked list that we don't know teh
	 * value of
	 * 
	 * @param spots
	 */

	private void eliminateUnknownSpots(LinkedList<Location> spots) {

		/**
		 * Loop through possible spots
		 */
		for (int i = 0; i < spots.size(); i++) {
			Location l = spots.get(i);

			/**
			 * If we don't have a value to put in at that spot, remove it from the linked
			 * list
			 */
			if (possibleMoves[l.x][l.y] == 0) {
				spots.remove(i);
				/**
				 * Adjust location because the linked list is now shorter and the next element
				 * in now in the same spot as the element that was just removed
				 */
				i--;
			}
		}
	}

	/**
	 * Given a linked list and the minimum number of remaining spots, choose one of
	 * the best options based on the number of points it will generate
	 * 
	 * @param options
	 * @param spots
	 */

	private void findBestSpot(LinkedList<Location> options, int spots) {
		int[] maxMove = new int[3];

		if (spots == 2) {
			maxMove[0] = rowPoints + columnPoints + squarePoints + 1;
		} else {
			maxMove[0] = 0;
		}

		/**
		 * It is easy if there is only one thing
		 */
		if (options.size() == 1) {
			int x = options.get(0).x;
			int y = options.get(0).y;
			move[0] = possibleMoves[x][y];
			move[1] = x;
			move[2] = y;
		} else {

			/**
			 * If there is not one thing, loop through the options and calculate the total
			 * number of points Then compare with the max and if greater, choose that move
			 */
			for (int i = 0; i < options.size(); i++) {
				int total = 0;
				int x = options.get(i).x;
				int y = options.get(i).y;

				int row = spotsInRow(x, y);
				if (row <= spots) {
					total += rowPoints;
				}

				int column = spotsInColumn(x, y);
				if (column <= spots) {
					total += columnPoints;
				}

				int square = spotsInSquare(x, y);
				if (square <= spots) {
					total += squarePoints;
				}

				if (total > maxMove[0] && spots != 2) {
					maxMove[0] = total;
					maxMove[1] = x;
					maxMove[2] = y;
				} else if (total < maxMove[0] && spots == 2) {
					maxMove[0] = total;
					maxMove[1] = x;
					maxMove[2] = y;
				}
			}

			/**
			 * Now switch from keeping track of the total number of points scored to keeping
			 * track of the value at the coordinates in maxMove 1 and maxMove 2
			 */
			maxMove[0] = possibleMoves[maxMove[1]][maxMove[2]];
			move = maxMove;
		}
	}

	/**
	 * Location is a class used to bundle the value and coordinates of a spot in the
	 * grid together when they are in a linkest list or array
	 * 
	 * @author kalinjohnson
	 *
	 */

	class Location {

		/**
		 * The row coordinate
		 */
		public int x;

		/**
		 * The column coordinate
		 */
		public int y;

		/**
		 * The Constructor when the value is not known yet
		 * 
		 * @param xIn
		 * @param yIn
		 */

		public Location(int xIn, int yIn) {
			x = xIn;
			y = yIn;
		}
	}

}
