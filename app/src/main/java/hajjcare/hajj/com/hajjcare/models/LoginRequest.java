package hajjcare.hajj.com.hajjcare.models;

/**
 * Created by amra on 2/2/2018.
 */

public class LoginRequest {

    private String Id;


    public LoginRequest(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


}
