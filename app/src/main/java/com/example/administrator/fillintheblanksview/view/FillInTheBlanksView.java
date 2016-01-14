package com.example.administrator.fillintheblanksview.view;

import android.content.Context;
import android.text.InputFilter;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-1-14.
 * 填空题View
 */
public class FillInTheBlanksView extends FlowLayout {
    private List<String> fillTexts;
    private List<String> nullTexts;
    private TextPaint paint;


    public FillInTheBlanksView(Context context) {
        super(context);
        paint = new TextPaint();
    }

    public FillInTheBlanksView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new TextPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 需要填空的字符用英文()包裹
     * ()必须一对
     *
     * @param text
     * @param fontSize
     * @param textColor
     */
    public void setText(String text, int fontSize, int textColor) {
        paint.setTextSize(fontSize);
        float viewWidth = 0;
        fillTexts = new ArrayList<String>();
        nullTexts = new ArrayList<String>();
        float textWidth = 0;
        String temp;
        while (text.contains("(")) {
            temp = text.substring(0, text.indexOf("("));
            fillTexts.add(temp);
            text = text.replace(temp, "");
            temp = text.substring(text.indexOf("("), text.indexOf(")") + 1);
            nullTexts.add(temp.replace("(", "").replace(")", ""));
            text = text.replace(temp, "");
        }
        if (text.length() > 0) {
            fillTexts.add(text);
        }

        for (int i = 0; i < fillTexts.size(); i++) {
            String str = fillTexts.get(i);
            TextView textView = null;
            if (viewWidth + paint.measureText(str) < widthSize - 10) {
                textWidth = getTextWidth(str, fontSize);
                viewWidth = viewWidth + textWidth;
                textView = new TextView(getContext());
                textView.setText(str);
                textView.setPadding(0,0,0,0);
                textView.setLayoutParams(new MarginLayoutParams((int) textWidth, LayoutParams.WRAP_CONTENT));
                textView.setTextSize(fontSize);
                textView.setTextColor(textColor);
                addView(textView);
            } else {
                for (int j = str.length(); j >= 0; j--) {
                    temp = str.substring(0, j);
                    textWidth =getTextWidth(temp, fontSize);
                    if (viewWidth + textWidth< widthSize - 10) {
                        textView = new TextView(getContext());
                        textView.setText(temp);
                        textView.setPadding(0,0,0,0);
                        textView.setTextSize(fontSize);
                        textView.setLayoutParams(new MarginLayoutParams((int) textWidth, LayoutParams.WRAP_CONTENT));
                        textView.setTextColor(textColor);
                        addView(textView);
                        TextView textView2 = new TextView(getContext());
                        textView2.setText(str.substring(j, str.length()));
                        textView2.setTextSize(fontSize);
                        textView2.setPadding(0,0,0,0);
                        textWidth = paint.measureText(str.substring(j, str.length()));
                        textView2.setLayoutParams(new MarginLayoutParams((int) textWidth, LayoutParams.WRAP_CONTENT));
                        textView2.setTextColor(textColor);
                        addView(textView2);
                        viewWidth = textWidth;
                        break;
                    }
                }
            }
            if (nullTexts.size() - 1 >= i) {
                float editTextWidth = getTextWidth(nullTexts.get(i), fontSize) + 10;
                EditText editText = new EditText(getContext());
                editText.setTextSize(fontSize);
                editText.setPadding(0,0,0,0);
                editText.setTextColor(textColor);
                editText.setSingleLine(true);
                editText.setFilters(new  InputFilter[]{ new  InputFilter.LengthFilter(nullTexts.get(i).length())});
                editText.setLayoutParams(new MarginLayoutParams((int) editTextWidth,LayoutParams.WRAP_CONTENT));
                addView(editText);
                if (editTextWidth + viewWidth < widthSize - 10) {
                    viewWidth = viewWidth + editTextWidth;
                } else {
                    viewWidth = editTextWidth;
                }
            }
        }


    }

    public float getTextWidth(String text, int textSize){
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity * textSize);
        return paint.measureText(text);
    }
}
