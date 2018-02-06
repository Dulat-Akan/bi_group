package bi.bigroup.life.data.params.auth;

import android.support.annotation.StringRes;

import bi.bigroup.life.R;

import static android.text.TextUtils.isEmpty;
import static bi.bigroup.life.utils.Constants.DEFAULT_MAX_LENGTH;
import static bi.bigroup.life.utils.Constants.MIN_LENGHT_PASSWORD;
import static bi.bigroup.life.utils.StringUtils.length;

public class NewPwdParams {
    public String password;
    public String password_confirmation;

    @StringRes
    public int validatePwd() {
        if (isEmpty(password)) {
            return R.string.field_error;
        } else if(length(password) < MIN_LENGHT_PASSWORD){
             return R.string.password_invalid_error;
        } else if (length(password) > DEFAULT_MAX_LENGTH) {
            return R.string.form_input_too_long;
        } else {
            return 0;
        }
    }

    public int validateConfirmPwd() {
        if (isEmpty(password_confirmation)) {
            return R.string.field_error;
        } else if(length(password) < MIN_LENGHT_PASSWORD){
            return R.string.password_invalid_error;
        } else if (!password.equals(password_confirmation)) {
            return R.string.passwords_not_matches;
        } else if (length(password_confirmation) > DEFAULT_MAX_LENGTH) {
            return R.string.form_input_too_long;
        } else {
            return 0;
        }
    }
}
