package bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_task;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.employees.Employee;
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
public class AddTaskPresenter extends BaseMvpPresenter<AddTaskView> {

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

    public void addTask(String title, boolean isAllDay, int reminder, String startDate, String endDate,
                        String performerCode, List<Employee> participants, int taskType, String content, String[] files) {
        if (!isStringOk(title)) {
            getViewState().showTitleError(R.string.field_error);
            return;
        }

        if (reminder == 0) {
            getViewState().showNotificationError(R.string.field_error);
            return;
        }

        if (!isStringOk(startDate)) {
            getViewState().showStartDateError(R.string.field_error);
            return;
        }

        if (!isStringOk(endDate)) {
            getViewState().showEndDateError(R.string.field_error);
            return;
        }

        if (!isStringOk(performerCode)) {
            getViewState().showPerformerError(R.string.field_error);
            return;
        }

        if (taskType == 0) {
            getViewState().showTaskTypeError(R.string.field_error);
            return;
        }

        if (!isStringOk(content)) {
            getViewState().showContentError(R.string.field_error);
            return;
        }

        // Participants
        List<String> participantsList = new ArrayList<>();
        for (int i = 0; i < participants.size(); i++) {
            participantsList.add(participants.get(i).getCode());
        }

        // Secondary images, multiple images
        List<MultipartBody.Part> attachments = null;
        if (files != null && files.length > 0) {
            attachments = new ArrayList<>();
            for (String file : files) {
                attachments.add(getMultipartParams(new File(file), KEY_ATTACHMENTS));
            }
        }

        Subscription subscription = TasksServicesRepositoryProvider.provideRepository(dataLayer.getApi())
                .addTask(title, performerCode, isAllDay, content, startDate, endDate, reminder, participantsList,
                        taskType, attachments)
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
                            getViewState().taskAddedSuccessfully();
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