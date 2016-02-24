package edu.cmu.cs.cs214.hw2.operator;

/**
 * BinaryOperators - an enum using an interface BinaryOperator. 
 *
 */
public enum BinaryOperators implements BinaryOperator {
	ADDITION("+") {
		/**
		 * apply Addition with two numbers given.
		 * 
		 * @param arg1 the first number before the operator
		 * @param arg2 the second number after the operator
		 * @return the addition with inputs arg1 and arg2
		 */
		public double apply(double arg1, double arg2) {
			return arg1 + arg2;
		}
	},
	SUBTRACTION("-") {
		/**
		 * apply subtraction with two numbers given.
		 * 
		 * @param arg1 the first number before the operator
		 * @param arg2 the second number after the operator
		 * @return the subtraction with inputs arg1 and arg2
		 */
		public double apply(double arg1, double arg2) {
			return arg1 - arg2;
		}
	},
	MULTIPLICATION("*") {
		/**
		 * apply multiplication with two numbers given.
		 * 
		 * @param arg1 the first number before the operator
		 * @param arg2 the second number after the operator
		 * @return the multiplication with inputs arg1 and arg2
		 */
		public double apply(double arg1, double arg2) {
			return arg1 * arg2;
		}
	},
	DIVISION("/") {
		/**
		 * apply division with two numbers given.
		 * 
		 * @param arg1 the first number before the operator
		 * @param arg2 the second number after the operator
		 * @return the division with inputs arg1 and arg2
		 */
		public double apply(double arg1, double arg2) {
			return arg1 / arg2;
		}
	},
	EXPONENTIATION("^") {
		/**
		 * apply exponentiation with two numbers given.
		 * 
		 * @param arg1 the first number before the operator
		 * @param arg2 the second number after the operator
		 * @return the exponentiation with inputs arg1 and arg2
		 */
		public double apply(double arg1, double arg2) {
			return Math.pow(arg1, arg2);
		}
	};
	private final String symbol;
	BinaryOperators(String symbol) {
		this.symbol = symbol;
	}
	@Override public String toString() {
		return symbol;
	}

}
