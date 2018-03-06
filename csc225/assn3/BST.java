public class BST
{
	int size;
	TreeNode root;


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
		return 1+Math.max(height(node.left), height(node.right));
	}
	public int height()
	{
		return height(root);
	}


	public String toString()
	{
		return (size == 0)? "tree is empty" : toString(root);
	}
	public String toString(TreeNode node)
	{
		if (node == null)
			return "";
		else
			return toString(node.left) 
					+ ((node.left != null)? " " : "") // space
					+ Integer.toString(node.value) 
					+ ((node.right != null)? " " : "") // space
					+ toString(node.right)
					;
	}


	public static void main(String[] args) 
	{
		BST test = new BST();
		test.insert(5);
		test.insert(5);
		test.insert(5);
		test.insert(5);
		test.insert(5);
		test.insert(5);
		System.out.println(test); 
		System.out.println(test.height());
		System.out.println(test.rightSubtree()); 
		System.out.println(test.rightSubtree().height());
	}
}


class TreeNode
{
	public int value;
	public TreeNode left;
	public TreeNode right;

	public TreeNode(int value, TreeNode left, TreeNode right)
	{
		this.value = value;
		this.left = left;
		this.right = right;
	}

	public TreeNode(int value)
	{
		this.value = value;
		this.left = null;
		this.right = null;
	}
}