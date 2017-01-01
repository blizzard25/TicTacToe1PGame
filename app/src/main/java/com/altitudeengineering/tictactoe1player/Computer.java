package com.altitudeengineering.tictactoe1player;

import java.util.ArrayList;
import java.util.List;

class Computer extends AIPlayer {

    Computer(Board board) {
        super(board);
    }

    @Override
    int[] move() {
        int[] result = minimax(2, computer, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new int[] { result[1], result[2] };
    }

    private int[] minimax(int depth, CellState player, int alpha, int beta) {
        int score;
        int bestRow = -1;
        int bestCol = -1;
        if (generateMoves().isEmpty() || depth == 0) return new int[] { evaluateLine(0, 0, 0, 1, 0, 2) + evaluateLine(1, 0, 1, 1, 1, 2) + evaluateLine(2, 0, 2, 1, 2, 2) + evaluateLine(0, 0, 1, 0, 2, 0) + evaluateLine(0, 1, 1, 1, 2, 1) + evaluateLine(0, 2, 1, 2, 2, 2) + evaluateLine(0, 0, 1, 1, 2, 2) + evaluateLine(0, 2, 1, 1, 2, 0), bestRow, bestCol };
        else {
            for (int[] move : generateMoves()) {
                cells[move[0]][move[1]].content = player;
                if (player == computer) {
                    score = minimax(depth - 1, player, alpha, beta)[0];
                    if (score > alpha) {
                        alpha = score;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {
                    score = minimax(depth - 1, computer, alpha, beta)[0];
                    if (score < beta) {
                        beta = score;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                cells[move[0]][move[1]].content = CellState.EMPTY;
                if (alpha >= beta) break;
            }
            return new int[] { (player == computer) ? alpha : beta, bestRow, bestCol };
        }
    }

    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<>();
        if(hasWon(computer) || hasWon(player)) return nextMoves;
        for(int row = 0; row < 3; row++) for (int col = 0; col < 3; col++) if (cells[row][col].content == CellState.EMPTY) nextMoves.add(new int[] { row, col });
        return nextMoves;
    }

    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = cells[row1][col1].content == computer ? 1 : -1;
        if (cells[row2][col2].content == computer) {
            if(score != -1) score = (score == 1) ? 10 : 1;
            else return 0;
        } else if (cells[row2][col2].content == player) {
            if(score != 1) score = (score == -1) ? -10 : -1;
            else return 0;
        }
        if (cells[row3][col3].content == computer) {
            if(score >= 0) score = (score > 0) ? score *= 10 : 1;
            else return 0;
        } else if (cells[row3][col3].content == player) {
            if(score <= 1) score = (score < 0) ? score *= 10 : -1;
            else return 0;
        }
        return score;
    }

    private boolean hasWon(CellState thePlayer) {
        int[] winningPatterns = { 0b111000000, 0b000111000, 0b000000111, 0b100100100, 0b010010010, 0b001001001, 0b100010001, 0b001010100 };
        int pattern = 0b000000000;
        for (int row = 0; row < 3; ++row) for (int col = 0; col < 3; ++col) if (cells[row][col].content == thePlayer) pattern |= (1 << (row * 3 + col));
        for (int winningPattern : winningPatterns) if ((pattern & winningPattern) == winningPattern) return true;
        return false;
    }
}
