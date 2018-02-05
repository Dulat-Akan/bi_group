package bi.bigroup.life.views.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

public class TextViewOpenSansNeueRegular extends AppCompatTextView {

    public TextViewOpenSansNeueRegular(Context context) {
        super(context);
        init();
    }

    public TextViewOpenSansNeueRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewOpenSansNeueRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        setTypeface(tf, Typeface.NORMAL);
        setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3.0f, getResources().getDisplayMetrics()), 1.0f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setLetterSpacing(.03f);
        }
    }
}