package edu.cmu.cs.cs214.hw2.expression;

/**
 * DerivativeExpression - The derivative of specific function.
 *
 */
public class DerivativeExpression implements Expression {
	
	private static final double DELTA_X = 1e-9;
	private Expression function;
	private VariableExpression variable;
	
	/**
	 * Create an expression representing the derivative of the specified
	 * function with respect to the specified variable.
	 * 
	 * @param fn the function whose derivative this expression represents
	 * @param independentVar the variable with respect to which we're
	 * 		  differentiating
	 */
	public DerivativeExpression(Expression fn, 
					VariableExpression independentVar) {
		function = fn;
		variable = independentVar;
	}

	@Override
	public double eval() {
		double fnValue1 = function.eval();
		variable.store(variable.eval() + DELTA_X);
		double fnValue2 = function.eval();
		return (fnValue2 - fnValue1) / DELTA_X;
	}
	

}
