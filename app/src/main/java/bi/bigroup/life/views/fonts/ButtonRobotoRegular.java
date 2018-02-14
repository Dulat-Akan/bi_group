package bi.bigroup.life.views.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class ButtonRobotoRegular extends Button {

    public ButtonRobotoRegular(Context context) {
        super(context);
        init();
    }

    public ButtonRobotoRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonRobotoRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Regular.ttf");
        setTypeface(tf, Typeface.NORMAL);
    }
}