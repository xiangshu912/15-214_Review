package edu.cmu.cs.cs214.hw2.parser;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import edu.cmu.cs.cs214.hw2.operator.BinaryOperator;
import edu.cmu.cs.cs214.hw2.operator.Operator;
import edu.cmu.cs.cs214.hw2.operator.UnaryOperator;

/**
 * 
 * Validator - validates the String expression given before it is parsed. 
 * 
 * Note: YOU SHOULD NOT NEED TO MODIFY THIS CLASS.
 */
public class Validator {

	/** The pattern template. */
	private static final String PATTERN_TEMPLATE = "^((%1$s?[\\( ]*)*\\d+(\\.\\d+)?[\\) ]*)( %2$s ((%1$s?[\\( ]*)*\\d+(\\.\\d+)?[\\) ]*))*$";

	/** The pattern. */
	private final String pattern;

	/**
	 * Instantiates a new validator.
	 *
	 * @param operations
	 *            the operations
	 */
	public Validator(Set<Operator> operations) {
		Set<Operator> binaryOperators = operations.stream().filter(o -> o instanceof BinaryOperator)
				.collect(Collectors.toSet());
		Set<Operator> unaryOperators = operations.stream().filter(o -> o instanceof UnaryOperator)
				.collect(Collectors.toSet());
		final String binaryPattern = buildQuotedPattern(binaryOperators);
		final String unaryPattern = buildQuotedPattern(unaryOperators);

		this.pattern = String.format(PATTERN_TEMPLATE, unaryPattern, binaryPattern);

	}

	/**
	 * Validate an expression
	 *
	 * @param expression
	 *            the expression
	 * @return true, if successful
	 */
	public boolean validate(String expression) {
		return expression.matches(pattern);
	}

	/**
	 * Builds the quoted pattern.
	 *
	 * @param operators
	 *            the operators
	 * @return the string
	 */
	private String buildQuotedPattern(Set<Operator> operators) {
		if (operators.size() == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder("(");
		for (Operator operator : operators) {
			builder.append(Pattern.quote(operator.toString()));
			builder.append("|");
		}
		builder.deleteCharAt(builder.length() - 1); // delete trailing pipe
		builder.append(")");

		return builder.toString();

	}

}
