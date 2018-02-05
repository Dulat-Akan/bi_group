package bi.bigroup.life.data.models.auth;

public class Auth {
    public String token;
    public Boolean social_authorized;

    public boolean isAuthorized() {
        return social_authorized != null ? social_authorized : false;
    }
}