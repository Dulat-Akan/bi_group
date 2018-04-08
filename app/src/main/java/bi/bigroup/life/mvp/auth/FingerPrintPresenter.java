package bi.bigroup.life.mvp.auth;

import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import com.arellomobile.mvp.InjectViewState;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.auth.Auth;
import bi.bigroup.life.data.params.auth.AuthParams;
import bi.bigroup.life.data.repository.auth.AuthRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import bi.bigroup.life.utils.LOTimber;
import rx.Subscriber;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;
import static bi.bigroup.life.utils.DeviceUtils.isAboveM;
import static bi.bigroup.life.utils.StringUtils.isStringOk;

@InjectViewState
public class FingerPrintPresenter extends BaseMvpPresenter<FingerPrintView> {
    private AuthParams form;
    // Finger print
    private FingerprintManager fingerprintManager;
    private KeyStore keyStore;
    private static final String KEY_NAME = "SwA";

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        if (!isAvailableFingerPrint()) {
            // If there is no way to authorize through finger
            getViewState().showRequestSuccess(R.string.fingerprint_not_available);
            getViewState().showFingerPrintError(R.string.fingerprint_not_available);
        } else {
            authenticateWithFinger();
        }
    }

    public void setAuth(AuthParams auth) {
        form = auth;
    }

    private void signIn(AuthParams params) {
        AuthRepositoryProvider.provideRepository(dataLayer.getApi())
                .signIn(params)
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
                        if (auth != null) {
                            if (isStringOk(auth.token)) {
                                preferences.setToken(auth.token);
                                List<String> roles = auth.roles;
                                if (roles != null && roles.size() > 0) {
                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 0; i < roles.size(); i++) {
                                        sb.append(roles.get(i)).append(",");
                                    }
                                    preferences.setRoles(sb.toString());
                                }
                                getViewState().onAuthorizationSuccess();
                            }
                        }
                    }
                });
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finger Print
    ///////////////////////////////////////////////////////////////////////////

    private void authenticateWithFinger() {
        if (isAboveM()) {
            // We are ready to set up the cipher and the key
            try {
                generateKey();
                Cipher cipher = generateCipher();
                if (cipher != null) {
                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    cryptoObject.getSignature();
                    CancellationSignal signal = new CancellationSignal();
                    try {
                        fingerprintManager.authenticate(cryptoObject, signal, 0,
                                new FingerprintManager.AuthenticationCallback() {
                                    @Override
                                    public void onAuthenticationError(int errorCode, CharSequence errString) {
                                        super.onAuthenticationError(errorCode, errString);
                                        getViewState().showFingerPrintError(R.string.hold_your_finger);
                                    }

                                    @Override
                                    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                                        super.onAuthenticationHelp(helpCode, helpString);
                                        getViewState().showFingerPrintError(R.string.hold_your_finger);
                                    }

                                    @Override
                                    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                                        super.onAuthenticationSucceeded(result);
                                        // Save user's data, for re-authorizing user with finger print
                                        if (form != null) {
                                            preferences.setAuthParams(form);
                                            getViewState().onAuthorizationSuccess();
                                        } else if (preferences.getAuthParams() != null) {
                                            // if user already authorized with finger print, then we should send saved data to our server
                                            AuthParams params = preferences.getAuthParams();
                                            if (isStringOk(params.login) && isStringOk(params.password)) {
                                                signIn(preferences.getAuthParams());
                                            } else {
                                                getViewState().showAuthError();
                                            }
                                        } else {
                                            getViewState().showAuthError();
                                        }
                                    }

                                    @Override
                                    public void onAuthenticationFailed() {
                                        super.onAuthenticationFailed();
                                        getViewState().showFingerPrintError(R.string.hold_your_finger);
                                    }
                                }, null);
                    } catch (SecurityException sce) {
                        sce.printStackTrace();
                    }
                }
            } catch (FingerprintException fpe) {
                // Handle exception
                getViewState().onAuthorizationSuccess();
            }
        }
    }

    private boolean isAvailableFingerPrint() {
        if (isAboveM()) {
            // Keyguard Manager
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
            // Fingerprint Manager
            fingerprintManager = (FingerprintManager) context.getSystemService(FINGERPRINT_SERVICE);
            try {
                // Check if the fingerprint sensor is present
                if (fingerprintManager != null && !fingerprintManager.isHardwareDetected()) {
                    LOTimber.d("FingerprintError: Fingerprint authentication not supported");
                    return false;
                }

                if (fingerprintManager != null && !fingerprintManager.hasEnrolledFingerprints()) {
                    LOTimber.d("FingerprintError: No fingerprint configured.");
                    return false;
                }

                if (keyguardManager != null && !keyguardManager.isKeyguardSecure()) {
                    LOTimber.d("FingerprintError: Secure lock screen not enabled");
                    return false;
                }
            } catch (SecurityException se) {
                se.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private void generateKey() throws FingerprintException {
        if (isAboveM()) {
            try {
                // Get the reference to the key store
                keyStore = KeyStore.getInstance("AndroidKeyStore");

                // Key generator to generate the key
                KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

                keyStore.load(null);
                keyGenerator.init(new
                        KeyGenParameterSpec.Builder(KEY_NAME,
                        KeyProperties.PURPOSE_ENCRYPT |
                                KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(
                                KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build());
                keyGenerator.generateKey();

            } catch (KeyStoreException
                    | NoSuchAlgorithmException
                    | NoSuchProviderException
                    | InvalidAlgorithmParameterException
                    | CertificateException
                    | IOException exc) {
                exc.printStackTrace();
                throw new FingerprintException(exc);
            }
        }
    }

    private Cipher generateCipher() throws FingerprintException {
        if (isAboveM()) {
            try {
                Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7);
                SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                        null);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                return cipher;
            } catch (NoSuchAlgorithmException
                    | NoSuchPaddingException
                    | InvalidKeyException
                    | UnrecoverableKeyException
                    | KeyStoreException exc) {
                exc.printStackTrace();
                throw new FingerprintException(exc);
            }
        }
        return null;
    }

    private class FingerprintException extends Exception {
        FingerprintException(Exception e) {
            super(e);
        }
    }
}