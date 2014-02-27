public class Percolation {
   
   private boolean [] states;
   private WeightedQuickUnionUF cellStorage_percolation;
   private WeightedQuickUnionUF cellStorage_isfull;
   private int N;


   public Percolation(int size)                 // create N-by-N grid, with all sites blocked
   {
         N = size;
         
         states = new boolean[N*N+2];
         states[N*N] = true;
         states[N*N+1] = true;
         for (int i = 0; i < N*N; i++)
            states[i] = false;

         cellStorage_percolation = new WeightedQuickUnionUF(N*N+2);
         cellStorage_isfull = new WeightedQuickUnionUF(N*N+1);

   }

   private void connectCells(int i1, int j1, int i2, int j2)
   {
         if (i1 < 1 || i1 > N || j1 < 1 || j1 > N) return;
         if (!isOpen(i1, j1)) return;
         
         if (i2 < 1 || i2 > N || j2 < 1 || j2 > N) return;
         if (!isOpen(i2, j2)) return;

         cellStorage_percolation.union(getIndex(i1, j1), getIndex(i2, j2));
         cellStorage_isfull.union(getIndex(i1, j1), getIndex(i2, j2));

   }
   
   public void open(int i, int j)         // open site (row i, column j) if it is not already
   {
         
         if (isOpen(i, j)) return;

         states[getIndex(i, j)] = true;
         connectCells(i, j, i-1, j);
         connectCells(i, j, i+1, j);
         connectCells(i, j, i, j-1);
         connectCells(i, j, i, j+1);

         if (i == 1) 
            {
               cellStorage_percolation.union(getIndex(i, j), N*N);
               cellStorage_isfull.union(getIndex(i, j), N*N);
            }
            
         if (i == N) cellStorage_percolation.union(getIndex(i, j), N*N+1);
         
   }

   private int getIndex(int i, int j)
   {
         return N*(i-1)+j-1;
   }

   public boolean isOpen(int i, int j)    // is site (row i, column j) open?
   {
         if (i <= 0 || i > N || j <= 0 || j > N)
            throw new IndexOutOfBoundsException("Illegal parameter value."); 

         return states[getIndex(i, j)];
   }

   public boolean isFull(int i, int j)    // is site (row i, column j) full?
   {
         if (i <= 0 || i > N || j <= 0 || j > N)
            throw new IndexOutOfBoundsException("Illegal parameter value."); 

         return cellStorage_isfull.connected(getIndex(i, j), N*N);
   }
   
   public boolean percolates()            // does the system percolate?
   {
         return cellStorage_percolation.connected(N*N, N*N+1);
   }


   public static void main(String[] args) 
   {

         Percolation p = new Percolation(3);
         StdOut.println(p.percolates());
         StdOut.println(p.isOpen(1, 1));
         StdOut.println(p.isFull(1, 1));
         p.open(1, 1);
         StdOut.println(p.percolates());
         StdOut.println(p.isOpen(1, 1));
         StdOut.println(p.isFull(1, 1));

         p.open(2, 1);
         p.open(3, 1);

         StdOut.println(p.percolates());
      
   }

}