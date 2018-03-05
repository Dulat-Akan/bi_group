package bi.bigroup.life.mvp.main.feed.suggestion;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.feed.news.AddComment;
import bi.bigroup.life.data.models.feed.news.Comment;
import bi.bigroup.life.data.models.feed.suggestions.Suggestion;
import bi.bigroup.life.data.repository.feed.news.NewsRepositoryProvider;
import bi.bigroup.life.data.repository.feed.suggestions.SuggestionsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;

import static bi.bigroup.life.utils.StringUtils.isStringOk;

@InjectViewState
public class SuggestionDetailPresenter extends BaseMvpPresenter<SuggestionDetailView> {
    private Subscription likeUnlikeSubscription;
    private Subscription likeCommentUnlikeSubscription;

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }

    public void getSuggestionDetail(String id) {
        Subscription subscription = SuggestionsRepositoryProvider.provideRepository(dataLayer.getApi())
                .getSuggestion(id)
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribe(new Subscriber<Suggestion>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(Suggestion object) {
                        if (object != null) {
                            getViewState().setSuggestion(object);
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }

    public void suggestionLikeSubscriptionUnsubscribe() {
        if (likeUnlikeSubscription != null
                && !likeUnlikeSubscription.isUnsubscribed()) {
            likeUnlikeSubscription.unsubscribe();
        }
    }

    public void likeSuggestion(String suggestionId, int voteType) {
        likeUnlikeSubscription = SuggestionsRepositoryProvider.provideRepository(dataLayer.getApi())
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

    // *********** Comments ***************

    public void addComment(String newsId, String content) {
        if (!isStringOk(newsId) || !isStringOk(content)) {
            return;
        }
        Subscription subscription = NewsRepositoryProvider.provideRepository(dataLayer.getApi())
                .addComment(newsId, new AddComment(content))
                .doOnSubscribe(() -> getViewState().showTransparentIndicator(true))
                .doOnTerminate(() -> getViewState().showTransparentIndicator(false))
                .subscribe(new Subscriber<Comment>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(Comment comment) {
                        if (comment != null) {
                            getViewState().addNewComment(comment);
                        }
                    }
                });

        compositeSubscription.add(subscription);
    }

    public void likeCommentSubscriptionUnsubscribe() {
        if (likeCommentUnlikeSubscription != null
                && !likeCommentUnlikeSubscription.isUnsubscribed()) {
            likeCommentUnlikeSubscription.unsubscribe();
        }
    }

    public void likeComment(String newsId, String commentId, int vote) {
        likeCommentUnlikeSubscription = NewsRepositoryProvider.provideRepository(dataLayer.getApi())
                .likeNewsComment(newsId, commentId, vote)
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