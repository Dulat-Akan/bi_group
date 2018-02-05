package bi.bigroup.life.data.network.interceptors;

import java.io.IOException;

import bi.bigroup.life.data.cache.preferences.Preferences;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UserTokenInterceptor implements Interceptor {

    private Preferences preferences;
    public UserTokenInterceptor(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder newRequestBuilder = originalRequest.newBuilder();
        if (!preferences.isAuthenticated()) {
            newRequestBuilder.header("userDeviceToken", "TOKEN");
        }
        newRequestBuilder.header("FBUser-Agent", "android");
        newRequestBuilder.header("Accept", "MeekConfig.BASIC_AUTH_HEADER");

        newRequestBuilder.method(originalRequest.method(), originalRequest.body());
        Request newRequest = newRequestBuilder.build();
        return chain.proceed(newRequest);
    }
}