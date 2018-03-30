package bi.bigroup.life.utils.view_pager;

import android.view.View;

public class ParallaxPageTransformer extends BaseTransformer {
    @Override
    protected void onTransform(View view, float position) {
        view.setPivotX(position < 0 ? 0 : view.getWidth());
        view.setScaleX(position < 0 ? 1f + position : 1f - position);
    }
}