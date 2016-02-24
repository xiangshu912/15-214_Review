package edu.cmu.cs.cs214.hw2.expression;

/**
 * compute the zeros of a function using Newton's Method.
 *
 */
public final class NewtonMethod {
	// this class should never be instantiated.
	/**
	 * Returns a zero of the specified function using Newton's method
	 * with approxZero as the initial estimate.
	 * 
	 * @param fn the function whose zero is to be found
	 * @param x the independent variable of the function
	 * @param approxZero initial approximation for the zero of the function
	 * @param tolerance how close zero the returned zero has to be
	 * @return a value x for which f(x) is "close to zero" (<= tolerance)
	 */
	public static double zero(Expression fn, VariableExpression x,
							double approxZero, double tolerance) {
		x.store(approxZero);
		DerivativeExpression deriveFn = new DerivativeExpression(fn, x);
		double next = approxZero - fn.eval() / deriveFn.eval();
		while(Math.abs(next - approxZero) > tolerance) {
			approxZero = next;
			x.store(approxZero);
			DerivativeExpression derive = new DerivativeExpression(fn, x);
			next = approxZero - fn.eval() / derive.eval();
		}
		return approxZero;
	}

}
