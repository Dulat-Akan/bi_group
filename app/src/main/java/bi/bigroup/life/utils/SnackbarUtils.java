package bi.bigroup.life.utils;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import bi.bigroup.life.R;

public class SnackbarUtils {
    private static final int DEFAULT_MAX_LINES = 5;

    public static Snackbar multilineSnackbar(View view, @StringRes int resId, int duration) {
        return multilineSnackbar(view, resId, duration, -1);
    }

    public static Snackbar multilineSnackbar(View view, CharSequence text, int duration) {
        return multilineSnackbar(view, text, duration, -1);
    }

    public static Snackbar multilineSnackbar(View view, @StringRes int resId, int duration, int maxLines) {
        return makeMultiline(Snackbar.make(view, resId, duration), maxLines);
    }

    public static Snackbar multilineSnackbar(View view, CharSequence text, int duration, int maxLines) {
        return makeMultiline(Snackbar.make(view, text, duration), maxLines);
    }

    private static Snackbar makeMultiline(Snackbar snackbar, int maxLines) {
        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(maxLines > 0 ? maxLines : DEFAULT_MAX_LINES);
        return snackbar;
    }

    public static void showSnackbar(View parent, int stringId) {
        final Snackbar snackbar = SnackbarUtils.multilineSnackbar(parent, stringId, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.dismiss, v -> snackbar.dismiss());
        snackbar.show();
    }

    public static void showSnackbar(View parent, String s) {
        final Snackbar snackbar = SnackbarUtils.multilineSnackbar(parent, s, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.dismiss, v -> snackbar.dismiss());
        snackbar.show();
    }
}
