package bi.bigroup.life.data.models.auth;

import android.support.annotation.NonNull;

import org.parceler.Parcel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Parcel
public class AuthForm {
    public String email;
    public String pwd;
    public String firstName;
    public String lastName;
    public Date birthdate;
    public String gPlusToken;

    public AuthForm() {
    }

    public AuthForm(String email, String firstName, String lastName, Date birthdate, String gPlusToken) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.gPlusToken = gPlusToken;
    }

    public String getBirthDateStr(@NonNull String dateFormatStr) {
        return birthdate == null ? null : getBirthdateStr(new SimpleDateFormat(dateFormatStr, Locale.US));
    }

    public String getBirthdateStr(@NonNull DateFormat dateFormat) {
        return birthdate == null ? null : dateFormat.format(birthdate);
    }

//    @StringRes
//    public int validateEmail() {
//        if (isEmpty(email)) {
//            return R.string.err_form_email_empty;
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            return R.string.err_form_email_not_valid;
//        } else {
//            return 0;
//        }
//    }
//
//    @StringRes
//    public int validateConfirmEmail(String email) {
//        if (isEmpty(email)) {
//            return R.string.err_form_email_empty;
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            return R.string.err_form_email_not_valid;
//        } else if (!this.email.equals(email)) {
//            return R.string.err_form_confir_email_not_match;
//        } else {
//            return 0;
//        }
//    }

//    @StringRes
//    public int validatePwd() {
//        if (isEmpty(pwd)) {
//            return R.string.err_form_pwd_empty;
//        } else if (length(pwd) > DEFAULT_MAX_LENGTH) {
//            return R.string.err_form_input_too_long;
//        } else {
//            return 0;
//        }
//    }

//    public int validateConfirmPwd(String confirmPwd) {
//        if (isEmpty(confirmPwd)) {
//            return R.string.err_form_pwd_empty;
//        } else if (!this.pwd.equals(confirmPwd)) {
//            return R.string.err_form_confir_pwd_not_match;
//        } else if (length(pwd) > DEFAULT_MAX_LENGTH) {
//            return R.string.err_form_input_too_long;
//        } else {
//            return 0;
//        }
//    }
//
//    @StringRes
//    public int validateFirstName() {
//        if (isEmpty(firstName)) {
//            return R.string.err_form_first_name_empty;
//        } else if (length(firstName) > DEFAULT_MAX_LENGTH) {
//            return R.string.err_form_input_too_long;
//        } else {
//            return 0;
//        }
//    }
//
//    @StringRes
//    public int validateLastName() {
//        if (isEmpty(lastName)) {
//            return R.string.err_form_last_name_empty;
//        } else if (length(lastName) > DEFAULT_MAX_LENGTH) {
//            return R.string.err_form_input_too_long;
//        } else {
//            return 0;
//        }
//    }
//
//    @StringRes
//    public int validateBirthdate() {
//        if (birthdate == null) {
//            return R.string.err_form_birthdate_not_selected;
//        } else {
//            Calendar birthdateCal = new GregorianCalendar();
//            birthdateCal.setTime(birthdate);
//            Calendar todayCal = new GregorianCalendar();
//            todayCal.setTimeInMillis(System.currentTimeMillis());
//            int years = todayCal.get(YEAR) - birthdateCal.get(YEAR);
//            if (years < MIN_AGE || (years == MIN_AGE && todayCal.get(DAY_OF_YEAR) < birthdateCal.get(DAY_OF_YEAR))) {
//                return R.string.err_form_birthdate_too_young;
//            } else {
//                return 0;
//            }
//        }
//    }
//

}
