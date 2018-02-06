package bi.bigroup.life.mvp.auth;

import bi.bigroup.life.mvp.BaseMvpView;

public interface NewPwdView extends BaseMvpView {
    void showNewPwdError(int errorRes);

    void showConfirmPwdError(int errorRes);

}
