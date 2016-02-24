package edu.cmu.cs.cs214.hw3;


import static org.junit.Assert.assertEquals;

import java.awt.List;
import java.util.ArrayList;


import org.junit.Before;
import org.junit.Test;

import jdk.nashorn.internal.runtime.BitVector;

/**
 * test the the cryptarithm solver class
 * 
 */
public class TestCryptarithm {
	private Permutation<Integer> permutation;
	private Integer[] permuList;
	
	/**
	 * call before each test
	 */
	@Before
	public void setUp() {
		permuList = new Integer[]{1,2,3};
		permutation = new Permutation<Integer>(permuList);
		
	}
	
	/**
	 * test the permutation generator
	 */
	@Test
	public void testPermutation() {
		permutation.generator(permuList, permuList.length);
	}
	
	/**
	 * test the cryptarithm class
	 */
	@Test
	public void testCryptarithm() {
		Cryptarithm cryptarithm = new Cryptarithm("A + B = C", permuList);
		assertEquals(cryptarithm.getCharList(), [A, B, C]);
	}
	
	/**
	 * test the Cryptarithm solver
	 */
	@Test
	public void testCryptarithmSolver() {
		CryptarithmSolver solver = new CryptarithmSolver("A + B = C");
		Cryptarithm cryptarithm = new Cryptarithm("A + B = C", permuList);
		solver.Solve(permuList.length);	
	}
}
