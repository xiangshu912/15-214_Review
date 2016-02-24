package edu.cmu.cs.cs214.hw2.parser;

import java.util.Map;

import edu.cmu.cs.cs214.hw2.operator.Operator;

/**
 * OrderOfOperationsRules - Used for calculating operator precedence.
 *
 * Note: YOU SHOULD NOT NEED TO MODIFY THIS CLASS.
 */
public class OrderOfOperationsRules {

	/** The precedence table. */
	private final Map<String, Integer> table;
	
	/**
	 * Instantiates a new order of operations rules.
	 *
	 * @param table the table
	 */
	public OrderOfOperationsRules(Map<String, Integer> table) {
		this.table = table;
	}
	
	/**
	 * Compares the precedence of two operators
	 *
	 * @param operator1 the operator1
	 * @param operator2 the operator2
	 * @return the comparison result
	 */
	public int compare(Operator operator1, Operator operator2) {
		return Integer.compare(getPrecedence(operator1), getPrecedence(operator2));
	}
	
	/**
	 * Gets the precedence.
	 *
	 * @param operator the operator
	 * @return the precedence
	 */
	private int getPrecedence(Operator operator) {
		
		final String operatorString = operator.toString();
		if(table.containsKey(operatorString)) {
			return table.get(operatorString);
		}
		return 0; // Operator isn't in the table, treat it as highest precendence
	}

}
