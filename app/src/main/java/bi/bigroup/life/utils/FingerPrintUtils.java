package bi.bigroup.life.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;
import static bi.bigroup.life.utils.DeviceUtils.isAboveM;

public class FingerPrintUtils {
    public static boolean isAvailableFingerPrint(Context context) {
        if (isAboveM()) {
            // Keyguard Manager
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
            // Fingerprint Manager
            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(FINGERPRINT_SERVICE);
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
}
