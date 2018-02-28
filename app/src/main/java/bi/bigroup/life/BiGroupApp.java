package bi.bigroup.life;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;

import bi.bigroup.life.config.DebugConfig;
import bi.bigroup.life.data.cache.preferences.Preferences;
import bi.bigroup.life.data.cache.preferences.PreferencesProvider;
import bi.bigroup.life.utils.stetho.StethoCustomConfigBuilder;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class BiGroupApp extends Application {
    @Override
    public void onCreate() {
//        MultiDex.install(getApplicationContext());
        super.onCreate();
        setupFabric();
        setupTimber();
//        if (DebugConfig.DEV_BUILD) {
        setupStetho();
//        }
        Preferences preferences = new PreferencesProvider(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    private void setupTimber() {
        if (DebugConfig.DEV_BUILD) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new Timber.DebugTree());
//            Timber.plant(new CrashlyticsTree());
        }
    }

    private void setupStetho() {
        Stetho.Initializer initializer = new StethoCustomConfigBuilder(this)
                .viewHierarchyInspectorEnabled(false)
                .build();
        Stetho.initialize(initializer);
    }

    private void setupFabric() {
        Fabric.with(this, new Crashlytics());
    }
}