package bi.bigroup.life.views.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

public class TextViewRobotoMedium extends AppCompatTextView {

    public TextViewRobotoMedium(Context context) {
        super(context);
        init();
    }

    public TextViewRobotoMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewRobotoMedium(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Medium.ttf");
        setTypeface(tf, Typeface.NORMAL);
        setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3.0f, getResources().getDisplayMetrics()), 1.0f);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setLetterSpacing(.03f);
//        }
    }
}