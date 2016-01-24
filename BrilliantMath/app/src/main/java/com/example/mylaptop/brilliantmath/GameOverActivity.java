/*  created by Tran Tuan Anh - HUST - 2016
 *  copyright (c) 2016
 */

package com.example.mylaptop.brilliantmath;

import android.content.Intent;

import android.graphics.Color;

import android.app.Activity;

import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class GameOverActivity extends Activity {

    int currentScore, highscore;        // --- Used to store currentScore and highScore ---

    final static int REQUEST_CODE = 1;      // --- Request_Code used in startActivityForResult ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_layout);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));        // --- Make trasparent back ground for activity ---
        setFinishOnTouchOutside(false);                 // --- Makeactivity can't be closed when user touched outside ---
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        Bundle extras = getIntent().getExtras();
        highscore = extras.getInt("highScore");         // --- get highscore from GameScreenActivity ---
        currentScore = extras.getInt("currentScore");   // --- get currentscore from GameScreenActivity ---

        TextView scorePoint = (TextView)findViewById(R.id.scorePoint);
        TextView bestPoint = (TextView)findViewById(R.id.bestPoint);

        scorePoint.setText(String.valueOf(currentScore));       // --- Set text in Score path ---
        bestPoint.setText(String.valueOf(highscore));           // --- Set text in Best path ---
    }

    public void onClick(View view){
        switch (view.getId()) {
            case (R.id.btnback):{               // --- Back to GameScreenActivity for playing again ---
                Intent intent = new Intent(getBaseContext(),GameScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("back",1); // --- "back" is used for know get back to play again or back to home back = 1 : play again ---
                startActivityForResult(intent,REQUEST_CODE);
            }
            case (R.id.btnhome):{
                Intent data = new Intent();
                data.putExtra("back",2);  // --- "back" is used for know get back to play again or back to home back = 2 : go to home page---
                data.putExtra("highScore",highscore);
                setResult(RESULT_OK,data);
                finish();
            }
        }
    }
}
