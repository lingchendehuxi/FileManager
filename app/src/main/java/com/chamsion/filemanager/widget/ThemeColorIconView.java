package com.chamsion.filemanager.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.util.BitmapUtil;
import com.chamsion.filemanager.util.ColorUtil;

/**
 * Created by panruijie on 16/12/27.
 * Email : zquprj@gmail.com
 * 能根据主题色变化icon颜色的view
 */

public class ThemeColorIconView extends View {

    private Bitmap mBitmap;
    private int mBitmapWidth;
    private int mBitmapHeight;
    private Paint mBitmapPaint;
    private int mHeight;
    private int mWidth;

    public ThemeColorIconView(Context context) {
        this(context, null);
    }

    public ThemeColorIconView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemeColorIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ThemeColorIconView);
        Drawable icon = ta.getDrawable(R.styleable.ThemeColorIconView_icon);

        int themePrimary = ColorUtil.getColorPrimary(context);
        icon.setColorFilter(themePrimary, PorterDuff.Mode.SRC_ATOP);

        mBitmap = BitmapUtil.getBitmapFromDrawable(icon);
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);
        mBitmapPaint.setFilterBitmap(true);
        mBitmapPaint.setStrokeWidth(5);
        mBitmapPaint.setStyle(Paint.Style.STROKE);

        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, mWidth / 2 - mBitmapWidth / 2, mHeight / 2 - mBitmapHeight / 2, mBitmapPaint);
        //invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }
}
