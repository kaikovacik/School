/**
 * MedListTester.java
 * A test program for CSC115 Assignment 2 : Containers.
 *
 */
public class MedListTester {

	/**
 	 * Runs a test on the current version of the List interface.
	 * @param args Not used.
	 */
	public static void main (String[] args) {
		System.out.println("EXTERNAL TESTING BEGINS:");
		List<Medication> list = null;
		try {
/*
 * PROGRAMMER NOTE:
 * The following line needs to be changed to test the
 * MedListRefBased class.
 * Change "ArrayBased" to "RefBased" and run this
 * tester.  NOTHING ELSE SHOULD BE ALTERED
 */
			list = new MedListRefBased();
		} catch (Exception e) {
			System.out.println("Constructor not working");
			e.printStackTrace();	
			return;
		}
		// Add some medicine:
		Medication m1 = new Medication("ibuprofen",200);
		list.add(m1,0);
		System.out.print("Adding "+m1+" to an empty list: ");
		if (list.size() != 1) {
			System.out.println("FAILURE");
			return;
		} else {
			System.out.println("SUCCESS");
		}
		System.out.print("Checking size method: ");
		if (!list.get(0).equals(m1)) {
			System.out.println("FAILURE");
			return;
		} else {
			System.out.println("SUCCESS");
		}
		Medication m2 = new Medication("meperidol",100);
		System.out.print("Adding "+m2+" to the end of the list and checking size: ");
		list.add(m2,1);
		if (list.size() != 2) {
			System.out.println("FAILURE");
			return;
		} else {
			System.out.println("SUCCESS");
		}
		Medication m3 = new Medication("cimetidine",300);
		System.out.print("Adding "+m3+" to the middle and checking the indexOf method: ");
		list.add(m3,1);
		if (list.indexOf(m2) != 2) {
			System.out.println("FAILURE");
			return;
		} else {
			System.out.println("SUCCESS");
		}
		System.out.print("Checking the isEmpty method: ");
		if (list.isEmpty()) {
			System.out.println("FAILURE");
			return;
		} else {
			System.out.println("SUCCESS");
		}
		Medication m4 = new Medication("ibuprofen",100);
		System.out.print("Checking the out of bounds error handling: ");
		try {
			list.add(m4,4);
			System.out.println("FAILURE");
			return;
		} catch (IndexOutOfBoundsException e) {
			System.out.println("SUCCESS");
		} catch (Exception e) {
			System.out.println("UNEXPECTED EXCEPTION THROWN");
			e.printStackTrace();
			return;
		}
		list.add(m4,3);
		Medication m = list.get(3);
		System.out.print("Checking to make sure "+m4+" is in the correct place: ");
		if (!m.equals(m4)) {
			System.out.println("FAILURE");
			return;
		} else {
			System.out.println("SUCCESS");
		}
		System.out.print("Checking the list order :");
		try {
			if (!list.get(0).equals(m1)) {
				System.out.println("\tFAILURE "+m1+" is not the first item.");
				return;
			}
			if (!list.get(1).equals(m3)) {
				System.out.println("\tFAILURE "+m3+" is not the second item.");
				return;
			}
			if (!list.get(2).equals(m2)) {
				System.out.println("\tFAILRE "+m2+" is not the third item.");
		 		return;
			}
			if (!list.get(3).equals(m4)) {
				System.out.println("\tFAILURE "+m4+" is not the fourth item.");
				return;
			}
			System.out.println("\tSUCCESS");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Unexpected index exception thrown:");
			System.out.println(e.getMessage());
			return;	
		} catch (Exception e) {
			System.out.println("UNEXPECTED EXCEPTION THROWN");
			e.printStackTrace();
			return;
		}
		// test to make sure two items are removed here.
		System.out.println("Changed dosage of ibuprofen so there are two meds that are the same");
		m4.setDosage(200);
		System.out.print("Testing the remove for all duplicates: ");
		list.remove(m4);
		if (list.indexOf(m4) != -1) {
			System.out.println("FAILURE");
			return;
		} else {
			System.out.println("SUCCESS");
		}
		System.out.print("Checking the size method after removal: ");
		if (list.size() != 2) {
			System.out.println("FAILURE");
		} else {
			System.out.println("SUCCESS");
		}
		System.out.print("Removing the first item from the list: ");
		list.remove(0);
		if (!list.get(0).equals(m2)) {
			System.out.println("FAILURE");
			return;
		} else {
			System.out.println("SUCCESS");
		}
		System.out.print("Checking that size = 1: ");
		if (list.size() != 1) {
			System.out.println("FAILURE");
			return;	
		} else {
			System.out.println("SUCCESS");
		}
		System.out.print("Checking the removeAll method: ");
		list.removeAll();
		if (!list.isEmpty()) {
			System.out.println("FAILURE");
			return;
		}
		if (list.size() != 0) {
			System.out.println("FAILURE");
			return;
		}
		try {
			list.get(0);
			System.out.println("FAILURE");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("SUCCESS");
		} catch (Exception e) {
			System.out.println("UNEXPECTED EXCEPTION THROWN");
			e.printStackTrace();
			return;
		}
		System.out.println("EXTERNAL TESTING ENDS");
	}
}

