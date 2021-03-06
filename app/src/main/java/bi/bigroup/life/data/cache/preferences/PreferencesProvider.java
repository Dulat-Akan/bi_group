package bi.bigroup.life.data.cache.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import bi.bigroup.life.data.models.user.User;
import bi.bigroup.life.data.params.auth.AuthParams;

import static android.text.TextUtils.isEmpty;
import static bi.bigroup.life.utils.Constants.CACHE_TIME;
import static bi.bigroup.life.utils.Constants.KEY_PARAMS;
import static bi.bigroup.life.utils.Constants.KEY_ROLES;
import static bi.bigroup.life.utils.Constants.KEY_TOKEN;
import static bi.bigroup.life.utils.Constants.KEY_USER;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;
import static bi.bigroup.life.utils.StringUtils.isStringOk;

public class PreferencesProvider implements Preferences {
    private final SharedPreferences sharedPreferences;
    private Gson gson;

    public PreferencesProvider(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
    }

    public void wipeOut() {
        synchronized (sharedPreferences) {
            // Don't clear the AUTH PARAMS (for re-authorizing with finger print)
            AuthParams authParams = getAuthParams();
            if (authParams != null && isStringOk(authParams.login) && isStringOk(authParams.password)) {
                sharedPreferences.edit().clear()
                        .putString(KEY_PARAMS, gson.toJson(authParams)).apply();
            } else {
                sharedPreferences.edit().clear().apply();
            }
        }
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

    @Override
    public void setRoles(String roles) {
        synchronized (sharedPreferences) {
            sharedPreferences.edit().putString(KEY_ROLES, roles).apply();
        }
    }

    @Override
    public String getRoles() {
        return sharedPreferences.getString(KEY_ROLES, EMPTY_STR);
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

    /****** User *******/
    @Override
    public void setUser(User user) {
        synchronized (sharedPreferences) {
            sharedPreferences.edit().putString(KEY_USER, gson.toJson(user)).apply();
        }
    }

    @Override
    public User getUser() {
        return gson.fromJson(sharedPreferences.getString(KEY_USER, null), User.class);
    }

    @Override
    public void setAuthParams(AuthParams params) {
        synchronized (sharedPreferences) {
            sharedPreferences.edit().putString(KEY_PARAMS, gson.toJson(params)).apply();
        }
    }

    @Override
    public AuthParams getAuthParams() {
        return gson.fromJson(sharedPreferences.getString(KEY_PARAMS, null), AuthParams.class);
    }

    @Override
    public boolean hasFingerPrint() {
        AuthParams params = getAuthParams();
        return params != null && isStringOk(params.login) && isStringOk(params.password);
    }
}
