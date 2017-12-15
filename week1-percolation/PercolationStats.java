import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class PercolationStats {
	private double[] thresholds;

       	/**
	 * perform trials independent experiments on an n-by-n grid
	 */
	public PercolationStats(int n, int trials) {
		thresholds = new double[trials];
		if (n <= 0 || trials <= 0) {
			throw new java.lang.IllegalArgumentException("Grid size and trial size must both be > 0.");
		}
		for(int i = 0; i < trials; i++) {
			thresholds[i] = simulation(n);
		}
	}

	/**
	 * sample mean of percolation threshold
	 */
	public double mean() {
		return StdStats.mean(thresholds);
	}

	/**
	 * sample standard deviation of percolation threshold
	 */
	public double stddev() {
		return StdStats.stddev(thresholds);
	}

	/**
	 * // low  endpoint of 95% confidence interval
	 */
	public double confidenceLo() {
		return mean() - (1.96 * stddev() / Math.sqrt(thresholds.length));
	}

	/**
	 * high endpoint of 95% confidence interval
	 */
	public double confidenceHi() {
		return mean() + (1.96 * stddev() / Math.sqrt(thresholds.length));
	}

	private double simulation(int n) {
		int row, col;
		Percolation system= new Percolation(n);
		do {
			row = StdRandom.uniform(1, n + 1);
			col = StdRandom.uniform(1, n + 1);
			if (!system.isOpen(row, col)) {
				system.open(row, col);
			}
		} while (!system.percolates());
		return (double) system.numberOfOpenSites() / (n * n);
	}

	/**
	 * test client (described below)
	 */
	public static void main(String[] args) {
		PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		System.out.printf("mean                    = %d\n", Integer.parseInt(args[1]), ps.mean());
		System.out.printf("stddev                  = %f\n", ps.stddev());
		System.out.printf("95%% confidence interval = [%f, %f] \n", ps.confidenceLo(), ps.confidenceHi());
	}
}
