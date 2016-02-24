package edu.cmu.cs.cs214.hw2.expression;

/**
 * NumberExpression - The numbers in expression.
 *
 */
public class NumberExpression implements Expression{
	private double value;
	
	/**
	 * NumberExpression class constructor
	 * 
	 * @param val the value of the number
	 */
	public NumberExpression(double val) {
		value = val;
	}
	
	@Override
	public double eval() {
		return value;
	}
	
	@Override
	public String toString() {
		return Double.toString(value);
	}
}
