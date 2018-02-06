package bi.bigroup.life.mvp.auth;

import android.support.annotation.StringRes;

import bi.bigroup.life.mvp.BaseMvpView;

public interface ForgotPwdView extends BaseMvpView {
    void showPhoneError(@StringRes int errorRes);

    void openNewPwdActivity();
}
