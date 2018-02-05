package bi.bigroup.life.data.network.exceptions;

public class AppVersionException extends Exception {

    public AppVersionException() {
    }

    public AppVersionException(String message) {
        super(message);
    }

    public AppVersionException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppVersionException(Throwable cause) {
        super(cause);
    }

}