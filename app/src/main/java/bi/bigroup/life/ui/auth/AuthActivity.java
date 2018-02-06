package bi.bigroup.life.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.inputmethod.EditorInfo;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.auth.AuthPresenter;
import bi.bigroup.life.mvp.auth.AuthView;
import bi.bigroup.life.ui.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

import static bi.bigroup.life.utils.ContextUtils.clearFocusFromAllViews;
import static bi.bigroup.life.utils.ContextUtils.hideSoftKeyboard;

public class AuthActivity extends BaseActivity implements AuthView {
    @InjectPresenter
    AuthPresenter mvpPresenter;
    @BindView(R.id.et_phone) MaterialEditText et_phone;
    @BindView(R.id.et_pwd) MaterialEditText et_pwd;

    public static Intent newLogoutIntent(Context context) {
        return new Intent(context, AuthActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, AuthActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_auth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.init(this, dataLayer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpPresenter.onDestroyView();
    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {
        clearFocusFromAllViews(fl_parent);
        hideSoftKeyboard(fl_parent);
        mvpPresenter.setPhone(et_phone.getText().toString());
        mvpPresenter.setPwd(et_pwd.getText().toString());
        mvpPresenter.checkGeneralInfo();
    }

    @OnEditorAction(R.id.et_pwd)
    boolean onPwdInputAction(int action) {
        if (action == EditorInfo.IME_ACTION_NEXT) {
            hideSoftKeyboard(et_pwd);
            clearFocusFromAllViews(fl_parent);
            return true;
        }
        return false;
    }

    private void showInputFieldError(MaterialEditText inputField, @StringRes int errorRes) {
        inputField.setError(getString(errorRes));
    }

    @OnClick(R.id.tv_help)
    void onForgotPwdClick() {
        mvpPresenter.onForgotPwdClick();
    }

    ///////////////////////////////////////////////////////////////////////////
    // AuthView implementation                                              ///
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showPhoneError(@StringRes int errorRes) {
        showInputFieldError(et_phone, errorRes);
    }

    @Override
    public void showPwdError(@StringRes int errorRes) {
        showInputFieldError(et_pwd, errorRes);
    }

    @Override
    public void openForgotPwdActivity() {
//        startActivity(ForgotPwdActivity.getIntent(this));
    }

    @Override
    public void onAuthorizationSuccess() {
//        startActivity(MainActivity.getIntent(this));
    }
}