package edu.cmu.cs.cs214.hw2.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import edu.cmu.cs.cs214.hw2.operator.Operator;

/**
 * Main - creates and runs a new Calculator GUI. 
 *
 */
public class Main {

	/**
	 * Runs the calculator GUI.
	 * 
	 * @param args arguments to the main function 
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> createAndShowSetupScreen());
	}
	
	private static void createAndShowSetupScreen() {
		List<Operator> operators = new ArrayList<>();		
		Calculator calculator = new Calculator(operators);
		calculator.launch();
	}

}
