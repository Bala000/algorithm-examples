import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.IllegalArgumentException;

/**
 * Created by bsampath on 3/5/17.
 */
public class Percolation {

    private int sites[][];
    private int numberOfOpenSites;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    // create n-by-n grid, with all sites blocked
    public Percolation(int n)
    {
        numberOfOpenSites=0;
        if(n<=0)
            throw new IllegalArgumentException();
        this.sites=new int[n+1][n+1];

        weightedQuickUnionUF=new WeightedQuickUnionUF((n*n)+2);
        int j=(n*n)-1;
        for(int i=0;i<n;i++) {
            for(int h=0;h<n;h++)
            {
                //sites =1 means blocked and =0 means open
                this.sites[i+1][h+1]=1;
            }
            weightedQuickUnionUF.union(n * n,i );
            weightedQuickUnionUF.union((n*n)+1,j);
            --j;

        }
    }
    // open site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if(row<1 || col<1 || row>(sites.length-1) || col>(sites.length-1) )
            throw new java.lang.IndexOutOfBoundsException();
        if(!isOpen(row,col))
        {
            sites[row][col]=0;
            ++this.numberOfOpenSites;
            int siteToOpen=((sites.length-1)*(row-1))+col-1;

            if(!((row-1)<1 || col<1 || (row-1)>(sites.length-1) || (col)>(sites.length-1) ))
                if(isOpen(row-1,col))
                    weightedQuickUnionUF.union(siteToOpen,((sites.length-1)*(row-2))+col-1);
            if(!((row+1)<1 || col<1 || (row+1)>(sites.length-1) || (col)>(sites.length-1)))
                if(isOpen(row+1,col))
                    weightedQuickUnionUF.union(siteToOpen,((sites.length-1)*(row))+col-1);
            if(!(row<1 || (col-1)<1 || row>(sites.length-1) || (col-1)>(sites.length-1)))
                if(isOpen(row,col-1))
                    weightedQuickUnionUF.union(siteToOpen,((sites.length-1)*(row-1))+col-2);
            if(!(row<1 || (col+1)<1 || row>(sites.length-1) || (col+1)>(sites.length-1)))
                if(isOpen(row,col+1))
                weightedQuickUnionUF.union(siteToOpen,((sites.length-1)*(row-1))+col);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col){
        if(row<1 || col<1 || row>(sites.length-1) || col>(sites.length-1) )
            throw new java.lang.IndexOutOfBoundsException();
        if(sites[row][col]==1)
            return false;

        return true;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col){
        if(row<1 || col<1 || row>(sites.length-1) || col>(sites.length-1) )
            throw new java.lang.IndexOutOfBoundsException();
        if((!weightedQuickUnionUF.connected(((sites.length-1)*(row-1))+col-1,(sites.length-1)*(sites.length-1)))||sites[row][col]==1)
            return false;

        return true;
    }

    // number of open sites
    public     int numberOfOpenSites()
    {

        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates()
    {
        if (weightedQuickUnionUF.connected((sites.length-1)*(sites.length-1),((sites.length-1)*(sites.length-1))+1)) {
            return true;
        }

        return false;
    }

    // test client (optional)
    public static void main(String[] args)
    {

    }

}
