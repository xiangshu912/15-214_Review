/**
 * 
 */
package edu.cmu.cs.cs214.hw2.parser;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import edu.cmu.cs.cs214.hw2.expression.Expression;
import edu.cmu.cs.cs214.hw2.operator.BinaryOperators;
import edu.cmu.cs.cs214.hw2.operator.Operator;
import edu.cmu.cs.cs214.hw2.operator.UnaryOperators;

/**
 * CommandLineParser - a command line calculator.
 * 
 * You will need to add any new Operators you create to the 
 * operatorSet or they will not be usable in the command line 
 * calculator.
 *
 */
public class CommandLineParser {
	
	/**
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		
		Set<Operator> operatorSet = new HashSet<Operator>();
		
		for (Operator biOp:BinaryOperators.values()) {
			operatorSet.add(biOp);
		}
		for (Operator uOp:UnaryOperators.values()) {
			operatorSet.add(uOp);
		}
		
		ExpressionParser parser = new ExpressionParser(operatorSet, new ExpressionMaker());
		
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("Enter an expression");
			String expression = scanner.nextLine();
			try {
				Expression exp = parser.parse(expression);
				System.out.println("Result: " + exp.eval());
			} catch (Exception e) {
				System.out.println("Input format not accepted. Please try again." );
			}
		} while(true);

		
	}

}
