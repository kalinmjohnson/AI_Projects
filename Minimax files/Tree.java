import java.util.Random;

public class Tree {

	/** Pointer to the root node */
	private TreeNode rootNode;

	/** Array of nodes: Assuming complete binary tree */
	private TreeNode [] arrayOfNodes;

	/**
	 * Getter for root node
	 * @return The root node
	 */
	public TreeNode getRootNode() {
		return rootNode;
	}
	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}

	/**
	 * Populate tree function
	 * @param size Size of the tree
	 * @param utilities Utilities of nodes by level
	 * @return Pointer to the root node
	 */
	public TreeNode populateTree(int size, int [] utilities)
	{
		Random r = new Random(size);
		arrayOfNodes = new TreeNode[size];
		for (int i = 0; i < size; i++)
		{
			arrayOfNodes[i] = new TreeNode(i, utilities[i]);
		}
		for (int i = 0; i < 7; i++)
		{
			arrayOfNodes[i].setLeftChild(arrayOfNodes[2*i+1]);
			arrayOfNodes[i].setRightChild(arrayOfNodes[2*i+2]);
		}
		return arrayOfNodes[0];
	}

	/**
	 * Level order traversal
	 */
	public void levelOrderTraversal()
	{
		for (int i = 0; i < arrayOfNodes.length; i++)
		{
			System.out.print(arrayOfNodes[i] + " ");
		}
		System.out.println();
	}

	/** 
	 * Main minimax search algorithm
	 * @param currentNode Current node being evaluated
	 * @param isMax true if this is a max level, false otherwise
	 * @return Utility of the current node
	 */
	public int minimaxSearch(TreeNode currentNode, boolean isMax)
	{
		// Base Case
		if (currentNode.getUtility() > 0) {
			return currentNode.getUtility();
		} else {
			int leftValue = minimaxSearch(currentNode.getLeftChild(), !isMax);
			int rightValue = minimaxSearch(currentNode.getRightChild(), !isMax);
			if(isMax) {
				int maxUtility = Math.max(leftValue, rightValue);
				currentNode.setUtility(maxUtility);
				return maxUtility;
			} else {
				int minUtility = Math.min(leftValue, rightValue);
				currentNode.setUtility(minUtility);
				return minUtility;
			}
		}
	}


}
