package bi.bigroup.life.mvp.main.feed;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.feed.FilterButton;
import bi.bigroup.life.mvp.BaseMvpPresenter;

@InjectViewState
public class FeedPresenter extends BaseMvpPresenter<FeedView> {
    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);

        List<FilterButton> filterButtonList = new ArrayList<>();
        filterButtonList.add(new FilterButton(context.getString(R.string.filter_news), 30,
                ContextCompat.getColor(context, R.color.filter_news)));
        filterButtonList.add(new FilterButton(context.getString(R.string.filter_poll), 43,
                ContextCompat.getColor(context, R.color.filter_poll)));
        filterButtonList.add(new FilterButton(context.getString(R.string.filter_offer), 223,
                ContextCompat.getColor(context, R.color.filter_offer)));

        getViewState().setFiltersList(filterButtonList);
    }
}
