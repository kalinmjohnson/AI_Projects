import java.util.PriorityQueue;

/**
 * Maze Agent
 * @version 03/01/23
 * @author DThomas
 *
 */
public class MazeAgent {
	private Maze currentMaze;

	public MazeAgent(Maze newMaze)
	{
		currentMaze = newMaze;
	}

	public State exploreMoves()
	{
		PriorityQueue<State> queue = new PriorityQueue<State>();
		State current = currentMaze.getStateAt(0, 0); // Start State
		current.setSequence("Start State: [" + currentMaze.getStartI() + "," + currentMaze.getStartJ() + "]\n");
		queue.add(current);
		while(!queue.isEmpty()) {
			current = queue.poll();
			System.out.println("Exploring: " + current.getRow() + " " + current.getColumn());
			if (current.isGoalState()) {
				return current;
			}
			char[] moves = getMoves(current);
			for(int i = 0; i < moves.length; i++) {
				State newState = currentMaze.getNewState(current, moves[i]);
				if (newState.getContent() != Maze.EXPLORED) {
					newState.setSteps(current.getSteps() + 1);
					newState.setSequence(current.getSequence() + 
						" go: " + moves[i] + "[" + newState.getRow() + "," + newState.getColumn() + "]" + 
						" Steps: " + newState.getSteps() + "\n");
					queue.add(newState);
					newState.setContent(Maze.EXPLORED);
				} else if (current.getSteps() + 1 < newState.getSteps()) {
					newState.setSteps(current.getSteps() + 1);
					newState.setSequence(current.getSequence() + 
						" go: " + moves[i] + "[" + newState.getRow() + "," + newState.getColumn() + "]" + 
						" Steps: " + newState.getSteps() + "\n");
					queue.add(newState);
				}
			}
		}
		// If here, there is no solution
		return null;
	}


	/**
	 * Generate string of possible moves
	 * @param current The current state
	 * @return Return string of ESWN to show possible directions it can move in
	 */
	public char [] getMoves(State current)
	{
		int currentI = current.getRow();
		int currentJ = current.getColumn();
		String dirs = "";

		if (currentJ+1 < Maze.SIZE && isViable(currentI, currentJ+1))
			dirs += 'E';
		if (currentJ-1 >= 0 && isViable(currentI, currentJ-1))
			dirs += 'W';
		if (currentI-1 >= 0 && isViable(currentI-1, currentJ))
			dirs += 'N';
		if (currentI+1 < Maze.SIZE && isViable(currentI+1, currentJ))
			dirs += 'S';
		return dirs.toCharArray();
	}

	/**
	 * Return true if possible direction move is viable
	 * @param row Row of new state
	 * @param column Column of new state
	 * @return True if viable, false otherwise
	 */
	public boolean isViable(int row, int column)
	{
		return currentMaze.getStateAt(row, column).getContent() != Maze.BLOCKED && currentMaze.getStateAt(row, column).getContent() != Maze.EXPLORED;
	}

}
