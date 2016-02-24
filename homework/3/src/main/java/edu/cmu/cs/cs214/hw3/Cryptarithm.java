package edu.cmu.cs.cs214.hw3;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Cryptarithm - a class to process the Crytarithm.
 *
 */
public class Cryptarithm {
	
	private String expression;
	private ArrayList<Character> charList;
	private HashMap<Character, Integer> charAndNum;
	private String leftExp;
	private String rightExp;
	
	/**
	 * Cryptarithm constructor
	 * @param inputExp the input expression of cryptarithm
	 * @param numComb the array contains k out of n number combination
	 */
	public Cryptarithm(String inputExp, Integer[] numComb) {
		this.expression = inputExp;
		this.charList = new ArrayList<>();
		if (check(expression)) {
			setCharList();
		}
		this.charAndNum = new HashMap<>();
		setMapping(numComb);
		this.leftExp = inputExp.split("=")[0].trim();
		this.rightExp = inputExp.split("=")[1].trim();
	}
	
	/**
	 * 
	 * @param inputExp check whether the input Cryptarithm is valid.
	 * @return ture if valid or false
	 */
	public boolean check(String inputExp) {
		// check "="
		int k = inputExp.indexOf( '=' );
		if (k < 0) throw new IllegalArgumentException("The cryptarithm should contain '=' sign.");
		// check valid variable expression
		ArrayList<Character> expElement = new ArrayList<>();
		for (int i = 0, n = expression.length(); i < n; i++) {
			char c = expression.toUpperCase().charAt(i);
			if (c >= 'A' && c <= 'Z') {
		    	expElement.add(c);
		    // check valid operator
		    } else if (c == '+' || c == '-' || c == '=' ||
		    				c == '*' || c == '/' || c == ' ') {
		    	continue;
		    } else {
		    	return false;
		    }
		    // check valid expression
		    if (expElement.size() > 10) {
		    	return false;
		    }
		} 
		return true;
	}
	
	/**
	 * set the character list of the input cryptarithm
	 */
	public void setCharList() {
		for (int i = 0, n = expression.length(); i < n; i++) {
			char c = expression.toUpperCase().charAt(i);
			if (c >= 'A' && c <= 'Z' && !charList.contains(c)) {
				charList.add(c);
			}
		}
	}
	
	/**
	 * get the character list of the input cryptarithm
	 * @return ArrayList of the character list
	 */
	public ArrayList<Character> getCharList() {
		return charList;
	}
	
	/**
	 * combine the charList and the number combination into a HashMap.
	 * @param numComb the number combination
	 */
	public void setMapping(Integer[] numComb) {
		for (int i = 0; i < charList.size(); i++) {
			charAndNum.put(charList.get(i), numComb[i]);
		}
	}
	
	/**
	 * get the Hashmap of the charList and the number combination
	 * @return the Hashmap of the charList and the number combination
	 */
	public HashMap<Character, Integer> getMapping() {
		return charAndNum;
	}
	
	/**
	 * get the left expression of the cryptarithm
	 * @return the left expression
	 */
	public String getLeftExp() {
		return leftExp;
	}
	
	/**
	 * get the right expression of the cryptarithm
	 * @return the right expression
	 */
	public String getRightExp() {
		return rightExp;
	}
	
	/**
	 * set each word in cryptarithm as variable expression
	 * @param exp the word in cryptarithm
	 * @return the variable expression
	 */
	public VariableExpression setVariableExp(String exp) {
		VariableExpression wordExp = new VariableExpression(exp);
		String value = "";
		for (int i = 0, n = exp.length(); i < n; i++) {
		    char c = exp.charAt(i);
		    value += Integer.toString(charAndNum.get(c));
		}
		wordExp.store(Integer.parseInt(value));
		return wordExp;
	}
	
	/**
	 * get the value of the variable expression
	 * @param exp the expression of the cryptarithm
	 * @return the value of the expression
	 */
	public int parse(String exp) {
		int result = 0;
		String[] expression = exp.split(" ");
		result += setVariableExp(expression[0]).eval();
		for (int i = 2; i < expression.length;) {
			switch(expression[i-1])
			{
				case "+":
					result += setVariableExp(expression[i]).eval();
					break;
				case "-":
					result -= setVariableExp(expression[i]).eval();
					break;
				case "*":
					result *= setVariableExp(expression[i]).eval();
					break;
				case "/":
					result /= setVariableExp(expression[i]).eval();
					break;
				default:
					System.out.println("Invalid Operator.");		
			}
		}
		return result;
	}

}
