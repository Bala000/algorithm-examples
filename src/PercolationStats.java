import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.IllegalArgumentException;

/**
 * Created by bsampath on 3/5/17.
 */
public class PercolationStats {

    private Percolation[] percolations;
    private int trials;
    private int n;
    private double[] percolationThreshold;
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        this.trials=trials;
        this.n=n;
        this.percolationThreshold=new double[trials];
        percolations = new Percolation[trials];
        int i=0;
        for(Percolation percolation: percolations) {

            percolation = new Percolation(n);
            while(!percolation.percolates())
                percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));

            this.percolationThreshold[i] = (double)percolation.numberOfOpenSites()/((double)(n*n));
            ++i;



        }
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(percolationThreshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(percolationThreshold);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean()-((1.96*stddev())/Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean()+((1.96*stddev())/Math.sqrt(trials));
    }

    // test client (described below)
    public static void main(String[] args)
    {
        int n=Integer.parseInt(args[0]);
        int T=Integer.parseInt(args[1]);

        PercolationStats percolationStats= new PercolationStats(n,T);
        System.out.println("mean                    = "+percolationStats.mean());
        System.out.println("stddev                  = "+percolationStats.stddev());
        System.out.println("95% confidence interval = ["+percolationStats.confidenceLo()+", "+percolationStats.confidenceHi()+"]");
    }
}
