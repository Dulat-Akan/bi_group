package bi.bigroup.life.utils;

import android.os.Looper;

public class Validate {
    private static boolean isUiThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public static void runningOnUiThread() {
        if (!isUiThread()) {
            throw new IllegalStateException("Method call should happen from the main thread.");
        }
    }

    public static void runningNotOnUiThread() {
        if (isUiThread()) {
            throw new IllegalStateException("Method call should not happen from the main thread.");
        }
    }
}
