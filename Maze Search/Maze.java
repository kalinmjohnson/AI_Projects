import java.util.Random;
/**
 * Maze class
 * @version 03/01/23
 * @author DThomas
 *
 */
public class Maze {
	public static int SIZE;

	public static final int BLOCKED = -1;

	public static final int EXPLORED = -2;

	/** Two dimensional state of state objects */
	private State [][] scene;

	/** Number of blocked squares */
	private int numBlocks;

	/** Starting row */
	private int startI;

	/** Starting column */
	private int startJ;

	/** Goal row */
	private int endI;

	/** Goal column */
	private int endJ;

	/**
	 * Constructor
	 * @param newNumBlocks The number of blocked squares
	 * @param newSize The size of the maze
	 * @param option How the board should be populated
	 */
	public Maze(int newNumBlocks, int newSize, int option)
	{
		SIZE = newSize;
		numBlocks = newNumBlocks;
		scene = new State[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++)
		{
			for (int j = 0; j < SIZE; j++)
				scene[i][j] = new State(i, j, 0, "");
		}
		startI = 0;
		startJ = 0;
		endI = SIZE-1; 
		endJ = SIZE-1;
		int i = 0;
		if (option == 0)
		{
			Random r = new Random(50);
			while (i < numBlocks)
			{
				int x = r.nextInt(SIZE);
				int y = r.nextInt(SIZE);
				if (scene[x][y].getContent() != BLOCKED && y != startJ && y != endJ)
				{
					scene[x][y].setContent(BLOCKED);
					i++;
				}
			}
		}
		else if (option == 1)
		{
			scene[0][1].setContent(BLOCKED);	scene[0][2].setContent(BLOCKED);	 	scene[0][4].setContent(BLOCKED);	scene[0][5].setContent(BLOCKED);	 	 	 	 	 	 
			scene[1][3].setContent(BLOCKED);	 	scene[1][5].setContent(BLOCKED);	scene[1][6].setContent(BLOCKED);	scene[1][7].setContent(BLOCKED);	 	 	 	 
			scene[2][1].setContent(BLOCKED);	 	 	 	 	 	scene[2][7].setContent(BLOCKED);	scene[2][8].setContent(BLOCKED);	 	 	 
			scene[3][4].setContent(BLOCKED);	scene[3][5].setContent(BLOCKED);	 	 	 	 	 	 
			scene[4][1].setContent(BLOCKED);	 	scene[4][3].setContent(BLOCKED);	scene[4][4].setContent(BLOCKED);	scene[4][5].setContent(BLOCKED);	 	scene[4][7].setContent(BLOCKED);	 	 	 	 
			scene[5][2].setContent(BLOCKED);	 	 	 	 	 	 	 	 	 
			scene[6][4].setContent(BLOCKED);	 	 	 	 	 	 	 
			scene[7][1].setContent(BLOCKED);	 	scene[7][3].setContent(BLOCKED);	 	scene[7][5].setContent(BLOCKED);	scene[7][6].setContent(BLOCKED);	 	 	 	 	 
			scene[8][3].setContent(BLOCKED);	 	 	scene[8][6].setContent(BLOCKED);	scene[8][7].setContent(BLOCKED);	 	 	 	 
			scene[9][1].setContent(BLOCKED);	 	scene[9][3].setContent(BLOCKED);	 	 	 	 	scene[9][8].setContent(BLOCKED);	 	 	 
		}
		else
		{
			scene[0][1].setContent(BLOCKED);	scene[0][5].setContent(BLOCKED);	 	 	 	 	 	 
			scene[1][3].setContent(BLOCKED);	 	scene[1][5].setContent(BLOCKED);	scene[1][6].setContent(BLOCKED);	scene[1][7].setContent(BLOCKED);	 	 	 	 
			scene[2][1].setContent(BLOCKED);	scene[2][2].setContent(BLOCKED); 	 	 	 	scene[2][7].setContent(BLOCKED);	scene[2][8].setContent(BLOCKED);	 	 	 
			scene[3][4].setContent(BLOCKED);	scene[3][5].setContent(BLOCKED);	 	 	 	 	 	 
			scene[4][1].setContent(BLOCKED);	 	scene[4][3].setContent(BLOCKED);	scene[4][4].setContent(BLOCKED);	scene[4][5].setContent(BLOCKED);	 	scene[4][7].setContent(BLOCKED);	 	 	 	 
			scene[5][2].setContent(BLOCKED);	 	 	 	 	 	 	 	 	 
			scene[6][4].setContent(BLOCKED);	 	 	 	 	 	 	 
			scene[7][1].setContent(BLOCKED);	 	scene[7][3].setContent(BLOCKED);	 	scene[7][5].setContent(BLOCKED);	scene[7][6].setContent(BLOCKED);	 	 	 	 	 
			scene[8][3].setContent(BLOCKED);	 	 	scene[8][6].setContent(BLOCKED);	scene[8][7].setContent(BLOCKED);	 	 	 	 
			scene[9][1].setContent(BLOCKED);	 	scene[9][3].setContent(BLOCKED);	 	 	 	 	scene[9][8].setContent(BLOCKED);	 	 	 
		}
	}


