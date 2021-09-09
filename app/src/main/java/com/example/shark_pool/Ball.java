package com.example.shark_pool;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Ball {
    int x,y;
    int vx;
    boolean isFinished = false;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Ball(int x, int y, int vx, boolean isFinished) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.isFinished = isFinished;
    }

    public void drawBall(Canvas canvas, Paint paint){
        canvas.drawBitmap(GameEngine.ball,x,y,paint);
    }
}
