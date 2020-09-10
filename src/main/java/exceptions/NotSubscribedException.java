package exceptions;

public class NotSubscribedException extends RuntimeException {
    private int errorCode;

    public  NotSubscribedException(String message, int errorCode){
        super(message);

        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
