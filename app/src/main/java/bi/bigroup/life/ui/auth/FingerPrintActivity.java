package bi.bigroup.life.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.parceler.Parcels;

import bi.bigroup.life.R;
import bi.bigroup.life.data.params.auth.AuthParams;
import bi.bigroup.life.mvp.auth.FingerPrintPresenter;
import bi.bigroup.life.mvp.auth.FingerPrintView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.ui.main.MainActivity;
import butterknife.BindView;
import butterknife.OnClick;

import static bi.bigroup.life.utils.Constants.KEY_PARAMS;
import static bi.bigroup.life.utils.ViewUtils.setStatusBarGradient;

public class FingerPrintActivity extends BaseActivity implements FingerPrintView {
    @InjectPresenter
    FingerPrintPresenter mvpPresenter;
    @BindView(R.id.tv_login_pwd) TextView tv_login_pwd;
    @BindView(R.id.tv_finger_state) TextView tv_finger_state;

    public static Intent getIntent(Context context, AuthParams result) {
        Intent i = new Intent(context, FingerPrintActivity.class);
        i.putExtra(KEY_PARAMS, Parcels.wrap(result));
        return i;
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, FingerPrintActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_finger_print;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradient(this, R.drawable.gradient_blue_bg);
        mvpPresenter.init(this, dataLayer);
        Intent intent = getIntent();
        if (intent.hasExtra(KEY_PARAMS)) {
            mvpPresenter.setAuth(Parcels.unwrap(getIntent().getParcelableExtra(KEY_PARAMS)));
        }
        tv_login_pwd.setPaintFlags(tv_login_pwd.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @OnClick(R.id.tv_login_pwd)
    void loginPwdClick() {
        finish();
    }

    ///////////////////////////////////////////////////////////////////////////
    // FingerPrintView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showFingerPrintError(@StringRes int errorRes) {
        tv_finger_state.setText(getString(errorRes));
    }

    @Override
    public void showAuthError() {
        dataLayer.clearAllPreferences();
        startActivity(AuthActivity.newLogoutIntent(this));
    }

    @Override
    public void onAuthorizationSuccess() {
        startActivity(MainActivity.getIntent(this));
    }
}