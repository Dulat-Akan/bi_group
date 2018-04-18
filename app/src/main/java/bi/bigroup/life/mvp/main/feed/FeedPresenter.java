package bi.bigroup.life.mvp.main.feed;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.cache.db.FeedDao;
import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.data.repository.feed.FeedRepository;
import bi.bigroup.life.data.repository.feed.FeedRepositoryProvider;
import bi.bigroup.life.data.repository.feed.news.NewsRepositoryProvider;
import bi.bigroup.life.data.repository.feed.questionnaire.QuestionnaireRepositoryProvider;
import bi.bigroup.life.data.repository.feed.suggestions.SuggestionsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import static bi.bigroup.life.data.models.feed.FeedEntityType.FEED_TYPE_NEWS;
import static bi.bigroup.life.data.models.feed.FeedEntityType.FEED_TYPE_QUESTIONNAIRE;
import static bi.bigroup.life.data.models.feed.FeedEntityType.FEED_TYPE_SUGGESTION;
import static bi.bigroup.life.utils.Constants.INITIAL_PAGE_NUMBER;
import static bi.bigroup.life.utils.Constants.REQUEST_COUNT;

@InjectViewState
public class FeedPresenter extends BaseMvpPresenter<FeedView> {
    public static final int TAB_FEED_ALL = 1;
    public static final int TAB_FEED_NEWS = 2;
    public static final int TAB_FEED_SUGGESTIONS = 3;
    public static final int TAB_FEED_QUESTIONNAIRES = 4;

    private int current_page;
    private Subscription feedSubscription;
    private Subscription likeUnlikeSubscription;
    private Subscription suggestionLikeUnlikeSubscription;
    private FeedDao feedDao;

    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        feedDao = dataLayer.getDatabase().feedDao();
    }

    public void getFeedList(boolean is_load_more, boolean is_refresh, boolean isInternetOn, int tabType) {
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

            FeedRepository repository = FeedRepositoryProvider.provideRepository(dataLayer.getApi());
            Observable<List<Feed>> listObservable = null;
            if (tabType == TAB_FEED_ALL) {
                listObservable = repository.getFeedList(REQUEST_COUNT, current_page, null);
            } else if (tabType == TAB_FEED_NEWS) {
                listObservable = repository.getNewsList(REQUEST_COUNT, current_page);
            } else if (tabType == TAB_FEED_SUGGESTIONS) {
                listObservable = repository.getSuggestionsList(REQUEST_COUNT, current_page);
            } else if (tabType == TAB_FEED_QUESTIONNAIRES) {
                listObservable = repository.getQuestionnairesList(REQUEST_COUNT, current_page);
            }

            if (listObservable == null) return;
            feedSubscription = listObservable
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
                                for (int i = 0; i < feedList.size(); i++) {
                                    if (tabType == TAB_FEED_NEWS) {
                                        feedList.get(i).setLayoutType(FEED_TYPE_NEWS);
                                    } else if (tabType == TAB_FEED_SUGGESTIONS) {
                                        feedList.get(i).setLayoutType(FEED_TYPE_SUGGESTION);
                                    } else if (tabType == TAB_FEED_QUESTIONNAIRES) {
                                        feedList.get(i).setLayoutType(FEED_TYPE_QUESTIONNAIRE);
                                    }
                                }

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
            if (tabType == TAB_FEED_ALL) {
                List<Feed> feedList = feedDao.loadAllFeed();
                if (feedList.size() > 0) {
                    getViewState().setFeedList(feedList);
                }
                showLoading(false, is_load_more, is_refresh);
            }
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

    public void getQuestionnaire(String id) {
        Subscription subscription = QuestionnaireRepositoryProvider.provideRepository(dataLayer.getApi())
                .getQuestionnaire(id)
                .doOnSubscribe(() -> getViewState().showTransparentIndicator(true))
                .doOnTerminate(() -> getViewState().showTransparentIndicator(false))
                .subscribe(new Subscriber<Questionnaire>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(Questionnaire object) {
                        if (object != null) {
                            getViewState().setQuestionnaire(object);
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }

}
