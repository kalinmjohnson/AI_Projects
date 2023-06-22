public class SlidingGameState  {

	int [][] board;

	private String sequence;

	private int size;

	private int emptyI;

	private int emptyJ;

	private int numberMoves;

	public boolean equals(SlidingGameState inSG)
	{
		if (emptyI != inSG.emptyI || emptyJ != inSG.emptyJ)
			return false;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (board[i][j] != inSG.board[i][j])
					return false;
		return true;
	}

	public boolean isValid(int iOffset, int jOffset)
	{
		int startI = iOffset + emptyI;
		int startJ = jOffset + emptyJ;
		return startI >= 0 && startI < size && startJ >= 0 && startJ < size;
	}

	public SlidingGameState(int newSize, int [] initialArray)
	{
		setSize(newSize);
		int index = 0;
		board = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
			{
				board[i][j] = initialArray[index];
				if (initialArray[index] == 0)
				{
					emptyI = i;
					emptyJ = j;
				}
				index++;
			}
		sequence = "Start state\n" + toString();
		numberMoves = 0;
	}

	public void setSize(int newSize)
	{
		size = newSize;
	}

	/**
	 * Move the token in one direction. If the move is a valid move, it also gets concatenated 
	 * onto the sequence String
	 * @param upDownMove +1 indicates down, -1 indicates up
	 * @param leftRightMove +1 indicates right, -1 indicates left
	 * @throws Exception If the new location offset more than one or in both directions simultaneously
	 */
	public void moveToken(int upDownMove, int leftRightMove) throws Exception
	{
		if (upDownMove < -1 || upDownMove > 1 || leftRightMove < -1 || leftRightMove > 1)
			throw new Exception("Slide operation cannot be more than one space");
		if (upDownMove != 0 && leftRightMove != 0)
			throw new Exception("You can only move in one direction at a time");
		int newI = upDownMove + emptyI;
		int newJ = leftRightMove + emptyJ;
		//System.out.println(newI + " " + newJ);
		board[emptyI][emptyJ] = board[newI][newJ];
		board[newI][newJ] = 0;
		numberMoves++;
		sequence += ("Move " + board[emptyI][emptyJ] + " at [" + newI + "," + 
				newJ + "] to [" + emptyI + "," + emptyJ + "]\n");
		sequence += toString();
		sequence += ("Number moves: " + numberMoves + "\n");
		emptyI = newI;
		emptyJ = newJ;

	}

	/**
	 * Copy constructor
	 * @param original The current state of the game to be copied
	 */
	public SlidingGameState(SlidingGameState original)
	{
		setSize(original.size);
		board = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				board[i][j] = original.board[i][j];
		emptyJ = original.emptyJ;
		emptyI = original.emptyI;
		sequence = original.sequence;
		numberMoves = original.numberMoves;
	}

	/**
	 * Checks to see if the game has been solved
	 * @return True if the game is solved, false otherwise
	 */
	public boolean isWin()
	{
		int tileNumber = 1;
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				// Last spot must be 0
				if (i == size - 1 && j == size - 1)
				{
					if (board[i][j] != 0)
						return false;
				}
				// All other tiles must be in order from 1 through N^2-1
				else if (board[i][j] != tileNumber)
					return false;
				tileNumber++;
			}
		}
		return true;
	}

	/**
	 * Return the string corresponding to the current sequence of moves
	 * @return The string of moves
	 */
	public String getSequence()
	{
		return sequence;

	}

	public String toString()
	{
		String s= "";
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
				s += (board[i][j] + "\t");
			s += "\n";
		}
		return s;


	}

}
