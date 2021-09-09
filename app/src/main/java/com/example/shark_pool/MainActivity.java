package com.example.shark_pool;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button start,settings,review;
    Button close,language,sound,control,review_close,send;
    Dialog d,d1;
    EditText reviewEt;
    TextView languageTv,soundTv,controlTv,reviewTv;
    public static SharedPreferences sp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp1 = getSharedPreferences("progressInfo",0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        settings = findViewById(R.id.settings);
        review = findViewById(R.id.review);
        start = findViewById(R.id.start);
        start.setOnClickListener(this);
        review.setOnClickListener(this);
        settings.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
         if(v == start)
         {
           Intent intent = new Intent(MainActivity.this,GameActivity.class);
           startActivity(intent);
         }
         if(v == settings){
            createSettingsDialog();
         }
         if(v == review){
             createReviewDialog();
         }
         if (v == close){
             d.dismiss();
         }
         if(v == language)
         {
             SharedPreferences.Editor editor=sp1.edit();
             if( sp1.getString("language",null) == "en")
             {
                 language.setBackgroundResource(R.drawable.rus);
                 editor.putString("language","rus");
                 languageTv.setText("Язык");
                 soundTv.setText("Звук");
                 controlTv.setText("Управление");
             }
             if(sp1.getString("language",null) == "rus")
             {
                 language.setBackgroundResource(R.drawable.ch);
                 editor.putString("language","chin");
                 languageTv.setText("语言");
                 soundTv.setText("声音");
                 controlTv.setText("控制");
             }
             if(sp1.getString("language",null) == "chin")
             {
                 language.setBackgroundResource(R.drawable.en);
                 editor.putString("language","en");
                 languageTv.setText("Language");
                 soundTv.setText("Sound");
                 controlTv.setText("Control");

             }
             editor.commit();
         }
         if(v == sound){
             SharedPreferences.Editor editor=sp1.edit();
             if(sp1.getString("sound",null) == "soundOff")
             {
                 sound.setBackgroundResource(R.drawable.sound_on);
                 editor.putString("sound","soundOn");
             }
             if( sp1.getString("sound",null) == "soundOn")
             {
                 sound.setBackgroundResource(R.drawable.sound_off);
                 editor.putString("sound","soundOff");
             }
             editor.commit();
         }
        if(v == control){
            SharedPreferences.Editor editor=sp1.edit();
            if(sp1.getString("control",null) == "1")
            {
                control.setBackgroundResource(R.drawable.thumbler2);
                editor.putString("control","2");
                GameActivity.isActivated = false;
            }
            if( sp1.getString("control",null) == "2")
            {
                control.setBackgroundResource(R.drawable.thumbler1);
                editor.putString("control","1");
                GameActivity.isActivated = false;
            }
            editor.commit();
        }
        if (v == review_close){
            d1.dismiss();
        }
        if(v == send)
        {
            if(reviewEt.getText().toString()!=null){
                String[] emails = new String[]{"dunaev.sania2020@gmail.com"};
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, emails);
                intent.putExtra(Intent.EXTRA_SUBJECT, "GameReview");
                intent.putExtra(Intent.EXTRA_TEXT, reviewEt.getText().toString());

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        }

    }
    public void createSettingsDialog() {
        d = new Dialog(this);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        d.setContentView(R.layout.settings_dialog);
        d.setCancelable(false);
        close = d.findViewById(R.id.close);
        language = d.findViewById(R.id.lan);
        sound = d.findViewById(R.id.sound);
        control = d.findViewById(R.id.control);
        languageTv = d.findViewById(R.id.lanTv);
        soundTv = d.findViewById(R.id.soundTv);
        controlTv = d.findViewById(R.id.controlTv);
        languageCheck();
        soundCheck();
        controlCheck();
        if( sp1.getString("language",null).length()==2)
        {
            language.setBackgroundResource(R.drawable.en);
            languageTv.setText("Language");
            soundTv.setText("Sound");
            controlTv.setText("Control");
        }
        if(sp1.getString("language",null).length()==3)
        {
            language.setBackgroundResource(R.drawable.rus);
            languageTv.setText("Язык");
            soundTv.setText("Звук");
            controlTv.setText("Управление");
        }
        if(sp1.getString("language",null).length()==4)
        {
            language.setBackgroundResource(R.drawable.ch);
            languageTv.setText("语言");
            soundTv.setText("声音");
            controlTv.setText("控制");
        }
        if(sp1.getString("sound",null).length()==8)
        {
            sound.setBackgroundResource(R.drawable.sound_off);

        }
        if( sp1.getString("sound",null).length()==7)
        {
            sound.setBackgroundResource(R.drawable.sound_on);

        }
        if(sp1.getString("control",null) == "1")
        {
            control.setBackgroundResource(R.drawable.thumbler1);
        }
        if(sp1.getString("control",null) == "2")
        {
            control.setBackgroundResource(R.drawable.thumbler2);
        }
        close.setOnClickListener(this);
        language.setOnClickListener(this);
        sound.setOnClickListener(this);
        control.setOnClickListener(this);
        d.show();
    }
    public void createReviewDialog() {
        d1 = new Dialog(this);
        d1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        d1.setContentView(R.layout.review_dialog);
        d1.setCancelable(false);
        review_close = d1.findViewById(R.id.close_review);
        reviewTv = d1.findViewById(R.id.reviewTv);
        reviewEt = d1.findViewById(R.id.reviewEt);
        send = d1.findViewById(R.id.send);
        if( sp1.getString("language",null).length()==2)
        {
            reviewTv.setText("Send your review of the game Shark Pool");
        }
        if(sp1.getString("language",null).length()==3)
        {
            reviewTv.setText("Отправьте свой отзыв об игре Shark Pool");
        }
        if(sp1.getString("language",null).length()==4)
        {
            reviewTv.setText("发送你对游戏 \"鲨鱼池 \"的评论");
        }
        reviewEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(reviewEt.getText().toString().length()>0)
                   send.setBackgroundResource(R.drawable.send);
                else
                    send.setBackgroundResource(R.drawable.send_disable);
            }
        });
        review_close.setOnClickListener(this);
        send.setOnClickListener(this);
        d1.show();
    }
    public void languageCheck(){
        if(sp1.getString("language",null) == null)
        {
            SharedPreferences.Editor editor=sp1.edit();
            editor.putString("language","en");
            editor.commit();
        }
    }
    public void soundCheck(){
        if(sp1.getString("sound",null) == null)
        {
            SharedPreferences.Editor editor=sp1.edit();
            editor.putString("sound","soundOn");
            editor.commit();
        }
    }
    public void controlCheck(){
        if(sp1.getString("control",null) == null)
        {
            SharedPreferences.Editor editor=sp1.edit();
            editor.putString("control","1");
            editor.commit();
        }
    }



}