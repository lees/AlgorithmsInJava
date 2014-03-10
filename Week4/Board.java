import java.util.Stack;

public class Board {
    
    private int N;
    private int[] position;
    private int zeroPosition;

    public Board(int[][] blocks)
    {
        N = blocks.length;
        position = new int[N * N];
        int counter = 0;
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks[i].length; j++)
            {
                position[counter++] = blocks[i][j];
                if (blocks[i][j] == 0) zeroPosition = counter - 1;
            }
    }

    public int dimension()                 // board dimension N
    {
        return N;
    }

    public int hamming()                   // number of blocks out of place
    {
        int result = 0;
        for (int i = 0; i < position.length - 1; i++) 
            if (position[i] != i + 1)
                result++;
        return result;
    }

    private int posX(int index)
    {
        return index % N;
    }

    private int posY(int index)
    {
        return index / N;
    }

    private int finalPosition(int number)
    {
        if (number == 0) return position.length - 1;
        return number - 1;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int result = 0;
        for (int i = 0; i < position.length; i++)
        {
            if (position[i] == 0) continue;
            int finalPos = finalPosition(position[i]);
            int currentPos = i;
            result = result + Math.abs(posX(finalPos) - posX(currentPos));
            result = result + Math.abs(posY(finalPos) - posY(currentPos));
        }
        return result;
    }
    
    public boolean isGoal()                // is this board the goal board?
    {
        for (int i = 0; i < position.length - 1; i++) 
            if (position[i] != i + 1)
                return false;
        if (position[position.length - 1] != 0)
            return false;
        return true;
    }

    private void exch(int a, int b)
    {
        int temp = position[b];
        position[b] = position[a];
        position[a] = temp;
    }

    private Board getCopy()
    {
        int[][] blocks = new int[N][N];
        for (int i = 0; i < position.length; i++)
            blocks[i / N][i % N] = position[i];
        Board newBoard = new Board(blocks);
        return newBoard;
    }

    public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
    {
        Board newBoard = getCopy();
        if (newBoard.zeroPosition > 1)
            newBoard.exch(0, 1);
        else
            newBoard.exch(position.length - 1, position.length - 2);
        return newBoard;
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;

        if (N != that.N) return false;
        
        for (int i = 0; i < position.length; i++)
            if (position[i] != that.position[i]) return false;

        return true;
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Board newBoard;
        Stack<Board> result = new Stack<Board>();
        int zeroX = posX(zeroPosition);
        int zeroY = posY(zeroPosition);

        if (zeroX == 0)
        {
            newBoard = getCopy();
            newBoard.exch(zeroPosition, zeroPosition + 1);
            newBoard.zeroPosition++;
            result.push(newBoard);
        }
        else if (zeroX == N-1)
        {
            newBoard = getCopy();
            newBoard.exch(zeroPosition, zeroPosition - 1);
            newBoard.zeroPosition--;
            result.push(newBoard);
        }
        else
        {
            newBoard = getCopy();
            newBoard.exch(zeroPosition, zeroPosition + 1);
            newBoard.zeroPosition++;
            result.push(newBoard);
            newBoard = getCopy();
            newBoard.exch(zeroPosition, zeroPosition - 1);
            newBoard.zeroPosition--;
            result.push(newBoard);
        }

        if (zeroY == 0)
        {
            newBoard = getCopy();
            newBoard.exch(zeroPosition, zeroPosition + N);
            newBoard.zeroPosition = zeroPosition + N; 
            result.push(newBoard);
        }
        else if (zeroY == N - 1)
        {
            newBoard = getCopy();
            newBoard.exch(zeroPosition, zeroPosition - N);
            newBoard.zeroPosition = zeroPosition - N;
            result.push(newBoard);
        }
        else
        {
            newBoard = getCopy();
            newBoard.exch(zeroPosition, zeroPosition + N);
            newBoard.zeroPosition = zeroPosition + N;
            result.push(newBoard);
            newBoard = getCopy();
            newBoard.exch(zeroPosition, zeroPosition - N);
            newBoard.zeroPosition = zeroPosition - N;
            result.push(newBoard);
        }

        return result;
    }

    public String toString()               // string representation of the board (in the output format specified below)
    {
        // String result = "";
        // int counter = N;
        // result = result + Integer.toString(N);
        // for (int i = 0; i < position.length; i++) 
        // {
        //     if (i % N == 0)
        //         result = result + '\n';
        //     result = result + Integer.toString(position[i]);
        // }
        // return result;

        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < position.length; i++) 
        {
            if (i % N == 0)
                s.append("\n");
            s.append(String.format("%2d ", position[i]));
        }
        
        return s.toString();

    }
}