package hajjcare.hajj.com.hajjcare.models.network;


/**
 * Created by amra on 03/03/2018.
 */

public class ServerBaseResponse {


    private String error;
    private Status status;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getFailureDetails() {

        StringBuilder builder = new StringBuilder(String.valueOf((getStatus().getStatusCode())));
        builder.append("\t");
        return builder.toString().isEmpty() ? "" : builder.toString();
    }

}

