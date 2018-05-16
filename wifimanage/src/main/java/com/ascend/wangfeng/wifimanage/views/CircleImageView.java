package com.ascend.wangfeng.wifimanage.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.ascend.wangfeng.wifimanage.R;

/**
 * Created by fengye on 2018/4/23.
 * email 1040441325@qq.com
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    // 为避免出现小数,比例采用倒数
    private static final int RADIUS_ICON = 3;// 小图标半径比例的倒数
    private static final int RADIUS_POINT = 5;//点半径比例的倒数
    private static final int BLANK = 20; //空白距离比例

    public static final int TYPE_NORMAL = 0;//有背景,有色图;
    public static final int TYPE_WHITE = 1;//有背景,纯白色图;
    public static final int TYPE_FULL = 2;//无背景,图片充满背景

    private int mBg;//背景颜色
    private int mRadius;
    private int mRadiusIcon;
    private int mRadiusPoint;
    private int mBlank;
    private Drawable mImage;// 图片
    private Drawable mIcon;//图标
    private boolean mShowOnline;//是否绘制点
    private boolean mOnline;//是否在线
    private int mSrcType;// 主图片绘制类型
    private int mWidth;
    private int mHeight;
    private Paint mImgPaint;// 绘制bitmap
    private Paint mIconPaint;// icon
    private Paint mBgPaint;//背景色
    private Paint mBlankPaint;//绘制空白
    private Paint mOnlinePaint;//绘制点;


    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0);
        if (ta.hasValue(R.styleable.CircleImageView_online)) {
            mShowOnline = true;
            mOnline = ta.getBoolean(R.styleable.CircleImageView_online, false);
        }
        if (ta.hasValue(R.styleable.CircleImageView_icon)) {
            mIcon = ta.getDrawable(R.styleable.CircleImageView_icon);
        }
        if (ta.hasValue(R.styleable.CircleImageView_bg)) {
            mBg = ta.getColor(R.styleable.CircleImageView_bg, 0);
        }
        if (ta.hasValue(R.styleable.CircleImageView_srcType)) {
            mSrcType = ta.getInt(R.styleable.CircleImageView_srcType, 0);
        }
        // 不再使用typedArray,释放资源,以便系统复用;
        ta.recycle();

    }

    // 设置图片
    public void setImage(Drawable drawable) {
        super.setImageDrawable(drawable);
        invalidate();
    }

    public void setImage(@DrawableRes int id) {
        setImage(getResources().getDrawable(id));
    }

    // 设置图标
    public void setIcon(Drawable drawable) {
        mIcon = drawable;
        invalidate();
    }
    public void setIcon(@DrawableRes int  id) {
        setIcon(getResources().getDrawable(id));
    }
    // 设置状态
    public void setState(boolean on) {
        mOnline = on;
        if (mOnlinePaint != null)
            mOnlinePaint.setColor(mOnline ? getResources().getColor(R.color.colorAccent) : Color.GRAY);
        invalidate();
    }

    public void setBg(int color) {
        this.mBg = color;
    }

    public void setSrcType(int type) {
        this.mSrcType = type;
    }

    private void init() {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mRadius = (mWidth > mHeight ? mHeight / 2 : mWidth / 2) - 2;
        mRadiusIcon = mRadius / RADIUS_ICON;
        mRadiusPoint = mRadius / RADIUS_POINT;
        mBlank = mRadius / BLANK;
        initPaint();
    }

    private void initPaint() {
        mImgPaint = new Paint();
        mImgPaint.setAntiAlias(true);

        mIconPaint = new Paint();
        mImgPaint.setAntiAlias(true);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setColor(mBg != 0 ? mBg : getResources().getColor(R.color.textFir));

        mBlankPaint = new Paint();
        mBlankPaint.setAntiAlias(true);
        mBgPaint.setStrokeWidth(2.0f);
        mBlankPaint.setColor(Color.WHITE);
        mBlankPaint.setStyle(Paint.Style.FILL);

        mOnlinePaint = new Paint();
        mOnlinePaint.setAntiAlias(true);
        mOnlinePaint.setColor(mOnline ? getResources().getColor(R.color.colorAccent) : Color.GRAY);
        mOnlinePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle(canvas);
        if (mIcon != null) drawIcon(canvas);
        if (mShowOnline) drawPoint(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 取短边长度
        int result = MeasureSpec.getSize(widthMeasureSpec) > MeasureSpec.getSize(heightMeasureSpec) ?
                heightMeasureSpec : widthMeasureSpec;
        super.onMeasure(result, result);
        init();
    }

    private void drawCircle(Canvas canvas) {
        Bitmap bitmap = null;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            bitmap = drawableToBitmap(drawable);
            if (!mOnline && mShowOnline) {
                ColorMatrix cm = new ColorMatrix();
                cm.setSaturation(0);
                ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
                mImgPaint.setColorFilter(f);
            }

            // 缩放
            switch (mSrcType) {
                case TYPE_NORMAL:
                    drawCircleWithBg(bitmap, canvas);
                    break;
                case TYPE_WHITE:
                    // paint 改为纯白色
                    ColorMatrix cm = new ColorMatrix(new float[]{
                            1, 0, 0, 0, 256,
                            0, 1, 0, 0, 256,
                            0, 0, 1, 0, 256,
                            0, 0, 0, 1, 0,
                    });
                    ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
                    mImgPaint.setColorFilter(f);
                    drawCircleWithBg(bitmap, canvas);
                    break;
                case TYPE_FULL:
                    drawFullCircle(bitmap, canvas);
                    break;
            }
            // recycle后其他图片加载异常
            // bitmap.recycle();
        }
    }

    private void drawCircleWithBg(Bitmap bitmap, Canvas canvas) {
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = (mRadius * 2 / 4) * 2.0f / Math.min(bitmap.getHeight(), bitmap.getWidth());

        // 绘制主圆
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mBgPaint);
        int left = (int) (mWidth / 2 - scale * bitmap.getWidth() / 2);
        int top = (int) (mHeight / 2 - scale * bitmap.getHeight() / 2);
        int right = (int) (mWidth / 2 + scale * bitmap.getWidth() / 2);
        int bottom = (int) (mHeight / 2 + scale * bitmap.getHeight() / 2);
        Rect rect = new Rect(left, top, right, bottom);
        canvas.drawBitmap(bitmap, null, rect, mImgPaint);
    }

    private void drawFullCircle(Bitmap bitmap, Canvas canvas) {
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = mRadius * 2.0f / Math.min(bitmap.getHeight(), bitmap.getWidth());
        Matrix matrix = new Matrix();
        matrix.setTranslate(mBlank, mBlank);
        matrix.preScale(scale, scale);
        shader.setLocalMatrix(matrix);
        mImgPaint.setShader(shader);
        // 绘制主圆
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mImgPaint);
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) return null;
        if (drawable instanceof BitmapDrawable)
            return ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = null;
        try {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void drawIcon(Canvas canvas) {
        Bitmap bitmap = null;
        if (mIcon != null) {
            bitmap = drawableToBitmap(mIcon);
            BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            float scale = (mRadiusIcon - 3 * mBlank) * 2.0f / Math.min(bitmap.getHeight(), bitmap.getWidth());
            ColorMatrix cm = new ColorMatrix(new float[]{
                    1, 0, 0, 0, 256,
                    0, 1, 0, 0, 256,
                    0, 0, 1, 0, 256,
                    0, 0, 0, 1, 0,
            });
            ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
            mIconPaint.setColorFilter(f);
            int left = (int) ((mWidth - mRadiusIcon) - scale * bitmap.getWidth() / 2);
            int top = (int) (mRadiusIcon - scale * bitmap.getHeight() / 2);
            int right = (int) ((mWidth - mRadiusIcon) + scale * bitmap.getWidth() / 2);
            int bottom = (int) (mRadiusIcon + scale * bitmap.getHeight() / 2);
            Rect rect = new Rect(left, top, right, bottom);
            canvas.drawCircle(mWidth - mRadiusIcon, mRadiusIcon, mRadiusIcon, mBlankPaint);
            canvas.drawCircle(mWidth - mRadiusIcon, mRadiusIcon, mRadiusIcon - mBlank, mBgPaint);
            canvas.drawBitmap(bitmap, null, rect, mIconPaint);
            // bitmap.recycle();
        }
    }

    private void drawPoint(Canvas canvas) {
        canvas.drawCircle(mWidth / 2 + mRadius * 7 / 10, mHeight / 2 + mRadius * 7 / 10, mRadiusPoint, mBlankPaint);
        canvas.drawCircle(mWidth / 2 + mRadius * 7 / 10, mHeight / 2 + mRadius * 7 / 10, mRadiusPoint - mBlank, mOnlinePaint);
    }
    @Deprecated
    // 无用方法,保留是为了记录矩阵的bitmap变换
    private void  drawBitmap(Bitmap bitmap,Canvas canvas){
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = (mRadius * 2 / 4) * 2.0f / Math.min(bitmap.getHeight(), bitmap.getWidth());
        // 矩阵移动缩放图片
        Matrix matrix = new Matrix();
        matrix.setTranslate(mRadius / 4, mRadius / 4);
        matrix.preScale(scale, scale);
        shader.setLocalMatrix(matrix);
        mImgPaint.setShader(shader);
        // 绘制主圆
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mImgPaint);
    }
}
