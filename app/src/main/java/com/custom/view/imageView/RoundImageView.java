package com.custom.view.imageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.custom.view.R;

/**
 * Custom Round ImageView
 *
 * @author StarryRivers
 */
public class RoundImageView extends AppCompatImageView {
    private Context mContext;
    private float width, height;
    private int defaultRadius = 10;
    private int radius;
    private int leftTopRadius, rightTopRadius, leftBottomRadius, rightBottomRadius;

    public RoundImageView(@NonNull Context context) {
        super(context);
        this.mContext = context;
        init(null);
    }

    public RoundImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    public RoundImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // 获取属性
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        radius = typedArray.getDimensionPixelOffset(R.styleable.RoundImageView_radius, defaultRadius);
        leftTopRadius = typedArray.getDimensionPixelOffset(R.styleable.RoundImageView_leftTopRadius, defaultRadius);
        rightTopRadius = typedArray.getDimensionPixelOffset(R.styleable.RoundImageView_rightTopRadius, defaultRadius);
        leftBottomRadius = typedArray.getDimensionPixelOffset(R.styleable.RoundImageView_leftBottomRadius, defaultRadius);
        rightBottomRadius = typedArray.getDimensionPixelOffset(R.styleable.RoundImageView_rightBottomRadius, defaultRadius);
        if (radius != 0) {
            if (leftTopRadius == 0) {
                leftTopRadius = radius;
            }
            if (rightTopRadius == 0) {
                rightTopRadius = radius;
            }
            if (leftBottomRadius == 0) {
                leftBottomRadius = radius;
            }
            if (rightBottomRadius == 0) {
                rightBottomRadius = radius;
            }
        }
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 保证图片宽高大于圆角宽高， 获取圆角的宽高
        // 取横着大的长度
        int maxLeft = Math.max(leftTopRadius, leftBottomRadius);
        int maxRight = Math.max(rightTopRadius, rightBottomRadius);
        int minWidth = maxLeft + maxRight;
        // 取竖着大的长度
        int maxTop = Math.max(leftTopRadius, rightTopRadius);
        int maxBottom = Math.max(leftBottomRadius, rightBottomRadius);
        int minHeight = maxTop + maxBottom;
        if (width > minWidth && height > minHeight) {
            Path path = new Path();
            //四个角：右上，右下，左下，左上
            path.moveTo(leftTopRadius, 0);
            path.lineTo(width - rightTopRadius, 0);
            path.quadTo(width, 0, width, rightTopRadius);

            path.lineTo(width, height - rightBottomRadius);
            path.quadTo(width, height, width - rightBottomRadius, height);

            path.lineTo(leftBottomRadius, height);
            path.quadTo(0, height, 0, height - leftBottomRadius);

            path.lineTo(0, leftTopRadius);
            path.quadTo(0, 0, leftTopRadius, 0);

            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }
}

