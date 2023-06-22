
public class PlayNQueens {

	public static void main(String [] args)
	{
		NQueens game = new NQueens();
		System.out.println(game);
		try
		{
			game.addQueen(2, 3);
			System.out.println(game);
			game.addQueen(0, 0);
			System.out.println(game);
			game.addQueen(7, 1);
			System.out.println(game);
			// This is an invalid placement
			game.addQueen(1, 1);
			System.out.println(game);
			
				
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		
	}
}
