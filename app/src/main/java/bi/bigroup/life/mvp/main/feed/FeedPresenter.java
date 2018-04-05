package bi.bigroup.life.mvp.main.feed;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.cache.db.FeedDao;
import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.repository.feed.FeedRepositoryProvider;
import bi.bigroup.life.data.repository.feed.news.NewsRepositoryProvider;
import bi.bigroup.life.data.repository.feed.suggestions.SuggestionsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;

import static bi.bigroup.life.utils.Constants.INITIAL_PAGE_NUMBER;
import static bi.bigroup.life.utils.Constants.REQUEST_COUNT;

@InjectViewState
public class FeedPresenter extends BaseMvpPresenter<FeedView> {
    private int current_page;
    private Subscription feedSubscription;
    private Subscription likeUnlikeSubscription;
    private Subscription suggestionLikeUnlikeSubscription;
    private FeedDao feedDao;

    //  LENTA http://life.bi-group.org:8090/api/lenta?rows=3&offset=0&withDescription=false
//  NEWS  http://life.bi-group.org:8090/api/news/withDetails?rows=3&offset=3
//  Questionnaires http://life.bi-group.org:8090/api/questionnaires/withDetails?rows=3&offset=0
//  Suggestions http://life.bi-group.org:8090/api/suggestions/withDetails?rows=3&offset=0


    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        feedDao = dataLayer.getDatabase().feedDao();
    }

    public void getFeedList(boolean is_load_more, boolean is_refresh, boolean isInternetOn) {
        if (feedSubscription != null
                && !feedSubscription.isUnsubscribed()) {
            feedSubscription.unsubscribe();
        }

        if (isInternetOn) {
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
                                    getViewState().showLoadingIndicator(false);
                                    getViewState().addFeedList(feedList);
                                }

                                if (!is_load_more) {
                                    new Thread(() -> {
                                        feedDao.deleteFeed();
                                        try {
                                            feedDao.insertFeed(feedList);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }).start();
                                }
                            } else if (!is_load_more) {
                                getViewState().showNotFoundPlaceholder();
                            }
                        }
                    });
        } else {
            List<Feed> feedList = feedDao.loadAllFeed();
            if (feedList.size() > 0) {
                getViewState().setFeedList(feedList);
            }
            showLoading(false, is_load_more, is_refresh);
        }
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

    // =============== Like News ===============
    public void likeSubscriptionUnsubscribe() {
        if (likeUnlikeSubscription != null
                && !likeUnlikeSubscription.isUnsubscribed()) {
            likeUnlikeSubscription.unsubscribe();
        }
    }

    public void likeNews(String newsId) {
        likeUnlikeSubscription = NewsRepositoryProvider.provideRepository(dataLayer.getApi())
                .likeNews(newsId)
                .doOnSubscribe(() -> getViewState().showTransparentIndicator(true))
                .doOnTerminate(() -> getViewState().showTransparentIndicator(false))
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(ResponseBody response) {
                    }
                });
    }

    // =============== Like Suggestions ===============
    public void suggestionLikeSubscriptionUnsubscribe() {
        if (suggestionLikeUnlikeSubscription != null
                && !suggestionLikeUnlikeSubscription.isUnsubscribed()) {
            suggestionLikeUnlikeSubscription.unsubscribe();
        }
    }

    public void likeSuggestion(String suggestionId, int voteType) {
        suggestionLikeUnlikeSubscription = SuggestionsRepositoryProvider.provideRepository(dataLayer.getApi())
                .likeSuggestion(suggestionId, voteType)
                .doOnSubscribe(() -> getViewState().showTransparentIndicator(true))
                .doOnTerminate(() -> getViewState().showTransparentIndicator(false))
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(ResponseBody response) {
                    }
                });
    }
}
