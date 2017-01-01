package com.altitudeengineering.tictactoe1player;

class Board {

    Cell[][] cells;
    int moveCount = 0;
    Board() { cells = new Cell[3][3]; for(int row = 0; row < 3; ++row) for(int col = 0; col < 3; ++col) cells[row][col] = new Cell(row, col); }
    boolean hasWon(CellState player, int row, int col) { return (cells[row][0].content == player && cells[row][1].content == player && cells[row][2].content == player || cells[0][col].content == player && cells[1][col].content == player && cells[2][col].content == player || row == col && cells[0][0].content == player && cells[1][1].content == player && cells[2][2].content == player || row + col == 2 && cells[0][2].content == player && cells[1][1].content == player && cells[2][0].content == player); }

}
