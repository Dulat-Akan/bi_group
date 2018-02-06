package bi.bigroup.life.data.params.auth;

import android.support.annotation.StringRes;

import bi.bigroup.life.R;

import static android.text.TextUtils.isEmpty;
import static bi.bigroup.life.utils.Constants.MAX_LENGTH_IIN;
import static bi.bigroup.life.utils.Constants.MIN_LENGHT_PASSWORD;
import static bi.bigroup.life.utils.StringUtils.length;

public class AuthParams {
    public String phone;
    public String password;

    @StringRes
    public int validatePhone() {
        if (isEmpty(phone)) {
            return R.string.field_error;
        } else if (length(phone) < MAX_LENGTH_IIN
                || length(phone) > MAX_LENGTH_IIN) {
            return -1;
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

