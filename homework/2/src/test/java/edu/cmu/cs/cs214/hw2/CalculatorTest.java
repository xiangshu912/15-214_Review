package edu.cmu.cs.cs214.hw2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.cmu.cs.cs214.hw2.expression.BinaryOperatorExpression;
import edu.cmu.cs.cs214.hw2.expression.DerivativeExpression;
import edu.cmu.cs.cs214.hw2.expression.Expression;
import edu.cmu.cs.cs214.hw2.expression.NewtonMethod;
import edu.cmu.cs.cs214.hw2.expression.NumberExpression;
import edu.cmu.cs.cs214.hw2.expression.UnaryOperatorExpression;
import edu.cmu.cs.cs214.hw2.expression.VariableExpression;
import edu.cmu.cs.cs214.hw2.operator.BinaryOperators;
import edu.cmu.cs.cs214.hw2.operator.UnaryOperators;
import edu.cmu.cs.cs214.hw2.parser.ExpressionMaker;

/**
 * unit tests for the DerivativeExpression class.
 *
 */
public class CalculatorTest {
	
	/**
	 * test the function of the calculator.
	 */
	@Test
	public void test() {
		
		/**
		 * test the binary operators
		 */
		final NumberExpression numExp1 = new NumberExpression(3.0);
		final NumberExpression numExp2 = new NumberExpression(2.0);
		assertEquals(numExp1.toString(), "3.0");
		assertEquals(numExp2.toString(), "2.0");
		
		BinaryOperators biOp1 = BinaryOperators.ADDITION;
		BinaryOperators biOp2 = BinaryOperators.SUBTRACTION;
		BinaryOperators biOp3 = BinaryOperators.MULTIPLICATION;
		BinaryOperators biOp4 = BinaryOperators.DIVISION;
		BinaryOperators biOp5 = BinaryOperators.EXPONENTIATION;
		
		assertEquals(biOp1.toString(), "+");
		assertEquals(biOp2.toString(), "-");
		assertEquals(biOp3.toString(), "*");
		assertEquals(biOp4.toString(), "/");
		assertEquals(biOp5.toString(), "^");
		
		Expression exp1 = new BinaryOperatorExpression(biOp1, numExp1, numExp2);
		Expression exp2 = new BinaryOperatorExpression(biOp2, numExp1, numExp2);
		Expression exp3 = new BinaryOperatorExpression(biOp3, numExp1, numExp2);
		Expression exp4 = new BinaryOperatorExpression(biOp4, numExp1, numExp2);
		Expression exp5 = new BinaryOperatorExpression(biOp5, numExp1, numExp2);
		
		assertEquals(exp1.toString(), "3.0+2.0");
		assertEquals(exp2.toString(), "3.0-2.0");
		assertEquals(exp3.toString(), "3.0*2.0");
		assertEquals(exp4.toString(), "3.0/2.0");
		assertEquals(exp5.toString(), "3.0^2.0");
		
		assertTrue(exp1.eval() == 5.0);
		assertTrue(exp2.eval() == 1.0);
		assertTrue(exp3.eval() == 6.0);
		assertTrue(exp4.eval() == 1.5);
		assertTrue(exp5.eval() == 9.0);
		System.out.println("The binary operations pass the test.");
		
		/**
		 * test the unary operators
		 */
		final NumberExpression numExp3 = new NumberExpression(-2.0);
		final NumberExpression numExp4 = new NumberExpression(3.0);
		UnaryOperators uOp1 = UnaryOperators.ABSOLUTE;
		UnaryOperators uOp2 = UnaryOperators.NEGATION;
		assertEquals(uOp1.toString(), "abs");
		assertEquals(uOp2.toString(), "neg");
		
		exp1 = new UnaryOperatorExpression(uOp1, numExp3);
		exp2 = new UnaryOperatorExpression(uOp2, numExp3);
		exp3 = new UnaryOperatorExpression(uOp1, numExp4);
		exp4 = new UnaryOperatorExpression(uOp2, numExp4);
		assertEquals(exp1.toString(), "abs-2.0");
		assertEquals(exp2.toString(), "neg-2.0");
		assertEquals(exp3.toString(), "abs3.0");
		assertEquals(exp4.toString(), "neg3.0");
		
		assertTrue(exp1.eval() == 2.0);
		assertTrue(exp2.eval() == 2.0);
		assertTrue(exp3.eval() == 3.0);
		assertTrue(exp4.eval() == -3.0);
		System.out.println("The unary operations pass the test.");
		
		/**
		 * test the expression maker
		 * construction of expression neg(3*2)
		 */
		ExpressionMaker createExp = new ExpressionMaker();
		Expression numExp5 = createExp.createNumberExpression(3.0);
		Expression numExp6 = createExp.createNumberExpression(2.0);
		Expression exp6 = createExp.createBinaryOperationExpression(biOp3, numExp5, numExp6);
		assertTrue(exp6.eval() == 6.0);
		Expression exp7 = createExp.createUnaryOperationExpression(uOp2, exp6);
		assertTrue(exp7.eval() == -6.0);
		System.out.println("The operations maker passes the test.");
		
		
		/**
		 * test the variable function x * x - 2.0
		 * where variable x=1.0
		 */
		String variableName = "x";
		VariableExpression variable = new VariableExpression(variableName);
		double variableValue = 1.0;
		variable.store(variableValue);
		assertTrue(variable.eval() == 1.0);
		assertEquals(variable.name(), "x");
		assertEquals(variable.toString(), "x=1.0");
		System.out.println("The variable expression passes the test.");
		
		/**
		 * test the derivative of the variable function x * x - 2.0
		 * where variable x=1.0
		 */
		final double tolerance = 1e-5;
		Expression fn1 = new BinaryOperatorExpression(biOp3, variable, variable);
		NumberExpression numExp = new NumberExpression(2.0);
		Expression fn = new BinaryOperatorExpression(biOp2, fn1, numExp);
		DerivativeExpression derive = new DerivativeExpression(fn, variable);
		double deriValue = derive.eval();
		assertTrue(Math.abs(deriValue-2.0) <= tolerance);	
		System.out.println("The derivative passes the test.");
		
		
		/**
		 * test the Newton's method to calculate zeros of the function 
		 * with initial estimate 1
		 */
		double ini = 1.0;
		double result = NewtonMethod.zero(fn, variable, ini, tolerance);
		final NumberExpression numExp7 = new NumberExpression(2.0);
		final NumberExpression numExp8 = new NumberExpression(0.5);
		Expression exp = new BinaryOperatorExpression(biOp5, numExp7, numExp8);
		assertTrue(Math.abs(result-exp.eval()) <= tolerance);		
		System.out.println("The Newton's Method passes the test.");
		
	}

}






