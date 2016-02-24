package edu.cmu.cs.cs214.hw2.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import edu.cmu.cs.cs214.hw2.expression.Expression;
import edu.cmu.cs.cs214.hw2.operator.BinaryOperator;
import edu.cmu.cs.cs214.hw2.operator.Operator;
import edu.cmu.cs.cs214.hw2.operator.UnaryOperator;

/**
 * ExpressionParser - parses String expressions and returns an Expression.
 * 
 *         Constraints: Supports custom configured operator precedence. Trying
 *         to trick the parser will work, it is tokenizing on white space.
 * 
 *         Expected Input: All inputs should have a single space between each
 *         'token'. A token is defined as either a number or an operator symbol.
 *         Parenthesis may be connected to a number token
 *         
 *         
 * Note: YOU SHOULD NOT NEED TO MODIFY THIS CLASS.
 */
public final class ExpressionParser {

	/** The set of supported operators */
	private final Set<Operator> operatorSet;

	/** The precedence table */
	private final OrderOfOperationsRules precedence;

	/** The expression builder */
	private final ExpressionMaker builder;

	/** Validation pattern */
	private final Validator validator;

	/**
	 * Instantiates a new parser.
	 *
	 * @param operationSet
	 *            the operation set
	 * @param builder
	 *            the builder
	 */
	public ExpressionParser(Set<Operator> operationSet, ExpressionMaker builder) {
		this.operatorSet = operationSet;
		this.builder = builder;
		Map<String, Integer> table = new HashMap<>();
		table.put("+", 1);
		table.put("-", 1);
		table.put("*", 2);
		table.put("/", 2);
		this.precedence = new OrderOfOperationsRules(table);
		this.validator = new Validator(operationSet);

	}

	/**
	 * Used for structuring the final expression
	 */
	private final class TreeHelper {

		/** State variables */
		private Expression root, left;

		/** The pending operator. */
		private BinaryOperator pendingOperator;

		/** Is rotation need (used for correcting operator precedence) */
		private boolean pendingRotation;

		/**
		 * Add a new expression to the tree (state should be updated)
		 *
		 * @param newExpression
		 *            the new expression
		 */
		private void buildTree(Expression newExpression) {
			if (pendingOperator != null) {
				if (pendingRotation) {
					// The pending operator has higher precedence than the root
					// (so it should replace the root's right child)
					assert (root != null);
					assert (left == null);
					assert (root instanceof BinaryNode);
					BinaryNode binaryRoot = (BinaryNode) root;
					Expression child = new BinaryNode(pendingOperator, binaryRoot.right, newExpression);
					binaryRoot.right = child;
					pendingRotation = false;

				} else {
					// The pending operator has lower precedence than the root
					// (so it should be the new root)
					assert (root == null) : root.toString();
					assert (left != null);
					root = new BinaryNode(pendingOperator, left, newExpression);
					left = null;
				}
				pendingOperator = null;

			} else {
				// Initial tree
				assert (!pendingRotation);
				assert (left == null);
				assert (root == null);
				root = newExpression;

			}
		}

		/**
		 * Sets the pending operator.
		 *
		 * @param operator
		 *            the new pending operator
		 */
		private void setPendingOperator(BinaryOperator operator) {
			assert (this.pendingOperator == null);
			assert (this.left == null);
			pendingOperator = operator;
			if (root instanceof BinaryNode) {
				final BinaryNode binaryNode = (BinaryNode) root;
				final int result = precedence.compare(binaryNode.operator, operator);
				if (result < 0) {
					// Keep Root since we are adding a higher precedence
					// operation. Rotation will correct for precedence on next
					// build
					this.pendingRotation = true;
					return;
				}
			}

			// Swap Out Root since we are adding a lower precedence operation or
			// the root is not binary
			left = root;
			root = null;

		}

		/**
		 * Gets the tree. Also validates invariants
		 *
		 * @return the tree
		 */
		private Expression getTree() {
			assert (left == null);
			assert (this.pendingOperator == null);
			return this.root;
		}

	}

	/**
	 * Parses the expression
	 *
	 * @param expression
	 *            the expression
	 * @return the expression
	 */
	public Expression parse(String expression) {
		if (!validator.validate(expression)) {
			throw new IllegalArgumentException("Invalid Expression");
		}
		Scanner scanner = new Scanner(expression);
		TreeHelper treeHelper = new TreeHelper();

		while (scanner.hasNext()) {
			final String token = scanner.next();
			treeHelper = evaluateToken(treeHelper, token, scanner);
		}

		scanner.close();
		return convert(treeHelper.getTree());
	}

	//CHECKSTYLE:OFF
	/**
	 * Evaluate token.
	 * 
	 * Mostly a convenience function, possibly used for multi-parsing complex
	 * tokens (not currently supported)
	 *
	 * @param base
	 *            the base
	 * @param token
	 *            the token
	 * @param scanner
	 *            the scanner
	 * @return the tree helper
	 */
	private TreeHelper evaluateToken(TreeHelper base, String token, Scanner scanner) {
		
		double value;
		Operator operator;
		if (token.charAt(0) == '(') {
			// Enter a new clause
			final Expression subExpression = parseSubExpression(scanner, token);
			base.buildTree(subExpression);

		} else if (!Double.isNaN((value = tryParseDouble(token)))) {
			// Found a number
			base.buildTree(builder.createNumberExpression(value));

		} else if ((operator = tryParseOperator(token)) != null) {

			if (operator instanceof BinaryOperator) {
				base.setPendingOperator((BinaryOperator) operator);
			} else if (operator instanceof UnaryOperator) {
				String strippedToken = token.substring(operator.toString().length());
				if (strippedToken.length() == 0) {
					strippedToken = scanner.next();
				}
				final Expression innerExpression = parseSubExpression(scanner, strippedToken);
				base.buildTree(new UnaryNode((UnaryOperator) operator, innerExpression));
			}

		} else {
			throw new IllegalArgumentException("Invalid Expression");
		}
	

		return base;
	}
	//CHECKSTYLE:ON
	

