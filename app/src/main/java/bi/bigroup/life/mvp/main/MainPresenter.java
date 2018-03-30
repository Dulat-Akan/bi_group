package bi.bigroup.life.mvp.main;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.user.User;
import bi.bigroup.life.data.repository.user.UserRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Subscriber;

import static bi.bigroup.life.data.models.employees.EmployeeRole.PR;
import static bi.bigroup.life.utils.StringUtils.isStringOk;

@InjectViewState
public class MainPresenter extends BaseMvpPresenter<MainView> {
    private User localUser;
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);

        // User roles
        boolean isRolePR = false;
        if (isStringOk(preferences.getRoles())) {
            String[] roles = preferences.getRoles().split(",");
            if (roles.length > 0) {
                for (String role : roles) {
                    if (role.equals(PR)) {
                        isRolePR = true;
                    }
                }
            }
        }
        getViewState().configureRolePR(isRolePR);

        localUser = preferences.getUser();
        if (localUser != null) {
            getViewState().showUserInfo(localUser);
        }
        getUser();
    }

    private void getUser() {
        UserRepositoryProvider.provideRepository(dataLayer.getApi())
                .getUser()
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(User user) {
                        if (user != null) {
                            localUser = user;
                            getViewState().showUserInfo(user);
                            preferences.setUser(user);
                        }
                    }
                });
    }
}