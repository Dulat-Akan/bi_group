package bi.bigroup.life.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import bi.bigroup.life.BuildConfig;
import bi.bigroup.life.R;

public class DeviceUtils {
    private final Context mContext;
    public static final String OS = "android";

    public DeviceUtils(Application context) {
        this.mContext = context;
    }

    public String getDeviceId() {
        return Settings.Secure.getString(this.mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    public static String getAppVersion() {
        return BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")";
    }

    public static String getApplicationName(Context context) {
        return (String) context.getApplicationInfo().loadLabel(context.getPackageManager());
    }

    public String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getOS() {
        return OS;
    }

    public static int[] getScreenWidthHeight(Context context) {
        int returnValue[] = new int[2];
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        if (isJellyBeanMR1OrAbove()) {
            DisplayMetrics realMetrics = new DisplayMetrics();
            display.getRealMetrics(realMetrics);
            returnValue[0] = realMetrics.widthPixels;
            returnValue[1] = realMetrics.heightPixels;

        } else {
            //This should be close, as lower API devices should not have window navigation bars
            returnValue[0] = display.getWidth();
            returnValue[1] = display.getHeight();
        }
        return returnValue;
    }

    public boolean isOnline() {
        NetworkInfo netInfo = ((ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    private static final String PLATFORM = "Android";

    private static double getDiagonalInInches(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        double d = displayMetrics.density * 160.0f;
        return Math.sqrt(Math.pow((double) displayMetrics.widthPixels / d, 2.0) + Math.pow((double) displayMetrics.heightPixels / d, 2.0));
    }

    public static Point getDisplaySize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }

    public static Point getDisplaySize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public static String getLanguage() {
        return DeviceUtils.getLocale().getLanguage().toLowerCase();
    }

    public static Locale getLocale() {
        return Locale.getDefault();
    }

    public static int getStatusBarHeight(Context context) {
        if (Build.VERSION.SDK_INT < 21) {
            return 0;
        }
        return DeviceUtils.getStatusBarHeight(context.getResources());
    }

    public static int getStatusBarHeight(Resources resources) {
        int n = 0;
        int n2 = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (n2 > 0) {
            n = resources.getDimensionPixelSize(n2);
        }
        return n;
    }

    public static int getToolbarHeight(Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
        }
        return context.getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material);
    }

    public static int getViewYLocationOnScreen(View view) {
        int[] arrn = new int[2];
        view.getLocationOnScreen(arrn);
        return arrn[1];
    }

    public static boolean hasLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == 0;
    }

    public static void removeOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
            return;
        }
        view.getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
    }

    public static float getScaledDensity(Context context, float fontSize) {
        return context.getResources().getDisplayMetrics().scaledDensity * fontSize;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void setUpViewBelowStatusBar(View view) {
        if (Build.VERSION.SDK_INT < 21 || view == null) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.setMargins(marginLayoutParams.leftMargin, DeviceUtils.getStatusBarHeight(view.getContext()), marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
    }

    public static boolean smallDensity(Context context) {
        int n = context.getResources().getDisplayMetrics().densityDpi;
        return n == 120 || n == 160 || n == 240;
    }

    public static boolean isAboveLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isAboveM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isAboveNougat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static boolean isJellyBeanMR1OrAbove() {
        return Build.VERSION.SDK_INT >= 17;
    }

    public static void logHashKey(Context context, String packageName) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.d("hash key", "hash:" + something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }
}
