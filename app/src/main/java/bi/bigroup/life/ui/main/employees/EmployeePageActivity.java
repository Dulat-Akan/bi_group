package bi.bigroup.life.ui.main.employees;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.mvp.main.employees.EmployeePagePresenter;
import bi.bigroup.life.mvp.main.employees.EmployeePageView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.utils.EmailUtils;
import bi.bigroup.life.utils.animation.AvatarAnimation;
import bi.bigroup.life.utils.picasso.PicassoUtils;
import bi.bigroup.life.views.RoundedImageView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static bi.bigroup.life.utils.ConnectionDetector.isInternetOn;
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
    @BindView(R.id.tv_administrative_manager) TextView tv_administrative_manager;
    @BindView(R.id.tv_functional_manager) TextView tv_functional_manager;
    @BindView(R.id.tv_phone) TextView tv_phone;
    @BindView(R.id.tv_dob) TextView tv_dob;
    @BindView(R.id.tv_email) TextView tv_email;
    @BindView(R.id.img_expanded) ImageView img_expanded;
    @BindView(R.id.user_photo_container) RelativeLayout user_photo_container;
    @BindString(R.string.no_data) String noData;

    private AvatarAnimation avatarAnimation;
    private String code;
    private Employee employee;

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
        avatarAnimation = new AvatarAnimation(this, user_photo_container,
                img_expanded, img_avatar);
        mvpPresenter.getEmployee(code, isInternetOn(this));
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            code = intent.getStringExtra(KEY_CODE);
        }
    }

    @Override
    public void onBackPressed() {
        if (user_photo_container.getVisibility() == View.VISIBLE) {
            if (avatarAnimation != null) {
                avatarAnimation.closeImage();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpPresenter.onDestroyView();
    }

    @OnClick(R.id.img_full_close)
    void onCloseImage() {
        if (img_expanded.getVisibility() == View.VISIBLE) {
            avatarAnimation.closeImage();
        }
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        finish();
    }

    @OnClick(R.id.img_share)
    void onShareClick() {
        if (employee != null && isStringOk(employee.getFullName()) && isStringOk(employee.getWorkPhoneNumber())) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, employee.getFullName() + ": " + employee.getWorkPhoneNumber());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }

    @OnClick({R.id.img_call, R.id.tv_phone})
    void onCallClick() {
        if (employee != null && isStringOk(employee.getWorkPhoneNumber())) {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + employee.getWorkPhoneNumber())));
        }
    }

    @OnClick(R.id.tv_email)
    void onEmailClick() {
        if (employee != null && isStringOk(employee.getEmail())) {
            if (EmailUtils.isEmailValid(employee.getEmail())) {
                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{employee.getEmail()});
                startActivity(Intent.createChooser(emailIntent, getString(R.string.send)));
            }
        }
    }

    @OnClick(R.id.ll_avatar_container)
    void onOpenUserPhoto() {
        avatarAnimation.onOpenUserPhoto();
    }

    ///////////////////////////////////////////////////////////////////////////
    // EmployeePageView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setEmployee(Employee employee) {
        this.employee = employee;
        PicassoUtils.showAvatar(dataLayer.getPicasso(), img_avatar, getProfilePicture(code), R.drawable.ic_user);
        PicassoUtils.showAvatar(dataLayer.getPicasso(), img_expanded, getProfilePicture(code), R.color.transparent);
        tv_surname.setText(employee.getLastName());
        tv_firstname.setText(employee.getFirstName());
        tv_specialty.setText(isStringOk(employee.getJobPosition()) ? employee.getJobPosition() : noData);
        tv_administrative_manager.setText(isStringOk(employee.getAdministrativeChiefName())
                ? employee.getAdministrativeChiefName() : noData);
        tv_functional_manager.setText(isStringOk(employee.getFunctionalChiefName()) ? employee.getFunctionalChiefName()
                : noData);
        tv_phone.setText(isStringOk(employee.getWorkPhoneNumber()) ? employee.getWorkPhoneNumber() : noData);
        tv_dob.setText(isStringOk(employee.getBirthDate(this)) ? employee.getBirthDate(this) : noData);
        tv_email.setText(isStringOk(employee.getEmail()) ? employee.getEmail() : noData);
    }
}