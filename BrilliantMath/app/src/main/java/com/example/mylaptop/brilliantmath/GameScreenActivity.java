/*  created by Tran Tuan Anh - HUST - 2016
 *  copyright (c) 2016
 */

package com.example.mylaptop.brilliantmath;

import android.app.Activity;

import android.content.Intent;

import android.os.CountDownTimer;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;

import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;


public class GameScreenActivity extends Activity{

    final static int REQUEST_CODE = 1;

    public int score = 0;               // --- Score ---
    public int currentHighScore = 0;    // --- Current High Score ---

    public int odd = 0;                 // --- Error in result if error = 0 : result is true ---

    public CountDownTimer timer;        // --- Timer display time  in game---

    SeekBar mySeekBar ;                 // --- Timer display time  in game---

    TextView expressionView;
    TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        getViewAndInitialize();

        timer = new CountDownTimer(1000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                mySeekBar.setProgress((int)millisUntilFinished);
            }

            @Override
            public void onFinish() {
                score = 0;
                gameOver();
            }
        };          // --- Create countdown timer ---
    }

    public void gameOver(){
        timer.cancel();
        Intent intent = new Intent(this,GameOverActivity.class);
        intent.putExtra("back", 1);

        if(checkNewHighScore()){
            currentHighScore = score;
            MainActivity.highScore = score;
        }

        intent.putExtra("highScore",currentHighScore);
        intent.putExtra("currentScore",score);

        startActivityForResult(intent, REQUEST_CODE);       // --- Call GameOverActivity ---
    }

    public void finish(){
        if(checkNewHighScore()){
            Intent intent = new Intent();
            intent.putExtra("highScore",score);
            setResult(RESULT_OK,intent);
        }
        super.finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            if(data.hasExtra("back")){
                if(data.getExtras().getInt("back") == 2){
                    Intent intent = new Intent(getBaseContext(),MainActivity.class);        // --- Move to home page ---
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    getViewAndInitialize();         // --- Play game again ---
                }
            }
        }
    }

    public void getViewAndInitialize(){
        mySeekBar = (SeekBar)findViewById(R.id.seekBar);
        mySeekBar.setMax(1000);
        mySeekBar.setProgress(10000);
        mySeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        }); // --- Prevent user change seekBar 's status ---

        expressionView = (TextView)findViewById(R.id.expression);   // --- expression appears on screens ---
        scoreView = (TextView)findViewById(R.id.numberpoint);

        scoreView.setText(String.valueOf(score));
        expressionView.setText(makeExpression());

        currentHighScore = MainActivity.highScore;      // --- Get current highScore
    }

    public void playGame(){
        timer.cancel();
        expressionView.setText(makeExpression());
        timer.start();
    }

    public void updateScore(){
        scoreView.setText(String.valueOf(score));
    }

    public boolean checkNewHighScore(){
        return (score > currentHighScore) ? true : false;
    }

    public void pressed(View view){
        switch (view.getId()){
            case (R.id.buttonTrue):{
                if(odd == 0){
                    score += 10;
                    updateScore();
                    playGame();
                }
                else{
                    gameOver();
                }
                break;
            }
            case (R.id.buttonFalse):{
                if(odd != 0){
                    score += 10;
                    updateScore();
                    playGame();
                }
                else {
                    gameOver();
                }
                break;
            }
        }
    }

    public String makeExpression() {
        Random random = new Random();
        String expression = new String();
        String operate = new String();

        int num1, num2, result = 0, operator, order;

        num1 = random.nextInt(20 - 0) + 0;
        num2 = random.nextInt(20 - 0) + 0;
        operator = random.nextInt(3 - 1) + 1;
        order = random.nextInt(2 - 1) + 1;
        odd = random.nextInt(5 - 0) + 0;

        switch (operator) {         // --- Plus expression ---
            case (1): {
                operate = " + ";
                result = num1 + num2;
                result += odd;
                expression = createExpression(num1,num2,result,operate);
                break;
            }
            case (2): {
                operate = " - ";    // --- Minus expression ---
                if (order == 1) {
                    result = num1 - num2;
                    result += odd;
                    expression = createExpression(num1,num2,result,operate);
                } else {
                    result = num2 - num1;
                    result += odd;
                    expression = createExpression(num2,num1,result,operate);
                }
                break;
            }
            case (3): {         // --- Mutiple expression ---
                operate = " x ";
                result = num1 * num2;
                result += odd;
                expression = createExpression(num1,num2,result,operate);
                break;
            }
        }
        return expression;
    }

    public String createExpression(int num1, int num2,int result,String operate){
        String expression = new String();
        expression = String.valueOf(num1) + operate + String.valueOf(num2) + " = " + String.valueOf(result);
        return expression;
    }
}
