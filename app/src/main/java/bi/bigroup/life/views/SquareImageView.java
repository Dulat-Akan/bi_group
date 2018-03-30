package bi.bigroup.life.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import bi.bigroup.life.R;

import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.UNSPECIFIED;

public class SquareImageView extends ImageView {
    private static final int DEFAULT_MAX_SIZE = Integer.MAX_VALUE;

    private int maxSize = DEFAULT_MAX_SIZE;

    public SquareImageView(Context context) {
        super(context);
        init(context, null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareImageView);
        try {
            maxSize = typedArray.getDimensionPixelSize(R.styleable.SquareImageView_siv_maxSize, DEFAULT_MAX_SIZE);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecValue = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecValue = MeasureSpec.getSize(heightMeasureSpec);
        int size;
        if (widthSpecMode == EXACTLY && (heightSpecMode == UNSPECIFIED || heightSpecValue == 0)) {
            size = getMeasuredWidth();
        } else if ((widthSpecMode == UNSPECIFIED || widthSpecValue == 0) && heightSpecMode == EXACTLY) {
            size = getMeasuredHeight();
        } else {
            size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        }
        size = Math.min(size, maxSize);
        setMeasuredDimension(size, size);
    }
}
