package bi.bigroup.life.data.cache.preferences;

public interface Preferences {
    boolean isAuthenticated();

    void setToken(String token);

    String getToken();

    void setGradientCacheTime(long time);

    long getGradientCacheTime();

}
