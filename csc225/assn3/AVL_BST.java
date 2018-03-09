// Kai Kovacik
// March 2, 2018
// CSC 225 - assn2


public class AVL_BST
{
	public static boolean checkAVL(BST b)
	{
		if (b.rootHeight() < 3)
			return true;
		else return Math.abs(b.leftSubtree().rootHeight()-b.rightSubtree().rootHeight()) <= 1
					&& checkAVL(b.leftSubtree())
					&& checkAVL(b.rightSubtree())
					;
	}
	
	public static BST createBST(int[] a)
	{
		BST temp = new BST();

		for (int i = 0; i < a.length; i++)
			temp.insert(a[i]);

		return temp;
	}

	public static void main(String[] args)
	{
		int[][] a = {{82,12},
					{82, 85, 153, 195, 124, 66, 200, 193, 185, 243, 73, 153, 76},
					{5, 3, 7, 1},
					{5, 1, 98, 100, -3, -5, 55, 3, 56, 50},
					{297, 619, 279, 458, 324, 122, 505, 549, 83, 186, 131, 71},
					{78},
					{55, 12,11,22,64,58}}
					;
		for (int i = 0; i < a.length; i++)
		{
			BST T = createBST(a[i]);
			System.out.println("CHECKING " + T);
			System.out.println("RETURNED " + checkAVL(T));
		}
	}
}


class BST
{
	int size;
	TreeNode root;

	class TreeNode
	{
		private int value;
		private int height;
		private TreeNode parent;
		private TreeNode left;
		private TreeNode right;

		private TreeNode(int value, int height, TreeNode parent, TreeNode left, TreeNode right)
		{
			this.value = value;
			this.height = height;
			this.parent = parent;
			this.left = left;
			this.right = right;
		}
		// Used for new leaf nodes
		private TreeNode(int value, TreeNode parent)
		{
			this.value = value;
			this.height = 1;
			this.parent = parent;
			this.left = null;
			this.right = null;
		}
		// Used for new roots
		private TreeNode(int value)
		{
			this.value = value;
			this.height = 1;
			this.parent = null;
			this.left = null;
			this.right = null;
		}
	}

	public BST(TreeNode root, int size)
	{
		this.root = root;
		this.size = size;
	}
	public BST()
	{
		this.root = null;
		this.size = 0;
	}

	private void updateNodeHeights(TreeNode node, String side)
	{
		switch(side)
		{
			case "root":
				return;
			case "left":
				if (node.parent.right == null || node.parent.right.height < node.height)
				{
					node.parent.height++;
					if(node.parent.parent != null)
						side = (node.parent.parent.value > node.parent.value)? "left" : "right";
					else side = "root";
					updateNodeHeights(node.parent, side);
					return;
				}
			case "right":
				if (node.parent.left == null || node.parent.left.height < node.height)
				{
					node.parent.height++;
					if(node.parent.parent != null)
						side = (node.parent.parent.value > node.parent.value)? "left" : "right";
					else side = "root";
					updateNodeHeights(node.parent, side);
					return;
				}
		}
	}
	private void insert(TreeNode node, int value)
	{
		if (node.left != null && value < node.value)
			insert(node.left, value);
		else if (node.right != null && value > node.value)
			insert(node.right, value);
		else if (node.left == null && value < node.value)
		{
			node.left = new TreeNode(value, node);
			updateNodeHeights(node.left, "left");
		}
		else if (node.right == null && value > node.value)
		{
			node.right = new TreeNode(value, node);
			updateNodeHeights(node.right, "right");
		}
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

	public int rootHeight()
	{
		return (root != null)? root.height : 0;
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