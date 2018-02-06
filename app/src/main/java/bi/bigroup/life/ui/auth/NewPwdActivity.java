package bi.bigroup.life.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.inputmethod.EditorInfo;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.auth.NewPwdPresenter;
import bi.bigroup.life.mvp.auth.NewPwdView;
import bi.bigroup.life.ui.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

import static bi.bigroup.life.utils.ContextUtils.clearFocusFromAllViews;
import static bi.bigroup.life.utils.ContextUtils.hideSoftKeyboard;

public class NewPwdActivity extends BaseActivity implements NewPwdView {
    @InjectPresenter
    NewPwdPresenter mvpPresenter;

    @BindView(R.id.et_pwd) MaterialEditText et_pwd;
    @BindView(R.id.et_pwd_confirm) MaterialEditText et_pwd_confirm;

    public static Intent getIntent(Context context) {
        return new Intent(context, NewPwdActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_new_pwd;
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

    @OnClick(R.id.btn_change)
    void onChangeClick() {
        clearFocusFromAllViews(fl_parent);
        hideSoftKeyboard(fl_parent);
        mvpPresenter.setNewPwd(et_pwd.getText().toString());
        mvpPresenter.setConfirmPwd(et_pwd_confirm.getText().toString());
        mvpPresenter.checkPasswords();
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

    ///////////////////////////////////////////////////////////////////////////
    // NewPwdView implementation                                              ///
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void showNewPwdError(int errorRes) {
        showInputFieldError(et_pwd, errorRes);
    }

    @Override
    public void showConfirmPwdError(int errorRes) {
        showInputFieldError(et_pwd_confirm, errorRes);
    }

}