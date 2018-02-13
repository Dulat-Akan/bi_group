package bi.bigroup.life.data.cache.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import static android.text.TextUtils.isEmpty;
import static bi.bigroup.life.utils.Constants.CACHE_TIME;
import static bi.bigroup.life.utils.Constants.KEY_TOKEN;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;

public class PreferencesProvider implements Preferences {
    private final SharedPreferences sharedPreferences;
    private Gson gson;

    public PreferencesProvider(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
    }

    public void clearAllPreferences() {
        synchronized (sharedPreferences) {
            sharedPreferences.edit().clear().apply();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Preferences implementation
    ///////////////////////////////////////////////////////////////////////////

    public boolean isAuthenticated() {
        return !isEmpty(sharedPreferences.getString(KEY_TOKEN, EMPTY_STR));
    }

    @Override
    public void setToken(String token) {
        synchronized (sharedPreferences) {
            sharedPreferences.edit().putString(KEY_TOKEN, token).apply();
        }
    }

    @Override
    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, EMPTY_STR);
    }

    /****** Gradient colors *******/
    @Override
    public void setGradientCacheTime(long time) {
        synchronized (sharedPreferences) {
            sharedPreferences.edit().putLong(CACHE_TIME, time).apply();
        }
    }

    @Override
    public long getGradientCacheTime() {
        return sharedPreferences.getLong(CACHE_TIME, 0);
    }

}
