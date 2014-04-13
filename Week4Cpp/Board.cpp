//
//  Board.cpp
//  Week4
//
//  Created by Вячеслав Ксенз on 23.03.14.
//  Copyright (c) 2014 Вячеслав Ксенз. All rights reserved.
//

#include <vector>


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
    
    
public:
    
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
    
    Board(Board const &from)
    {
        N = from.N;
        position = new int[N * N];
        for(int i = 0; i < N * N; i++)
            position[i] = from.position[i];
        zeroPosition = from.zeroPosition;
    }

    Board(std::vector< std::vector<int> > blocks)
    {
        if (blocks.size() == 0 || blocks[0].size() == 0)
            throw "There is empty initial data";
        N = (int) blocks.size();
        position = new int[N * N];
        zeroPosition = -1;
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
                    zeroPosition = positionPointer - 1;
                }
            }
        }

    }
    
    Board * changed(int newPosition)
    {
        Board *newBoard = new Board(*this);
        newBoard->exch(zeroPosition, newPosition);
        newBoard->zeroPosition = newPosition;
        return newBoard;
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
    
    Board * twin()                    // a board obtained by exchanging two adjacent blocks in the same row
    {
        Board * newBoard = new Board(*this);
        if (newBoard->zeroPosition > 1)
            newBoard->exch(0, 1);
        else
            newBoard->exch(N * N - 1, N * N - 2);
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
            s += " ";
        }
        s += '\n';
        return s;
    }
    
    std::vector<Board *> neighbors()
    {
        std::vector<Board *> result;
        int zeroX = posX(zeroPosition);
        int zeroY = posY(zeroPosition);
        
        if (zeroX == 0)
            result.push_back(changed(zeroPosition+1));
        else if (zeroX == N-1)
            result.push_back(changed(zeroPosition-1));
        else
        {
            result.push_back(changed(zeroPosition-1));
            result.push_back(changed(zeroPosition+1));
        }
        
        if (zeroY == 0)
            result.push_back(changed(zeroPosition+N));
        else if (zeroY == N - 1)
            result.push_back(changed(zeroPosition-N));
        else
        {
            result.push_back(changed(zeroPosition+N));
            result.push_back(changed(zeroPosition-N));
        }
        
        return result;
        
    }
    
    bool isEqual(Board *y)
    {
        //if (&y == this) return true;
        if (N != y->N) return false;
        for(int i = 0; i < N * N; i++)
            if (position[i] != y->position[i]) return false;
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