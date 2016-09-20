import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private WeightedQuickUnionUF map;
   private WeightedQuickUnionUF full;
   private boolean[][] openSite;
   private int n;

   // create n-by-n grid, with all sites blocked
   public Percolation(int n) {
       if (n <= 0) {
           throw new IllegalArgumentException();
       }
       
       int num = n*n+2;
       this.n = n;
       this.map = new WeightedQuickUnionUF(num);
       this.full = new WeightedQuickUnionUF(num);
       for (int i = 0; i < n; i++) {
           map.union(0, i+1);
           map.union(num-1, num-2-i);
           
           full.union(0, i+1);
       }
       openSite = new boolean[n][n];
   }
   private void testRange(int i, int j, int range) {
       if ((i > range) || (i < 1)) {
           throw new IndexOutOfBoundsException("i out of range");
       }
           
       if ((j > range) || (j < 1)) {
           throw new IndexOutOfBoundsException("j out of range");
       }
   }
   // open site (row i, column j) if it is not open already
   public void open(int i, int j) {
       testRange(i, j, this.n);
       j = j-1;
       i = i-1;
       if (!openSite[i][j]) {
           openSite[i][j] = true;
       }
       // left
       if ((j-1 >= 0) && (openSite[i][j-1])) {
           map.union((i*n+j+1), (i*n+(j-1)+1));
           full.union((i*n+j+1), (i*n+(j-1)+1));
       }
       // right
       if ((j+1 <= n-1) && (openSite[i][j+1])) {
           map.union((i*n+j+1), (i*n+(j+1)+1));
           full.union((i*n+j+1), (i*n+(j+1)+1));
       }
       // up
       if ((i-1 >= 0) && (openSite[i-1][j])) {
           map.union((i*n+j+1), ((i-1)*n+j+1));
           full.union((i*n+j+1), ((i-1)*n+j+1));
       }
       // down
       if ((i+1 <= n-1) && (openSite[i+1][j])) {
           map.union((i*n+j+1), ((i+1)*n+j+1));
           full.union((i*n+j+1), ((i+1)*n+j+1));
       }
   }
   // is site (row i, column j) open?
   public boolean isOpen(int i, int j) {
       testRange(i, j, this.n);
       return openSite[i-1][j-1];
   } 
   // is site (row i, column j) full?
   public boolean isFull(int i, int j) {
       testRange(i, j, this.n);
       return full.connected(0, (i-1)*n+(j-1)+1) && isOpen(i, j);
   } 
   // does the system percolate?    
   public boolean percolates() {
       if (this.n == 1) {
           return isOpen(1, 1);
       }
       return map.connected(0, n*n+1);
   }
    // test client (optional)
   public static void main(String[] args) { 
       Percolation test = new Percolation(50);
       System.out.println(test.isOpen(1, 1));
       System.out.println(test.percolates());
       System.out.println(test.isFull(1, 1));
   }
}