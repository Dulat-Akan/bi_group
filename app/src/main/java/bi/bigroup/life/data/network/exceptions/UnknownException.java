package bi.bigroup.life.data.network.exceptions;

public class UnknownException extends Exception {

    private String errorTitle;
    private String errorMessage;

    public UnknownException() {

    }

    public UnknownException(String errorTitle, String errorMessage) {
        this.errorTitle = errorTitle;
        this.errorMessage = errorMessage;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}