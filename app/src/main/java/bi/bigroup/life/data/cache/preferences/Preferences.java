package bi.bigroup.life.data.cache.preferences;

import bi.bigroup.life.data.models.user.User;

public interface Preferences {
    boolean isAuthenticated();

    void setToken(String token);

    String getToken();

    void setGradientCacheTime(long time);

    long getGradientCacheTime();

    void setUser(User user);

    User getUser();

}
