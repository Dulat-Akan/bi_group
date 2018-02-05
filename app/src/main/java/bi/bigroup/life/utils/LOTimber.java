package bi.bigroup.life.utils;

import bi.bigroup.life.BuildConfig;
import timber.log.Timber;

public class LOTimber {
    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            Timber.d(message);
        }
    }
}
