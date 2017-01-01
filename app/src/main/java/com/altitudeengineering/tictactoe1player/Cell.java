package com.altitudeengineering.tictactoe1player;

class Cell {

    CellState content;
    int row, col;
    Cell(int row, int col) { this.row = row; this.col = col; content = CellState.EMPTY; }

}
