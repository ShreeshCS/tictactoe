package com.example.multiscreen_ttt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class game_main extends AppCompatActivity {
    ArrayList<String> namesList;
    TextView playerNameHolder;
    int moveCount = 0;
    boolean gameActive = true;
    boolean winFlag = false;
    String winnerName;
    // Player representation
    // 0 - O
    // 1 - X
    boolean activePlayer = false;
    int tappedCell;
    int[] gameState = {2, 2 , 2, 2, 2, 2, 2, 2, 2};
    //    State meanings:
    //    0 - O
    //    1 - X
    //    2 - Null
    int[][] winPositions = {{0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}};


    public void cellTap(View view){

        ImageView img = (ImageView) view;
        TextView turnStatus = findViewById(R.id.turn_status);

        tappedCell = Integer.parseInt(img.getTag().toString());
        if(gameState[tappedCell] == 2){
            if(!gameActive){
                img.setImageResource(R.drawable.blank_cell);
            }
            else{
                moveCount++;
                gameState[tappedCell] = activePlayer ? 1 : 0;
                if(activePlayer){
                    img.setImageResource(R.drawable.x);
                    turnStatus.setText(namesList.get(0) + "'s turn");
                }
                else{
                    img.setImageResource(R.drawable.o);
                    turnStatus.setText(namesList.get(1) + "'s turn");
                }
                activePlayer = !activePlayer;
            }
        }

        for(int[] winPos : winPositions){
            if(gameState[winPos[0]] == gameState[winPos[1]] && gameState[winPos[1]] == gameState[winPos[2]] && gameState[winPos[2]] != 2 && gameActive){
                //Game Finished - Declare Winner
                winFlag = true;

                if(gameState[winPos[0]] == 0){
                    //O won the game
                    winnerName = namesList.get(0);
                }
                else{
                    //X won the game
                    winnerName = namesList.get(1);
                }
                //Deactivate game
                gameActive = false;
                // Update the status bar for winner announcement
                turnStatus.setText(winnerName + " won!");

                Toast.makeText(this, "Congrats " + winnerName, Toast.LENGTH_SHORT).show();
//                Button btn = findViewById(R.id.reset_button);
//                btn.setVisibility(View.VISIBLE);
            }
        }
        if(moveCount == 9 && !winFlag) {
            gameActive = false;
            turnStatus.setText("No more moves left");
            Toast.makeText(this, "It's a TIE ;)", Toast.LENGTH_SHORT).show();

        }

    }

    public void gameReset(View view) {
        gameActive = true;
        activePlayer = false;
        winFlag = false;
        Arrays.fill(gameState, 2);
        moveCount = 0;
        ((ImageView)findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);

        playerNameHolder = (TextView) findViewById(R.id.turn_status);
        playerNameHolder.setText(namesList.get(0) + "'s turn");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        Intent intent = getIntent();
        namesList = intent.getStringArrayListExtra("name001");
        System.out.println(namesList.get(0));
        System.out.println(namesList.get(1));
        playerNameHolder = (TextView)findViewById(R.id.turn_status);
        Toast.makeText(game_main.this, "Welcome to the GAME!", Toast.LENGTH_LONG).show();

        playerNameHolder.setText(namesList.get(0));

    }

}