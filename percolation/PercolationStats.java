import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
    
public class PercolationStats {
   private double[] count;
   private double mean;
   private double stddev;
   private int trials;

   // perform trials independent experiments on an n-by-n grid
   public PercolationStats(int n, int trials) {
       if ((n <= 0) || (trials <= 0)) {
           throw new IllegalArgumentException();
       }
       
       this.count = new double[trials];
       this.trials = trials;
       for (int i = 0; i < trials; i++) {
           Percolation test = new Percolation(n);
           int counter = 0;
           
           while(!test.percolates()) {
               int rand = StdRandom.uniform(0, n*n);
               int row = rand/n;
               int col = rand % n;
               if (!test.isOpen(row+1, col+1)) {    
                   test.open(row+1, col+1);
                   counter += 1;
               }
           }                    
           count[i] = (double) counter/(double) (n*n);
       }
       this.mean = StdStats.mean(this.count);
       this.stddev = StdStats.stddev(this.count);
       
   }
   // sample mean of percolation threshold
   public double mean() {
       return this.mean;
   }
   // sample standard deviation of percolation threshold
   public double stddev() {
       return this.stddev;
   }
   // low  endpoint of 95% confidence interval
   public double confidenceLo()  {
       return this.mean-(1.96*this.stddev)/Math.sqrt(this.trials);
   }
   // high endpoint of 95% confidence interval
   public double confidenceHi() {
       return this.mean+(1.96*this.stddev)/Math.sqrt(this.trials);       
   }
   // test client (described below)
   public static void main(String[] args) {
       int n =  Integer.parseInt(args[0]);
       int trials =  Integer.parseInt(args[1]);
       PercolationStats test = new PercolationStats(n, trials);
       System.out.println("mean                    = " +test.mean());
       System.out.println("stddev                  = " +test.stddev());
       System.out.println("95% confidence interval = "+test.confidenceLo()
                              +", "+test.confidenceHi());
   }
}