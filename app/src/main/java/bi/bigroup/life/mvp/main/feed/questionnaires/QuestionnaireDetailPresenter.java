package bi.bigroup.life.mvp.main.feed.questionnaires;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.feed.questionnaire.Question;
import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.data.models.feed.questionnaire.Variants;
import bi.bigroup.life.data.params.questionnaire.QuestionnaireAnswer;
import bi.bigroup.life.data.repository.feed.questionnaire.QuestionnaireRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;

import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;
import static bi.bigroup.life.utils.StringUtils.isStringOk;

@InjectViewState
public class QuestionnaireDetailPresenter extends BaseMvpPresenter<QuestionnaireDetailView> {

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }

    public void getQuestionnaireDetail(String id) {
        Subscription subscription = QuestionnaireRepositoryProvider.provideRepository(dataLayer.getApi())
                .getQuestionnaire(id)
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
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

    public void setUserAnswers(String questionnaireId, List<Question> data) {
        for (int i = 0; i < data.size(); i++) {
            Question question = data.get(i);
            // Collect answered variant ids
            List<Variants> variants = question.variants;
            List<String> userAnswerVariantIds = new ArrayList<>();
            if (variants != null && variants.size() > 0) {
                for (Variants item : variants) {
                    if (item.isVariantChecked()) {
                        userAnswerVariantIds.add(item.getId());
                    }
                }
            }

            // Set comment of user
            QuestionnaireAnswer answer = new QuestionnaireAnswer();
            if (question.isCommented() && isStringOk(question.getUserComment())) {
                answer.setCommented(true);
                answer.setUserAnswerCommentText(question.getUserComment());
            } else {
                answer.setCommented(false);
                answer.setUserAnswerCommentText(EMPTY_STR);
            }

            answer.setUserAnswerVariantIds(userAnswerVariantIds);
            questionnaireUserAnswers(questionnaireId, question.getId(), answer);
        }
    }

    private void questionnaireUserAnswers(String id, String questionId, QuestionnaireAnswer params) {
        Subscription subscription = QuestionnaireRepositoryProvider.provideRepository(dataLayer.getApi())
                .questionnaireUserAnswers(id, questionId, params)
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
                    public void onNext(ResponseBody object) {
                        if (object != null) {
                            getViewState().showRequestSuccess(R.string.success_response);
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }
}