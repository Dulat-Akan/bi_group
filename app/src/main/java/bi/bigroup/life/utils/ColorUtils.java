package bi.bigroup.life.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;

import bi.bigroup.life.R;

public class ColorUtils {

    public static int getColor(Context context, int colorResourceId) {
        return ContextCompat.getColor(context, colorResourceId);
    }

    public static int[] getColorsForSwipeRefreshLayout(Context context) {
        return new int[]{
                getColor(context, R.color.colorPrimary),
                getColor(context, R.color.colorPrimaryDark),
                getColor(context, R.color.colorAccent)
        };
    }

    public static void changeRoundedShapeBackgroundColor(View v, int color) {
        Drawable background = v.getBackground();

        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(color);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(color);
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background).setColor(color);
        } else {
            v.setBackgroundColor(color);
        }
    }

    public static int getColorAttr(Context context, int attr) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attr, typedValue, true))
            return typedValue.data;
        else
            return Color.TRANSPARENT;
    }
}
