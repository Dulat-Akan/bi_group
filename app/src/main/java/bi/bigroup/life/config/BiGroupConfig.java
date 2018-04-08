package bi.bigroup.life.config;

import bi.bigroup.life.BuildConfig;

public class BiGroupConfig {
    public static final String SCHEME = "https://";// Test is: http     Prod is https
    public static final String API_BASE_URL = SCHEME + BuildConfig.URL_BASE;
}