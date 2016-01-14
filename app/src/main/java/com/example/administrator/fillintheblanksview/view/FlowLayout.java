package com.example.administrator.fillintheblanksview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by Administrator on 15-9-15.
 */
public class FlowLayout extends ViewGroup {
    private int lineHeight = 0;
    protected int widthSize = 0;

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context) {
        super(context);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSizeMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSizeMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthMax = 0;
        int heightMax = 0;
        int lineWidth = 0;
        int lineHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            if (child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin + lineWidth
                    > widthSize) {
                widthMax = lineWidth;
                heightMax = heightMax + lineHeight;
                lineWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                lineHeight = 0;
            } else {
                lineWidth = lineWidth + child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
            int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            if (child instanceof EditText){
                childHeight = childHeight + 20;
            }
            if (childHeight> lineHeight) {
                lineHeight = childHeight;
            }
            if (i == getChildCount() - 1) {
                widthMax = Math.max(child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin, lineWidth);
                heightMax += lineHeight;
            }
        }
        setMeasuredDimension(widthSizeMode == MeasureSpec.EXACTLY ? widthSize : widthMax, heightSizeMode ==
                MeasureSpec.EXACTLY ? heightSize : heightMax);
        ;

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        lineHeight = 0;
        int lineWidth = getWidth();
        int maxHeight = 0;
        int widthSum = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            if (widthSum + child.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin > lineWidth) {
                lineHeight = lineHeight + maxHeight;
                widthSum = 0;
            }
            if (child.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin
                    > maxHeight) {
                maxHeight = child.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            }
            if (child.getVisibility() == GONE) {
                return;
            }
            int left = widthSum + marginLayoutParams.leftMargin;
            int top = lineHeight +
                    marginLayoutParams.topMargin;
            int right = widthSum + marginLayoutParams.leftMargin
                    + child.getMeasuredWidth();
            int bottom = lineHeight +
                    marginLayoutParams.topMargin + child.getMeasuredHeight();
            child.layout(left, top, right, bottom);
            widthSum = widthSum + child.getMeasuredWidth() + marginLayoutParams.leftMargin
                    + marginLayoutParams.rightMargin;
        }
    }

    public void setAdapter(Adapter adapter) {
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                addView(adapter.getView(i, null, this));
            }
        }

    }

    public void setOnItemClickListenter(OnClickListener onItemClickListenter) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setOnClickListener(onItemClickListenter);
        }
    }

}
