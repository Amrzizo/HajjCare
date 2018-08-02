package hajjcare.hajj.com.hajjcare.models.network;

/**
 * Created by amra on 01/02/2018.
 */

public class Status {

    private int statusCode;
    private String description;
    private String hostStatusCode = "0";
    private String message;
    private String userMessage;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHostStatusCode() {
        return hostStatusCode;
    }

    public void setHostStatusCode(String hostStatusCode) {
        this.hostStatusCode = hostStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}