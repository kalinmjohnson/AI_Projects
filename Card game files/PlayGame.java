
public class PlayGame {

	public static void main(String [] args)
	{
		CardDraw cd = new CardDraw(20);
		CardAgent agent = new CardAgent(cd);
		for (int i = 0; i < 20; i++)
		{
			cd.drawCard();

			System.out.println(cd);
			System.out.println(agent);
		}
	}
}
