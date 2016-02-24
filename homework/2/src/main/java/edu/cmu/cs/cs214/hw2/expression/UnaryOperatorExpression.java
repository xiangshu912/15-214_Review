package edu.cmu.cs.cs214.hw2.expression;

import edu.cmu.cs.cs214.hw2.operator.UnaryOperator;

/**
 * UnaryOperatorExpression - The expression with unary operators.
 *
 */
public class UnaryOperatorExpression implements Expression {
	
	private UnaryOperator uOperator;
	private Expression para;
	
	/**
	 * UnaryOperatorExpression class constructor
	 * 
	 * @param uOp the unary operator in the expression
	 * @param p the expression right of the operator
	 */
	public UnaryOperatorExpression(UnaryOperator uOp, Expression p) {
		uOperator = uOp;
		para = p;
	}
	
	@Override
	public double eval() {
		double numValue = para.eval();
		return uOperator.apply(numValue);
	}
	
	@Override
	public String toString() {
		return uOperator.toString() + Double.toString(para.eval());
	}

}
