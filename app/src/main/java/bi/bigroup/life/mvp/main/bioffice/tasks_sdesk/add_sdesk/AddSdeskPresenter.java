package bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_sdesk;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.repository.bioffice.tasks_sdesk.TasksServicesRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;

import static bi.bigroup.life.utils.Constants.KEY_ATTACHMENTS;
import static bi.bigroup.life.utils.StringUtils.isStringOk;

@InjectViewState
public class AddSdeskPresenter extends BaseMvpPresenter<AddSdeskView> {

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }

    public void onSelectMultipleFiles(boolean isPermissionGranted, boolean shouldShowRequestPermission) {
        if (isPermissionGranted) {
            // Has permission access
            selectMultipleFiles();
        } else {
            // Permission not granted
            onPermissionNotGranted(shouldShowRequestPermission);
        }
    }

    // Asking permissions
    public void selectMultipleFiles() {
        getViewState().selectMultipleFiles();
    }

    public void onPermissionNotGranted(boolean shouldShowRequestPermission) {
        if (shouldShowRequestPermission) {
            getViewState().showRequestPermissionDialog(false);
        } else {
            getViewState().showRequestPermissionDialog(true);
        }
    }

    public void addRequest(String content, String dateTime, String[] files) {
        if (!isStringOk(content)) {
            getViewState().showContentError(R.string.field_error);
            return;
        }

        if (!isStringOk(dateTime)) {
            getViewState().showDateError(R.string.field_error);
            return;
        }

        // Secondary images, multiple images
        List<MultipartBody.Part> attachments = null;
        if (files != null && files.length > 0) {
            attachments = new ArrayList<>();
            for (String file : files) {
                attachments.add(getMultipartParams(new File(file), KEY_ATTACHMENTS));
            }
        }
        addRequest(attachments, content, dateTime);
    }

    private void addRequest(List<MultipartBody.Part> attachments, String content, String dateTime) {
        Subscription subscription = TasksServicesRepositoryProvider.provideRepository(dataLayer.getApi())
                .addRequest(attachments, content, dateTime)
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
                            getViewState().requestAddedSuccessfully();
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