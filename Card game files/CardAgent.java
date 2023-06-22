import java.util.ArrayList;

public class CardAgent {
	/** Pointer to card game*/
	private CardDraw cd;
	
	/** Probability next card has the same value*/
	private double nextEqual;
	
	/** Probability next card has higher value */
	private double nextHigher;
	
	/** Probability next card is of the same suit */
	private double nextSameSuit;

	/** ArrayList of cards seen (from card deck*/
	private ArrayList<Card> cardsSeen;

	private boolean [][] track;

	/**
	 * Constructor
	 * @param newCd Pointer to card game
	 */
	public CardAgent(CardDraw newCd)
	{
		cd = newCd;
		cardsSeen = cd.getCardsSeen();
		track = new boolean [4][13];
		for (int i = 0; i < 4; i++) {
			for(int j = 0; j < 13; j++) {
				track[i][j] = false;
			}
		}
	}
	
	/**
	 * Setter for probabilities
	 */
	private void calculateProbabilities()
	{
		updateTrack();
		setNextEqual();
		setNextHigher();
		setNextSameSuit();
	}

	private void updateTrack() {
		Card lastCard = cardsSeen.get(cardsSeen.size()-1);
		int value = lastCard.getValue();
		char suit = lastCard.getSuit();
		int suitIndex = 0;
		if (suit == 'D') {
			suitIndex = 1;
		} else if (suit == 'H') {
			suitIndex = 2;
		} else if (suit == 'S') {
			suitIndex = 3;
		}
		track[suitIndex][value] = true;
	}
	
	/**
	 * Calculate probability next card has higher value
	 */
	private void setNextHigher()
	{
		Card lastCard = cardsSeen.get(cardsSeen.size()-1);
		int value = lastCard.getValue();
		int higherCards = 4 * (12 - value);


		for(int i = 0; i < cardsSeen.size()-1; i++) {
			if (cardsSeen.get(i).getValue() > (value)) {
				higherCards--;
			}
		}
		int cardsRemaining = 52 - cardsSeen.size();
		nextHigher = (double)higherCards/cardsRemaining;
	}
	
	/**
	 * Calculate probability next card is of same suit
	 */
	private void setNextSameSuit()
	{
		Card lastCard = cardsSeen.get(cardsSeen.size()-1);
		char suit = lastCard.getSuit();
		int numberLeft = 12;

		for(int i = 0; i < cardsSeen.size()-1; i++) {
			if (cardsSeen.get(i).getSuit() == suit) {
				numberLeft--;
			}
		}
		int cardsRemaining = 52 - cardsSeen.size();
		nextSameSuit = (double)numberLeft/cardsRemaining;
	}
	
	/** 
	 * Calculate probability next card has the same value
	 */
	private void setNextEqual()
	{
		Card lastCard = cardsSeen.get(cardsSeen.size()-1);
		int value = lastCard.getValue();
		int numberLeft = 3;

		for(int i = 0; i < cardsSeen.size()-1; i++) {
			if (cardsSeen.get(i).getValue() == value) {
				numberLeft--;
			}
		}
		int cardsRemaining = 52 - cardsSeen.size();
		nextEqual = (double)numberLeft/cardsRemaining;
	}
	
	/**
	 * Tostring method
	 */
	public String toString()
	{
		calculateProbabilities();
		String s = "Probability next card has same value: " + nextEqual + "\n";
		s += "Probability next card has higher value: " + nextHigher + "\n";
		s += "Probability next card is of the same suit: " + nextSameSuit + "\n";
		return s;
	}
}
