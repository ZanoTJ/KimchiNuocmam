package com.gamecodeschool.snakegame;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by David on 3/27/2024.
 */

public class FontView extends AppCompatTextView {

    public FontView(Context context) {
        super(context);
        initTypeface(context);
    }

    public FontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeface(context);
    }

    public FontView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeface(context);
    }

    private void initTypeface(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "dancing_script.ttf");
        this.setTypeface(typeface);
    }
}