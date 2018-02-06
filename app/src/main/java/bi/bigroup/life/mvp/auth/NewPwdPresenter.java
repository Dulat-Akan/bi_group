package bi.bigroup.life.mvp.auth;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.params.auth.NewPwdParams;
import bi.bigroup.life.mvp.BaseMvpPresenter;

import static bi.bigroup.life.utils.StringUtils.trim;

@InjectViewState
public class NewPwdPresenter extends BaseMvpPresenter<NewPwdView> {
    private NewPwdParams formNewPwd;

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        formNewPwd = new NewPwdParams();
    }

    public void setNewPwd(String newPwd) {
        formNewPwd.password = trim(newPwd);
    }

    public void setConfirmPwd(String confirmPwd) {
        formNewPwd.password_confirmation = trim(confirmPwd);
    }

    public void checkPasswords() {
        if (validatePwd() && validateConfirmPwd()) {

        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Validation                                                           ///
    ///////////////////////////////////////////////////////////////////////////

    private boolean validatePwd() {
        int errorRes = formNewPwd.validatePwd();
        if (errorRes != 0) {
            getViewState().showNewPwdError(errorRes);
        }
        return errorRes == 0;
    }

    private boolean validateConfirmPwd() {
        int errorRes = formNewPwd.validateConfirmPwd();
        if (errorRes != 0) {
            getViewState().showConfirmPwdError(errorRes);
        }
        return errorRes == 0;
    }
}