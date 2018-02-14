/**
 * CSC115 Assignment 2 : Containers
 * Medication.java
 * Creawted for use by CSC115 Summer2016
 */

/*
 * NOTE TO PROGRAMMER:
 * Do not alter this file.
 * It is still in its beta version.
 * In its marketable form, valid medication names
 * and appropriate dosages will be checked
 * against a look-up table or database.
 * This simplified version is for educational purposes only.
 */

/**
 * Medication is a class representing a single medication,
 * identified by its generic name and current dispensing dosage.
 */
public class Medication {
	
	private String genericName;
	// in milligrams	
	private double dosage;

	/**
	 * Creates a single medication object.
 	 * @param name The generic name, which is not currently checked for accuracy.
	 * @param dosage The number of milligrams in a single tablet, capsule or patch.
	 */
	public Medication(String name, int dosage) {
		genericName = name;
		this.dosage = dosage;
	}

	/**
	 * Creates a single medication object with a default
	 * dosage of 0 mg.
 	 * @param name The generic name, which is not currently checked for accuracy.
 	 */
	public Medication(String name) {
		genericName = name;
	}

	/**
	 * @return The generic name of the medication.
 	 */
	public String getName() {
		return genericName;
	}

	/**
	 * @return The current dosage of the medication.
	 */
	public double getDosage() {
		return dosage;
	}

	/**
	 * Updates the dosage.
 	 * @param dosage The updated dosage (in milligrams).
	 */
	public void setDosage(double dosage) {
		this.dosage = dosage;	
	}

	/**
	 * @return A string representation of this medication,
	 * using the following format:
	 * <br><code>
	 * &lt;genericName&gt;:&lt;dd&gt;mg
	 * </code><br>
	 * where <code>&lt;genericName&gt;</code> is the actual name and <code>&lt;xx&gt;</code> is a number,
	 * with no more than 3 decimal precision.
	 */
	public String toString() {
		return genericName+":"+dosage+"mg";
	}

	/**
	 * Two medications are equivalent if they have the same
	 * generic name and equal dosages.
	 * @param other The medication that may or may not be equivalent to this medication.
	 * @return True if this medication is equivalent to other, false otherwise.
	 */
	public boolean equals(Medication other) {
		return genericName.equalsIgnoreCase(other.genericName) 
			&& dosage==other.dosage;
	}

	/**
	 * Determines the ordering of two medications.
	 * Ordering is determined by the alphabetical ordering of the name
	 * followed by the numerical ordering of the dosage.
	 * @param other The medication to compare to this medication.
	 * @return n, where n is either: <ul>
	 *	<li> 0 if the medications are equivalent,</li>
	 *	<li> &lt; 0 if this medication precedes the other medication, or</li>
	 *	<li> &gt; 0 if this medication comes after the other mediation.</li>
	 * </ul>
	 */
	public int compareTo(Medication other) {
		int cmpName = genericName.compareTo(other.genericName);
		if (cmpName == 0) {
			return (int)(dosage*1000) - (int)(other.dosage*1000);
		} else {
			return cmpName;
		}
	}

	/* NOTE TO STUDENT: 
	The main method was used to test this class internally and then removed before delivering to the client.
	*/
}
	
