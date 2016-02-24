package edu.cmu.cs.cs214.hw3;

import java.util.ArrayList;

/**
 * Cryptarithm Solver class
 * 
 */

public class CryptarithmSolver{
	
	private String stringExp;
	private final int n = (int) Math.pow(2, 10);
	private Integer[] numList;
	private Integer[] onePermutation;
	private ArrayList<Integer[]> assignment;
	
	/**
	 * The CryptarithmSolver constructor
	 * @param str the string of cryptarithm
	 */
	public CryptarithmSolver(String str) {
		this.stringExp = str;
	}
	
	/**
	 * get the expression element
	 * @param exp the string of cryptarithm
	 * @return the expression element
	 */
	public String[] getExpValue(String exp) {
		String[] result = exp.split(" ");
		return result;
	}
	
	/**
	 * Solve one cryptarithm with one number permutation.
	 * @param k the k out of n numbers
	 */
	public void solve(int k) {
		for (int bitVec = 0; bitVec < 1 << n; bitVec++) {
			if (Integer.bitCount(bitVec) == k) {
				int position = 0;
				int num = 0;
				while (bitVec >= 1) {
					if (bitVec % 2 == 1) {
						numList[position] = num;
						position += 1;
					}
					num += 1;
					bitVec /= 2;
				}
			}
			Cryptarithm cryptarithm = new Cryptarithm(stringExp, numList);
			Permutation<Integer> permu = new Permutation<Integer>(numList);
			permu.generator(numList, numList.length);
			
			System.out.println(onePermutation);
			
			String left = cryptarithm.getLeftExp();
			String right = cryptarithm.getRightExp();
			if (cryptarithm.parse(left) == cryptarithm.parse(right)) {
				assignment.add(numList);
			}
		}
	}

}
