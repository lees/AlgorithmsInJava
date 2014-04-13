//
//  Solver.cpp
//  Week4
//
//  Created by Вячеслав Ксенз on 23.03.14.
//  Copyright (c) 2014 Вячеслав Ксенз. All rights reserved.
//

#include <vector>
#include <queue>
#include "Board.cpp"

class Node
{
private:
    int priorityCache;
public:
    Node* parent;
    int moves;
    Board* thisBoard;
    
    Node(Node* parentIn, Board *boardIn)
    {
        parent = parentIn;
        if (parent == NULL)
            moves = 0;
        else
            moves = parent->moves + 1;
        thisBoard = boardIn;
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
        return x->priority() > y->priority();
    }
};

class Solver
{
private:
    
    bool isSolvable;
    Node* finishNode;
    
    
public:
    
    Solver(Board *initial)
    {
        Node* currentNode;
        std::vector<Board *> neighbors;
        std::priority_queue<Node *, std::vector<Node *>, NodeCompare> pq_main;
        std::priority_queue<Node *, std::vector<Node *>, NodeCompare> pq_twins;
        pq_main.push(new Node(NULL,initial));
        pq_twins.push(new Node(NULL,initial->twin()));
        int step = 0;
        while (1)
        {
            step++;
            
            // Get the min Node
            currentNode = pq_main.top();
            pq_main.pop();
            
            // If it the end Node then we finished
            if (currentNode->thisBoard->isGoal())
            {
                isSolvable = 1;
                finishNode = currentNode;
                return;
            }
            
            // Get all the neighbors and push them in the queue
            neighbors = currentNode->thisBoard->neighbors();
            for (auto it: neighbors)
            {
                if (currentNode->parent != NULL && it->isEqual(currentNode->parent->thisBoard))
                    continue;
                
                pq_main.push(new Node(currentNode, it));
            }

            // Repeat all the same, but for the twins queue
            currentNode = pq_twins.top();
            pq_twins.pop();
            // If it the end Node then we finished
            if (currentNode->thisBoard->isGoal())
            {
                isSolvable = 1;
                finishNode = currentNode;
                return;
            }
            
            // Get all the neighbors and push them in the queue
            neighbors = currentNode->thisBoard->neighbors();
            for (auto it: neighbors)
            {
        
                if (currentNode->parent != NULL && it->isEqual(currentNode->parent->thisBoard))
                    continue;
                
                pq_twins.push(new Node(currentNode, it));
            }

            
        }
        
    }
    
    bool isSolvavle()
    {
        return isSolvable;
    }
    
    int moves()
    {
        if (isSolvable) return finishNode->moves;
        return -1;
    }
    
    std::vector<Node *> solution()
    {
        std::vector<Node *> result;
        if (!isSolvable) return result;
        
        Node *currentNode = finishNode;
        while (currentNode != NULL)
        {
            result.push_back(currentNode);
            currentNode = currentNode->parent;
        }
        
        return result;
        
    }
    
    ~Solver()
    {
        //delete finishNode;
    }
    
};