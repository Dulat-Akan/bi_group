package bi.bigroup.life.utils;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;

public class HtmlSourceUtils {

    public static void htmlHyperlinkClickable(Context context, TextView tv, String source) {
        CharSequence sequence = fromHtml(source);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for (URLSpan span : urls) {
            makeLinkClickable(context, strBuilder, span);
        }
        tv.setText(strBuilder);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private static void makeLinkClickable(final Context context, SpannableStringBuilder strBuilder, final URLSpan span) {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
//                new ChromeCustomTabs().loadWebSite(context, span.getURL());
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    public static String styleTextForWebView(String text) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>" +
                "<head>" +
                "<style type=\"text/css\">" +
                "@font-face {" +
                "    font-family: MyFont;" +
                "    src: url(\"file:///android_asset/OpenSans-Light.ttf\")" +
                "}" +
                "body {" +
                "    font-family: MyFont;" +
                "    font-size: medium;" +
                "    text-align: justify;" +
                "    color: #455A64;" +
                "}" +
                "</style>" +
                "</head>");
        sb.append("<body style='margin-left:0;margin-right:0;'>");
        sb.append(text);
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }


    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        source = source.replaceAll("\\<p>|\\</p>", EMPTY_STR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source, null, new HtmlTagHandler());
        }
    }
}