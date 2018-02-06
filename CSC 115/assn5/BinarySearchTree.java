/*
 * Name: Kai Kovacik
 * ID: V00880027
 * Date: November 14, 2017
 * Filename: BinarySearchTree.java
 * Details: CSC115 Assignment 5
 */

import java.util.Iterator;

/**
 * BinarySearchTree is an ordered binary tree, where the element in each node
 * comes after all the elements in the left subtree rooted at that node
 * and before all the elements in the right subtree rooted at that node.
 * For this assignment, we can assume that there are no elements that are
 * identical in this tree. 
 * A small modification will allow duplicates.
 */
public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E>
{

	// The root is inherited from BinaryTree.

	/**
	 * Create an empty BinarySearchTree.
	 */
	public BinarySearchTree()
	{
		super();
	}

	/**
	 * Creates a BinarySearchTree with a single item.
	 * @param item The single item in the tree.
	 */
	public BinarySearchTree(E item)
	{
		super(item);
	}

	/**
 	 * <b>This method is not allowed in a BinarySearchTree.</b>
	 * It's description from the subclass:<br>
	 * <br>
	 * {@inheritDoc}
	 * @throws UnsupportedOperationException if this method is invoked.
	 */
	public void attachLeftSubtree(BinaryTree<E> left)
	{
		throw new UnsupportedOperationException();
	}

	public void attachRightSubtree(BinaryTree<E> right)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Inserts a new item into the tree, maintaining its order.
	 * If the item already exists in the tree, nothing happens.
	 * @param item The newest item.
	*/
	//ITERATIVE
	/*public void insert(E item)
	{
		TreeNode<E> newNode = new TreeNode<E>(item);
		TreeNode<E> curr = root;

		//if tree is empty, set node as root
		if(isEmpty()) 
			root = newNode;
		else while(curr != null)
		{
			//if item is lesser than curr.item
			if(curr.item.compareTo(item) > 0)
			{
				if(curr.left == null)
				{
					curr.left = newNode;
					newNode.parent = curr;
				}
				else 
					curr = curr.left;
			}
			// if item is greater than curr.item
			else if(curr.item.compareTo(item) < 0)
			{
				if(curr.right == null)
				{
					curr.right = newNode;
					newNode.parent = curr;
				}
				else 
					curr = curr.right;
			}
			// exits if item is already in tree
			else 
				return;
		}
	}*/
	//RECCURSIVE
	public void insert(E item)
	{
		root = insert(root, item);
	}

	private TreeNode<E> insert(TreeNode<E> node, E item)
	{
		if(node == null)
			return new TreeNode<E>(item);
		else if(node.item.compareTo(item) > 0)
		{
			node.left = insert(node.left, item);
			node.left.parent = node;
		}
		else if(node.item.compareTo(item) < 0)
		{
			node.right = insert(node.right, item);
			node.right.parent = node;
		}
		return node;
	}
	
	/**
	 * Looks for the item in the tree that is equivalent to the item.
	 * @param item The item that is equivalent to the item we are looking for. 
	 * Equality is determined by the equals method of the item.
	 * @return The item if it's in the tree, null if it is not.
	*/
	//ITERATIVE
	/*public E retrieve(E item)
	{
		TreeNode<E> curr = root;

		if(isEmpty()) 
			throw new TreeException("Cannot retrieve from an empty tree.");
		else while(curr != null)
		{
			//return item if found
			if(curr.item.compareTo(item) == 0) 
				return item;
			// iterate
			else if(curr.item.compareTo(item) > 0) 
				curr = curr.left;
			else if(curr.item.compareTo(item) < 0) 
				curr = curr.right;
		}
		//return null if no item found
		return null;
	}*/
	//RECCURSIVE
	public E retrieve(E item)
	{
		if(isEmpty()) 
			throw new TreeException("Cannot retrieve from an empty tree.");
		else
			return retrieve(root, item);
	}

	private E retrieve(TreeNode<E> node, E item)
	{
		if(node == null)
			return null;
		else if(node.item.compareTo(item) == 0)
			return item;
		else if(node.item.compareTo(item) > 0)
			return retrieve(node.left, item);
		return retrieve(node.right, item);
	}

	/**
	 * Recursive helper method.
	 * Finds the minimum item in a tree.
	 * @param node The current node.
	 */
	private E findMinimum(TreeNode<E> node)
	{
		if(node.left == null)
			return node.item;
		return findMinimum(node.left);
	}

	/**
	 * Recursive helper method.
	 * Finds the maximum item in a tree.
	 * @param node The current node.
	 */
	private E findMaximum(TreeNode<E> node)
	{
		if(node.right == null)
			return node.item;
		return findMaximum(node.right);
	}

	/**
	 * Finds the item that is equivalent to the item and 
	 * removes it, if it's in the tree.
	 * @param item The item that is equivalent to the item 
	 * we are looking for. Equality is determined by the 
	 * equals method of the item.
	 * @return The actual item that was removed, or 
	 * null if it is not in the tree.
	 */
	//ITERATIVE
	int iteration = 0;
	public E delete(E item)
	{
		TreeNode<E> curr = root;

		//return null if tree is empty
		if(isEmpty()) 
			return null;
		else while(curr != null)
		{
			if(curr.item.compareTo(item) == 0)
			{
				// if node has no child nodes
				if(curr.left == null && curr.right == null)
				{
					if(curr.parent.item.compareTo(item) > 0)
						curr.parent.left = null;
					else if(curr.parent.item.compareTo(item) < 0)
						curr.parent.right = null;
					curr.parent = null;
					//return item if founs
					return item;
				}
				// if node has one child node
				else if(curr.left == null && curr.right != null)
				{
					if(curr.parent.item.compareTo(item) > 0)
						curr.parent.left = curr.right;
					else if(curr.parent.item.compareTo(item) < 0)
						curr.parent.right = curr.right;
					curr.right.parent = curr.parent;
					curr.parent = null;
					//return item if found
					return item;
				}
				else if(curr.left != null && curr.right == null)
				{
					if(curr.parent.item.compareTo(item) > 0)
						curr.parent.left = curr.left;
					else if(curr.parent.item.compareTo(item) < 0)
						curr.parent.right = curr.left;
					curr.left.parent = curr.parent;
					curr.parent = null;
					//return item if found
					return item;
				}
				// if node has two child nodes
				else
				{
					if(iteration%2 == 0)
					{
						E temp = findMaximum(curr.left);
						delete(temp);
						curr.item = temp;
					}
					else
					{
						E temp = findMinimum(curr.right);
						delete(temp);
						curr.item = temp;
					}
					curr.parent = null;
					iteration++;
					return item;
				}
			}
			// otherwise iterate throught the binary search tree
			else if(curr.item.compareTo(item) > 0) 
				curr = curr.left;
			else if(curr.item.compareTo(item) < 0) 
				curr = curr.right;
		}
		//return null if item not found
		return null;
	}

	/**
	 * Internal test harness.
	 * @param args Not used.
	 */
	//------------------COMPLETE--------------------
	public static void main(String[] args){
		System.out.println("====================== testing insert =======================");
		// testing of insert()
		BinarySearchTree<String> tree = new BinarySearchTree<String>();
		tree.insert(new String("j"));
		tree.insert(new String("o"));
		tree.insert(new String("e"));
		tree.insert(new String("c"));
		tree.insert(new String("g"));
		tree.insert(new String("q"));
		tree.insert(new String("m"));
		tree.insert(new String("b"));
		tree.insert(new String("a"));
		tree.insert(new String("d"));
		tree.insert(new String("f"));
		tree.insert(new String("i"));
		tree.insert(new String("h"));
		tree.insert(new String("k"));
		tree.insert(new String("l"));
		tree.insert(new String("s"));
		tree.insert(new String("q"));
		tree.insert(new String("r"));
		tree.insert(new String("t"));
		if(!tree.isEmpty())
			System.out.println("Confirmed");
		else
			System.out.println("Failed");

		// visualisation 1
		DrawableBTree<String> dbt;
		dbt = new DrawableBTree<String>(tree);
		dbt.showFrame();

		// testing of retrieve()
		System.out.println("\n===================== testing retrieval =====================");
		String test;
		test = tree.retrieve("a");
		if (test != null && !test.equals("")) 
		{
			System.out.println("retrieving the node that contains 'a'");
			if (test.equals("a"))
				System.out.println("Confirmed");
			else
				System.out.println("retrieve returns the wrong item");
		}
		else
			System.out.println("retrieve couldn't find 'a'");
		test = tree.retrieve("j");
		if (test != null && !test.equals("")) 
		{
			System.out.println("retrieving the node that contains 'j'");
			if (test.equals("j"))
				System.out.println("Confirmed");
			else
				System.out.println("retrieve returns the wrong item");
		}
		else
			System.out.println("retrieve couldn't find 'j'");
		test = tree.retrieve("4");
		if (test != null && !test.equals("")) 
		{
			System.out.println("retrieving the node that contains '4'");
			if (test.equals("4"))
				System.out.println("Confirmed");
			else
				System.out.println("retrieve returns the wrong item");
		}
		else
			System.out.println("retrieve couldn't find '4'");

		//testing of iterators
		System.out.println("\n==================== testing iterators ====================");
		Iterator<String> it;
		it = tree.preorderIterator();
		System.out.println("printing out the contents of the tree in 'pre' order:");
		while (it.hasNext())
			System.out.print(it.next()+" ");
		System.out.println("\nprinting out the contents of the tree in 'in' order:");
		it = tree.inorderIterator();
		while (it.hasNext())
			System.out.print(it.next()+" ");
		System.out.println("\nprinting out the contents of the tree in 'post' order:");
		it = tree.postorderIterator();
		while (it.hasNext()) 
			System.out.print(it.next()+" ");
		System.out.println();

		//testing of height()
		System.out.println("\n================ testing height algortithm ================");
		System.out.println("printing out the height of the tree:");
		System.out.println(tree.height());

		//testing of delete()
		System.out.println("\n==================== testing deletion =====================");
		System.out.println("deleting the node that contains 'j'");
		if(tree.delete("j") != null)
		{
			// visualisation
			dbt = new DrawableBTree<String>(tree);
			dbt.showFrame();
			System.out.println("Confirmed");
		}
		else
			System.out.println("delete couldn't find 'j'");
		System.out.println("deleting the node that contains 'h'");
		if(tree.delete("h") != null)
		{
			// visualisation
			dbt = new DrawableBTree<String>(tree);
			dbt.showFrame();
			System.out.println("Confirmed");
		}
		else
			System.out.println("delete couldn't find 'h'");
		System.out.println("deleting the node that contains 'q'");
		if(tree.delete("q") != null)
		{
			// visualisation
			dbt = new DrawableBTree<String>(tree);
			dbt.showFrame();
			System.out.println("Confirmed");
		}
		else
			System.out.println("delete couldn't find 'q'");
		System.out.println("deleting the node that contains 's'");
		if(tree.delete("s") != null)
		{
			// visualisation
			dbt = new DrawableBTree<String>(tree);
			dbt.showFrame();
			System.out.println("Confirmed");
		}
		else
			System.out.println("delete couldn't find 's'");
		System.out.println("deleting the node that contains '4'");
		if(tree.delete("4") != null)
		{
			// visualisation
			dbt = new DrawableBTree<String>(tree);
			dbt.showFrame();
			System.out.println("Confirmed");
		}
		else
			System.out.println("delete couldn't find '4'");
	}
}