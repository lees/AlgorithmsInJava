//
//  main.cpp
//  Week4
//
//  Created by Вячеслав Ксенз on 23.03.14.
//  Copyright (c) 2014 Вячеслав Ксенз. All rights reserved.
//

#include <iostream>
#include "Solver.cpp"

int main(int argc, const char * argv[])
{

    std::vector< std::vector<int> > blocks;
    std::vector<int> row;
    row.push_back(6);
    row.push_back(5);
    row.push_back(11);
    row.push_back(4);
    blocks.push_back(row);
    row.clear();
    row.push_back(10);
    row.push_back(13);
    row.push_back(2);
    row.push_back(1);
    blocks.push_back(row);
    row.clear();
    row.push_back(9);
    row.push_back(15);
    row.push_back(7);
    row.push_back(3);
    blocks.push_back(row);
    row.clear();
    row.push_back(14);
    row.push_back(12);
    row.push_back(8);
    row.push_back(0);
    blocks.push_back(row);
    
    Board testBoard (blocks);
    testBoard.print();
    testBoard.twin()->print();
    
    Solver sol = Solver(&testBoard);
    std::cout << sol.isSolvavle() << std::endl;
    std::cout << sol.moves() << std::endl;
    
    
    return 0;
}