	/**
	 * Getter for the start state row
	 * @param startI The state state row
	 */
	public int getStartI() {
		return startI;
	}

	/**
	 * Setter for the start state row
	 * @param startI The start state row
	 */

	public void setStartI(int startI) {
		this.startI = startI;
	}

	/**
	 * Getter for the start state column
	 * @param startJ The state state column
	 */
	public int getStartJ() {
		return startJ;
	}

	/**
	 * Setter for the start state column
	 * @param startJ The start state column
	 */
	public void setStartJ(int startJ) {
		this.startJ = startJ;
	}

	/**
	 * Getter for the goal state row
	 * @return The goal state row
	 */
	public int getEndI() {
		return endI;
	}

	/**
	 * Setter for the end state row
	 * @param endI The end state row
	 */
	public void setEndI(int endI) {
		this.endI = endI;
	}

	/**
	 * Getter for the goal state column
	 * @return The goal state column
	 */
	public int getEndJ() {
		return endJ;
	}

	/**
	 * Setter for the end state column
	 * @param endJ The end state column
	 */
	public void setEndJ(int endJ) {
		this.endJ = endJ;
	}

	/**
	 * Getter for the number of blocked squares
	 * @return The number of blocked squares
	 */
	public int getNumBlocks() {
		return numBlocks;
	}


	/**
	 * Getter for a particular state
	 * @param i The row number
	 * @param j The column number
	 * @return The state of that particular square
	 */
	public State getStateAt(int i, int j)
	{
		return scene[i][j];
	}

	/**
	 * Getter for a particular new state based on a current state and a direction
	 * @param current The current state
	 * @param moveType The direction to move specified by S/N/W/E
	 * @return The new state
	 */
	public State getNewState(State current, char moveType)
	{
		if (moveType == 'S')
			return scene[current.getRow()+1][current.getColumn()];
		else if (moveType == 'N')
			return scene[current.getRow()-1][current.getColumn()];
		else if (moveType == 'E')
			return scene[current.getRow()][current.getColumn()+1];
		else 
			return scene[current.getRow()][current.getColumn()-1];
	}

	/**
	 * toString method
	 */
	public String toString()
	{
		String s = "";
		for (int i = 0; i < SIZE; i++)
		{
			for (int j = 0; j < SIZE; j++)
			{
				if (i == startI && j == startJ)
					s += "S\t";
				else if (i == endI && j == endJ)
					s += "G\t";

				else if (scene[i][j].getContent() == BLOCKED)
					s += "X\t";
				else if (scene[i][j].getContent() == EXPLORED)
					s += "" + scene[i][j].getTotalCost(endI, endJ) + "\t";
				else
					s += ".\t";
			}
			s += "\n";
		}
		return s;
	}


}
