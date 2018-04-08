package bi.bigroup.life.mvp.auth;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.auth.Auth;
import bi.bigroup.life.data.params.auth.AuthParams;
import bi.bigroup.life.data.repository.auth.AuthRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Subscriber;

import static bi.bigroup.life.utils.FingerPrintUtils.isAvailableFingerPrint;
import static bi.bigroup.life.utils.StringUtils.isStringOk;
import static bi.bigroup.life.utils.StringUtils.trim;

@InjectViewState
public class AuthPresenter extends BaseMvpPresenter<AuthView> {
    private AuthParams form;

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        getViewState().showFingerPrintContainer(isAvailableFingerPrint(context));
        form = new AuthParams();
        if (preferences.isAuthenticated()) {
            getViewState().alreadyAuthorized();
        } else if (preferences.hasFingerPrint()) {
            getViewState().openFingerPrintActivity();
        }
    }

    public void setUsername(String login) {
        form.login = trim(login);
    }

    public void setPwd(String password) {
        form.password = trim(password);
    }

    public void checkGeneralInfo(boolean fpChecked) {
        boolean ok = validateUsername();
        ok &= validatePwd();
        if (ok) {
            signIn(fpChecked);
        }
    }

    private void signIn(boolean fpChecked) {
        AuthRepositoryProvider.provideRepository(dataLayer.getApi())
                .signIn(form)
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribe(new Subscriber<Auth>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(Auth auth) {
                        if (auth != null) {
                            if (isStringOk(auth.token)) {
                                preferences.setToken(auth.token);
                                List<String> roles = auth.roles;
                                if (roles != null && roles.size() > 0) {
                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 0; i < roles.size(); i++) {
                                        sb.append(roles.get(i)).append(",");
                                    }
                                    preferences.setRoles(sb.toString());
                                }
                                if (fpChecked) {
                                    if (!isAvailableFingerPrint(context)) {
                                        // If there is no way to authorize through finger
                                        getViewState().showRequestSuccess(R.string.fingerprint_not_available);
                                        getViewState().onAuthorizationSuccess();
                                    } else {
                                        getViewState().openFingerPrintActivity(form);
                                    }
                                } else {
                                    // If there is no way to authorize through finger
                                    getViewState().onAuthorizationSuccess();
                                }
                            }
                        }
                    }
                });
    }

//    public void onForgotPwdClick() {
//        getViewState().openForgotPwdActivity();
//    }

    ///////////////////////////////////////////////////////////////////////////
    // Validation                                                           ///
    ///////////////////////////////////////////////////////////////////////////

    private boolean validateUsername() {
        int errorRes = form.validateLogin();
        if (errorRes != 0) {
            getViewState().showUsernameError(errorRes);
        }
        return errorRes == 0;
    }

    private boolean validatePwd() {
        int errorRes = form.validatePwd();
        if (errorRes != 0) {
            getViewState().showPwdError(errorRes);
        }
        return errorRes == 0;
    }
}