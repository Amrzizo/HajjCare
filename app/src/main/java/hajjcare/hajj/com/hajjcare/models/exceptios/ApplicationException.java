package hajjcare.hajj.com.hajjcare.models.exceptios;

/**
 * Created by amra on 2/2/2018.
 */

public class ApplicationException extends RuntimeException {

    private Throwable cause;
    private String userMessage;
    private int errorCode;
    private String methodName;

    public ApplicationException(String message) {

        this.userMessage = message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
