
#include <vector>
#include <iostream>
#include <string>
#include <queue>

class Board
	{
		private:
		
			int N;
			int* position;
			int zeroPosition;

			int posX(int index)
		    {
		        return index % N;
		    }

		    int posY(int index)
		    {
		        return index / N;
		    }

		    int finalPosition(int number)
		    {
		        if (number == 0) return N * N - 1;
		        return number - 1;
		    }

		    void exch(int a, int b)
		    {
		        int temp = position[b];
		        position[b] = position[a];
		        position[a] = temp;
		    }

		    Board getCopy()
		    {
		        std::vector< std::vector<int> > blocks (N);
		        int positionPointer = 0;
		        for (int i = 0; i < N; ++i)
		        {
		        	std::vector<int> row (N);
		        	for (int j = 0; j < N; ++j)
		        		row[j] = position[positionPointer++];
		        	blocks[i] = row;
		        	
		        }

		        Board newBoard (blocks);
		        return newBoard;
		    }

		public:
			
			Board(std::vector< std::vector<int> > blocks)
			{
				// if (blocks == NULL || blocks->size() == 0 || blocks->at(0) == NULL || blocks->at(0)->size() == 0) 
				// 	throw "There is empty initial data";
				if (blocks.size() == 0 || blocks[0].size() == 0) 
					throw "There is empty initial data";
				N = blocks.size();
				position = new int[N * N];
				int zeroPosition = -1;
				int positionPointer = 0;
				for (int i = 0; i < N; i++)
				{
					std::vector<int> row = blocks[i];
					if (row.size() != N) throw "Size of initial blocks is corrupted";
					for (int j = 0; j < N; j++)
					{
						position[positionPointer++] = row[j];
						if (row[j] == 0)
						{
							if (zeroPosition > -1) throw "There is more than one zero on the board";
						}
					}		
				}
			}

			~Board()
			{
				delete[] position;
			}

			int dimension()
			{
				return N;
			}

			int hamming()
			{
				int result = 0;
		        for (int i = 0; i < (N * N - 1); i++) 
		            if (position[i] != i + 1)
		                result++;
		        return result;
			}

			int manhattan()                 // sum of Manhattan distances between blocks and goal
		    {
		        int result = 0;
		        for (int i = 0; i < N * N; i++)
		        {
		            if (position[i] == 0) continue;
		            int finalPos = finalPosition(position[i]);
		            int currentPos = i;
		            result = result + abs(posX(finalPos) - posX(currentPos));
		            result = result + abs(posY(finalPos) - posY(currentPos));
		        }
		        return result;
		    }

		    bool isGoal()                // is this board the goal board?
		    {
		        for (int i = 0; i < N * N - 1; i++) 
		            if (position[i] != i + 1)
		                return false;
		        if (position[N * N - 1] != 0)
		            return false;
		        return true;
		    }

		    Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
		    {
		        Board newBoard = getCopy();
		        if (newBoard.zeroPosition > 1)
		            newBoard.exch(0, 1);
		        else
		            newBoard.exch(N * N - 1, N * N - 2);
		        return newBoard;
		    }

		    std::string toString()               // string representation of the board (in the output format specified below)
		    {

		        std::string s;
		        s += std::to_string(N);
		        for (int i = 0; i < N * N ; i++) 
		        {
		            if (i % N == 0)
		                s += '\n';
		            if (position[i] <= 9)
		            	s += " ";
		            s += std::to_string(position[i]);
		        }
		        s += '\n';
		        return s;
		    }

		    std::vector<Board> neighbors()
		    {
		    	Board newBoard = getCopy();
		    	std::vector<Board> result;
		    	int zeroX = posX(zeroPosition);
		    	int zeroY = posY(zeroPosition);

		    	if (zeroX == 0)
		    	{
		    		newBoard = getCopy();
		    		newBoard.exch(zeroPosition, zeroPosition + 1);
		    		newBoard.zeroPosition++;
		    		result.push_back(newBoard);
		    	}
		    	else if (zeroX == N-1)
		        {
		            newBoard = getCopy();
		            newBoard.exch(zeroPosition, zeroPosition - 1);
		            newBoard.zeroPosition--;
		            result.push_back(newBoard);
		        }
		        else
		        {
		            newBoard = getCopy();
		            newBoard.exch(zeroPosition, zeroPosition + 1);
		            newBoard.zeroPosition++;
		            result.push_back(newBoard);
		            newBoard = getCopy();
		            newBoard.exch(zeroPosition, zeroPosition - 1);
		            newBoard.zeroPosition--;
		            result.push_back(newBoard);
		        }

		        if (zeroY == 0)
		        {
		            newBoard = getCopy();
		            newBoard.exch(zeroPosition, zeroPosition + N);
		            newBoard.zeroPosition = zeroPosition + N; 
		            result.push_back(newBoard);
		        }
		        else if (zeroY == N - 1)
		        {
		            newBoard = getCopy();
		            newBoard.exch(zeroPosition, zeroPosition - N);
		            newBoard.zeroPosition = zeroPosition - N;
		            result.push_back(newBoard);
		        }
		        else
		        {
		            newBoard = getCopy();
		            newBoard.exch(zeroPosition, zeroPosition + N);
		            newBoard.zeroPosition = zeroPosition + N;
		            result.push_back(newBoard);
		            newBoard = getCopy();
		            newBoard.exch(zeroPosition, zeroPosition - N);
		            newBoard.zeroPosition = zeroPosition - N;
		            result.push_back(newBoard);
		        }

		        return result;

		    }

		    bool isEqual(Board y)
		    {
		    	if (&y == this) return true;
		    	if (N != y.N) return false;
		    	for(int i = 0; i < N; i++)
		    		if (position[i] != y.position[i]) return false;
		    	return true;
		    }

		    void print()
		    {
		    	std::cout << toString();
		  		std::cout << "Dimension "<< dimension()<< '\n';
		  		std::cout << "is Goal "<< isGoal() << '\n';
		  		std::cout << "hamming "<< hamming() << '\n';
		  		std::cout << "manhattan " << manhattan() << '\n';
		    }
	};

