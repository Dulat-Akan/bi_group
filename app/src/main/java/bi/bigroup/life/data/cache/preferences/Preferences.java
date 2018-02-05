package bi.bigroup.life.data.cache.preferences;

public interface Preferences {
    boolean isAuthenticated();

    void setToken(String token);

    String getToken();

    boolean hasShownSplashScreen(String key);

    void setShownSplashScreen(boolean shown, String key);

    void setGradientCacheTime(long time);

    long getGradientCacheTime();

}
