package com.ascend.wangfeng.latte.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fengye on 2017/9/6.
 * email 1040441325@qq.com
 */

public class AutoPhotoLayout extends LinearLayoutCompat {
    private int mCurrentNum = 0;
    private final int mMaxNum;
    private final int mMaxLineNum;
    private IconTextView mIconAdd = null;
    private LayoutParams mParams = null;
    //要删除的图片ID
    private int mDeleteId = 0;
    private AppCompatImageView mTargetImageView = null;
    private final int mImageMargin;
    private LatteDelegate mDelegate = null;
    private ArrayList<View> mLineViews = null;
    private AlertDialog mTargetDialog = null;
    private static final String ICON_TEXT = "{fa-plus}";
    private final float mIconSize;
    private final ArrayList<ArrayList<View>> ALL_VIEWS = new ArrayList<>();
    private final ArrayList<Integer> LINE_HEIGHTS = new ArrayList<>();
    private final HashMap<Integer,Uri> mUris =new HashMap<>();

    //防止多次的测量和布局过程
    private boolean mIsOnceInitOnMeasure = false;
    private boolean mHasInitOnLayout = false;

    public final void onCrop(Uri uri) {
        createNewImageView();
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop();
        mUris.put(mTargetImageView.getId(),uri);
        Glide.with(mDelegate)
                .load(uri)
                .apply(options)
                .into(mTargetImageView);

    }
    public  HashMap<Integer,Uri> getUris(){

        return mUris;
    }

    private void createNewImageView() {
        mTargetImageView = new AppCompatImageView(getContext());
        mTargetImageView.setId(mCurrentNum);
        mTargetImageView.setLayoutParams(mParams);
        mTargetImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取图片id
                mDeleteId = view.getId();
                mTargetDialog.show();
                final Window window = mTargetDialog.getWindow();
                if (window != null) {
                    window.setContentView(R.layout.dialog_image_click_panel);
                    window.setGravity(Gravity.BOTTOM);
                    window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
                    final WindowManager.LayoutParams params = window.getAttributes();
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    params.dimAmount = .5f;
                    window.setAttributes(params);
                    window.findViewById(R.id.dialog_image_clicked_btn_delete)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final AppCompatImageView deleteImageView =
                                            (AppCompatImageView) findViewById(mDeleteId);
                                    //设置动画
                                    final AlphaAnimation animation = new AlphaAnimation(1, 0);
                                    animation.setDuration(500);
                                    animation.setRepeatCount(0);
                                    animation.setFillAfter(true);
                                    animation.setStartOffset(0);
                                    deleteImageView.setAnimation(animation);
                                    animation.start();
                                    mUris.remove(mDeleteId);
                                    AutoPhotoLayout.this.removeView(deleteImageView);
                                    mCurrentNum -= 1;
                                    if (mCurrentNum < mMaxNum) {
                                        mIconAdd.setVisibility(VISIBLE);
                                    }
                                    mTargetDialog.cancel();
                                }
                            });
                    window.findViewById(R.id.dialog_image_clicked_btn_undetermined)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mTargetDialog.cancel();
                                }
                            });
                    window.findViewById(R.id.dialog_image_clicked_btn_cancel)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mTargetDialog.cancel();
                                }
                            });
                }
            }
        });
        //添加子View时传入位置
        this.addView(mTargetImageView, mCurrentNum);
        mCurrentNum++;
        //当添加至上限,隐藏添加键
        if (mCurrentNum >= mMaxNum) {
            mIconAdd.setVisibility(View.GONE);
        }
    }


    public AutoPhotoLayout(Context context) {
        this(context, null);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context
                .obtainStyledAttributes(attrs, R.styleable.camera_flow_layout);
        mMaxNum = typedArray.getInt(R.styleable.camera_flow_layout_max_count, 1);
        mMaxLineNum = typedArray.getInt(R.styleable.camera_flow_layout_line_count, 3);
        mImageMargin = typedArray.getInt(R.styleable.camera_flow_layout_item_margin, 0);
        mIconSize = typedArray.getDimension(R.styleable.camera_flow_layout_icon_size, 20);
        //回收资源
        typedArray.recycle();
    }

    public final void setDelegate(LatteDelegate delegate) {
        this.mDelegate = delegate;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initAddIcon();
        mTargetDialog = new AlertDialog.Builder(getContext()).create();
    }

    private void initAddIcon() {
        mIconAdd = new IconTextView(getContext());
        mIconAdd.setText(ICON_TEXT);
        mIconAdd.setGravity(Gravity.CENTER);
        mIconAdd.setBackgroundColor(Color.WHITE);
        mIconAdd.setTextSize(mIconSize);
        mIconAdd.setBackgroundResource(R.drawable.border_text);
        mIconAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               // mDelegate.startCameraWithCheck();
            }
        });
        this.addView(mIconAdd);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        final int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //wrap_content
        int width = 0;
        int height = 0;
        //记录 每一行的宽和高
        int lineWidth = 0;
        int lineHeight = 0;
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            final View child = getChildAt(i);
            //测量子View宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //子View宽高
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            //换行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //对比得到最大宽度
                width = Math.max(width, lineWidth);
                //重置lineWidth
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                //未换行,
                width += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //最后一个子控件
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                //判断是否数量达上限
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );
        //设置一行所有图片的宽高
        final int imageSideLen = sizeWidth / mMaxLineNum;
        if (!mIsOnceInitOnMeasure) {
            mParams = new LayoutParams(imageSideLen, imageSideLen);
            mIsOnceInitOnMeasure = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ALL_VIEWS.clear();
        LINE_HEIGHTS.clear();
        //当前ViewGroup的宽度
        final int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        if (!mHasInitOnLayout) {
            mLineViews = new ArrayList<>();
            mHasInitOnLayout = true;
        }
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            final View child = getChildAt(i);
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            final int childWidth = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin >
                    width - getPaddingLeft() - getPaddingRight()) {
                LINE_HEIGHTS.add(lineHeight);
                ALL_VIEWS.add(mLineViews);
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                mLineViews.clear();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, lineHeight + lp.topMargin + lp.bottomMargin);
            mLineViews.add(child);
        }
        //处理最后一行
        LINE_HEIGHTS.add(lineHeight);
        ALL_VIEWS.add(mLineViews);
        //设置子View位置
        int left = getPaddingLeft();
        int top = getPaddingTop();
        final int lineNum = ALL_VIEWS.size();
        for (int i = 0; i < lineNum; i++) {
            //当前行所有的View
            mLineViews = ALL_VIEWS.get(i);
            lineHeight = LINE_HEIGHTS.get(i);
            final int size = mLineViews.size();
            for (int j = 0; j < size; j++) {
                final View child = mLineViews.get(j);
                if (child.getVisibility() == GONE) {
                    continue;
                }
                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                final int lc = left + lp.leftMargin;
                final int tc = top + lp.topMargin;
                final int rc = lc + child.getMeasuredWidth() - mImageMargin;
                final int bc = tc + child.getMeasuredHeight();
                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
        mIconAdd.setLayoutParams(mParams);
        mHasInitOnLayout = false;


    }
}
