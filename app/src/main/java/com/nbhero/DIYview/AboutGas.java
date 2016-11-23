package com.nbhero.DIYview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.nbhero.flower.R;
import com.nbhero.util.tool;

/**
 * Created by zhenglingzhong on 2016/11/8.
 */

public class AboutGas extends View{

    private  int   mCycleFactorW ;
    //view
    private  int viewheight;
    private  int viewwidth;
    private  Paint paintbit;
    private  Bitmap mybit;
    private  int bitWidth;
    private  int bitHeight;
    private PorterDuffXfermode mXfermode;
    private Rect mSrcRect, mDestRect,mDynamicRect;
    private  Paint mBitPaint;

    private  float move = 0.5f;


    public AboutGas(Context context) {
        this(context,null);
    }

    public AboutGas(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AboutGas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        viewheight = View.MeasureSpec.getSize(heightMeasureSpec);
        viewwidth = View.MeasureSpec.getSize(widthMeasureSpec);
        if (viewheight > viewwidth){
            viewheight = viewwidth;
        }
        setMeasuredDimension(viewwidth, viewheight);
        init();

    }


    private  void init(){

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        //画笔
        paintbit = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintbit.setFilterBitmap(true);
        paintbit.setDither(true);
        paintbit.setAntiAlias(true);
        paintbit.setColor(Color.YELLOW);
        //图像对象
        mybit = ((BitmapDrawable) getResources().getDrawable(R.drawable.gastudio))
                .getBitmap();
        bitWidth = mybit.getWidth();
        bitHeight = mybit.getHeight();
        Log.e("zlz","width" + bitWidth);
        Log.e("zlz","bitHeight" + bitHeight);
        int rawHeight = bitHeight;
        int rawWidth = bitWidth;
        // 设定图片新的高宽
        int newHeight = viewwidth;
        // 计算缩放因子
        float heightScale = ((float) newHeight) / rawHeight;

        // 新建立矩阵
        Matrix matrix = new Matrix();
        matrix.postScale(heightScale, heightScale);

        mybit = Bitmap.createBitmap(mybit, 0, 0, rawWidth,
                rawHeight, matrix, true);

        bitWidth = mybit.getWidth();
        bitHeight = mybit.getHeight();

        mSrcRect = new Rect(0,0,bitWidth,bitHeight);

        mDestRect = new Rect(bitHeight / 2 - bitWidth / 2,0,bitHeight / 2 - bitWidth / 2 + bitWidth,bitHeight);
        mDynamicRect = new Rect(bitHeight / 2 - bitWidth / 2,bitHeight,bitHeight / 2 - bitWidth / 2 + bitWidth,bitHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBitPaint = new Paint();
        mBitPaint.setColor(Color.YELLOW);
        int saveLayerCount = canvas.saveLayer(0, 0, viewwidth, viewwidth, mBitPaint,
                Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mybit,mSrcRect,mDestRect,paintbit);
        mBitPaint.setXfermode(mXfermode);
        // 绘制源图形

        canvas.drawRect(mDynamicRect, mBitPaint);
        if(mDynamicRect.top >= (int)((1-move) * (float) bitHeight)){
            Log.e("zlz","mDynamicRect.top " + mDynamicRect.top );
            mDynamicRect.top -= 1;
            postInvalidate();
        }
    }

    public  void setGas(float gas){

        this.move = gas;

    }

    private  Bitmap makeBit(){

        //得到图片原始的高宽
        int rawHeight = bitHeight;
        int rawWidth = bitWidth;
        // 设定图片新的高宽
        int newHeight = viewwidth;
        // 计算缩放因子
        float heightScale = ((float) newHeight) / rawHeight;
        // 新建立矩阵
        Matrix matrix = new Matrix();
        matrix.postScale(heightScale, heightScale);
        // 设置图片的旋转角度
        // matrix.postRotate(-30);
        // 设置图片的倾斜
        // matrix.postSkew(0.1f, 0.1f);
        // 将图片大小压缩
        // 压缩后图片的宽和高以及kB大小均会变化
        Bitmap newBitmap = Bitmap.createBitmap(mybit, 0, 0, rawWidth,
                rawHeight, matrix, true);

        return  newBitmap;

    }
}




