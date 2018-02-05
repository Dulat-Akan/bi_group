package bi.bigroup.life.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

public class SpannableStringUtils {
    public static void underlineClickable(Callback callback, SpannableString spannableStr, int start, int end, int underlineTxtColor) {
        spannableStr.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                callback.underlinePartClicked();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(underlineTxtColor);
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Underline
        spannableStr.setSpan(new UnderlineSpan(), start, end, 0);
    }
    public interface Callback {
        void underlinePartClicked();
    }
}
