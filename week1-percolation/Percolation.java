import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation
 * @ param size the size n of the n x n grid to be used.
 *
*/
public class Percolation {
	private int size;
	private boolean[] openSites;
	private int openSiteCount = 0;

	/**
	 *  create an n-by-n grid, with all sites blocked
	 */
	public Percolation(int n) {
		size = n;
		openSites = new boolean[size + 2];
	}

	/**
	 * open site (row, col) if it is not open already
      	 */
	private void open (int row, int col) {
		validateRowCol(row, col);
		openSites[rowColToIndex(row, col)] = true;
		openSiteCount++;
	}
    
	/**
	 * Returns true if the site is open
	 */
       	public boolean isOpen(int row, int col) {  // is site (row, col) open?
		validateRowCol(row, col);
		return openSites[rowColToIndex(row, col)];
	}
	
	public boolean isFull(int row, int col) { // is site (row, col) full?
		return true;
	}
	
	/**
	 * Returns the number of open sites in the grid.
	 */
	public int numberOfOpenSites() { // number of open sites
		return openSiteCount;
	}
	
	public boolean percolates() { // does the system percolate?
		return true;
	}

	/**
	 * Throws an exception of the given row & column are not for a valid site in the grid.
	 */
	private void validateRowCol(int row, int col) {
		if (row > size || col > size || row < 1 || col < 1) {
			throw new IndexOutOfBoundsException("Invalid input: Row index out of bounds.");
		}
	}

	/**
	 * Converts 2-D row and column coordinates to 1-D index
	 */
	private int rowColToIndex(int row, int col) {
		validateRowCol(row, col);
		return (row - 1) * size + col;
	}
	
	public static void main(String[] args) {  //test client
		System.out.println("Creating a 5x5 test grid...");
		Percolation Test = new Percolation(5);
		System.out.println("Trying to open site (1,2)...");
		Test.open(1,2);
		System.out.println("Trying to open site (6,2)...");
		try {
			Test.open(6,2);
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("I don't think so!");
		}
		System.out.println("Checking if (1,2) is open...");
		if (Test.isOpen(1,2)) {
			System.out.println("It is open!");
		}
		else {
			System.out.println("It is not open!");
		}
		System.out.println("Checking if (2,1) is open...");
		if (Test.isOpen(2,1)) {
			System.out.println("It is open!");
		}
		else {
			System.out.println("It is not open!");
		}
		System.out.format("Total number of open sites: %d\n", Test.numberOfOpenSites());
	}
}
