package bi.bigroup.life.mvp.main.feed.news;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.bioffice.top_questions.AddQuestionParams;
import bi.bigroup.life.data.repository.biboard.top_questions.TopQuestionsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import okhttp3.ResponseBody;
import rx.Subscriber;

@InjectViewState
public class AddNewsPresenter extends BaseMvpPresenter<AddNewsView> {

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }

    private void addNews(AddQuestionParams params) {
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

    public void onSelectMultipleImages(boolean isPermissionGranted, boolean shouldShowRequestPermission) {
        if (isPermissionGranted) {
            // Has permission access
            selectMultipleImages();
        } else {
            // Permission not granted
            onPermissionNotGranted(shouldShowRequestPermission);
        }
    }

    // Asking permissions
    public void selectMultipleImages() {
        getViewState().selectMultipleImages();
    }

    public void onPermissionNotGranted(boolean shouldShowRequestPermission) {
        if (shouldShowRequestPermission) {
            getViewState().showRequestPermissionDialog(false);
        } else {
            getViewState().showRequestPermissionDialog(true);
        }
    }
}
