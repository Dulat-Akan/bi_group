package bi.bigroup.life.ui.auth;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;

import bi.bigroup.life.R;
import bi.bigroup.life.data.params.auth.AuthParams;
import bi.bigroup.life.mvp.auth.AuthPresenter;
import bi.bigroup.life.mvp.auth.AuthView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.ui.main.MainActivity;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

import static bi.bigroup.life.utils.Constants.HELP_PHONE;
import static bi.bigroup.life.utils.ContextUtils.clearFocusFromAllViews;
import static bi.bigroup.life.utils.ContextUtils.hideSoftKeyboard;
import static bi.bigroup.life.utils.ViewUtils.setStatusBarGradient;

public class AuthActivity extends BaseActivity implements AuthView {
    @InjectPresenter
    AuthPresenter mvpPresenter;
    @BindView(R.id.et_username) MaterialEditText et_username;
    @BindView(R.id.et_pwd) MaterialEditText et_pwd;
    @BindView(R.id.tv_bi_group_year) TextView tv_bi_group_year;
    @BindView(R.id.ll_fingerprint) LinearLayout ll_fingerprint;
    @BindView(R.id.sw_finger_print) Switch sw_finger_print;

    public static Intent newLogoutIntent(Context context) {
        return new Intent(context, AuthActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, AuthActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_auth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradient(this, R.drawable.gradient_blue_bg);
//        if (DebugConfig.DEV_BUILD) {
//        et_username.setText(TEST_USERNAME);
//        et_pwd.setText(TEST_PWD);
//        }

        mvpPresenter.init(this, dataLayer);
        tv_bi_group_year.setText(getString(R.string.copyright, String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));

//        final MaskedTextChangedListener listener = new MaskedTextChangedListener(
//                PHONE_MASK,
//                true,
//                et_phone,
//                null,
//                (maskFilled, extractedValue) -> {
//                    LOTimber.d(extractedValue);
//                    LOTimber.d(String.valueOf(maskFilled));
//                }
//        );

//        et_phone.addTextChangedListener(listener);
//        et_phone.setOnFocusChangeListener(listener);
//        et_phone.setHint(listener.placeholder());

//        EditTextWatcher watcherPhoneNumber = new EditTextWatcher(this, til_phone_number);
//        et_phone.addTextChangedListener(watcherPhoneNumber);
    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {
        clearFocusFromAllViews(fl_parent);
        hideSoftKeyboard(fl_parent);
        mvpPresenter.setUsername(et_username.getText().toString());
        mvpPresenter.setPwd(et_pwd.getText().toString());
        mvpPresenter.checkGeneralInfo(sw_finger_print.isChecked());
    }

    @OnClick(R.id.tv_help)
    void onHelpClick() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + HELP_PHONE)));
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.help))
                .setMessage(getString(R.string.help_description)).setPositiveButton(getString(R.string.call), dialogClickListener)
                .setNegativeButton(getString(R.string.cancellation), dialogClickListener).show();
    }

//    private String getPhone() {
//        Mask mask = new Mask(PHONE_MASK);
//        final String input = et_phone.getText().toString();
//        Mask.Result result = mask.apply(
//                new CaretString(input, input.length()),
//                true // you may consider disabling autocompletion for your case
//        );
//
//        //String output = result.getFormattedText().getString();
//        return "7" + result.getExtractedValue();
//    }

    @OnEditorAction(R.id.et_pwd)
    boolean onPwdInputAction(int action) {
        if (action == EditorInfo.IME_ACTION_NEXT) {
            hideSoftKeyboard(et_pwd);
            clearFocusFromAllViews(fl_parent);
            return true;
        }
        return false;
    }

    private void showInputFieldError(MaterialEditText inputField, @StringRes int errorRes) {
        inputField.setError(getString(errorRes));
    }

    ///////////////////////////////////////////////////////////////////////////
    // AuthView implementation                                              ///
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showUsernameError(@StringRes int errorRes) {
        showInputFieldError(et_username, errorRes);
    }

    @Override
    public void showPwdError(@StringRes int errorRes) {
        showInputFieldError(et_pwd, errorRes);
    }

    @Override
    public void openForgotPwdActivity() {
        startActivity(ForgotPwdActivity.getIntent(this));
    }

    @Override
    public void onAuthorizationSuccess() {
        startActivity(MainActivity.getIntent(this));
    }

    @Override
    public void alreadyAuthorized() {
        startActivity(MainActivity.alreadyAuthorized(this));
        finish();
    }

    @Override
    public void showFingerPrintContainer(boolean show) {
        ll_fingerprint.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void openFingerPrintActivity(AuthParams form) {
        startActivity(FingerPrintActivity.getIntent(this, form));
    }

    @Override
    public void openFingerPrintActivity() {
        // Call this method when user already registered finger print
        startActivity(FingerPrintActivity.getIntent(this));
    }
}