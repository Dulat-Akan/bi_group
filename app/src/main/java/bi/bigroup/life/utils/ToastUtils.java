package bi.bigroup.life.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class ToastUtils {
    public static void showCenteredToast(Context context, @StringRes int resId, int duration) {
        Toast toast = Toast.makeText(context, resId, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showCenteredToast(Context context, String message, int duration) {
        Toast toast = Toast.makeText(context, message, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, @StringRes int errorRes) {
        Toast.makeText(context, errorRes, Toast.LENGTH_SHORT).show();
    }
}
