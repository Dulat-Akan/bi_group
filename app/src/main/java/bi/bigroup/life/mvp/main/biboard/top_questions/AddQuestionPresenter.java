package bi.bigroup.life.mvp.main.biboard.top_questions;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.bioffice.top_questions.AddQuestionParams;
import bi.bigroup.life.data.repository.biboard.top_questions.TopQuestionsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import okhttp3.ResponseBody;
import rx.Subscriber;

import static bi.bigroup.life.utils.StringUtils.isStringOk;

@InjectViewState
public class AddQuestionPresenter extends BaseMvpPresenter<AddQuestionView> {

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }

    public void setFields(String content, String tags) {
        if (!isStringOk(tags)) {
            getViewState().showTagsError(R.string.field_error);
            return;
        }

        if (!isStringOk(content)) {
            getViewState().showContentError(R.string.field_error);
            return;
        }

        addQuestion(new AddQuestionParams());
    }

    private void addQuestion(AddQuestionParams params) {
        TopQuestionsRepositoryProvider.provideRepository(dataLayer.getApi())
                .addQuestion(params)
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
                    }
                });
    }
}