public class Percolation {
    private static final int SITE_BLOCKED_STATE = 0;
    private static final int SITE_OPEN_STATE = 1;
    private static final int SITE_FULL_STATE = 2;
    
    private WeightedQuickUnionUF wqu;
    private int[][] matrix;
    private int maxSide;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        maxSide = N;
        wqu = new WeightedQuickUnionUF(maxSide * maxSide + 2);
        matrix = new int[maxSide][maxSide];
        for (int i = 0; i < maxSide; ++i) {
            for (int j = 0; j < maxSide; ++j) {
                matrix[i][j] = SITE_BLOCKED_STATE;
            }
        }
    }
    
    public static void main(String[] args) {
        
    }
    
    public void open(int i, int j) {
        if (i < 1 || i > maxSide || j < 1 || j > maxSide) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int row = i - 1; // cause array begin from 0
        int column = j - 1;
        matrix[row][column] = SITE_OPEN_STATE;
        connectToNeighborsIfOpen(i, j);
    }
    
    public boolean isOpen(int i, int j) {   // is site (row i, column j) open?
        if (i < 1 || i > maxSide || j < 1 || j > maxSide) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return isFull(i, j) || matrix[i - 1][j - 1] == SITE_OPEN_STATE;
    }
    
    public boolean isFull(int i, int j) {    // is site (row i, column j) full?
        if (i < 1 || i > maxSide || j < 1 || j > maxSide) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return matrix[i - 1][j - 1] == SITE_FULL_STATE;
    }
    
    public boolean percolates() {
        
        return wqu.connected(0, maxSide * maxSide + 1);
    }
    
    private void connectToNeighborsIfOpen(int i, int j) {
        // left
        if (j != 1 && isOpen(i, j - 1)) {
            wqu.union(getInnerRepresentation(i, j),
                      getInnerRepresentation(i, j - 1));
        }
        // top
        if (i != 1 && isOpen(i - 1, j)) {
            wqu.union(getInnerRepresentation(i, j),
                      getInnerRepresentation(i - 1, j));
        }
        // right
        if (j != maxSide && isOpen(i, j + 1)) {
            wqu.union(getInnerRepresentation(i, j),
                      getInnerRepresentation(i, j + 1));
        }
        // bottom
        if (i != maxSide && isOpen(i + 1, j)) {
            wqu.union(getInnerRepresentation(i, j),
                      getInnerRepresentation(i + 1, j));
        }
        
        if (i == 1) {
            wqu.union(getInnerRepresentation(i, j), 0);
        }
        
        if (i == maxSide && !percolates()) {
            wqu.union(getInnerRepresentation(i, j), maxSide * maxSide + 1);
        }
        
        checkRecursiveIfSiteIsFull(i, j, false);
    }
    
    private void checkRecursiveIfSiteIsFull(int i, int j,
                                            boolean recConnectedCall) {
        if (i == 0 || j == 0 || i == maxSide + 1
            || j == maxSide + 1 || !isOpen(i, j) || isFull(i, j)) {
            return;
        }
        if (recConnectedCall 
            || wqu.connected(0, getInnerRepresentation(i, j))) {
            boolean isFullSiteNear = (i == 1);
            if ((j != 1 && isFull(i, j - 1)) 
                || (i != maxSide && isFull(i + 1, j))
                || (j != maxSide && isFull(i, j + 1))
                || (i != 1 && isFull(i - 1, j))
               ) {
                isFullSiteNear = true;
            }
            if (isFullSiteNear) {
                int row = i - 1; // cause array begin from 0
                int column = j - 1;
                matrix[row][column] = SITE_FULL_STATE;
                checkRecursiveIfSiteIsFull(i, j - 1, true); //left
                checkRecursiveIfSiteIsFull(i - 1, j, true); //top
                checkRecursiveIfSiteIsFull(i, j + 1, true); //right
                checkRecursiveIfSiteIsFull(i + 1, j, true); //bottom
            }
        }
    }
    
    // get number of element in WeightedQuickUnionUF class
    private int getInnerRepresentation(int i, int j) {
        return j + (i - 1) * maxSide; // 0 is for top virtual element
    }
}