class Node
	{
		private:
			Node* parent;
			int priorityCache;
		public:
			int moves;
			Board* thisBoard;
			
			Node(Node* parentIn, Board boardIn)
			{
				parent = parentIn;
				if (parent == NULL) 
					moves = 0;
				else
					moves = parent->moves + 1;
				thisBoard = &boardIn;
				priorityCache = thisBoard->manhattan() + moves;
			}

			int priority()
			{
				return priorityCache;
			}

			~Node()
			{
				delete thisBoard;
			}
	};

class NodeCompare
	{ 
		public:
			bool operator()(Node* x, Node* y) 
			{ 
				return x->priority() < y->priority(); 
			}
	};

class Solver
	{
		private:
			
			bool isSolvable;
			Node* finishNode;


		public:
			Solver(Board initial)
			{
				Node* currentNode;
				std::priority_queue<Node *, std::vector<Node *>, NodeCompare> pq_main;
				std::priority_queue<Node *, std::vector<Node *>, NodeCompare> pq_twins;
				pq_main.push(new Node(NULL,initial));
				pq_twins.push(new Node(NULL,initial.twin()));

				int steps = 0;

				while (1)
				{
					currentNode = pq_main.top();
					pq_main.pop();
					if (currentNode->thisBoard->isGoal())
					{
						isSolvable = 1;
						finishNode = currentNode;
						return;
					}

				}

				finishNode = currentNode;

			}
			
			~Solver()
			{

			}

			/* data */
	};

int main () 
{
  std::vector< std::vector<int> > blocks;
  std::vector<int> row;
  row.push_back(0);
  row.push_back(1);
  row.push_back(2);
  blocks.push_back(row);
  row.clear();
  row.push_back(3);
  row.push_back(4);
  row.push_back(5);
  blocks.push_back(row);
  row.clear();
  row.push_back(6);
  row.push_back(7);
  row.push_back(8);
  blocks.push_back(row);

  Board testBoard (blocks);
  testBoard.print();
  testBoard.twin().print();

  


  return 0;
}
