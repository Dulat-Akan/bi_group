package bi.bigroup.life.views.circle_page_indicator;

import android.support.v4.view.ViewPager;

public interface CirclePageIndicate extends ViewPager.OnPageChangeListener {
    void setViewPager(ViewPager view);

    void setViewPager(ViewPager view, int initialPosition);

    void setCurrentItem(int item);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

    void notifyDataSetChanged();
}
