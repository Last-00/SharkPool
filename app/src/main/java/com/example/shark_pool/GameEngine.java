package com.example.shark_pool;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;


import java.util.Random;


public class GameEngine extends View {
    static Shark[] sharks;
    int sharkNum = 0;
    static int points;
    static Shark shark1,shark2,shark3,shark4;
    static Ball ball1, ball2,ball3,ball4,ball5;
    static Ball [] balls;
    static Bitmap down_right,down_left,up_right,up_left,ball,sharkAttack_down,sharkAttack_up;
    Context context;
    Paint[] SharkPaints;
    Paint[] BallPaints;
    boolean up = true;
    static boolean gotcha;
    Random random;
    int ballNum = 0;

    public GameEngine(Context context) {
        super(context);
        this.context = context;
        gotcha = false;
        points = 0;
        BallPaints = new Paint[]{new Paint(),new Paint(),new Paint(),new Paint(),new Paint()};
        SharkPaints = new Paint[]{new Paint(),new Paint(),new Paint(),new Paint()};
        down_right =BitmapFactory.decodeResource(getResources(), R.drawable.shark_right);
        down_left =  BitmapFactory.decodeResource(getResources(), R.drawable.shark_left);
        up_right = BitmapFactory.decodeResource(getResources(), R.drawable.shark_right_down);
        up_left = BitmapFactory.decodeResource(getResources(), R.drawable.shark_left_down);
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        sharkAttack_down = BitmapFactory.decodeResource(getResources(), R.drawable.shark_attack);
        sharkAttack_up = BitmapFactory.decodeResource(getResources(), R.drawable.shark_attack_up);
        balls = new Ball[]{ball1, ball2,ball3,ball4,ball5};
        balls[0] = new Ball(0,(getScreenHeight() / 2) - ((getScreenHeight() / 10)),getScreenWidth()/60,false);
        balls[1] = new Ball(0,getScreenHeight() / 2,getScreenWidth()/60,false);
        balls[2] = new Ball(0,(getScreenHeight() / 2) + ((getScreenHeight() / 10)),getScreenWidth()/60,false);
        balls[3] = new Ball(0,(getScreenHeight() / 2) + ((getScreenHeight() / 10)*2),getScreenWidth()/60,false);
        balls[4] = new Ball(0,(getScreenHeight() / 2) +((getScreenHeight() / 10)*3),getScreenWidth()/60,false);
        random = new Random();
        sharks = new Shark[]{shark1,shark2,shark3,shark4};
        sharks[0] = new Shark(-800 - random.nextInt(200),getScreenHeight()/40 + random.nextInt(60),true,true);
        sharks[1] = new Shark(getScreenHeight() + random.nextInt(400),getScreenHeight()/40 + random.nextInt(60), false,false);
        sharks[2] = new Shark(-400 - random.nextInt(200),getScreenHeight()/40 + random.nextInt(60),true,false);
        sharks[3] = new Shark(getScreenHeight() + random.nextInt(1000),getScreenHeight()/40 + random.nextInt(60),false,false);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        SharkAttack(canvas);
        NextShark(balls[ballNum]);
        Log.d("sharkNum","sharkNum is "+sharkNum);
        sharks[0].drawShark(canvas,GameActivity.rope_1.getRight()-15,SharkPaints[0]); sharks[1].drawShark(canvas,GameActivity.rope_2.getRight()-15,SharkPaints[1]); sharks[2].drawShark(canvas,GameActivity.rope_4.getRight()-15,SharkPaints[2]); sharks[3].drawShark(canvas,GameActivity.rope_5.getRight()-15,SharkPaints[3]);
        balls[0].drawBall(canvas,BallPaints[0]); balls[1].drawBall(canvas,BallPaints[1]); balls[2].drawBall(canvas,BallPaints[2]); balls[3].drawBall(canvas,BallPaints[3]); balls[4].drawBall(canvas,BallPaints[4]);
        SharkUpdate(sharks[0]);SharkUpdate(sharks[1]);SharkUpdate(sharks[2]);SharkUpdate(sharks[3]);

        if(CatchCheck(balls[ballNum],sharks[sharkNum],down_left.getWidth(),down_left.getHeight()*5/10)){
           gotcha = true; BallPaints[ballNum].setAlpha(0);SharkPaints[sharkNum].setAlpha(0);}


        if(GameActivity.isActivated)
        {
            BallUpdate(balls[ballNum]);


           if (balls[ballNum].getX()>GameActivity.rope_6.getRight()-20)
           {
               if( MainActivity.sp1.getString("sound",null).length()==7)
                   GameActivity.sp.play(GameActivity.ballOnShoreId, 1, 1, 0, 0, 1);
               GameActivity.isActivated = false;
               GameActivity.count++;
               points++;
               ballNum++;
               sharkNum = 0;
               BallsReturn(GameActivity.rope_6.getRight()-20);
           }
        }

    }
   public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
   public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
   public void SharkUpdate(Shark shark){
        if(shark.direction_down)
        {
          shark.setY(shark.getY()+shark.getVy());
             if(shark.getY() > getScreenHeight()+200) {shark.setY(-800 - random.nextInt(600)); shark.setVy(getScreenHeight()/40 + random.nextInt(40));}
        }
        else
        {
           shark.setY(shark.getY()-shark.getVy());
             if(shark.getY() < -400) {shark.setY(getScreenHeight() + random.nextInt(400)); shark.setVy(getScreenHeight()/40 + random.nextInt(30));}
        }
    }
   public void BallUpdate(Ball ball){
        ball.setX(ball.getX()+ball.getVx());
        if(up){
            ball.setY(ball.getY()+10);
            up = false;
        }
        else {ball.setY(ball.getY()-10);
        up = true;}
    }
   public boolean CatchCheck(Ball ball,Shark shark,int sharkWidth,int sharkHeight){

       if ((ball.getX() >= GameActivity.IvCoordinates[sharkNum].getRight()-15 && ball.getX() <= GameActivity.IvCoordinates[sharkNum].getRight()-15 + sharkWidth/2) &&
               (ball.getY() >= shark.getY() && ball.getY() < shark.getY()+sharkHeight))
           return true;
        return false;
   }
   public void NextShark(Ball ball){
        if( sharkNum!=3 && ball.getX() > GameActivity.IvCoordinates[sharkNum+1].getRight()-40 )
            sharkNum++;
   }
   public void SharkAttack(Canvas canvas){
       if(sharks[sharkNum].direction_down)
       {
           if(gotcha){
               canvas.drawBitmap(GameEngine.sharkAttack_down,GameActivity.IvCoordinates[sharkNum].getRight()-70,sharks[sharkNum].getY(),null);
               GameActivity.pause = true;
               if( MainActivity.sp1.getString("sound",null).length()==7)
               GameActivity.sp.play(GameActivity.sharkAttackId, 1, 1, 0, 0, 1);
           }
       }
       else if(!sharks[sharkNum].direction_down)
       {
           if(gotcha){
               canvas.drawBitmap(GameEngine.sharkAttack_up,GameActivity.IvCoordinates[sharkNum].getRight()-70,sharks[sharkNum].getY(),null);
               GameActivity.pause = true;
               if( MainActivity.sp1.getString("sound",null).length()==7)
               GameActivity.sp.play(GameActivity.sharkAttackId, 1, 1, 0, 0, 1);
           }
       }
   }
   public void BallsReturn(int endPoint){
        if (balls[balls.length-1].getX()>endPoint){
            for(int i = 0; i<balls.length; i++){
                balls[i].setX(0);
            }
            ballNum = 0;
        }
 }


}