	/**
	 * Parses the sub expression.
	 *
	 * @param scanner
	 *            the scanner
	 * @param base
	 *            the base
	 * @return the expression
	 */
	private Expression parseSubExpression(Scanner scanner, String base) {
		final String subClause = buildSubClause(scanner, base);
		final Expression subExpression = parse(subClause);
		return subExpression;
	}

	/**
	 * Builds the sub clause.
	 * 
	 * The result should be the expression contained within a single set of
	 * parenthesis (with a single pair of parenthesis stripped)
	 *
	 * @param scanner
	 *            the scanner
	 * @param initial
	 *            the initial
	 * @return the string
	 */
	private String buildSubClause(Scanner scanner, String initial) {
		int parenthesisCount = countSymbol(initial, '(') - countSymbol(initial, ')');
		StringBuilder builder = new StringBuilder(initial);
		while (scanner.hasNext() && parenthesisCount > 0) {
			final String token = scanner.next();
			parenthesisCount += countSymbol(token, '(') - countSymbol(token, ')');
			builder.append(" ");
			builder.append(token);
		}

		if (parenthesisCount != 0) {
			throw new IllegalArgumentException("Missmatched parenthesis");
		}
		final String rawString = builder.toString();
		if (rawString.matches("\\(.*\\)")) {
			// Has Leading and trailing parenthesis, so drop them
			return rawString.substring(1, rawString.length() - 1);
		} else {
			return rawString; // Doesn't have leading parenthesis
		}

	}

	/**
	 * Count a symbol in a string
	 *
	 * @param text
	 *            the text
	 * @param symbol
	 *            the symbol
	 * @return the int
	 */
	int countSymbol(String text, char symbol) {
		int count = 0;
		for (int index = 0; index < text.length(); index++) {
			if (text.charAt(index) == symbol) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Try parse double. Returns NaN if the parameter is not a double
	 *
	 * @param possibleNumber
	 *            the possible number
	 * @return the double
	 */
	private double tryParseDouble(String possibleNumber) {
		try {
			return Double.parseDouble(possibleNumber);

		} catch (NumberFormatException e) {
			return Double.NaN;
		}
	}

	/**
	 * Try parse operator. Returns null if the parameter is not an operator.
	 * Will also match if the parameter starts with an operator (this is needed
	 * for fuzzy matching complex tokens in unary operators)
	 *
	 * @param possibleOperator
	 *            the possible operator
	 * @return the operator
	 */
	private Operator tryParseOperator(String possibleOperator) {
		for (Operator operator : operatorSet) {
			if (possibleOperator.startsWith(operator.toString())) {
				return operator;
			}
		}
		return null;
	}

	/**
	 * Used to build support for order of operations without contaminating the
	 * Expression interface
	 */
	private abstract class AbstractNode implements Expression {

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cmu.cs.cs214.hw2.expression.Expression#eval()
		 */
		@Override
		public double eval() {
			// This should never exist outside of the parser
			throw new RuntimeException("Parse Error");

		}
	}

	/**
	 * Used to build support for order of operations without contaminating the
	 * Expression interface
	 */
	private final class BinaryNode extends AbstractNode {

		/** The operator. */
		private final BinaryOperator operator;

		/** The children */
		private Expression left, right;

		/**
		 * Instantiates a new binary node.
		 *
		 * @param operator
		 *            the operator
		 * @param left
		 *            the left
		 * @param right
		 *            the right
		 */
		private BinaryNode(BinaryOperator operator, Expression left, Expression right) {
			this.operator = operator;
			this.left = left;
			this.right = right;
		}

	}

	/**
	 * Used to build support for order of operations without contaminating the
	 * Expression interface
	 */
	private final class UnaryNode extends AbstractNode {

		/** The operator. */
		private final UnaryOperator operator;

		/** The child. */
		private Expression child;

		/**
		 * Instantiates a new unary node.
		 *
		 * @param operator
		 *            the operator
		 * @param child
		 *            the child
		 */
		private UnaryNode(UnaryOperator operator, Expression child) {
			this.operator = operator;
			this.child = child;
		}

	}

	/**
	 * Removes any internal Parse Nodes from the Expression tree
	 *
	 * @param tree
	 *            the tree
	 * @return the expression
	 */
	private Expression convert(Expression tree) {
		if (tree instanceof BinaryNode) {
			final BinaryNode binaryNode = (BinaryNode) tree;
			return builder.createBinaryOperationExpression(binaryNode.operator, convert(binaryNode.left),
					convert(binaryNode.right));
		} else if (tree instanceof UnaryNode) {
			final UnaryNode unaryNode = (UnaryNode) tree;
			return builder.createUnaryOperationExpression(unaryNode.operator, convert(unaryNode.child));
		}

		return tree;
	}

}
