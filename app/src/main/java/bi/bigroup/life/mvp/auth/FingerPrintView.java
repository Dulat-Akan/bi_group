package bi.bigroup.life.mvp.auth;

import android.support.annotation.StringRes;

import bi.bigroup.life.mvp.BaseMvpView;

public interface FingerPrintView extends BaseMvpView {
    void showFingerPrintError(@StringRes int errorRes);

    void showAuthError();

    void onAuthorizationSuccess();
}