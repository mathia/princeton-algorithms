import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation
 * @ param size the size n of the n x n grid to be used.
 *
*/
public class Percolation {
    private final int size;
    private WeightedQuickUnionUF grid;
    private boolean[] openSites;
    private int openSiteCount = 0;
    private final int virtualTopIndex, virtualBottomIndex;

    /**
     *  create an n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid grid size.  Must be greater than zero.");
        }
        size = n;
        grid = new WeightedQuickUnionUF(size * size + 2);  // n x n grid plus 2 sites for the virtual top and bottom.  grid[0] is the virtual top and grid[size + 1] is the virtual bottom.
        virtualTopIndex = 0;
        virtualBottomIndex = size * size + 1;
        openSites = new boolean[size * size + 1];  // Adding and additional element to allow the index of the first site to be 1 as  is done with grid.
    }

    /**
     * open site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        validateRowCol(row, col);
        if (!isOpen(row, col)) {
            openSites[rowColToIndex(row, col)] = true;
            openSiteCount++;
            // If in the top row, connect to the virtual top
            if (row == 1) {
                grid.union(virtualTopIndex, rowColToIndex(row, col));
            }
            // If in the bottom row, connect to the virtual bottom
            if (row == size) {
                grid.union(virtualBottomIndex, rowColToIndex(row, col));
            }
            // Connect to any adjacent open sites
            if (row > 1 && isOpen(row - 1, col)) {
                grid.union(rowColToIndex(row, col), rowColToIndex(row - 1, col));
            }
            if (row < size && isOpen(row + 1, col)) {
                grid.union(rowColToIndex(row, col), rowColToIndex(row + 1, col));
            }
            if (col > 1 && isOpen(row, col - 1)) {
                grid.union(rowColToIndex(row, col), rowColToIndex(row, col - 1));
            }
            if (col < size && isOpen(row, col + 1)) {
                grid.union(rowColToIndex(row, col), rowColToIndex(row, col + 1));
            }
        }
    }
    
    /**
     * Returns true if the site is open
     */
    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        validateRowCol(row, col);
        return openSites[rowColToIndex(row, col)];
    }
    
    public boolean isFull(int row, int col) { // is site (row, col) full?
        return grid.connected(virtualTopIndex, rowColToIndex(row, col));
    }
    
    /**
     * Returns the number of open sites in the grid.
     */
    public int numberOfOpenSites() { // number of open sites
        return openSiteCount;
    }
    
    public boolean percolates() { // does the system percolate?
        return grid.connected(virtualTopIndex, virtualBottomIndex);
    }

    /**
     * Throws an exception of the given row & column are not for a valid site in the grid.
     */
    private void validateRowCol(int row, int col) {
        if (row > size || col > size || row < 1 || col < 1) {
            throw new IndexOutOfBoundsException("Invalid input: Index out of bounds.");
        }
    }

    /**
     * Converts 2-D row and column coordinates to 1-D index
     */
    private int rowColToIndex(int row, int col) {
        validateRowCol(row, col);
        return (row - 1) * size + col;
    }
    
    public static void main(String[] args) {  
        System.out.println("Creating a 5x5 test grid...");
        Percolation test = new Percolation(5);
        System.out.println("Trying to open site (1, 2)...");
        test.open(1, 2);
        System.out.println("Trying to open site (6, 2)...");
        try {
            test.open(6, 2);
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("I don't think so!");
        }
        System.out.println("Checking if (1, 2) is open...");
        if (test.isOpen(1, 2)) {
            System.out.println("It is open!");
        }
        else {
            System.out.println("It is not open!");
        }
        System.out.println("Checking if (2, 1) is open...");
        if (test.isOpen(2, 1)) {
            System.out.println("It is open!");
        }
        else {
            System.out.println("It is not open!");
        }
        System.out.format("Total number of open sites: %d\n", test.numberOfOpenSites());
    }
}
