package com.nbhero.DIYview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.nbhero.flower.R;
import com.nbhero.util.tool;




/**
 * Created by zhenglingzhong on 2016/11/7.
 * 描述：实例化出一个对象然后直接调用set方法传入 运行时间 或者运行和租用总时长
 *
 */

public class ProView extends View {

    private   Context con;

    //view
    private   int left;
    private   int viewheight;
    private   int viewwidth;
    private   PorterDuffXfermode porterDuffXfermode;

    //point1
    private  Paint paintStart;
    private  int  startX;
    private  int  startY;
    private  int  startWidth;

    //line1
    private  Paint paintLineback;
    private  int lineBackStartX;
    private  int lineBackStartY;
    private  int lineBackEndX;
    private  int lineBackEndY;
    private  int lineBackWidth;

    //line2

    private  Paint paintLinePro;
    private  int lineProStartX;
    private  int lineProStartY;
    private  int lineProEndX;
    private  int lineProEndY;

    //point2

    private  Paint paintEnd;
    private  int  endX;
    private  int  endY;
    private  int  endWidth;

    //数据
    private  float pro = 1.0f;
    private  float linewidth;
    private  int move = 0;

    private  int stype = -1; //0为自运营 1为租用
    //运行时间
    private  int runtimeh = -1;
    private  int runtimem = -1;
    //租用时间
    private  int renttimeh = -1;
    private  int renttimem = -1;

    //文字
    private  Paint painttextTop;
    private  Paint painttextBottom;



    private int speed = 2;
    public ProView(Context context) {
        this(context,null);
    }

    public ProView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        con = context;

    }


    public   void set(int runtime){

        stype = 0;

        runtimeh = runtime /60;
        runtimem = runtime % 60;
        pro = 1.0f;

    }

    public   void set(int runtime,int renttime){
        stype = 1;
        pro = runtime / renttime;


        runtimeh = runtime /60;
        runtimem = runtime % 60;
        renttimeh = renttime / 60;
        renttimem = renttime % 60;

        pro = (float) runtime /(float) renttime;

    }
    /**
     *传入速度int型 2 到10有效 范围外默认为2
     */
    public void setSpeed(int speed){

        if(2<speed && speed<10){

            this.speed = speed;
        }


    }

    //初始化画笔
    private  void init(){
        //view

        left = tool.dip2px(con,40);
        move =  left + startWidth * 2 ;
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        //起始点
        paintStart = new Paint();
        paintStart.setColor(getResources().getColor(R.color.headcolor));
        paintStart.setStrokeWidth(tool.dip2px(con,14));
        startWidth =  tool.dip2px(con,10);
        startX = left + startWidth  ;
        startY = viewheight / 2;
        //背景线条
        paintLineback  = new Paint();
        paintLineback.setColor(getResources().getColor(R.color.textcolor));
        paintLineback.setStrokeWidth(tool.dip2px(con,2));
        lineBackStartX = left + startWidth * 2;
        lineBackStartY = startY;
        lineBackEndX = viewwidth - left - startWidth * 2;
        lineBackEndY =  startY;
        lineBackWidth =  lineBackEndX - lineBackStartX;
        //进度条
        paintLinePro = new Paint();
        paintLinePro.setColor(getResources().getColor(R.color.headcolor));
        paintLinePro.setStrokeWidth(tool.dip2px(con,3));
        lineProStartX = lineBackStartX;
        lineProStartY = startY;
        linewidth = pro * lineBackWidth;
        lineProEndX = (int)linewidth + left + startWidth * 2;
        lineProEndY = startY;
        //结束点
        paintEnd = new Paint();
        paintEnd.setColor(getResources().getColor(R.color.textcolor));
        endX = viewwidth -  startX;
        endY =  startY;
        endWidth = startWidth;

        //文字
        painttextTop = new Paint();
//        painttextTop.setStrokeWidth(tool.dip2px(con,16));
        painttextTop.setTextSize(tool.dip2px(con,16));
        painttextTop.setTextAlign(Paint.Align.CENTER);
        painttextTop.setColor(getResources().getColor(R.color.headcolor));
//文字下
        painttextBottom = new Paint();
//        painttextBottom.setStrokeWidth(tool.dip2px(con,16));
        painttextBottom.setTextSize(tool.dip2px(con,12));
        painttextBottom.setTextAlign(Paint.Align.CENTER);
        painttextBottom.setColor(getResources().getColor(R.color.headcolor));
        Log.e("zlz",lineProStartX+"lineProStartX");
        Log.e("zlz",lineBackWidth+"lineBackWidth");
        Log.e("zlz",lineBackEndX+"lineBackEndX");
        Log.e("zlz",lineProEndX+"lineProEndX");

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        viewheight = View.MeasureSpec.getSize(heightMeasureSpec);
        viewwidth = View.MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(viewwidth, viewheight);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(stype == 0){
            //自运营状态

            canvas.drawText(runtimeh+"h:"+runtimem+"m",endX ,endY - tool.dip2px(con,20),painttextTop);

            canvas.drawText("运行总时长",endX ,endY + tool.dip2px(con,30),painttextBottom);

        }else if(stype == 1){
            //租用状态


            canvas.drawText(runtimeh+"h:"+runtimem+"m",startX ,endY - tool.dip2px(con,20),painttextTop);

            canvas.drawText("运行总时长",startX ,endY + tool.dip2px(con,30),painttextBottom);

            canvas.drawText(renttimeh+"h:"+renttimem+"m",endX ,endY - tool.dip2px(con,20),painttextTop);

            canvas.drawText("租用总时长",endX ,endY + tool.dip2px(con,30),painttextBottom);
            //浮标
            canvas.drawLine(move,startY - startWidth,move,startY+startWidth,paintLinePro);
            canvas.drawText((int)(((float)(move - left - startWidth * 2)/ (float)lineBackWidth) * 100) + 1 + "%",move,endY + tool.dip2px(con,30),painttextBottom );

        }

        canvas.drawLine(lineBackStartX,lineBackStartY,lineBackEndX,lineBackEndY,paintLineback);
        canvas.drawCircle(startX,startY,startWidth,paintStart);
        canvas.drawCircle(endX,endY,endWidth,paintEnd);
        paintLinePro.setXfermode(porterDuffXfermode);
        canvas.drawLine(lineProStartX,lineProStartY,move,lineBackEndY,paintLinePro);




        if(move + speed < lineProEndX ) {

            move += speed;
            postInvalidate();

        }
    }

}
