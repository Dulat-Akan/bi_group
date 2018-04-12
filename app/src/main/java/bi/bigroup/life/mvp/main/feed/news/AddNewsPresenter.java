package bi.bigroup.life.mvp.main.feed.news;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.feed.news.Tags;
import bi.bigroup.life.data.repository.feed.news.NewsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import in.myinnos.awesomeimagepicker.models.Image;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;

import static bi.bigroup.life.utils.Constants.KEY_MAIN_IMAGE;
import static bi.bigroup.life.utils.Constants.KEY_SECONDARY_IMAGES;
import static bi.bigroup.life.utils.StringUtils.isStringOk;

@InjectViewState
public class AddNewsPresenter extends BaseMvpPresenter<AddNewsView> {

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        getNewsTags();
    }

    private void getNewsTags() {
        Subscription subscription = NewsRepositoryProvider.provideRepository(dataLayer.getApi())
                .getNewsTags()
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
                            getViewState().setNewsTags(object);
                        }
                    }
                });
        compositeSubscription.add(subscription);
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

    public void addNews(Image imageSingle, ArrayList<Image> imagesMultiple,
                        String title, String content, List<Tags> selectedTagsList,
                        boolean isPressService, boolean isHistoryEvent) {
        if (!isStringOk(title)) {
            getViewState().showTitleError(R.string.field_error);
            return;
        }

        if (!isStringOk(content)) {
            getViewState().showContentError(R.string.field_error);
            return;
        }

        if (selectedTagsList == null || selectedTagsList.size() == 0) {
            getViewState().showToastError(R.string.please_select_tag);
            return;
        }

        // Main image checking
        MultipartBody.Part mainImage = null;
        if (imageSingle != null) {
            mainImage = getMultipartParams(new File(imageSingle.path), KEY_MAIN_IMAGE);
        }

        // Secondary images, multiple images
        List<MultipartBody.Part> secondaryImages = null;
        if (imagesMultiple != null && imagesMultiple.size() > 0) {
            secondaryImages = new ArrayList<>();
            for (int i = 0; i < imagesMultiple.size(); i++) {
                secondaryImages.add(getMultipartParams(new File(imagesMultiple.get(i).path), KEY_SECONDARY_IMAGES));
            }
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
        addNews(mainImage, secondaryImages, title, content, title, isPressService, isHistoryEvent, tagsList);
    }

    private void addNews(MultipartBody.Part mainImage, List<MultipartBody.Part> secondaryImages,
                         String title, String text, String rawText, Boolean isPressService, Boolean isHistoryEvent,
                         List<String> tags) {
        Subscription subscription = NewsRepositoryProvider.provideRepository(dataLayer.getApi())
                .addNews(mainImage, secondaryImages, title, text, rawText, isPressService, isHistoryEvent, tags)
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
                    public void onNext(ResponseBody responseBody) {
                        if (responseBody != null) {
                            getViewState().newsAddedSuccessfully();
                        }
                    }
                });

        compositeSubscription.add(subscription);
    }

    private MultipartBody.Part getMultipartParams(File file, String keyword) {
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"), //MediaType.parse(getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(keyword, file.getName(), requestFile);
    }
}