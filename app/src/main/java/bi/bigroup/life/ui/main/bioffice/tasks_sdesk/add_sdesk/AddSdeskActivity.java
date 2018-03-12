package bi.bigroup.life.ui.main.bioffice.tasks_sdesk.add_sdesk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.File;
import java.util.Date;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_sdesk.AddSdeskPresenter;
import bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_sdesk.AddSdeskView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.utils.ToastUtils;
import bi.bigroup.life.views.dialogs.CommonDialog;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

import static bi.bigroup.life.utils.DateUtils.TASKS_SERVICES_DISPLAY_FORMAT;
import static bi.bigroup.life.utils.DateUtils.getCreatingDate;
import static bi.bigroup.life.utils.DateUtils.getTaskSdeskCreationDate;
import static bi.bigroup.life.utils.permission.PermissionRequestCodes.PERMISSIONS_STORAGE;
import static bi.bigroup.life.utils.permission.PermissionRequestCodes.PERM_WRITE_EXTERNAL_STORAGE;
import static bi.bigroup.life.utils.permission.PermissionRequestCodes.STORAGE_PERMISSION_CODE;
import static bi.bigroup.life.utils.permission.PermissionUtils.isPermissionGranted;
import static bi.bigroup.life.utils.permission.PermissionUtils.reqPermissions;
import static bi.bigroup.life.utils.permission.PermissionUtils.shouldShowRequestPermission;
import static bi.bigroup.life.utils.permission.PermissionUtils.verifyPermissions;

public class AddSdeskActivity extends BaseActivity implements AddSdeskView {
    @InjectPresenter
    AddSdeskPresenter mvpPresenter;

    @BindView(R.id.et_content) MaterialEditText et_content;
    @BindView(R.id.et_title) MaterialEditText et_title;
    @BindView(R.id.et_category) MaterialEditText et_category;
    @BindView(R.id.et_deadline) MaterialEditText et_deadline;
    @BindArray(R.array.categories) String[] categories;
    private final String TEMP_PERFORMER = "Service Desk";
    private CommonDialog commonDialog;
    private FilePickerDialog filePickerDialog;
    private String[] files;
    private String endDate;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddSdeskActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_sdesk;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.init(this, dataLayer);
        commonDialog = new CommonDialog(this);
        // Multi files selection
        DialogProperties dialogProperties = new DialogProperties();
        dialogProperties.selection_mode = DialogConfigs.MULTI_MODE;
        dialogProperties.selection_type = DialogConfigs.FILE_SELECT;
        dialogProperties.root = new File(DialogConfigs.DEFAULT_DIR);
        dialogProperties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        dialogProperties.offset = new File(DialogConfigs.DEFAULT_DIR);
        dialogProperties.extensions = null;

        filePickerDialog = new FilePickerDialog(this, dialogProperties);
        filePickerDialog.setTitle(getString(R.string.choose_file));
        filePickerDialog.setDialogSelectionListener(files -> this.files = files);
        et_title.setText(TEMP_PERFORMER);
        et_category.setText(categories[0]);
        et_deadline.setText(getTaskSdeskCreationDate());
        endDate = getCreatingDate(new Date());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (verifyPermissions(grantResults)) {
                mvpPresenter.selectMultipleFiles();
            } else {
                mvpPresenter.onPermissionNotGranted(
                        shouldShowRequestPermission(AddSdeskActivity.this, PERM_WRITE_EXTERNAL_STORAGE));
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        finish();
    }

    @OnClick(R.id.et_title)
    void onPerformerClick() {
        onShowPerformerDialog();
    }

    @OnClick(R.id.et_category)
    void onCategoryClick() {
        onShowCategoryDialog();
    }

    @OnClick(R.id.et_deadline)
    void onDeadlineClick() {
        onShowDateTimePicker();
    }

    @OnClick(R.id.btn_add)
    void onAddRequestClick() {
        mvpPresenter.addRequest(et_content.getText().toString(), endDate,
                files);
    }

    @OnClick(R.id.ll_attach_file)
    void onAttachFileClick() {
        if (Build.VERSION.SDK_INT >= 23) {
            mvpPresenter.onSelectMultipleFiles(
                    isPermissionGranted(AddSdeskActivity.this, PERM_WRITE_EXTERNAL_STORAGE),
                    shouldShowRequestPermission(AddSdeskActivity.this, PERM_WRITE_EXTERNAL_STORAGE));
        } else {
            mvpPresenter.selectMultipleFiles();
        }
    }

    void onShowPerformerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] performers = {TEMP_PERFORMER};
        builder.setItems(performers, (dialog, which) -> {
            switch (which) {
                case 0:
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void onShowCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(categories, (dialog, which) -> et_category.setText(categories[which]));
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            String dateTime = TASKS_SERVICES_DISPLAY_FORMAT.format(date);
            endDate = getCreatingDate(date);
            et_deadline.setText(dateTime);
        }

        @Override
        public void onDateTimeCancel() {
        }
    };

    void onShowDateTimePicker() {
        Date currentDate = new Date();
        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(listener)
                .setInitialDate(currentDate)
                .setMinDate(currentDate)
                //.setMaxDate(maxDate)
                .setIs24HourTime(true)
                //.setTheme(SlideDateTimePicker.HOLO_DARK)
                //.setIndicatorColor(Color.parseColor("#990000"))
                .build()
                .show();
    }

    private void showInputFieldError(MaterialEditText inputField, @StringRes int errorRes) {
        inputField.setError(getString(errorRes));
    }

    ///////////////////////////////////////////////////////////////////////////
    // AddSdeskView implementation
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void showRequestPermissionDialog(boolean isRequestPermission) {
        commonDialog.showDialogYesNo(getString(R.string.rationale_storage));
        commonDialog.setCallback(new CommonDialog.CallbackYesNo() {
            @Override
            public void onClickYes() {
                if (isRequestPermission) {
                    reqPermissions(AddSdeskActivity.this, PERMISSIONS_STORAGE, STORAGE_PERMISSION_CODE);
                } else {
                    // Open Settings activity for allowing permission
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, STORAGE_PERMISSION_CODE);
                }
            }

            @Override
            public void onClickNo() {
            }
        });
    }

    @Override
    public void selectMultipleFiles() {
        if (filePickerDialog != null) {
            filePickerDialog.show();
        }
    }

    @Override
    public void showContentError(int error) {
        showInputFieldError(et_content, error);
    }

    @Override
    public void showDateError(int error) {
        showInputFieldError(et_deadline, error);
    }

    @Override
    public void requestAddedSuccessfully() {
        ToastUtils.showCenteredToast(this, R.string.success_response);
        finish();
    }
}