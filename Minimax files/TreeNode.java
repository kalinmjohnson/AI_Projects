
public class TreeNode {
	/** The parent pointer */
	private TreeNode parent;
	
	/** Left child */
	private TreeNode leftChild;
	
	/** Right child */
	private TreeNode rightChild;
	
	/** Utility of the node */
	private int utility;
	
	/** Unique identifier of the node */
	private int identifier;

	/** Getter for utility
	 * @return The utility of the node
	 */
	public int getUtility() {
		return utility;
	}

	/** 
	 * Setter for utility
	 * @param utility The new utility value
	 */
	public void setUtility(int utility) {
		this.utility = utility;
	}

	/**
	 * Getter for the identifier
	 * @return The identifier of the node
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * Setter for the identifier
	 * @param identifier The new identifier 
	 */
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	/**
	 * Getter for the parent
	 * @return Pointer to the parent
	 */
	public TreeNode getParent() {
		return parent;
	}

	/**
	 * Setter for the parent
	 * @param parent The pointer to the parent
	 */
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	/**
	 * Getter for the left child
	 * @return Pointer to the left child
	 */
	public TreeNode getLeftChild() {
		return leftChild;
	}

	/**
	 * Setter for the left child
	 * @param newLeftChild Pointer to the new left child
	 */
	public void setLeftChild(TreeNode newLeftChild) {
		this.leftChild = newLeftChild;
		if (newLeftChild != null)
			newLeftChild.setParent(this);
	}

	/**
	 * Getter for the right node
	 * @return The pointer to the right child
	 */
	public TreeNode getRightChild() {
		return rightChild;
	}

	/**
	 * Setter for the right child
	 * @param newRightChild The new right child
	 */
	public void setRightChild(TreeNode newRightChild) {
		this.rightChild = newRightChild;
		if (newRightChild != null)
			newRightChild.setParent(this);
		
	}
	/**
	 * Constructor
	 * @param newIdentifier The identifier of the node
	 * @param utility The utility of the node (-10 if unknown)
	 */
	public TreeNode(int newIdentifier, int utility)
	{
		setIdentifier(newIdentifier);
		setUtility(utility);
		setRightChild(null);
		setLeftChild(null);
		setParent(null);
	}

	/**
	 * Tostring method
	 */
	public String toString()
	{
		return identifier + ":" + utility;
	}
}
