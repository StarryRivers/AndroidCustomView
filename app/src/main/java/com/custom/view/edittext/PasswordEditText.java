package com.custom.view.edittext;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.custom.view.R;

/**
 * Custom Password EditText
 *
 * @author StarryRivers
 */
public class PasswordEditText extends AppCompatEditText {
    private Context mContext;
    /**
     * 绘制EditText的宽高
     */
    private int height, width;
    /**
     * 分割线宽度，用来计算分割点的坐标
     */
    private int divingWidth;
    private int count = 6;
    /**
     * 输入位置
     */
    private int position = 0;
    /**
     * 密码框中第一个黑色圆心坐标
     */
    private int startX, startY;
    /**
     * 密码圆半径
     */
    private int radius = 12;
    private RectF rectF = new RectF();
    /**
     * 绘制宽度
     */
    private int borderWidth = 2;
    private int lineWidth = 1;
    /**
     * 输入框圆角
     */
    private int roundAngle = 10;
    /**
     * 边框、分割线、圆心的画笔
     */
    private Paint borderPaint, divingPaint, circlePaint;
    /**
     * 绘制颜色，画笔颜色
     */
    private int borderColor = Color.GRAY;
    private int divingColor = Color.GRAY;
    private int circleColor = Color.BLACK;

    public PasswordEditText(@NonNull Context context) {
        super(context);
        mContext = context;
        setAttribute(null);
        initPaint();
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(count)});
        this.setCursorVisible(false);
    }

    public PasswordEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setAttribute(attrs);
        initPaint();
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(count)});
        this.setCursorVisible(false);
    }

    /**
     * Initial Paint
     */
    private void initPaint() {
        // 抗锯齿
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setAntiAlias(true);
        // 设置画笔宽度
        borderPaint.setStrokeWidth(borderWidth);
        // 设置画笔风格
        borderPaint.setStyle(Paint.Style.STROKE);
        // 设置画笔颜色
        borderPaint.setColor(borderColor);
        // 分割线画笔
        divingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        divingPaint.setAntiAlias(true);
        divingPaint.setStrokeWidth(lineWidth);
        divingPaint.setColor(divingColor);
        divingPaint.setStyle(Paint.Style.FILL);
        // 圆心画笔
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(circleColor);
        circlePaint.setStrokeWidth(radius);
    }

    /**
     * set Attribute
     */
    private void setAttribute(AttributeSet attrs) {
        // 获取配置属性
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
        count = typedArray.getInt(R.styleable.PasswordEditText_inputMaxNum, count);
        roundAngle = typedArray.getDimensionPixelOffset(R.styleable.PasswordEditText_roundAngle, roundAngle);
        borderColor = typedArray.getColor(R.styleable.PasswordEditText_borderColor, borderColor);
        divingColor = typedArray.getColor(R.styleable.PasswordEditText_borderColor, divingColor);
        circleColor = typedArray.getColor(R.styleable.PasswordEditText_circleColor, circleColor);
        borderWidth = typedArray.getDimensionPixelOffset(R.styleable.PasswordEditText_borderWidth, borderWidth);
        lineWidth = typedArray.getDimensionPixelOffset(R.styleable.PasswordEditText_divingWidth, lineWidth);
        radius = typedArray.getDimensionPixelOffset(R.styleable.PasswordEditText_circleRadius, radius);
        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 获取宽高坐标等数据
        height = h;
        width = w;
        // 分割线宽度
        divingWidth = w / count;
        // 密码黑色圆坐标
        startX = w / count / 2;
        startY = h / 2;
        rectF.set(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制圆角矩形
        canvas.drawRoundRect(rectF, roundAngle, roundAngle, borderPaint);
        // 绘制分割线，5个分割线
        for (int i = 1; i < count; i++) {
            canvas.drawLine(divingWidth * i, 0, divingWidth * i, height, divingPaint);
        }
        for (int i = 0; i < position; i++) {
            canvas.drawCircle(startX * (2 * i + 1), startY, radius, circlePaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        // 获取当前输入位置
        position = text.length();
    }
}

