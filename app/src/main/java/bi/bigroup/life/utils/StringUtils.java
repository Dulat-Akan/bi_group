package bi.bigroup.life.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

public class StringUtils {
    public static final String EMPTY_STR = "";

    public static String trim(String s) {
        return s != null ? s.trim() : null;
    }

    public static int length(String s) {
        return s == null ? 0 : s.length();
    }

    public static String removeLastChar(String str) {
        if (length(str) > 0) {
            return str.substring(0, str.length() - 1);
        } else {
            return StringUtils.EMPTY_STR;
        }
    }

    public static int getOkInt(Integer number) {
        return number != null ? number : 0;
    }

    public static double getOkDouble(Double number) {
        return number != null ? number : 0;
    }

    public static boolean isStringOk(String text) {
        return text != null && text.trim().length() > 0;
    }

    public static boolean isStringOk(String text, int length) {
        return isStringOk(text) && text.length() >= 10;
    }

    public static boolean isOkBoolean(Boolean bool) {
        return bool != null ? bool : false;
    }

    public static String replaceNull(String text) {
        if (isStringOk(text)) {
            return text.trim();
        } else {
            return EMPTY_STR;
        }
    }

    public static String replaceToNull(String text) {
        if (isStringOk(text))
            return text;

        return null;
    }

    public static String okTxt(String text, TextView textView) {
        if (isStringOk(text)) {
            textView.setVisibility(View.VISIBLE);
            return text;
        } else {
            textView.setVisibility(View.GONE);
            return EMPTY_STR;
        }
    }

    public static SpannableStringBuilder setBoldStyleToSpecificWord(String boldWord, String wholeWord) {
        SpannableStringBuilder str = new SpannableStringBuilder(wholeWord);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, boldWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return str;
    }

    public static SpannableStringBuilder okTextBoldStyleToSpecificWord(String boldWord, String valueWord, TextView textView) {
        SpannableStringBuilder str = new SpannableStringBuilder(boldWord + " " + okTxt(valueWord, textView));
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, boldWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return str;
    }

    public static SpannableStringBuilder setBoldStyleWithSpecificColor(String wholeWord, String value, int color) {
        SpannableStringBuilder str = new SpannableStringBuilder(wholeWord);
        int start = wholeWord.indexOf(value);
        str.setSpan(new android.text.style.ForegroundColorSpan(color), start, wholeWord.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, wholeWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return str;
    }

    public static String substring(String text, int length) {
        if (isStringOk(text)) {
            if (text.length() > length) {
                return text.substring(0, length);
            }
            return text;
        }

        return EMPTY_STR;
    }
}