
public class NQueens implements Cloneable{

	/** Two dimensional board */
	private int [][] board;

	/** Queen indicator */
	public static int QUEEN = 1;

	/** Empty spot */
	public static int EMPTY = 0;

	/** Invalid spot updated automatically based on queen placement */
	public static int INVALID = 2;

	/** Size of board */
	public static int SIZE = 8;

	/** Number of open squares left */
	private int openSquares;

	/** Number of queens placed */
	private int numberQueensPlaced;

	/**
	 * Default constructor
	 */
	public NQueens()
	{
		board = new int[SIZE][SIZE];
		openSquares = SIZE * SIZE;
		numberQueensPlaced = 0;
	}

	/**
	 * Function to place queen in a particular spot by specifying row and column
	 * @param row The row number of the board
	 * @param column The column number of the board
	 * @throws Exception If the row/column number is invalid or if the space is not a valid spot
	 */
	public void addQueen(int row, int column) throws Exception
	{
		if (row <0 || row >= SIZE || column < 0 || column >= SIZE)
			throw new Exception("Invalid row/column number");
		if (board[row][column] != EMPTY)
			throw new Exception("That space is not a valid spot");
		// Add item to board
		board[row][column] = QUEEN;
		numberQueensPlaced++;
		openSquares--;

		// Marking invalid positions in row/column
		for (int i = 0; i < SIZE; i++)
		{
			if (board[i][column] == EMPTY)
			{
				board[i][column] = INVALID;
				openSquares--;
			}
			if (board[row][i] == EMPTY)
			{
				board[row][i] = INVALID;
				openSquares--;
			}
		}
		// TL to BR diagonal
		int step = 1;
		while (row + step < SIZE && column + step < SIZE)
		{
			if (board[row+step][column+step] == EMPTY)
			{
				board[row+step][column+step] = INVALID;
				openSquares--;
			}
			step++;

		}
		step = 1;
		while (row - step >= 0 && column - step >= 0)
		{
			if (board[row-step][column-step] == EMPTY)
			{
				board[row-step][column-step] = INVALID;
				openSquares--;
			}
			step++;

		}
		// TR to BL diagonal
		step = 1;
		while (row - step >= 0 && column + step < SIZE)
		{
			if (board[row-step][column+step] == EMPTY)
			{
				board[row-step][column+step] = INVALID;
				openSquares--;
			}
			step++;

		}
		step = 1;
		while (row + step < SIZE && column - step >= 0)
		{
			if (board[row+step][column-step] == EMPTY)
			{
				board[row+step][column-step] = INVALID;
				openSquares--;
			}
			step++;

		}


	}

	/**
	 * Method that checks if correct number of queens is place
	 * @return True if the number of queens placed equals SIZE, false otherwise
	 */
	public boolean checkForWin()
	{
		return numberQueensPlaced == SIZE;
	}
	
	/**
	 * toString method
	 */
	public String toString()
	{
		String s= "Number queens: " + numberQueensPlaced + " Open squares: " + openSquares + "\n";
		for (int i = 0; i < SIZE; i++)
		{
			for (int j = 0; j < SIZE; j++)
				s += (board[i][j] + "\t");
			s += "\n";
		}
		return s;
	}

	/**
	 * Getter for the board
	 * @return The current board
	 */
	public int [][] getBoard()
	{
		return board;
	}

	/**
	 * Copy constructor
	 * @param original The original object to copy
	 */
	public NQueens(NQueens original)
	{
		board = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				board[i][j] = original.getBoard()[i][j];
		openSquares = original.getOpenSquares();
		numberQueensPlaced = original.getNumberQueensPlaced();
		System.out.println("Board: " + board.length);
	}

	/**
	 * Getter for the number of queens placed
	 * @return The number of queens placed
	 */
	public int getNumberQueensPlaced()
	{
		return numberQueensPlaced;
	}

	/**
	 * Getter for the number of open squares left
	 * @return The number of open squares left
	 */
	public int getOpenSquares()
	{
		return openSquares;
	}


}
