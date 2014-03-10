import java.util.Stack;
import java.util.LinkedList;

public class Solver 
{
    private boolean isSolvable;
    private Node finishNode;

    private class Node implements Comparable<Node>
    {
        private Node parent;
        private int priority;
        public int moves;
        public Board board;

        public Node(Node parentIn, Board boardIn)
        {
            parent = parentIn;
            if (parent == null)
                moves = 0;
            else
                moves = parent.moves + 1;

            board = boardIn;
            priority = board.manhattan();
        }

        public int priority()
        { return priority + moves; }

        public int compareTo(Node that)
        {
            return Integer.compare(priority(), that.priority());
        }
    }

    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {
        Node currentNode;
        MinPQ<Node> queue = new MinPQ<Node>();
        queue.insert(new Node(null, initial));
        MinPQ<Node> queueTwins = new MinPQ<Node>();
        queueTwins.insert(new Node(null, initial.twin()));

        int steps = 0;

        while (true)
        {
            currentNode = queue.delMin();

            //StdOut.println("Step "); StdOut.print(steps++);
            //StdOut.println("Get out nod with priority ");
            //StdOut.print(currentNode.priority());
            //StdOut.println(currentNode.board);

            if (currentNode.board.isGoal())
            {
                isSolvable = true;
                finishNode = currentNode;
                return;
            }
            for (Board newBoard : currentNode.board.neighbors())
            {

                if (currentNode.parent != null && newBoard.equals(currentNode.parent.board)) 
                {
                    //StdOut.println("Skipped board");
                    //StdOut.println(currentNode.board);
                    continue;
                }
                queue.insert(new Node(currentNode, newBoard));
            }

            currentNode = queueTwins.delMin();
            if (currentNode.board.isGoal())
            {
                isSolvable = false;
                finishNode = currentNode;
                return;
            }
            for (Board newBoard : currentNode.board.neighbors())
            {
                if (newBoard.equals(currentNode.parent)) continue;
                queueTwins.insert(new Node(currentNode, newBoard));
            }

        }

    }

    public boolean isSolvable()             // is the initial board solvable?
    {
        return isSolvable;
    }

    public int moves()                      // min number of moves to solve initial board; -1 if no solution
    {
        if (isSolvable) return finishNode.moves;
        return -1;
    }

    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
    {
        if (!isSolvable) return null;

        LinkedList<Board> result = new LinkedList<Board>();
        Node currentNode = finishNode;
        while (currentNode != null)
        {
            result.addFirst(currentNode.board);
            currentNode = currentNode.parent;
        }

        return result;

    }

    public static void main(String[] args)  // solve a slider puzzle (given below)
    {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);


        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else 
        {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}