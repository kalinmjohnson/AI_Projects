/**
 * State class
 * @version 03/01/23
 * @author DThomas
 *
 */
public class State implements Comparable<State>{

	/** The current sequence of steps to get to this square */
	private String sequence;

	/** Whether it is blocked, explored, unexplored */
	private int content;

	/** The row number */
	private int row;

	/** The column number */
	private int column;

	/** The actual number of steps to get from the start state to it*/
	private int steps;

	/**
	 * Getter for the number of steps
	 * @return The number of steps needed
	 */
	public int getSteps() {
		return steps;
	}

	/** 
	 * Setter for the number of steps
	 * @param steps The number of steps needed
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}

	/** 
	 * Getter for the row number
	 * @return The row number
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Setter for the row number
	 * @param row The row number
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/** 
	 * Setter for the sequence to get this node
	 * @param sequence The sequence 
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	/**
	 * Getter for the column number
	 * @return The column number
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Setter for the column number
	 * @param column The column number
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * Constructor
	 * @param row Row number
	 * @param column Column number
	 * @param content Blocked/explored/cost
	 * @param sequence The sequence to get here
	 */
	public State(int row, int column, int content, String sequence)
	{
		this.row = row;
		this.column = column;
		this.setContent(content);
		this.sequence = sequence;
	}

	/**
	 * Tostring method
	 */
	public String toString()
	{
		return sequence;
	}

	/**
	 * Getter for the sequence
	 * @return The sequence so far
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * Getter for the cost to get here
	 * @return The cost to get here
	 */
	public int getCostSoFar()
	{
		return steps*10;
	}

	/**
	 * Getter for the estimated cost based on the H(n) = Euclidean distance from this point to the end
	 * @param endI The goal state row
	 * @param endJ The goal state column
	 * @return The estimated cost
	 */
	public int getEstimatedCost(int endI, int endJ)
	{
		return (int)(Math.sqrt(Math.pow(row - endI, 2) + Math.pow(column - endJ, 2.0))*10);

	}

	/**
	 * Getter for the estimated for via this state from start to the end
	 * @param endI The row of the goal state
	 * @param endJ The column of the goal state
	 * @return The estimated cost
	 */
	public int getTotalCost(int endI, int endJ)
	{
		return (steps*10) + getEstimatedCost(endI, endJ);
	}

	/**
	 * Getter for content
	 * @return Whether blocked/explored
	 */
	public int getContent() {
		return content;
	}

	/**
	 * Setter for content
	 * @param content Set to blocked/explored
	 */
	public void setContent(int content) {
		this.content = content;
	}

	/**
	 * Returns wther this is a goal state or not
	 * @return True if goal state, false otherwise
	 */
	public boolean isGoalState()
	{
		return row == Maze.SIZE-1 && column == Maze.SIZE-1;
	}

	/**
	 * Compareto method for priority queue
	 */
	@Override
	public int compareTo(State other) {
		return this.getTotalCost(Maze.SIZE-1, Maze.SIZE-1) - other.getTotalCost(Maze.SIZE-1, Maze.SIZE-1);
	}

}
