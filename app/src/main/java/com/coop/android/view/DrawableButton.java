package com.coop.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

public class DrawableButton extends Button {

    public DrawableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas = getTopCanvas(canvas);
        super.onDraw(canvas);
    }

    private Canvas getTopCanvas(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables == null) {
            return canvas;
        }
        Drawable drawable = drawables[0];// 判断左面的drawable是否存在
        if (drawable == null) {
            drawable = drawables[2];// 判断右面的drawable是否存在
        }

        float textSize = getPaint().measureText(getText().toString());
        int ImgWidth = drawable.getIntrinsicWidth();
        int buttonPadding = getCompoundDrawablePadding();
        float contentWidth = textSize + ImgWidth + buttonPadding;//获取当前所占宽
        int rightPadding = (int) (getWidth() - contentWidth);//计算右边应该保留的宽度
        setPadding(0, 0, rightPadding, 0); // 直接贴到左边
        float dx = (getWidth() - contentWidth) / 2;//获取中心位置
        canvas.translate(dx, 0);// 整体右移
        return canvas;
    }
}
