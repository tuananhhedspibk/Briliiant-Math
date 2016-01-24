/*  created by Tran Tuan Anh - HUST - 2016
 *  copyright (c) 2016
 */

package com.example.mylaptop.brilliantmath;

import android.app.Activity;
import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.view.View;
import android.view.Window;

import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    final static int DIALOG = 1;                // --- Used to get helper 's dialog ---
    static int highScore = 0;                   // --- Store highScore 's value ---
    static ToolTipWindow toolTipWindow;         // --- Used to show highScore ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);             // --- Set UI for MainAcitivity via activity_main.xml file ---
        // --- Get preferences to get highScore ---
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        highScore = prefs.getInt("key", 0);
    }

    public void onClick(View view){
        switch (view.getId()){
            case (R.id.buttonPlay):{            // --- Click to buttob has text = "Play" to start game ---
                Intent intent = new Intent(MainActivity.this,GameScreenActivity.class);
                startActivity(intent);          // --- start Acitivity GameScreenActivity by intent to start game
                break;
            }
            case (R.id.buttonHelp):{            // --- Click to button has text = "Help" to open game 's helper in dialog---
                makeDialog(DIALOG);
                break;
            }
            case (R.id.buttonHighScore):{       // --- Click to buttob has text = "High Score" to show game 's highscore in ToolTip ---
                if(toolTipWindow != null && toolTipWindow.isTooltipShown()){
                    toolTipWindow.dismissTooltip();         // --- If tooltip has shown ---> dismiss it and create new tooltip ---
                }
                else{
                    toolTipWindow = new ToolTipWindow(this,highScore);
                    if(!toolTipWindow.isTooltipShown()){
                        Button button = (Button)findViewById(R.id.buttonHighScore);
                        toolTipWindow.showToolTip(button);
                    }
                }
                break;
            }
        }
    }

    public void makeDialog(int id){
        switch (id){
            case (DIALOG):{
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);       // --- Remove title in default dialog ---
                dialog.setContentView(R.layout.my_dialog);                  // --- Set UI for dialog via my_dialog.xml file ---
                dialog.setCanceledOnTouchOutside(false);                    // --- Make dialog can't be closed by outside touch ---
                dialog.show();                                              // --- Show dialog ---

                Button btnOK = (Button)dialog.findViewById(R.id.buttonOK);          // --- This is positive button of dialog ---
                Button btnCancel = (Button)dialog.findViewById(R.id.buttonCancel);  // --- This is negative button of dialog ---
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        }
    }

    // --- Store highScore when exit game ---
    public void onDestroy(){
        storeHighScore();
        super.onDestroy();
    }

    // --- Store highScore when exit game ---
    public void onPause(){
        storeHighScore();
        super.onPause();
    }

    public void storeHighScore(){
        // --- Setting preferences ---
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("key", highScore);
        editor.commit();
    }
}
