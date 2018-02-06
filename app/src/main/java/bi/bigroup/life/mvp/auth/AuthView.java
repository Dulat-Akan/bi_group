package bi.bigroup.life.mvp.auth;

import android.support.annotation.StringRes;

import bi.bigroup.life.mvp.BaseMvpView;

public interface AuthView extends BaseMvpView {
    void showPhoneError(@StringRes int errorRes);

    void showPwdError(@StringRes int errorRes);

    void openForgotPwdActivity();

    void onAuthorizationSuccess();
}
