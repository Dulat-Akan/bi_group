package bi.bigroup.life.mvp.main.feed.questionnaires;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.data.models.notifications.Notification;
import bi.bigroup.life.data.repository.feed.questionnaire.QuestionnaireRepositoryProvider;
import bi.bigroup.life.data.repository.notifications.NotificationsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;

@InjectViewState
public class QuestStatisticsPresenter extends BaseMvpPresenter<QuestStatisticsView> {

    public void getQuestStatistics(String id, boolean is_refresh) {
        Subscription subscription = QuestionnaireRepositoryProvider.provideRepository(dataLayer.getApi())
                .getQuestStatistics(id)
                .doOnSubscribe(() -> showLoading(true, is_refresh))
                .doOnTerminate(() -> showLoading(false, is_refresh))
                .subscribe(new Subscriber<List<Questionnaire>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(List<Questionnaire> list) {
                        if (list != null && list.size() > 0) {
                            if (is_refresh) {
                                getViewState().setQuestionnaireList(list);
                            } else {
                                getViewState().addQuestionnaireList(list);
                            }
                        } else {
                            getViewState().showNotFoundPlaceholder();
                        }
                    }
                });

        compositeSubscription.add(subscription);
    }

    public void removeNotification(String id) {
        Subscription subscription = NotificationsRepositoryProvider.provideRepository(dataLayer.getApi())
                .removeNotification(id)
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
                    public void onNext(ResponseBody responseBody) {
                        if (responseBody != null) {

                        }
                    }
                });

        compositeSubscription.add(subscription);
    }

    private void showLoading(boolean show, boolean is_refresh) {
        if (!is_refresh) {
            getViewState().showLoadingIndicator(show);
        } else {
            getViewState().showRefreshLoading(show);
        }
    }
}
