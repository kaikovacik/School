/*
 * Name: Kai Kovacik
 * ID: V00880027
 * Date: October 12, 2017
 * Filename: ArithExpression.java
 * Details: CSC115 Assignment 3
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ArithExpression {

	private TokenList postfixTokens;
	private TokenList infixTokens;
	private StringStack stack;

	/**
	 * Sets up a legal standard Arithmetic expression.
	 * The only parentheses accepted are "(" and ")".
	 * @param word An arithmetic expression in standard infix order.
	 * 	An invalid expression is not expressly checked for, but will not be
	 * 	successfully evaluated, when the <b>evaluate</b> method is called.
	 * @throws InvalidExpressionException if the expression cannot be properly parsed,
	 *  	or converted to a postfix expression.
	 */
	public ArithExpression(String word) {
		if (Tools.isBalancedBy("()",word)) {
			tokenizeInfix(word);
			infixToPostfix();
		} else {
			throw new InvalidExpressionException("Parentheses unbalanced");
		}
	}

	/**
	 * A private helper method that tokenizes a string by separating out
	 * any arithmetic operators or parens from the rest of the string.
	 * It does no error checking.
	 * The method makes use of Java Pattern matching and Regular expressions to
	 * isolate the operators and parentheses.
	 * The operands are assumed to be the substrings delimited by the operators and parentheses.
	 * The result is captured in the infixToken fromList, where each token is 
	 * an operator, a paren or a operand.
	 * @param express The string that is assumed to be an arithmetic expression.
	 */
	private void tokenizeInfix(String express) {
		infixTokens  = new TokenList(express.length());

		// regular expression that looks for any operators or parentheses.
		Pattern opParenPattern = Pattern.compile("[-+*/^()]");
		Matcher opMatcher = opParenPattern.matcher(express);

		String matchedBit, nonMatchedBit;
		int lastNonMatchIndex = 0;
		String lastMatch = "";

		// find all occurrences of a matched substring
		while (opMatcher.find()) {
			matchedBit = opMatcher.group();
			// get the substring between matches
			nonMatchedBit = express.substring(lastNonMatchIndex, opMatcher.start());
			nonMatchedBit = nonMatchedBit.trim(); //removes outside whitespace
			// The very first '-' or a '-' that follows another operator is considered a negative sign
			if (matchedBit.charAt(0) == '-') {
				if (opMatcher.start() == 0 || 	
					!lastMatch.equals(")") && nonMatchedBit.equals("")) {
					continue;  // ignore this match
				}
			}
			// nonMatchedBit can be empty when an operator follows a ')'
			if (nonMatchedBit.length() != 0) {
				infixTokens.append(nonMatchedBit);
			}
			lastNonMatchIndex = opMatcher.end();
			infixTokens.append(matchedBit);
			lastMatch = matchedBit;
		}
		// parse the final substring after the last operator or paren:
		if (lastNonMatchIndex < express.length()) {
			nonMatchedBit = express.substring(lastNonMatchIndex,express.length());
			nonMatchedBit = nonMatchedBit.trim();
			infixTokens.append(nonMatchedBit);
		}
	}

	/**
	 * Determines whether a single character string is an operator.
	 * The allowable operators are {+,-,*,/,^}.
	 * @param op The string in question.
	 * @return True if it is recognized as a an operator.
	 */
	public static boolean isOperator(String op) {
		switch(op) {
			case "+":
			case "-":
			case "/":
			case "*":
			case "^":
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * Determines if an operator is of lower or equal 
	 * presidence compared to another operator.
	 * @param fromList, fromStack Strings that hold one operator each
	 * to compare.
	 * @return true if <i>fromList</i> is of lower or equal presidence than <i>fromStack<i/>.
	 */
	private static boolean isLowerOrEqualPresidence(String fromList, String fromStack)
	{
		//+ or -
	 	if(fromList.charAt(0) == '+' || fromList.charAt(0) == '-')
		{	
			//always lower or equal presidence
	 		return fromStack.charAt(0) != '(';
	 	}
	 	// * or /
	 	else if(fromList.charAt(0) == '*' || fromList.charAt(0) == '/')
	 	{
	 		//either lower, equal, or higher presidence
	 		return !(fromStack.charAt(0) == '+' || fromStack.charAt(0) == '-') && fromStack.charAt(0) != '(';
	 	}
	 	// ^
	 	else if(fromList.charAt(0) == '^')
	 	{
	 		//never lower presidence, potentially equal
	 		return fromStack.charAt(0) == '^' && fromStack.charAt(0) != '(';
	 	}
		//if unrecognized, returns true (shouldn't happen)
	 	return true;
	}

	/**
	 * Determines whether a single character string is an operand.
	 * The allowable operatands are all digits.
	 * @param op The string in question.
	 * @return True if it is recognized as a an operand.
	 */
	private static boolean isOperand(String op)
	{
		return Character.isDigit(op.charAt(0));
	}				

	 /**
	 * A private method that initializes the postfixTokens data field.
	 * It takes the information from the infixTokens data field.
	 * If, during the process, it is determined that the expression is invalid,
	 * an InvalidExpressionException is thrown.
 	 * Note that since the only method that calls this method is the constructor,
	 * the Exception is propogated through the constructor.
	 */
	private void infixToPostfix() {
		stack = new StringStack();
		postfixTokens = new TokenList(infixTokens.size());
		boolean isHigherPresidence = false;
		boolean isEqualPresidence = false;

	 	for(int i = 0; i < infixTokens.size(); i++)
	 	{
	 		//if token is operand
	 		if(isOperand(infixTokens.get(i)))
	 		{
	 			//appends operand to list
	 			postfixTokens.append(infixTokens.get(i));
	 		}
	 		//if token is operator
	 		else if(isOperator(infixTokens.get(i)))
	 		{
	 			//is the stack is empty
	 			if(stack.isEmpty())
	 			{
	 				//pushes operator to stack
	 				stack.push(infixTokens.get(i));
	 			}
	 			else
	 			{
	 				//if greater presidence
	 				if(!isLowerOrEqualPresidence(infixTokens.get(i), stack.peek()))
	 				{
	 					//pushes operator to stack
	 					stack.push(infixTokens.get(i));
	 				}
	 				//if lower or equal presidence
	 				else
	 				{
	 					while(!stack.isEmpty())
	 					{
	 						if(!isLowerOrEqualPresidence(infixTokens.get(i), stack.peek()))
	 						{
	 							break;
	 						}
	 						//appends higher presidence operators to list
	 						postfixTokens.append(stack.pop());
	 					}
	 					//pushes operator to stack
	 					stack.push(infixTokens.get(i));
	 				}
	 			}
	 		}
	 		//if left parenthesis
	 		else if(infixTokens.get(i).charAt(0) == '(')
	 		{
	 			//pushes parenthesis to stack
	 			stack.push(infixTokens.get(i));
	 		}
	 		//if right parenthesis
	 		else if(infixTokens.get(i).charAt(0) == ')')
	 		{
	 			//pops and appends all tokens until left brace to list
	 			while(!stack.isEmpty() && stack.peek().charAt(0) != '(')
	 			{
	 				postfixTokens.append(stack.pop());
	 			}
	 			//pops left brace from stack
	 			stack.pop();
	 		}
	 		//if unrecognized, just dump it on the stack and we'll deal with it later
	 		else
	 		{
	 			//pushes unknown thing to stack
	 			postfixTokens.append(infixTokens.get(i));
	 		}
	 	}
	 	//when end of string
	 	while(!stack.isEmpty())
	 	{
	 		//appends all leftover tokens in stack
	 		postfixTokens.append(stack.pop());	
	 	}
	}

	/**
	 * public method that fetches the infixExpression.
	 * @return infixTokens.toString() The string representation of postfixExpression
	 */
	public String getInfixExpression() {
		return infixTokens.toString();
	}

	/**
	 * public method that fetches the postfixExpression.
	 * @return postfixTokens.toString() The string representation of postfixExpression
	 */
	public String getPostfixExpression() {
		return postfixTokens.toString();
	}
	
	/**
	 * public method that evaluates the postfix arithmetic expression.
	 * @return evaluation The double value of the arithmetic expression.
	 */
	public double evaluate() {
		double operand1;
		double operand2;
		double evaluation = 1;

		for(int i = 0; i < postfixTokens.size(); i++)
		{
			//if token is operand
			if(isOperand(postfixTokens.get(i)))
			{
				//push operator onto stack
				stack.push(postfixTokens.get(i));
			}
			//if token is operator, apply to two prev operands
			else if(isOperator(postfixTokens.get(i)))
			{
				//takes top two tokens from stack in correct order
				operand2 = Double.parseDouble(stack.pop());
				operand1 = Double.parseDouble(stack.pop());

				//adds
				if(postfixTokens.get(i).charAt(0) == '+')
				{
					evaluation = operand1+operand2;
				}
				//subtracts
				else if(postfixTokens.get(i).charAt(0) == '-')
				{
					evaluation = operand1-operand2;
				}
				//multiplies
				else if(postfixTokens.get(i).charAt(0) == '*')
				{
					evaluation = operand1*operand2;
				}
				//divides
				else if(postfixTokens.get(i).charAt(0) == '/')
				{
					//stops division by 0
					if(operand2 == 0)
					{
						throw new InvalidExpressionException("INDETERMINANT ANSWER");
					}
					else
					{
						evaluation = operand1/operand2;
					}
				}
				//raises to the power
				else if(postfixTokens.get(i).charAt(0) == '^')
				{
					evaluation = Math.pow(operand1, operand2);
				}
				stack.push(Double.toString(evaluation));
			}
			//if unrecognized operand
			else
			{
				throw new InvalidExpressionException("Invalid expression: evaluation not possible");
			}
		}
		return evaluation;
	}

	/**
	 * public method that converts ArithExpression object to string representation
	 * of both the infix and postfix expression.
	 * @return ("infix " + getInfixExpression() + "\nPostfix: " + getPostfixExpression())
	 * The string representation of ArithExpression.
	 */
	public String toString()
	{
		return "Infix: " + getInfixExpression() + "\nPostfix: " + getPostfixExpression(); 
	}
	
	//Testing					
	public static void main(String[] args) {
		ArithExpression exp;
		exp = new ArithExpression("(2+5*7)");
		System.out.println(exp);
		System.out.println("Evaluation: " + exp.evaluate());
		if(exp.evaluate() == 37)
		{
			System.out.println("Test: PASSED\n");
		}
		else
		{
			System.out.println("Test: FAILED\n");
		}

		exp = new ArithExpression("(6+4-1)*7-5/(3+1)");
		System.out.println(exp);
		System.out.println("Evaluation: " + exp.evaluate());
		if(exp.evaluate() == 61.75)
		{
			System.out.println("Test: PASSED\n");
		}
		else
		{
			System.out.println("Test: FAILED\n");
		}

		exp = new ArithExpression("9 5*2^3");
		System.out.println(exp);
		System.out.println("Evaluation: " + exp.evaluate());
		if(exp.evaluate() == 49)
		{
			System.out.println("Test: PASSED\n");
		}
		else
		{
			System.out.println("Test: FAILED\n");
		}

		exp = new ArithExpression("(a+b)*c+d/e-f");
		System.out.println(exp);
		try
		{
			System.out.println("Evaluation: " + exp.evaluate());
			System.out.println("Error handling: FAILED\n");
		}
		catch(Exception e)
		{
			System.out.println("Error handling: PASSED\n");
		}

		exp = new ArithExpression("X%123fH@");
		System.out.println(exp);
		try
		{
			System.out.println("evaluation: " + exp.evaluate());
			System.out.println("Error handling: FAILED\n");
		}
		catch(Exception e)
		{
			System.out.println("Error handling: PASSED\n");
		}

		System.out.println("END OF TESTING");
	}		
}
