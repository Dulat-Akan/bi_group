package bi.bigroup.life.views.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class ButtonOpenSansRegular extends Button {

    public ButtonOpenSansRegular(Context context) {
        super(context);
        init();
    }

    public ButtonOpenSansRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonOpenSansRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        setTypeface(tf, Typeface.NORMAL);
    }
}