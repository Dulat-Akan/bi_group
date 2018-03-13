package bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_task;

import bi.bigroup.life.mvp.BaseMvpView;

public interface AddTaskView extends BaseMvpView {

    void showRequestPermissionDialog(boolean isRequestPermission);

    void selectMultipleFiles();

    void showTitleError(int error);

    void showNotificationError(int error);

    void showStartDateError(int error);

    void showEndDateError(int error);

    void showPerformerError(int error);

    void showTaskTypeError(int error);

    void showContentError(int error);

    void taskAddedSuccessfully();
}
