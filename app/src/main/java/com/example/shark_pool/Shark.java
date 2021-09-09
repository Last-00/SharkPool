package com.example.shark_pool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

public class Shark {
    int x,y;
    int vy;
    Paint paint;
    boolean direction_down,right_side;

    public boolean isRight_side() {
        return right_side;
    }

    public void setRight_side(boolean right_side) {
        this.right_side = right_side;
    }

    public Shark(int y, int vy, boolean direction_down, boolean right_side) {
        this.y = y;
        this.vy = vy;
        this.direction_down = direction_down;
        this.right_side = right_side;
    }


    public void setY(int y) {
        this.y = y;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }



    public void setUp(boolean down) {
        this.direction_down = down;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVy() {
        return vy;
    }



    public boolean isDown() {
        return direction_down;
    }
    public void drawShark(Canvas canvas, int x, Paint paint){
        if(this.direction_down)
        {

            if(this.right_side)
            {
                canvas.drawBitmap(GameEngine.down_right,x,y,paint);
                this.right_side = false;
            }
            else
            {
                canvas.drawBitmap(GameEngine.down_left,x,y,paint);
                this.right_side = true;
            }
        }
        else
        {
            if(this.right_side)
            {
                canvas.drawBitmap(GameEngine.up_right,x,y,paint);
                this.right_side = false;
            }
            else
                {
                canvas.drawBitmap(GameEngine.up_left,x,y,paint);
                this.right_side = true;
                }
        }
    }
}
