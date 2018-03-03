// Kai Kovacik
// March 2, 2018
// CSC 225 - assn2


public class AVL_BST
{
	public static boolean checkAVL(BST b)
	{
		if (b.height() < 3)
			return true;
		else return Math.abs(b.leftSubtree().height()-b.rightSubtree().height()) <= 1
					&& checkAVL(b.leftSubtree()) 
					&& checkAVL(b.rightSubtree())
					;
	}
	
	public static BST createBST(int[] a)
	{
		BST temp = new BST();

		for(int i = 0; i < a.length; i++)
			temp.insert(a[i]);

		return temp;
	}

	public static void main(String[] args)
	{
		int[] a = {78};
		BST T = createBST(a);
		System.out.println(T);
		System.out.println(checkAVL(T));
	}
}


class BST
{
	int size;
	TreeNode root;

	class TreeNode
	{
		private int value;
		private TreeNode left;
		private TreeNode right;

		private TreeNode(int value, TreeNode left, TreeNode right)
		{
			this.value = value;
			this.left = left;
			this.right = right;
		}

		private TreeNode(int value)
		{
			this.value = value;
			this.left = null;
			this.right = null;
		}
	}

	public BST(TreeNode root, int size)
	{
		this.size = size;
		this.root = root;
	}
	public BST()
	{
		this.size = 0;
		this.root = null;
	}

	private void insert(TreeNode node, int value)
	{
		if (node.left != null && value < node.value)
			insert(node.left, value);
		else if (node.right != null && value > node.value)
			insert(node.right, value);
		else if (node.left == null && value < node.value)
			node.left = new TreeNode(value);
		else if (node.right == null && value > node.value)
			node.right = new TreeNode(value);
	}
	public void insert(int value)
	{
		if (size == 0)
			root = new TreeNode(value);
		else insert(root, value);
		size++;
	}

	public void empty()
	{
		size = 0;
		root = null;
	}

	public BST leftSubtree()
	{
		return new BST(root.left, size-1);
	}

	public BST rightSubtree()
	{
		return new BST(root.right, size-1);
	}

	public int size()
	{
		return size;
	}

	private int height(TreeNode node)
	{
		if(node == null)
			return 0;
		else return 1+Math.max(height(node.left), height(node.right));
	}
	public int height()
	{
		return height(root);
	}

	private String toString(TreeNode node)
	{
		if (node == null)
			return "";
		else return toString(node.left) 
					+ ((node.left != null)? " " : "") // space
					+ Integer.toString(node.value) 
					+ ((node.right != null)? " " : "") // space
					+ toString(node.right)
					;
	}
	public String toString()
	{
		return (size == 0)? "tree is empty" : toString(root);
	}
}