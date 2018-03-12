package bi.bigroup.life.mvp.main.biboard.top_questions;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.bioffice.top_questions.AddQuestionParams;
import bi.bigroup.life.data.models.feed.news.Tags;
import bi.bigroup.life.data.repository.biboard.top_questions.TopQuestionsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;

import static bi.bigroup.life.utils.StringUtils.isStringOk;

@InjectViewState
public class AddQuestionPresenter extends BaseMvpPresenter<AddQuestionView> {

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        getTags();
    }

    private void getTags() {
        Subscription subscription = TopQuestionsRepositoryProvider.provideRepository(dataLayer.getApi())
                .getTags()
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribe(new Subscriber<List<Tags>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(List<Tags> object) {
                        if (object != null && object.size() > 0) {
                            getViewState().setTags(object);
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }

    public void addQuestion(String content, List<Tags> selectedTagsList, boolean isAnonymous) {
        if (!isStringOk(content)) {
            getViewState().showContentError(R.string.field_error);
            return;
        }

        if (selectedTagsList == null || selectedTagsList.size() == 0) {
            getViewState().showToastError(R.string.please_select_tag);
            return;
        }

        // Tags list
        List<String> tagsList = new ArrayList<>();
        for (int i = 0; i < selectedTagsList.size(); i++) {
            Tags tag = selectedTagsList.get(i);
            if (tag.getNsiTagId() == null) {
                // New tags
                tagsList.add(tag.getName());
            } else {
                // Existing tags
                tagsList.add(tag.getNsiTagId());
            }
        }
        addQuestion(new AddQuestionParams(content, isAnonymous, tagsList));
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
                        if (responseBody != null) {
                            getViewState().questionAddedSuccessfully();
                        }
                    }
                });
    }
}