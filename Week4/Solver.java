import java.util.Stack;

public class Solver 
{
	private boolean isSolvable;
	private Node finishNode;

	private class Node implements Comparable<Node>
	{
		private Node parent;
		public int moves;
		private int priority;
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
			return Integer.compare(priority(),that.priority());
		}
	}

    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {
    	Node currentNode;
    	MinPQ<Node> queue = new MinPQ<Node>();
    	queue.insert(new Node(null, initial));
    	MinPQ<Node> queueTwins = new MinPQ<Node>();
    	queueTwins.insert(new Node(null, initial.twin()));

    	while (true)
    	{
    		currentNode = queue.delMin();
    		if (currentNode.board.isGoal())
    		{
    			isSolvable = true;
    			finishNode = currentNode;
    			return;
    		}
    		for (Board newBoard : currentNode.board.neighbors())
    		{
    			if (newBoard.equals(currentNode.parent)) continue;
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
    	return finishNode.moves;
    }

    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
    {
    	if (!isSolvable) return null;

    	Stack<Board> result = new Stack<Board>();
    	Node currentNode = finishNode;
    	while (currentNode != null)
    	{
    		result.push(currentNode.board);
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