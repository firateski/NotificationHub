package exceptions;

public class CompanyBlacklistedException extends RuntimeException {
    private int errorCode;

    public  CompanyBlacklistedException(String message, int errorCode){
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
