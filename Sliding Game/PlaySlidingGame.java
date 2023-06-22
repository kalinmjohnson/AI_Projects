public class PlaySlidingGame {
	public static void main(String [] args)
	{
		/*
		int [] exampleArray = {1, 2, 3, 4, 8, 5, 7, 6, 0}; 
		SlidingGameState exampleState = new SlidingGameState(3, exampleArray);
		try {
			exampleState.moveToken(0, -1); // Move empty space left
			if (exampleState.isWin())
				System.out.println(exampleState.getSequence());

			exampleState.moveToken(-1, 0); // Move empty space up
			if (exampleState.isWin())
				System.out.println(exampleState.getSequence());
			exampleState.moveToken(0, 1); // Move empty right
			if (exampleState.isWin())
				System.out.println(exampleState.getSequence());
			exampleState.moveToken(1, 0); // Move empty space down
			if (exampleState.isWin())
				System.out.println(exampleState.getSequence());			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		*/
		int [][] array = {{1, 2, 3, 0}, 
				{1, 5, 2, 8, 3, 6, 4, 7, 0}, 
				{1, 5, 2, 8, 7, 3, 4, 6, 0}, 
				{2, 4, 3, 1, 0, 5, 7, 8, 6}, 
				{0, 3, 2, 1}, 
				{1, 2, 3, 4, 0, 5, 7, 8, 6}};
		for (int i = 0; i < array.length; i++)
		{
			System.out.println("Run: " + i);
			try {
			
				SlidingGameState start = new SlidingGameState((int)Math.sqrt(array[i].length), array[i]);

				SlidingGamePlayer ai = new SlidingGamePlayer(start);

				System.out.println(ai.planGame());
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
	}
}
