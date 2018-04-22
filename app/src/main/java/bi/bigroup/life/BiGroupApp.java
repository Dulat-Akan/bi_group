package bi.bigroup.life;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;

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
        setupStetho();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    private void setupTimber() {
        Timber.plant(new Timber.DebugTree());
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