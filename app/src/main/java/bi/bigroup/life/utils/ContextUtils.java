package bi.bigroup.life.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.RawRes;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;

import java.io.IOException;
import java.io.InputStream;

import okio.Okio;
import timber.log.Timber;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS;
import static android.view.inputmethod.InputMethodManager.SHOW_FORCED;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;
import static bi.bigroup.life.utils.ViewUtils.getRelativeTopInAncestor;

public class ContextUtils {
    public static int dp2pixels(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics));
    }

    public static int pixels2dp(Context context, float px){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int)dp;
    }

    public static float sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static float dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }


    public static void showSoftKeyboard(EditText editText) {
        Context context = editText.getContext();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, SHOW_FORCED);
    }

    public static void hideSoftKeyboard(Context context) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public static void hideSoftKeyboard(View view) {
        Context context = view.getContext();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), HIDE_NOT_ALWAYS);
    }

    public static void clearFocusFromAllViews(ViewGroup viewGroup) {
        viewGroup.requestFocus();
    }

    public static void smoothScrollTo(ScrollView scrollView, View view, final int adjustOffset) {
        scrollView.smoothScrollTo(0, getRelativeTopInAncestor(scrollView, view) - adjustOffset);
    }

    public static String getRawAsString(Context context, @RawRes int fileRes) {
        InputStream inputStream = context.getResources().openRawResource(fileRes);
        try {
            return Okio.buffer(Okio.source(inputStream)).readUtf8();
        } catch (IOException e) {
            Timber.e(e, null);
            return null;
        }
    }

    public static String getVersionName(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return pInfo != null ? pInfo.versionName : EMPTY_STR;
    }
}
