import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
//import java.util.Stack;

public class SlidingGamePlayer {

	private SlidingGameState state;

	public SlidingGamePlayer(SlidingGameState newState) {
		state = newState;
	}

	/** This method is called to have the AI player play the sliding game
	 * 
	 * @return the sequence of moves from teh state with the quickest win
	 * @throws Exception
	 */
	public String planGame() throws Exception {

		// create the queue and add the inital state to the queue
		Queue<SlidingGameState> queue = new LinkedList<SlidingGameState>();
		queue.add(state);

		// create the arraylist and set the boolean contains (c) to false
		ArrayList<SlidingGameState> pastStates = new ArrayList<SlidingGameState>();
		boolean c = false;

		// run the loop until either a winning state is found or all possible states have been explored
		while (!queue.isEmpty()) {
			// dequeue the state from the queue to check and explore the childeren states
			SlidingGameState currentState = queue.remove();

			// if a winning state, return the sequence to get to it
			if (currentState.isWin()) {
				return currentState.getSequence();
			}
		
			// check whether we have already visited this state
			c = false;
			for (int i = 0; i < pastStates.size(); i++) {
				if (currentState.equals(pastStates.get(i))) {
					c = true;
				}
			}
			
			//  if we haven't visited this state, add its children to the queue
 			if (c == false) {
				pastStates.add(currentState);				

				// if valid, move empty spot up and add it to the queue
				if (currentState.isValid(1, 0)) {
					SlidingGameState newState = new SlidingGameState(currentState);
					newState.moveToken(1, 0);
					queue.add(newState);
				}

				// if valid, move empty spot dwon and add it to the queue
				if (currentState.isValid(-1, 0)) {
					SlidingGameState newState = new SlidingGameState(currentState);
					newState.moveToken(-1, 0);
					queue.add(newState);
				}
				
				// if valid, move empty spot right and add it to the queue
				if (currentState.isValid(0, 1)) {
					SlidingGameState newState = new SlidingGameState(currentState);
					newState.moveToken(0, 1);
					queue.add(newState);
				}

				// if valid, move empty spot left and add it to the queue
				if (currentState.isValid(0, -1)) {
					SlidingGameState newState = new SlidingGameState(currentState);
					newState.moveToken(0, -1);
					queue.add(newState);
				}
			}
		}

		return "There is no solution";
	}
}