package com.altitudeengineering.tictactoe1player;

abstract class AIPlayer {

    Cell[][] cells;
    CellState computer;
    CellState player;
    AIPlayer(Board board) {
        cells = board.cells;
    }
    void setAIPiece(CellState piece) { this.computer = piece; player = (computer == CellState.CROSS) ? CellState.CIRCLE : CellState.CROSS; }
    abstract int[] move();

}
