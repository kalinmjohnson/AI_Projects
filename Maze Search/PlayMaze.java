
public class PlayMaze {
	public static void main(String [] args)
	{
		Maze m = new Maze(10, 10, 2);//
		System.out.println(m);
		MazeAgent a = new MazeAgent(m);
		State finalState = a.exploreMoves();
		if (finalState != null)
		{
			System.out.println("Goal state reached");
		
			System.out.println(finalState);
		}
		else
			System.out.println("Not reached");
		System.out.println(m);

	}
	
	
}
