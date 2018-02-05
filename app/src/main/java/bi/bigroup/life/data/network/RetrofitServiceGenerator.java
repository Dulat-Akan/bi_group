package bi.bigroup.life.data.network;

import java.util.concurrent.TimeUnit;

import bi.bigroup.life.config.DebugConfig;
import bi.bigroup.life.data.cache.preferences.Preferences;
import bi.bigroup.life.data.network.interceptors.UserTokenInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceGenerator {
    private static final int CONNECTION_TIMEOUT_IN_MS = 60000;
    private static final int READ_TIMEOUT_IN_MS = 60000;

    private static RetrofitServiceGenerator instance = null;

    private Preferences preferences;
    private OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    public static RetrofitServiceGenerator getInstance(Preferences preferences) {
        if (instance == null) {
            instance = new RetrofitServiceGenerator(preferences);
        }
        return instance;
    }

    private RetrofitServiceGenerator(Preferences preferences) {
        this.preferences = preferences;
    }

    public <S> S createService(Class<S> serviceClass, String apiUrl) {
        configureOkHttpClientBuilder(apiUrl);
        OkHttpClient okHttpClient = okHttpClientBuilder.build();
        Retrofit retrofit = getRetrofitBuilder(apiUrl).client(okHttpClient).build();
        return retrofit.create(serviceClass);
    }

    private void configureOkHttpClientBuilder(String apiUrl) {
        okHttpClientBuilder
                .connectTimeout(CONNECTION_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS);

        if (DebugConfig.DEV_BUILD) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(logging);

            // If Stetho is enabled, StethoInterceptor allows monitoring network packets in Chrome Dev Tools on your
            // PC through chrome://inspect
            okHttpClientBuilder.addNetworkInterceptor(new com.facebook.stetho.okhttp3.StethoInterceptor());
        }

        okHttpClientBuilder.addInterceptor(new UserTokenInterceptor(preferences));
    }

    private Retrofit.Builder getRetrofitBuilder(String apiUrl) {
        return new Retrofit.Builder()
                .baseUrl(apiUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonProvider.gson));
    }
}