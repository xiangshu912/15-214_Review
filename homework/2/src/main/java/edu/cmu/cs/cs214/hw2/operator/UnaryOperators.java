package edu.cmu.cs.cs214.hw2.operator;

/**
 * UnaryOperators - an enum using an interface UnaryOperator. 
 *
 */
public enum UnaryOperators implements UnaryOperator{
	NEGATION("neg") {
		/**
		 * apply Negation on the number given.
		 * 
		 * @param arg the number given for the operator
		 * @return the negation of the input arg
		 */
		public double apply(double arg) {
			return -arg;
		}
	},
	ABSOLUTE("abs") {
		/**
		 * apply absolute value on the number given.
		 * 
		 * @param arg the number given for the operator
		 * @return the absolute value of the input arg
		 */
		public double apply(double arg) {
			if (arg < 0) {
				return -arg;
			} else {
				return arg;
			}
		}
	};
	private final String symbol;
	UnaryOperators(String symbol) {
		this.symbol = symbol;
	}
	@Override public String toString() {
		return symbol;
	}

}
