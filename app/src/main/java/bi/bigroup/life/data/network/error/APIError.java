package bi.bigroup.life.data.network.error;

public class APIError {
    private String error;
    private String statusCode;

    public String getErrorMessage() {
        return error;
    }

    public String getStatusCode() {
        return statusCode;
    }
}