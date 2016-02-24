package edu.cmu.cs.cs214.hw3;


/**
 * The Permutation class generate the permutation of one 
 * Array with generic type.
 * @param <T> the generic type
 */
public class Permutation<T> implements AbstractPermu{
	 
	private T[] permuList;
	private AbstractPermu absPermu;
	
	/**
	 * the Permutation constructor
	 * @param arr the input array
	 */
	public Permutation(T[] arr) {
		this.permuList = arr;
	}
	
	/**
	 * the permutation generator with heap's algorithm
	 * @param arr the input array 
	 * @param n the length of the array
	 */
	public void generator(T[] arr, int n) {		
		// heap's algorithm
		if (n == 1) {
			this.permuList = arr;
			
 		} else {
			for (int i = 0; i < n; i++) {
				generator(arr, n - 1);
				if (n % 2 == 0) {
					swap(arr, i, n-1);
				} else {
					swap(arr, 0, n-1);
				}
			}
		}
	}
	
	/**
	 * swap two element in the array.
	 * @param arr the input array
	 * @param i the swap element with index i
	 * @param j the swap element with index j
	 */
	public void swap(T[] arr, int i, int j) {
		T temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	@Override
	public T[] getOnePermutation() {
		return permuList;
	}

}
