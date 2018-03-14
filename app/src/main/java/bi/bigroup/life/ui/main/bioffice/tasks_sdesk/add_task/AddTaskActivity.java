package bi.bigroup.life.ui.main.bioffice.tasks_sdesk.add_task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_task.AddTaskPresenter;
import bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_task.AddTaskView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.utils.ToastUtils;
import bi.bigroup.life.views.dialogs.CommonDialog;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

import static bi.bigroup.life.utils.ContextUtils.clearFocusFromAllViews;
import static bi.bigroup.life.utils.ContextUtils.hideSoftKeyboard;
import static bi.bigroup.life.utils.DateUtils.TASKS_SERVICES_DISPLAY_FORMAT;
import static bi.bigroup.life.utils.DateUtils.getCreatingDate;
import static bi.bigroup.life.utils.DateUtils.getTaskSdeskCreationDate;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;
import static bi.bigroup.life.utils.StringUtils.isStringOk;
import static bi.bigroup.life.utils.permission.PermissionRequestCodes.PERMISSIONS_STORAGE;
import static bi.bigroup.life.utils.permission.PermissionRequestCodes.PERM_WRITE_EXTERNAL_STORAGE;
import static bi.bigroup.life.utils.permission.PermissionRequestCodes.STORAGE_PERMISSION_CODE;
import static bi.bigroup.life.utils.permission.PermissionUtils.isPermissionGranted;
import static bi.bigroup.life.utils.permission.PermissionUtils.reqPermissions;
import static bi.bigroup.life.utils.permission.PermissionUtils.shouldShowRequestPermission;
import static bi.bigroup.life.utils.permission.PermissionUtils.verifyPermissions;

public class AddTaskActivity extends BaseActivity implements AddTaskView, DialogFragmentCallback {
    @InjectPresenter
    AddTaskPresenter mvpPresenter;

    @BindView(R.id.et_title) MaterialEditText et_title;
    @BindView(R.id.et_start_date) MaterialEditText et_start_date;
    @BindView(R.id.et_end_date) MaterialEditText et_end_date;
    @BindView(R.id.et_content) MaterialEditText et_content;
    @BindView(R.id.cb_all_day) CheckBox cb_all_day;

    // Notifications
    @BindView(R.id.et_notification) MaterialEditText et_notification;
    @BindArray(R.array.notify_minutes) String[] notifications_minutes;
    private int[] notify_minute_values = {5, 10, 15, 30, 60};
    private int reminder = 0;

    // Task type
    @BindView(R.id.et_task_type) MaterialEditText et_task_type;
    @BindArray(R.array.task_types) String[] task_types;
    private int[] task_type_values = {10, 20};// Execute = 10, Approve = 20
    private int task_value = 0;

    private CommonDialog commonDialog;
    private FilePickerDialog filePickerDialog;
    private String[] files;

    private String startDate;
    private String endDate;
    private boolean isStartDate;

    // Performer & participants
    private android.support.v4.app.FragmentManager fragmentManager;
    private SearchUserDialogFragment searchDialog;
    private boolean isPerformerClick;
    @BindView(R.id.et_performer) MaterialEditText et_performer;
    @BindView(R.id.participants_layout) TagFlowLayout participants_layout;
    private String performerCode = EMPTY_STR;
    private TagAdapter<Employee> selectedUsersAdapter;
    private List<Employee> selectedUsersList;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddTaskActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_task;
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

        startDate = getCreatingDate(new Date());
        endDate = startDate;
        et_start_date.setText(getTaskSdeskCreationDate());
        et_end_date.setText(getTaskSdeskCreationDate());

