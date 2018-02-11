package bi.bigroup.life.data;

import android.content.Context;

import bi.bigroup.life.data.cache.preferences.Preferences;
import bi.bigroup.life.data.cache.preferences.PreferencesProvider;
import bi.bigroup.life.data.network.RetrofitServiceGenerator;
import bi.bigroup.life.data.network.api.bi_group.API;

import static bi.bigroup.life.config.BiGroupConfig.API_BASE_URL;

public class DataLayer {
    private static volatile DataLayer instance;

    public static DataLayer getInstance(Context context) {
        if (instance == null) {
            synchronized (DataLayer.class) {
                if (instance == null)
                    instance = new DataLayer(context.getApplicationContext());
            }
        }
        return instance;
    }

    private final PreferencesProvider preferencesProvider;
    private final API api;

    private DataLayer(Context context) {
        preferencesProvider = new PreferencesProvider(context);
        RetrofitServiceGenerator retrofitServiceGenerator = RetrofitServiceGenerator.getInstance(preferencesProvider);
        api = retrofitServiceGenerator.createService(API.class);
    }

    public Preferences getPreferences() {
        return preferencesProvider;
    }

    public void wipeOut() {
        preferencesProvider.clearAllPreferences();
    }

    public API getApi() {
        return api;
    }
}
