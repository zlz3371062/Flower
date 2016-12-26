package com.nbhero.DIYview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhenglingzhong on 2016/11/9.
 */

public class SwitchOpen  extends View{


    private  int viewWidth;
    private  int viewHeight;
    private  int changeColor = -1;

    //背景参数 画圆角矩形需要画笔，背景矩形  x 方向半径 y方向半径，画笔
    private  int layerWidth;
    private  int layerHeight;
    private  RectF layerRect;
    private  int layerRectLeft;
    private  int layerRectTop;
    private  int layerRectRight;
    private  int layerRectBottom;
    private  Paint layerPaint;
    //画两端两个小点 画圆需要 圆心坐标，半径 和画笔三个参数
    private  Paint  pointPaint;
    private  int pointLeftX,pointLeftY,pointLeftradius,pointRightX,pointRightY,pointRightradius;
    //画表示状态的大圆 参数同上
    private  Paint statePaint;
    private  int stateX,stateY,stateradius;

    //表示开关此时的状态共有三中四种状态 0 此时开关关闭状态 1 开关正在打开状态 2 开关正在关闭状态 3 开关打开状态
    // 0  3  状态独立    有 1 状态必有 3状态  有2 必有 0状态
    private  int state = 0; //默认0状态



    private SwitchListen listen;

    public SwitchOpen(Context context) {
        this(context,null);
    }

    public SwitchOpen(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwitchOpen(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        this.viewHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        this.viewWidth = View.MeasureSpec.getSize(widthMeasureSpec);

        layerWidth = viewWidth - 100;
        layerHeight = viewWidth / 3;
        Log.e("zlz",layerWidth + "layerWidth" + layerHeight);
        //bug : 必须设置 的宽 / 3 小于 设的 高   viewwidth / 3 < viewHeight

        setMeasuredDimension(viewWidth,viewHeight);
        init();

    }

    private  void init(){

        //初始化背景参数
        layerPaint = new Paint();
        layerPaint.setStyle(Paint.Style.FILL);//充满
        layerPaint.setAntiAlias(true);// 设置画笔的锯齿效果
        layerRectLeft = 50;
        layerRectTop = viewHeight / 2 - layerHeight / 2;
        layerRectRight = layerWidth + 50;
        layerRectBottom = viewHeight / 2 + layerHeight / 2;
        layerRect = new RectF(layerRectLeft,layerRectTop, layerRectRight,layerRectBottom);
        //初始化 点的 参数
        pointPaint = new Paint();
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.WHITE);
        pointPaint.setAntiAlias(true);
//        pointLeftX,pointLeftY,pointradius,pointRightX,pointRightY
        pointLeftX = layerRectLeft + layerHeight / 2;
        pointLeftY = viewHeight / 2;
        pointRightX = layerRectRight -  layerHeight / 2;
        pointRightY = pointLeftY;

        //初始化 大圆的参数
        statePaint = new Paint();
        statePaint.setStyle(Paint.Style.FILL);
        statePaint.setColor(Color.WHITE);
        statePaint.setAntiAlias(true);
        stateradius = layerHeight / 10 * 4;
    }


    public void setColor(int color){

       changeColor = color;

    }
    @Override
    protected void onDraw(Canvas canvas) {

        if(state == 0){

            closed(canvas);

        }else  if (state == 1){

            open(canvas);

        }else  if(state == 2){

            close(canvas);
        }else if(state == 3){

            opended(canvas);
        }

    }

    //0状态
    private  void closed(Canvas canvas){
        layerPaint.setColor(Color.GRAY);
        pointLeftradius = layerHeight / 10 * 4;
        pointRightradius = layerHeight/ 10;
        canvas.drawRoundRect(layerRect, layerHeight / 2, layerHeight / 2 , layerPaint);//第二个参数是x半径，第三个参数是y半径
        canvas.drawCircle(pointLeftX,pointLeftY,pointLeftradius,pointPaint);
        canvas.drawCircle(pointRightX,pointRightY,pointRightradius,pointPaint);
        state = 0;

    }

    //3状态
    private  void opended(Canvas canvas){

        layerPaint.setColor(Color.RED);
        if(changeColor != -1){

            layerPaint.setColor(changeColor);
        }
        pointLeftradius = layerHeight / 10 ;
        pointRightradius = layerHeight/ 10 * 4;
        canvas.drawRoundRect(layerRect, layerHeight / 2, layerHeight / 2 , layerPaint);//第二个参数是x半径，第三个参数是y半径
        canvas.drawCircle(pointLeftX,pointLeftY,pointLeftradius,pointPaint);
        canvas.drawCircle(pointRightX,pointRightY,pointRightradius,pointPaint);
        state = 3;

    }
    //1状态
    private  void open(Canvas canvas){


        canvas.drawRoundRect(layerRect, layerHeight / 2, layerHeight / 2 , layerPaint);//第二个参数是x半径，第三个参数是y半径
        canvas.drawCircle(pointLeftX,pointLeftY,pointLeftradius,pointPaint);
        canvas.drawCircle(pointRightX,pointRightY,pointRightradius,pointPaint);
        if(pointLeftradius > viewHeight / 10 ){

            pointLeftradius -= 1;
            pointRightradius += 1;
            postInvalidate();
        }
      if(pointLeftradius < viewHeight / 10  + 1){

          opended(canvas);
      }
    }
    private  void close(Canvas canvas){


        canvas.drawRoundRect(layerRect, layerHeight / 2, layerHeight / 2 , layerPaint);//第二个参数是x半径，第三个参数是y半径
        canvas.drawCircle(pointLeftX,pointLeftY,pointLeftradius,pointPaint);
        canvas.drawCircle(pointRightX,pointRightY,pointRightradius,pointPaint);
        if(pointRightradius > viewHeight / 10 ){

            pointLeftradius += 1;
            pointRightradius -= 1;
            postInvalidate();
        }
        if(pointRightradius < viewHeight / 10 + 1){
             closed(canvas);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_UP){

            if (state == 0){

                state = 1;
                postInvalidate();
                listen.open();

            }else if(state == 3){

                state = 2;
                postInvalidate();
                listen.close();

            }

        }



        return true;
    }

    public void setSwitchListen(SwitchListen switchListen){

        listen = switchListen;

    }





    public interface SwitchListen {

        public void open();

        public void close();
    }




}
