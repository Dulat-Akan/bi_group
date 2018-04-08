package bi.bigroup.life.mvp.auth;

import android.support.annotation.StringRes;

import bi.bigroup.life.data.params.auth.AuthParams;
import bi.bigroup.life.mvp.BaseMvpView;

public interface AuthView extends BaseMvpView {
    void showUsernameError(@StringRes int errorRes);

    void showPwdError(@StringRes int errorRes);

    void openForgotPwdActivity();

    void onAuthorizationSuccess();

    void alreadyAuthorized();

    void showFingerPrintContainer(boolean show);

    void openFingerPrintActivity(AuthParams form);

    void openFingerPrintActivity();
}