/**
 * CSC115 Assignment 2 : Containers
 * MedicationNode.java
 * Created for use by CSC115 Summer2016 
 */ 

/**
 * The MedicationNode class is the single component of the MedListRefBased.
 * See Textbook, page 249 for a description of the Integer Node, which has a similar format.
 * Note that we do not make the data fields private or public for the reasons cited in
 * the textbook.
 * In this version, we leave out the package statement and assume that
 * the package is the current directory we are working in.
 */

class MedicationNode {
	protected Medication item;
	protected MedicationNode prev;
	protected MedicationNode next;

	/**
	 * Creates a Node that contains a Medication object.
 	 * @param item The Medication contained in the node.
	 * @param prev The node that comes before this one in the list.
	 * @param next The node that comes after this one in the list.
	 */
	protected MedicationNode(Medication item, MedicationNode prev, MedicationNode next) {
		this.item = item;
		this.prev = prev;
		this.next = next;
	}

	/**
	 * Creates a Node that contains a Medication that is not attached to the list.
	 * @param item The Medication contained in the node.
	 */
	protected MedicationNode(Medication item) {
		this(item,null,null);
	}
}


