package bi.bigroup.life.views.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewRobotoRegular extends AppCompatTextView {

    public TextViewRobotoRegular(Context context) {
        super(context);
        init();
    }

    public TextViewRobotoRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewRobotoRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Regular.ttf");
        setTypeface(tf, Typeface.NORMAL);
    }
}