import java.util.Stack;

public class NQueensPlayer {

	private NQueens currentGame;
	
	private NQueens winningGame;
	
	public NQueensPlayer(NQueens newCurrentGame)
	{
		currentGame = newCurrentGame;
	}
	
	public boolean makeMoveFixForColumn() throws Exception
	{
		Stack<NQueens> stack = new Stack<NQueens>();
		stack.push(currentGame);
		while (!stack.isEmpty())
		{
			NQueens currentChild = stack.pop();
			if (currentChild.checkForWin())
			{
				winningGame = currentChild;
				return true;
			}
			int column = currentChild.getNumberQueensPlaced();
			for (int i = 0; i < NQueens.SIZE; i++)
			{
				if (currentChild.getBoard()[i][column] == NQueens.EMPTY)
				{
					NQueens trialGame = new NQueens(currentChild);
					trialGame.addQueen(i, column);
					stack.push(trialGame);
				}
			}
		}
		// If we get here, there is no way to win, return false
		return false;
	}
	
	public boolean makeMove() throws Exception
	{
		Stack<NQueens> stack = new Stack<NQueens>();
		stack.push(currentGame);
		int column = currentGame.getNumberQueensPlaced(); // Keeps track of which column to insert into
		for (int i = 0; i < NQueens.SIZE; i++)
		{
			NQueens trialGame = new NQueens(currentGame);
			trialGame.addQueen(i, column);
			stack.push(trialGame);
		}
		// While the stack is not empty, pop the stack, check if it's a goal state
		// If yes: Return true
		// If not: Generate children, push them onto the stack
		while (!stack.isEmpty())
		{
			NQueens currentChild = stack.pop();
			if (currentChild.checkForWin())
			{
				winningGame = currentChild;
				return true;
			}
			column = currentChild.getNumberQueensPlaced();
			for (int i = 0; i < NQueens.SIZE; i++)
			{
				if (currentChild.getBoard()[i][column] == NQueens.EMPTY)
				{
					NQueens trialGame = new NQueens(currentChild);
					trialGame.addQueen(i, column);
					stack.push(trialGame);
				}
			}
		}
		// If we get here, there is no way to win, return false
		return false;
	}
	
	public NQueens getWinningGame()
	{
		return winningGame;
	}
}