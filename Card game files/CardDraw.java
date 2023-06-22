import java.util.ArrayList;

public class CardDraw {
	
	/** Pointer to deck of cards */
	private CardDeck deck;
	
	/** ArrayList of cards seen */
	private ArrayList<Card> cardsSeen;
	
	/**
	 * Constructor
	 * @param seed Random number generator seed
	 */
	public CardDraw(int seed)
	{
		deck = new CardDeck(seed);
		cardsSeen = new ArrayList<Card>();
	}
	
	/**
	 * Method to draw another card from the pile
	 */
	public void drawCard()
	{
		Card nextCard = deck.getCard();
		cardsSeen.add(nextCard);
	}
	
	/**
	 * Getter for pointer to array list of cards seen
	 * @return
	 */
	public ArrayList<Card> getCardsSeen()
	{
		return cardsSeen;
	}
	
	/**
	 * toString method
	 */
	public String toString()
	{
		String s = "Number cards opened: " + cardsSeen.size() + "\n";
		for (int i = 0; i < cardsSeen.size(); i++)
			s += cardsSeen.get(i).toString() + "\n";
		return s;
	}
	
	
}
