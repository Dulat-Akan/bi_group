package bi.bigroup.life.data.network;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import bi.bigroup.life.config.DebugConfig;
import bi.bigroup.life.data.cache.preferences.Preferences;
import bi.bigroup.life.data.network.interceptors.UserTokenInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static bi.bigroup.life.config.BiGroupConfig.API_BASE_URL;

public class RetrofitServiceGenerator {
    private static final int CONNECTION_TIMEOUT_IN_MS = 60000;
    private static final int READ_TIMEOUT_IN_MS = 60000;

    private static RetrofitServiceGenerator instance = null;

    private Preferences preferences;
    private OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    public static RetrofitServiceGenerator getInstance(Preferences preferences) {
        if (instance == null) {
            instance = new RetrofitServiceGenerator(preferences);
        }
        return instance;
    }

    private RetrofitServiceGenerator(Preferences preferences) {
        this.preferences = preferences;
    }

    public <S> S createService(Class<S> serviceClass) {
        if (okHttpClient == null) {
            configureOkHttpClientBuilder();
            // DEV
//            okHttpClient = okHttpClientBuilder.build();

            // PROD
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkServerTrusted(final X509Certificate[] chain,
                                               final String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(final X509Certificate[] chain,
                                               final String authType) throws CertificateException {
                }
            }};

            SSLContext sslContext;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                okHttpClientBuilder.sslSocketFactory(sslContext.getSocketFactory());

                HostnameVerifier hostnameVerifier = (hostname, session) -> true;
                okHttpClientBuilder.hostnameVerifier( hostnameVerifier);
                okHttpClient = okHttpClientBuilder.build();

            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }
        }

        if (retrofit == null) {
            retrofit = getRetrofitBuilder().client(okHttpClient).build();
        }
        return retrofit.create(serviceClass);
    }

    private void configureOkHttpClientBuilder() {
        okHttpClientBuilder
                .connectTimeout(CONNECTION_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS);
        if (DebugConfig.DEV_BUILD) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(logging);
        }

        // If Stetho is enabled, StethoInterceptor allows monitoring network packets in Chrome Dev Tools on your
        // PC through chrome://inspect
        okHttpClientBuilder.addNetworkInterceptor(new com.facebook.stetho.okhttp3.StethoInterceptor());
        okHttpClientBuilder.addInterceptor(new UserTokenInterceptor(preferences));
    }

    private Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonProvider.gson));
    }
}