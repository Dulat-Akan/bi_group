package bi.bigroup.life.views.edittext;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;

import com.rengwuxian.materialedittext.MaterialEditText;

public class SettingsEditText extends MaterialEditText {
    public SettingsEditText(Context context) {
        super(context);
        init();
    }

    public SettingsEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SettingsEditText(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        init();
    }

    private void init() {
        // Override the default value of focus fraction here, because there is no way to tweak it from the layout XML.
        setFocusFraction(1.0f);
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            setError(null);
            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }
}
