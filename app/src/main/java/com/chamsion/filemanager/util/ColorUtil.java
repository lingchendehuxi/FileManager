package com.chamsion.filemanager.util;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;

import com.chamsion.filemanager.R;


/**
 * Created by panruijie on 16/12/26.
 * Email : zquprj@gmail.com
 */

public class ColorUtil {

    public static int getColorPrimary(Context context){
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.themeColor, typedValue, true);
        return typedValue.data;
    }

    public static int getBackgroundColor(Context context){
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.widgetBackground, typedValue, true);
        return typedValue.data;
    }
}
