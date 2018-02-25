package bi.bigroup.life.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.EditorInfo;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.auth.ForgotPwdPresenter;
import bi.bigroup.life.mvp.auth.ForgotPwdView;
import bi.bigroup.life.ui.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

import static bi.bigroup.life.utils.ContextUtils.clearFocusFromAllViews;
import static bi.bigroup.life.utils.ContextUtils.hideSoftKeyboard;

public class ForgotPwdActivity extends BaseActivity implements ForgotPwdView {
    @InjectPresenter
    ForgotPwdPresenter mvpPresenter;
    @BindView(R.id.et_phone) MaterialEditText et_phone;

    public static Intent getIntent(Context context) {
        return new Intent(context, ForgotPwdActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_fogot_pwd;
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

    @OnClick(R.id.btn_send)
    void onLoginClick() {
        clearFocusFromAllViews(fl_parent);
        hideSoftKeyboard(fl_parent);
        mvpPresenter.setPhone(et_phone.getText().toString());
        mvpPresenter.checkGeneralInfo();
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        finish();
    }

    @OnEditorAction(R.id.et_phone)
    boolean onPwdInputAction(int action) {
        if (action == EditorInfo.IME_ACTION_NEXT) {
            hideSoftKeyboard(et_phone);
            clearFocusFromAllViews(fl_parent);
            return true;
        }
        return false;
    }

    private void showInputFieldError(MaterialEditText inputField, @StringRes int errorRes) {
        inputField.setError(getString(errorRes));
    }

    ///////////////////////////////////////////////////////////////////////////
    // ForgotPwdView implementation                                              ///
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showPhoneError(@StringRes int errorRes) {
        showInputFieldError(et_phone, errorRes);
    }

    @Override
    public void openNewPwdActivity() {
        startActivity(NewPwdActivity.getIntent(this));
    }
}