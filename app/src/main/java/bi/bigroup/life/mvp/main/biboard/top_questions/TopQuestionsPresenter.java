package bi.bigroup.life.mvp.main.biboard.top_questions;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.biboard.top_questions.TopQuestions;
import bi.bigroup.life.data.models.biboard.top_questions.TopVideoAnswers;
import bi.bigroup.life.data.repository.biboard.top_questions.TopQuestionsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;

@InjectViewState
public class TopQuestionsPresenter extends BaseMvpPresenter<TopQuestionsView> {
    private Subscription likeUnlikeSubscription;

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        getTopQuestions();
        getTopVideoAnswers();
    }

    // Top Questions
    private void getTopQuestions() {
        Subscription subscription = TopQuestionsRepositoryProvider.provideRepository(dataLayer.getApi())
                .getTopQuestions()
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribe(new Subscriber<List<TopQuestions>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(List<TopQuestions> list) {
                        if (list != null && list.size() > 0) {
                            getViewState().setTopQuestions(list);
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }

    private void getTopVideoAnswers() {
        Subscription subscription = TopQuestionsRepositoryProvider.provideRepository(dataLayer.getApi())
                .getTopVideoAnswers()
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribe(new Subscriber<List<TopVideoAnswers>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(List<TopVideoAnswers> list) {
                        if (list != null && list.size() > 0) {
                            getViewState().setTopVideoAnswers(list);
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }

    public void likeSubscriptionUnsubscribe() {
        if (likeUnlikeSubscription != null
                && !likeUnlikeSubscription.isUnsubscribed()) {
            likeUnlikeSubscription.unsubscribe();
        }
    }

    public void likeTopQuestion(String id) {
        likeUnlikeSubscription = TopQuestionsRepositoryProvider.provideRepository(dataLayer.getApi())
                .likeTopQuestion(id)
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
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