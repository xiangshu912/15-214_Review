package edu.cmu.cs.cs214.hw2.expression;

import edu.cmu.cs.cs214.hw2.operator.BinaryOperator;

/**
 * BinaryOperatorExpression - The expression with binary operators.
 *
 */
public class BinaryOperatorExpression implements Expression{
	
	private BinaryOperator biOperator;
	private Expression para1;
	private Expression para2;
	
	/**
	 * BinaryOperatorExpression class constructor
	 * 
	 * @param biOp the binary operator in the expression
	 * @param p1 the expression left of the operator
	 * @param p2 the expression right of the operator
	 */
	public BinaryOperatorExpression(BinaryOperator biOp, 
			Expression p1, Expression p2) {
		biOperator = biOp;
		para1 = p1;
		para2 = p2;
	}
	
	@Override
	public double eval() {
		double num1Value = para1.eval();
		double num2Value = para2.eval();
		return biOperator.apply(num1Value, num2Value);
	}
	
	@Override
	public String toString() {
		return Double.toString(para1.eval()) + biOperator.toString() 
			+ Double.toString(para2.eval());
	}
}