        fragmentManager = getSupportFragmentManager();
        searchDialog = new SearchUserDialogFragment();
        EmployeesLayoutAdapter employeesAdapter = new EmployeesLayoutAdapter(this, R.layout.adapter_tags_list);
        employeesAdapter.setCallback(this::addEmployee);
        configureUsersLayout();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (verifyPermissions(grantResults)) {
                mvpPresenter.selectMultipleFiles();
            } else {
                mvpPresenter.onPermissionNotGranted(
                        shouldShowRequestPermission(AddTaskActivity.this, PERM_WRITE_EXTERNAL_STORAGE));
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            String dateTime = TASKS_SERVICES_DISPLAY_FORMAT.format(date);
            if (isStartDate) {
                startDate = getCreatingDate(date);
                et_start_date.setText(dateTime);
            } else {
                endDate = getCreatingDate(date);
                et_end_date.setText(dateTime);
            }
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

    private void configureUsersLayout() {
        selectedUsersList = new ArrayList<>();
        final LayoutInflater mInflater = LayoutInflater.from(this);
        participants_layout.setAdapter(selectedUsersAdapter = new TagAdapter<Employee>(selectedUsersList) {
            @Override
            public View getView(FlowLayout parent, int position, Employee employee) {
                View tagsLayout = mInflater.inflate(R.layout.tags_layout, participants_layout, false);
                TextView tv_name = tagsLayout.findViewById(R.id.tv_name);
                tv_name.setText(employee.getFullName());
                return tagsLayout;
            }
        });
        participants_layout.setOnTagClickListener((view, position, parent) -> {
            selectedUsersList.remove(position);
            selectedUsersAdapter.notifyDataChanged();
            return true;
        });
    }

    void addEmployee(Employee employee) {
        selectedUsersList.add(employee);
        selectedUsersAdapter.notifyDataChanged();
        clearFocusFromAllViews(fl_parent);
        hideSoftKeyboard(fl_parent);
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        finish();
    }

    @OnClick(R.id.et_notification)
    void onNotificationClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(notifications_minutes, (dialogInterface, index) -> {
            et_notification.setText(notifications_minutes[index]);
            reminder = notify_minute_values[index];
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.et_start_date)
    void onStartDateClick() {
        isStartDate = true;
        onShowDateTimePicker();
    }

    @OnClick(R.id.et_end_date)
    void onEndDateClick() {
        isStartDate = false;
        onShowDateTimePicker();
    }

    @OnClick(R.id.et_performer)
    void onSelectPerformerClick() {
        isPerformerClick = true;
        searchDialog.show(fragmentManager, EMPTY_STR);
    }

    @OnClick(R.id.et_add_participants)
    void onAddParticipants() {
        isPerformerClick = false;
        searchDialog.show(fragmentManager, EMPTY_STR);
    }

    @OnClick(R.id.et_task_type)
    void onAddTaskType() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(task_types, (dialogInterface, index) -> {
            et_task_type.setText(task_types[index]);
            task_value = task_type_values[index];
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.ll_attach_file)
    void onAttachFileClick() {
        if (Build.VERSION.SDK_INT >= 23) {
            mvpPresenter.onSelectMultipleFiles(
                    isPermissionGranted(AddTaskActivity.this, PERM_WRITE_EXTERNAL_STORAGE),
                    shouldShowRequestPermission(AddTaskActivity.this, PERM_WRITE_EXTERNAL_STORAGE));
        } else {
            mvpPresenter.selectMultipleFiles();
        }
    }

    @OnClick(R.id.btn_send)
    void onSendClick() {
        mvpPresenter.addTask(
                et_title.getText().toString(),
                cb_all_day.isChecked(),
                reminder,
                startDate, endDate,
                performerCode,
                selectedUsersList,
                task_value,
                et_content.getText().toString(),
                files);
    }

    private void showInputFieldError(MaterialEditText inputField, @StringRes int errorRes) {
        inputField.setError(getString(errorRes));
    }

    ///////////////////////////////////////////////////////////////////////////
    // AddTaskView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showRequestPermissionDialog(boolean isRequestPermission) {
        commonDialog.showDialogYesNo(getString(R.string.rationale_storage));
        commonDialog.setCallback(new CommonDialog.CallbackYesNo() {
            @Override
            public void onClickYes() {
                if (isRequestPermission) {
                    reqPermissions(AddTaskActivity.this, PERMISSIONS_STORAGE, STORAGE_PERMISSION_CODE);
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
    public void showTitleError(int error) {
        showInputFieldError(et_title, error);
    }

    @Override
    public void showNotificationError(int error) {
        showInputFieldError(et_notification, error);
    }

    @Override
    public void showStartDateError(int error) {
        showInputFieldError(et_start_date, error);
    }

    @Override
    public void showEndDateError(int error) {
        showInputFieldError(et_end_date, error);
    }

    @Override
    public void showPerformerError(int error) {
        showInputFieldError(et_performer, error);
    }

    @Override
    public void showTaskTypeError(int error) {
        showInputFieldError(et_task_type, error);
    }

    @Override
    public void showContentError(int error) {
        showInputFieldError(et_content, error);
    }

    @Override
    public void taskAddedSuccessfully() {
        ToastUtils.showCenteredToast(this, R.string.success_response);
        finish();
    }

    ///////////////////////////////////////////////////////////////////////////
    // DialogFragmentCallback implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onEmployeeSelected(Employee employee) {
        clearFocusFromAllViews(fl_parent);
        hideSoftKeyboard(fl_parent);
        if (isPerformerClick) {
            performerCode = employee.getCode();
            et_performer.setText(employee.getFullName());
        } else {
            if (employee != null && isStringOk(employee.getCode())) {
                boolean isAlreadyExist = false;
                for (int i = 0; i < selectedUsersList.size(); i++) {
                    if (employee.getCode().equals(selectedUsersList.get(i).getCode())) {
                        isAlreadyExist = true;
                        break;
                    }
                }

                if (isAlreadyExist) {
                    ToastUtils.showCenteredToast(this, R.string.employee_already_exist);
                } else {
                    addEmployee(employee);
                }
            } else {
                ToastUtils.showCenteredToast(this, R.string.field_error);
            }
        }
    }
}