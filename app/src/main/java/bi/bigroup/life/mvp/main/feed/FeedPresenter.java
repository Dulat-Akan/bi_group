package bi.bigroup.life.mvp.main.feed;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.models.feed.FilterButton;
import bi.bigroup.life.data.repository.feed.FeedRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Subscriber;
import rx.Subscription;

import static bi.bigroup.life.utils.Constants.INITIAL_PAGE_NUMBER;
import static bi.bigroup.life.utils.Constants.REQUEST_COUNT;

@InjectViewState
public class FeedPresenter extends BaseMvpPresenter<FeedView> {
    private int current_page;

    private Subscription feedSubscription;

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

    public void getFeedList(boolean is_load_more, boolean is_refresh) {
        if (feedSubscription != null
                && !feedSubscription.isUnsubscribed()) {
            feedSubscription.unsubscribe();
        }
        if (is_load_more) {
            current_page += REQUEST_COUNT;
        } else {
            current_page = INITIAL_PAGE_NUMBER;
        }
        feedSubscription = FeedRepositoryProvider.provideRepository(dataLayer.getApi())
                .getFeedList(REQUEST_COUNT, current_page, null)
                .doOnSubscribe(() -> showLoading(true, is_load_more, is_refresh))
                .doOnTerminate(() -> showLoading(false, is_load_more, is_refresh))
                .subscribe(new Subscriber<List<Feed>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(List<Feed> feedList) {
                        if (feedList != null && feedList.size() > 0) {
                            if (is_refresh) {
                                getViewState().setFeedList(feedList);
                            } else {
                                getViewState().addFeedList(feedList);
                            }
                        } else if (!is_load_more) {
                            getViewState().showNotFoundPlaceholder();
                        }
                    }
                });
    }

    private void showLoading(boolean show, boolean is_load_more, boolean is_refresh) {
        if (!is_load_more && !is_refresh) {
            getViewState().showLoadingIndicator(show);
        } else if (is_load_more) {
            getViewState().showLoadingItemIndicator(show);
        } else {
            getViewState().showRefreshLoading(show);
        }
    }
}