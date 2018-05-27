import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;

public class RedBlackBST 
{

    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private Node root;     // root of the BST
    public float numOfRed;

    // BST helper node data type
    private class Node 
    {
        private int key;           // key
        private Node left, right;  // links to left and right subtrees
        private boolean color;     // color of parent link
        private int size;          // subtree count
        //private int sizeRed;       // subtree red node count

        public Node(int key, boolean color, int size/*, int sizeRed*/) 
        {
            this.key = key;
            this.color = color;
            this.size = size;
            //this.sizeRed = sizeRed;
        }
    }

	public RedBlackBST() 
    {
        numOfRed = 0.0f;
	}
   /***************************************************************************
    *  Node helper methods.
    ***************************************************************************/
    // is node x red; false if x is null ?
    private boolean isRed(Node x) 
    {
        if (x == null) return false;
        return x.color == RED;
    }
    
    private int getNumOfRed(Node x)
    {
        if (x == null)
            return 0;
        else if (isRed(x))
            return 1 + getNumOfRed(x.left) + getNumOfRed(x.right);
        else
            return getNumOfRed(x.left) + getNumOfRed(x.right);
    }

    /**
     * Returns the number of red-designated, key-value pairs in this symbol table.
     * @return the number of red-designated, key-value pairs in this symbol table
     */
    public int getNumOfRed()
    {
        return getNumOfRed(root);
    }

    // return the percent of red nodes in the tree
    public float percentRed()
    {
        return numOfRed/root.size*100.0f;
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) 
    {
        if (x == null) return 0;
        return x.size;
    } 
    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() 
    {
        return size(root);
    }

   /**
     * Is this symbol table empty?
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() 
    {
        return root == null;
    }

   /***************************************************************************
    *  Red-black tree insertion.
    ***************************************************************************/

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key the key
     * @param val the value
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public void put(int key) 
    {
        root = put(root, key);
        root.color = BLACK;
        numOfRed = getNumOfRed();
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node h, int key) 
    { 
        if (h == null) return new Node(key, RED, 1/*, 1*/);

        int cmp = key - h.key;
        if      (cmp < 0) h.left  = put(h.left,  key); 
        else if (cmp > 0) h.right = put(h.right, key); 
        else              h.key   = key;

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;
        //h.sizeRed = sizeRed(h.left) + sizeRed(h.right) + ((isRed(h))? 1 : 0);

        return h;
    }

   /***************************************************************************
    *  Red-black tree helper functions.
    ***************************************************************************/

    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) 
    {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        //x.sizeRed = sizeRed(x.left) + sizeRed(x.right) + ((isRed(x))? 1 : 0);
        h.size = size(h.left) + size(h.right) + 1;
        //h.sizeRed = sizeRed(h.left) + sizeRed(h.right) + ((isRed(h))? 1 : 0);
        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) 
    {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        //x.sizeRed = sizeRed(x.left) + sizeRed(x.right) + ((isRed(x))? 1 : 0);
        h.size = size(h.left) + size(h.right) + 1;
        //h.sizeRed = sizeRed(h.left) + sizeRed(h.right) + ((isRed(h))? 1 : 0);
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) 
    {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
        //h.sizeRed--;
    }

    /**
     * Unit tests the {@code RedBlackBST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) 
    { 
		// RedBlackBST st = new RedBlackBST();
		// st.put(0);
		// st.put(1);
		// st.put(2);
		// st.put(3);
		// st.put(4);
		// st.put(5);
		// System.out.printf("Size of tree is %d\n", st.size());
        RedBlackBST tree = new RedBlackBST();

        try
        {
            Scanner file = new Scanner(new File(args[0]));

            while(file.hasNextInt()) 
                tree.put(file.nextInt());
        }
        catch (Exception e)
        {
            System.out.println("I need to deal with this");        
        }
        System.out.println("Size is: " + tree.size());
        System.out.println("SizeRed is: " + tree.numOfRed);
        System.out.println("Percent red is: " + tree.percentRed());
    }
}

/******************************************************************************
 *  Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/