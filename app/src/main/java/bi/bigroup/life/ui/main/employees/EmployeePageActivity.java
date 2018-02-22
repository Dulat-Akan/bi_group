package bi.bigroup.life.ui.main.employees;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.mvp.main.employees.EmployeePagePresenter;
import bi.bigroup.life.mvp.main.employees.EmployeePageView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.utils.GlideUtils;
import bi.bigroup.life.views.RoundedImageView;
import butterknife.BindView;
import butterknife.OnClick;

import static bi.bigroup.life.utils.Constants.KEY_CODE;
import static bi.bigroup.life.utils.Constants.getProfilePicture;
import static bi.bigroup.life.utils.StringUtils.isStringOk;

public class EmployeePageActivity extends BaseActivity implements EmployeePageView {
    @InjectPresenter
    EmployeePagePresenter mvpPresenter;
    @BindView(R.id.img_avatar) RoundedImageView img_avatar;
    @BindView(R.id.tv_surname) TextView tv_surname;
    @BindView(R.id.tv_firstname) TextView tv_firstname;
    @BindView(R.id.tv_specialty) TextView tv_specialty;
    @BindView(R.id.tv_login) TextView tv_login;
    @BindView(R.id.tv_manager) TextView tv_manager;
    @BindView(R.id.tv_phone) TextView tv_phone;
    @BindView(R.id.tv_email) TextView tv_email;


    private String code;
    private String mobilePhoneNumber;

    public static Intent getIntent(Context context, String code) {
        Intent intent = new Intent(context, EmployeePageActivity.class);
        intent.putExtra(KEY_CODE, code);
        return intent;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_employee_page;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.init(this, dataLayer);
        handleIntent();
        mvpPresenter.getEmployee(code);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            code = intent.getStringExtra(KEY_CODE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpPresenter.onDestroyView();
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        finish();
    }

    @OnClick(R.id.img_call)
    void onCallClick() {
        if (isStringOk(mobilePhoneNumber)) {

        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // EmployeePageView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setEmployee(Employee employee) {
        GlideUtils.showAvatar(this, img_avatar, getProfilePicture(employee.getCode()), R.drawable.ic_avatar);
        tv_surname.setText(employee.getFullName());
        tv_firstname.setText(employee.getFirstName());
        tv_specialty.setText(employee.getJobPosition());
        tv_login.setText(employee.getLogin());
        tv_manager.setText(employee.getLogin()); // TODO: add manager's fullname
        tv_phone.setText(employee.getMobilePhoneNumber());
        tv_email.setText(employee.getEmail());
    }
}