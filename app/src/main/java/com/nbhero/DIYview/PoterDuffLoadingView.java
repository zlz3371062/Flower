package com.nbhero.DIYview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;


import com.nbhero.flower.R;
import com.nbhero.util.tool;


public class PoterDuffLoadingView extends View {

    private  Context con;

    private Resources mResources;
    private Paint mBitPaint;
    protected Bitmap mBitmap;

    private int mTotalWidth, mTotalHeight;
    private int mBitWidth, mBitHeight;
    private Rect mSrcRect, mDestRect;
    private PorterDuffXfermode mXfermode;

    private Rect mDynamicRect;
    private int mCurrentTop;
    private int mStart, mEnd;

    private  int coun = 0;

    public PoterDuffLoadingView(Context context) {
        super(context);
        this.con = context;
        mResources = getResources();
        initBitmap();
        initPaint();
        initXfermode();
    }

    private void initXfermode() {
        // 叠加处绘制源图
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    private void initPaint() {
        // 初始化paint
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
        mBitPaint.setAntiAlias(true);
        mBitPaint.setColor(Color.YELLOW);
        mBitPaint.setTextSize(tool.dip2px(con,12));
    }

    private void initBitmap() {
        // 初始化bitmap
        mBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.gastudio))
                .getBitmap();
        mBitWidth = mBitmap.getWidth();
        mBitHeight = mBitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float  zlz   = (264 - mCurrentTop)  ;
        float  pre = zlz /264;
        if(pre < 0.1){

            mBitPaint.setColor(Color.YELLOW);
        }
        else if(0.1 < pre && pre< 0.5  ){
            mBitPaint.setColor(Color.GREEN);

        }else if(0.5 < pre && pre< 1 ){
            mBitPaint.setColor(Color.BLUE);
        }

        // 存为新图层
        int saveLayerCount = canvas.saveLayer(0, 0, mTotalWidth, mTotalHeight, mBitPaint,
                Canvas.ALL_SAVE_FLAG);
        // 绘制目标图
        canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mBitPaint);
        // 设置混合模式
        mBitPaint.setXfermode(mXfermode);
        // 绘制源图形
        canvas.drawRect(mDynamicRect, mBitPaint);
        //绘制文字
        int me = (int) (pre * 100);
        // 清除混合模式
        mBitPaint.setXfermode(null);
        // 恢复保存的图层；
        canvas.restoreToCount(saveLayerCount);

        // 改变Rect区域，真实情况下时提供接口传入进度，计算高度

        mCurrentTop -= 2;

        if (mCurrentTop <= mEnd) {
            mCurrentTop = mStart;
        }

        mDynamicRect.top = mCurrentTop;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mSrcRect = new Rect(0, 0, mBitWidth, mBitHeight);
        // 让左边和上边有些距离
        int left = (int) TypedValue.complexToDimension(20, mResources.getDisplayMetrics());
        mDestRect = new Rect(left, left, left + mBitWidth, left + mBitHeight);
        mStart = left + mBitHeight;
        Log.e("zlz",mStart + "");
        mCurrentTop = mStart;
        mEnd = left;
        mDynamicRect = new Rect(left, mStart, left + mBitWidth, left + mBitHeight); //(左上右下)
    }
}