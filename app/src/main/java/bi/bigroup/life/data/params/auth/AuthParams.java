package bi.bigroup.life.data.params.auth;

import android.support.annotation.StringRes;

import org.parceler.Parcel;

import bi.bigroup.life.R;

import static android.text.TextUtils.isEmpty;
import static bi.bigroup.life.utils.Constants.MIN_LENGHT_PASSWORD;
import static bi.bigroup.life.utils.StringUtils.length;

@Parcel
public class AuthParams {
    public String login;
    public String password;

    @StringRes
    public int validateLogin() {
        if (isEmpty(login)) {
            return R.string.field_error;
        } else {
            return 0;
        }
    }

    @StringRes
    public int validatePwd() {
        if (isEmpty(password)) {
            return R.string.field_error;
        } else if (length(password) < MIN_LENGHT_PASSWORD) {
            return R.string.password_invalid_error;
        } else {
            return 0;
        }
    }
}