package bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_sdesk;

import bi.bigroup.life.mvp.BaseMvpView;

public interface AddSdeskView extends BaseMvpView {
    void showRequestPermissionDialog(boolean isRequestPermission);

    void selectMultipleFiles();

    void showContentError(int field_error);

    void showDateError(int field_error);

    void requestAddedSuccessfully();
}
