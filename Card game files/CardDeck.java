import java.util.Random;

public class CardDeck {
	
	/**
	 * Indication that a card has been retrieved already
	 */
	private int [] seen;
	
	/** Random number generator */
	private Random r;
	
	/**
	 * Constructor
	 */
	public CardDeck(int seed)
	{
		r = new Random(seed);
		seen = new int[52];
	}
	
	/**
	 * Method to randomly draw a card
	 * @return The card drawn
	 */
	public Card getCard()
	{
		char [] suit = {'C', 'D', 'H', 'S'};
		int index = r.nextInt(52);
		while (seen[index] == 1)
			index = r.nextInt(52);
		seen[index] = 1;
		Card flippedCard = new Card(suit[index/13], index%13);
		return flippedCard;
	}
}
