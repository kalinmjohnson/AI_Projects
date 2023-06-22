
public class Card {
	/** Char for suit: S/H/D/C*/
	private char suit;
	
	/** Value ranging from 1 (Ace) through 12 (King) */
	private int value;
	
	/**
	 * Constructor
	 * @param newSuit Suit of card
	 * @param newValue Value of card
	 */
	public Card(char newSuit, int newValue)
	{
		setSuit(newSuit);
		setValue(newValue);
	}

	/**
	 * Getter for suit
	 * @return Char corresponding to suit
	 */
	public char getSuit() {
		return suit;
	}

	/**
	 * Setter for suit
	 * @param suit The char of the new suit
	 */
	public void setSuit(char suit) {
		this.suit = suit;
	}

	/**
	 * Getter for value
	 * @return The value of the card
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Setter for the value
	 * @param value Value of the card (1 - 14)
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * toString method
	 */
	public String toString()
	{
		return "Suit: " + suit + " value: " + (value+1);
	}
}
