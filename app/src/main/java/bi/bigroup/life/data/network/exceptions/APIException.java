package bi.bigroup.life.data.network.exceptions;

public class APIException extends Exception {

    private String errorMessage;

    public APIException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}