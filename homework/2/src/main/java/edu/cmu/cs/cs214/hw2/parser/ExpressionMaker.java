package edu.cmu.cs.cs214.hw2.parser;

import com.sun.javafx.fxml.expression.VariableExpression;

import edu.cmu.cs.cs214.hw2.expression.BinaryOperatorExpression;
import edu.cmu.cs.cs214.hw2.expression.Expression;
import edu.cmu.cs.cs214.hw2.expression.NumberExpression;
import edu.cmu.cs.cs214.hw2.expression.UnaryOperatorExpression;
import edu.cmu.cs.cs214.hw2.operator.BinaryOperator;
import edu.cmu.cs.cs214.hw2.operator.UnaryOperator;

/**
 * ExpressionMaker - a helper class for creating Expressions given various 
 * Operators and Expressions
 * 
 */
public class ExpressionMaker {
	
	/**
	 * Creates a binary operation expression.
	 *
	 * @param operator
	 *            the operator
	 * @param opperand1
	 *            the opperand1
	 * @param opperand2
	 *            the opperand2
	 * @return the expression
	 */
	public Expression createBinaryOperationExpression(BinaryOperator operator, Expression opperand1,
			Expression opperand2) {
		Expression biOpExp;
		biOpExp = new BinaryOperatorExpression(operator, opperand1, opperand2);
		return biOpExp;
	}

	/**
	 * Creates a unary operation expression.
	 *
	 * @param operator
	 *            the operator
	 * @param opperand
	 *            the opperand
	 * @return the expression
	 */
	public Expression createUnaryOperationExpression(UnaryOperator operator, Expression opperand) {
		Expression uOpExp;
		uOpExp = new UnaryOperatorExpression(operator, opperand);
		return uOpExp;
	}

	/**
	 * Creates a number expression.
	 *
	 * @param value
	 *            the value
	 * @return the expression
	 */
	public Expression createNumberExpression(double value) {
		Expression numOpExp;
		numOpExp = new NumberExpression(value);
		return numOpExp;
	}

}
