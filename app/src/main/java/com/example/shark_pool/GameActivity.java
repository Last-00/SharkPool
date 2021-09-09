package com.example.shark_pool;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
   Handler handler;
   Dialog d;
   TextView lose_score,best_score,score;
   Button restart,home;
   FrameLayout thisLayout;
   static int count = 0;
   static ImageView rope_1,rope_2,rope_4,rope_5,rope_6;
   static ImageView[] IvCoordinates;
   static boolean isActivated = false;
   public static SoundPool sp;
   static int sharkAttackId,ballOnShoreId,gameOverId;
   static boolean pause;
   GameEngine gameEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        score = findViewById(R.id.score);

         pause = false;
        handler = new Handler();

        rope_1 = findViewById(R.id.rope_1);
        rope_2 = findViewById(R.id.rope_2);
        rope_4 = findViewById(R.id.rope_4);
        rope_5 = findViewById(R.id.rope_5);
        rope_6 = findViewById(R.id.rope_6);
        IvCoordinates = new ImageView[]{rope_1,rope_2,rope_4,rope_5};
        gameEngine = new GameEngine(this);
        thisLayout = findViewById(R.id.layout);
        thisLayout.addView(gameEngine);



        StartUpdateHandler();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){ // create sound variable according to sdk level
            AudioAttributes aa = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME).build();
            sp = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(aa).build();
        }
        else {
            sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        }
        sharkAttackId = sp.load(this,R.raw.shark_attack,1);
        ballOnShoreId = sp.load(this,R.raw.ball_on_shore,1);
        gameOverId = sp.load(this,R.raw.game_over,1);
    }



    public void StartUpdateHandler()  // handler function that creates movement of sharks, and each move checks its state
    {

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
               if(!pause)
               {
                  handler.postDelayed(this,110);
                  score.setText(String.valueOf(GameEngine.points));
                  gameEngine.invalidate();
               }
               else
               {
                   saveResult(GameEngine.points);
                   createLoseDialog();
                   if( MainActivity.sp1.getString("sound",null).length()==7)
                   sp.play(gameOverId, 1, 1, 0, 0, 1);
               }
            }


        },200);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(MainActivity.sp1.getString("control",null) == "1")
        {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
            {
                if(count%2 == 0)
                   isActivated = true;
                else
                   isActivated = false;
             count++;
            }
        }
        if(MainActivity.sp1.getString("control",null) == "2")
        {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
            {
               isActivated = true;
            }
            if(event.getAction() == MotionEvent.ACTION_UP)
            {
                isActivated = false;
            }
        }
        return true;
    }
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause = true;
    }
    public void createLoseDialog() {
        d = new Dialog(this);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        d.setContentView(R.layout.lose_dialog);
        d.setCancelable(false);
        lose_score = d.findViewById(R.id.lose_score);
        best_score = d.findViewById(R.id.best_score);


            if(MainActivity.sp1.getString("language",null).length()==2)
            {
                lose_score.setText("Score: "+GameEngine.points);
                if(MainActivity.sp1.getString("bestResult",null) != null)
                    best_score.setText("Best score: "+Integer.valueOf(MainActivity.sp1.getString("bestResult",null)));
            }
            if(MainActivity.sp1.getString("language",null).length()==3)
            {
                lose_score.setText("Счет: "+GameEngine.points);
                if(MainActivity.sp1.getString("bestResult",null) != null)
                    best_score.setText("Лучший счет: "+Integer.valueOf(MainActivity.sp1.getString("bestResult",null)));
            }
            if(MainActivity.sp1.getString("language",null).length()==4)
            {
                lose_score.setText("分数。"+GameEngine.points);
                if(MainActivity.sp1.getString("bestResult",null) != null)
                   best_score.setText("最佳得分。"+Integer.valueOf(MainActivity.sp1.getString("bestResult",null)));
            }


        restart = d.findViewById(R.id.btn_restart);
        home = d.findViewById(R.id.btn_home);
        restart.setOnClickListener(this);
        home.setOnClickListener(this);
        d.show();
    }
    public void saveResult(int newRes){ // saves your best record in SharedPreferences
        SharedPreferences.Editor editor=MainActivity.sp1.edit();
        editor.putString("result",Integer.toString(newRes));
        if(MainActivity.sp1.getString("bestResult",null) == null || MainActivity.sp1.getString("bestResult",null) == "0")
            editor.putString("bestResult",Integer.toString(newRes));
        else if(newRes>Integer.valueOf(MainActivity.sp1.getString("bestResult",null)))
            editor.putString("bestResult",Integer.toString(newRes));
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        if(v == restart){
            this.recreate();
            d.dismiss();

        }
        if(v == home){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            d.dismiss();
        }
    }
}