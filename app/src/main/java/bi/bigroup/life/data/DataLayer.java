package bi.bigroup.life.data;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import bi.bigroup.life.data.cache.db.AppDatabase;
import bi.bigroup.life.data.cache.preferences.Preferences;
import bi.bigroup.life.data.cache.preferences.PreferencesProvider;
import bi.bigroup.life.data.network.RetrofitServiceGenerator;
import bi.bigroup.life.data.network.api.bi_group.API;
import bi.bigroup.life.data.network.interceptors.UserTokenInterceptor;
import okhttp3.OkHttpClient;

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
    private final Picasso picasso;
    private final AppDatabase mDb;

    private DataLayer(Context context) {
        preferencesProvider = new PreferencesProvider(context);
        mDb = AppDatabase.getInMemoryDatabase(context);
        RetrofitServiceGenerator retrofitServiceGenerator =
                RetrofitServiceGenerator.getInstance(preferencesProvider);
        api = retrofitServiceGenerator.createService(API.class);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new UserTokenInterceptor(preferencesProvider))
                .build();

//        .protocols(Collections.singletonList(Protocol.HTTP_1_1))
        picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .build();
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

    public Picasso getPicasso() {
        return picasso;
    }

    public AppDatabase getDatabase() {
        return mDb;
    }
}
