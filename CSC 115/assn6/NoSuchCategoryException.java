/*
 * CSC115 Assignment 5 : Emergency Room
 * NoSuchCategoryException.java
 * Created for use by CSC115 Spring 2016.
 */

/**
 * An runtime exception indicating that a particular category was not found
 * for a process to continue.
 * In this context, it is used to indicate that a
 * particular ER_Patient symptom was not recognized and an ER priority number
 * cannot be attributed to the patient.
 */

public class NoSuchCategoryException extends RuntimeException {

	/**
	 * Create an exception.
	 * @param msg The message associated with this exception.
	 */
	public NoSuchCategoryException(String msg) {
		super(msg);
	}

	/**
	 * Create a default exception.
	 */
	public NoSuchCategoryException() {
		super();
	}
}