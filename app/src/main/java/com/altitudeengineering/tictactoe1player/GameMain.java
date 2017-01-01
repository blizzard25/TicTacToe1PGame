package com.altitudeengineering.tictactoe1player;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GameMain extends AppCompatActivity {

    private Board board;
    private Computer ai;
    private GameState currentState;
    private CellState currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        board = new Board();
        ai = new Computer(board);
        currentPlayer = CellState.CROSS;
        ai.setAIPiece(CellState.CIRCLE);
        currentState = GameState.IN_PROGRESS;
    }

    @Override
    public void recreate() {
        super.recreate();
    }

    public void cellClick(View v) {
        TextView cell = (TextView)findViewById(v.getId());
        int row, col;
        char[] idChars = ("" + cell.getId()).toCharArray();
        if(cell.getText() == "" && currentState == GameState.IN_PROGRESS) {
            row = idChars[8] == '1' ? 0 : Character.getNumericValue(idChars[9]) > 3 ? 2 : 1;
            col = row == 0 ? Character.getNumericValue(idChars[9]) - 7 : row == 1 ? Character.getNumericValue(idChars[9]) - 1 : Character.getNumericValue(idChars[9]) - 5;
            cell.setText("X");
            board.moveCount++;
            setMove(currentPlayer, row, col);
            if(board.hasWon(currentPlayer, row, col)) currentState = (currentPlayer == CellState.CROSS) ? GameState.CROSS_WON : GameState.CIRCLE_WON;
            if(board.moveCount == 9) currentState = GameState.DRAW;
            currentPlayer = (currentPlayer == CellState.CROSS) ? CellState.CIRCLE : CellState.CROSS;
            if(currentState == GameState.IN_PROGRESS) {
                int[] move = ai.move();
                TextView moveCell = (move[0] == 0) ? (move[1] == 0 ? (TextView)findViewById(R.id.cell11) : move[1] == 1 ? (TextView)findViewById(R.id.cell12) : (TextView)findViewById(R.id.cell13)) : (move[0] == 1) ? (move[1] == 0 ? (TextView)findViewById(R.id.cell21) : move[1] == 1 ? (TextView)findViewById(R.id.cell22) : (TextView)findViewById(R.id.cell23)) : (move[1] == 0 ? (TextView)findViewById(R.id.cell31) : move[1] == 1 ? (TextView)findViewById(R.id.cell32) : (TextView)findViewById(R.id.cell33));
                if (board.cells[move[0]][move[1]].content == CellState.EMPTY) {
                    moveCell.setText("O");
                    board.moveCount++;
                    setMove(ai.computer, move[0], move[1]);
                    if(board.hasWon(ai.computer, move[0], move[1])) currentState = (currentPlayer == CellState.CROSS) ? GameState.CROSS_WON : GameState.CIRCLE_WON;
                    if(board.moveCount == 9) currentState = GameState.DRAW;
                    if(currentState == GameState.IN_PROGRESS) currentPlayer = (currentPlayer == CellState.CROSS) ? CellState.CIRCLE : CellState.CROSS;
                }
            } else Toast.makeText(getApplicationContext(), currentState == GameState.CROSS_WON ? "You win!" : currentState == GameState.CIRCLE_WON ? "The AI has won." : "Game ends in a draw", Toast.LENGTH_LONG).show();
        } else Toast.makeText(getApplicationContext(), currentState == GameState.CROSS_WON ? "You win!" : currentState == GameState.CIRCLE_WON ? "The AI has won." : "Game ends in a draw", Toast.LENGTH_LONG).show();
    }

    public void resetClick(View v) {
        recreate();
    }

    private void setMove(CellState currentPlayer, int row, int col) {
        board.cells[row][col].content = currentPlayer;
    }

}
