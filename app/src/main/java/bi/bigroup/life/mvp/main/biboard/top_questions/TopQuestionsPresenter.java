package bi.bigroup.life.mvp.main.biboard.top_questions;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.biboard.top_questions.TopQuestions;
import bi.bigroup.life.data.repository.biboard.top_questions.TopQuestionsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Subscriber;
import rx.Subscription;

@InjectViewState
public class TopQuestionsPresenter extends BaseMvpPresenter<TopQuestionsView> {

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        getTopQuestions();
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
}