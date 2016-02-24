package edu.cmu.cs.cs214.hw2.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//import edu.cmu.cs.cs214.hw2.operator.Absolute;
//import edu.cmu.cs.cs214.hw2.operator.Addition;
import edu.cmu.cs.cs214.hw2.operator.BinaryOperator;
import edu.cmu.cs.cs214.hw2.operator.BinaryOperators;
import edu.cmu.cs.cs214.hw2.operator.Operator;
import edu.cmu.cs.cs214.hw2.operator.UnaryOperator;
import edu.cmu.cs.cs214.hw2.operator.UnaryOperators;

/**
 * Calculator - a JPanel containing a GUI for a calculator. 
 * 
 * Note: YOU SHOULD NOT NEED TO MODIFY THIS CLASS.
 *
 */
public class Calculator extends JPanel {

	//CHECKSTYLE:OFF
	private List<Operator> supportedOperatorList;
	private static final int WIDTH = 500;
	private static final int HEIGHT = 250;

	private JLabel runningValueDisplay;
	private double runningValue;
	private BinaryOperator selectedOperator;
	private double newValue;
	private boolean enteringNumber;

	/**
	 * Constructor for the Calculator class.
	 * 
	 * @param supportedOperatorList a list of operators to be added to the calculator
	 */
	public Calculator(List<Operator> supportedOperatorList) {
		this.supportedOperatorList = supportedOperatorList;
		for (Operator biOp:BinaryOperators.values()) {
			supportedOperatorList.add(biOp);
		}
		for (Operator uOp:UnaryOperators.values()) {
			supportedOperatorList.add(uOp);
		}
	}

	/**
	 * A method used to run the GUI.
	 * 
	 */
	public void launch() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		this.init();
		frame.add(this);
		frame.setVisible(true);

	}

	private void init() {
		JPanel panel = new JPanel(new GridBagLayout());

		JPanel displayPanel = setupDisplay();
		GridBagConstraints displayConstraint = new GridBagConstraints();
		displayConstraint.fill = GridBagConstraints.HORIZONTAL;
		displayConstraint.gridx = 0;
		displayConstraint.gridy = 0;
		displayConstraint.gridwidth = 2;
		panel.add(displayPanel, displayConstraint);

		JPanel numberPanel = setupNumberPad();
		GridBagConstraints numberConstraint = new GridBagConstraints();
		numberConstraint.gridx = 0;
		numberConstraint.gridy = 1;
		panel.add(numberPanel, numberConstraint);

		JPanel operatorPanel = setupOperators();
		GridBagConstraints operatorConstraint = new GridBagConstraints();
		operatorConstraint.gridx = 1;
		operatorConstraint.gridy = 1;
		panel.add(operatorPanel, operatorConstraint);
		add(panel);
		this.setDisplay(0.0); // Reset to zero on launch

	}

	private JPanel setupDisplay() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel display = new JLabel("###");
		this.runningValueDisplay = display;
		panel.add(display, BorderLayout.EAST);
		return panel;
	}

	private JPanel setupNumberPad() {
		JPanel numberPanel = new JPanel(new GridLayout(4, 3));
		layoutNumberRange(numberPanel, 7, 9);
		layoutNumberRange(numberPanel, 4, 6);
		layoutNumberRange(numberPanel, 1, 3);
		numberPanel.add(new JLabel()); // Offset the Zero
		layoutNumberRange(numberPanel, 0, 0);
		return numberPanel;

	}

	private void layoutNumberRange(JPanel panel, int lower, int upper) {
		for (int number = lower; number <= upper; number++) {
			final int finalNumber = number;
			JButton button = new JButton(String.valueOf(number));
			button.addActionListener((e) -> numberPressed(finalNumber));
			panel.add(button);
		}
	}

	private JPanel setupOperators() {
		JPanel operatorPanel = new JPanel(new GridLayout(0, 3));
		
		for (Operator operator : supportedOperatorList) {
			final Operator finalOperator = operator;
			JButton button = new JButton(finalOperator.toString());
			// Need to do something fancy with the sub-types
			if (operator instanceof UnaryOperator) {
				button.addActionListener((e) -> applyUnaryOperator((UnaryOperator) finalOperator));
			} else if (operator instanceof BinaryOperator) {
				button.addActionListener((e) -> setSelectedOperator((BinaryOperator) finalOperator));
			}
			operatorPanel.add(button);
		}
		JButton equalsButton = new JButton("=");
		equalsButton.addActionListener((e) -> calculate());
		operatorPanel.add(equalsButton);
		return operatorPanel;
	}

	private void setDisplay(double value) {
		this.runningValueDisplay.setText(String.valueOf(value));
	}

	private void setSelectedOperator(BinaryOperator binaryOperator) {

		if(this.selectedOperator != null) {
			calculate();
		}
		endNumberEntry();
		this.selectedOperator = binaryOperator;
	}
	
	private void endNumberEntry() {
		if(this.enteringNumber) {
			this.runningValue = this.newValue;
			this.enteringNumber = false;
		}
	}

	private void numberPressed(int number) {
		
		if(this.enteringNumber) {
			this.newValue = this.newValue * 10 + number;
		} else {
			this.enteringNumber = true;
			this.newValue = number;
		}
		this.setDisplay(this.newValue);

	}

	private void calculate() {
		
		
		// Don't need to do anything if the user didn't enter a new number
		if (this.enteringNumber) {
			if(this.selectedOperator != null) {
				this.runningValue = this.selectedOperator.apply(this.runningValue, this.newValue);
			} else {
				this.runningValue = this.newValue;
			}
			
			setDisplay(this.runningValue);
		}
		
		// Cancel any pending operator (possible weird edge case)
		this.selectedOperator = null;
		this.enteringNumber = false;

	}

	private void applyUnaryOperator(UnaryOperator unaryOperator) {
		endNumberEntry();
		this.runningValue = unaryOperator.apply(this.runningValue);
		setDisplay(this.runningValue);
	}

}
