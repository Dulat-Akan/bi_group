package bi.bigroup.life.mvp.auth;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.auth.Auth;
import bi.bigroup.life.data.params.auth.AuthParams;
import bi.bigroup.life.data.repository.auth.AuthRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Subscriber;
import rx.Subscription;

import static bi.bigroup.life.utils.StringUtils.trim;

@InjectViewState
public class AuthPresenter extends BaseMvpPresenter<AuthView> {
    private AuthParams form;

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        form = new AuthParams();
    }

    public void setPhone(String phone) {
        form.phone = trim(phone);
    }

    public void setPwd(String password) {
        form.password = trim(password);
    }

    public void checkGeneralInfo() {
        boolean ok = validatePhone();
        ok &= validatePwd();
        if (ok) {
            signIn();
        }
    }

    private void signIn() {
        Subscription subscription = AuthRepositoryProvider.provideRepository(dataLayer.getApi())
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
                        getViewState().onAuthorizationSuccess();
                    }
                });
        compositeSubscription.add(subscription);
    }

    public void onForgotPwdClick() {
        getViewState().openForgotPwdActivity();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Validation                                                           ///
    ///////////////////////////////////////////////////////////////////////////

    private boolean validatePhone() {
        int errorRes = form.validatePhone();
        if (errorRes != 0) {
            getViewState().showPhoneError(errorRes);
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