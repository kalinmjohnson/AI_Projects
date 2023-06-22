
public class TestMinimaxSearch {

	public static void main(String[] args) {
		Tree myTree = new Tree();
		int [] utilities = {-10, -10, -10, -10, -10, -10, -10, 5, 3, 16, 20, 8, 5, 1, 9};
		myTree.setRootNode(myTree.populateTree(15, utilities));
		myTree.levelOrderTraversal();
		System.out.println(myTree.minimaxSearch(myTree.getRootNode(), true));
		myTree.levelOrderTraversal();

	}

}
