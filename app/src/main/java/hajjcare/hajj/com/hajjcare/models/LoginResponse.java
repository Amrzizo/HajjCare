package hajjcare.hajj.com.hajjcare.models;


import hajjcare.hajj.com.hajjcare.models.network.ServerBaseResponse;

/**
 * Created by amra on 2/5/2018.
 */

public class LoginResponse extends ServerBaseResponse {

    private String Find;

    public String getFind() {
        return Find;
    }

    public void setFind(String find) {
        Find = find;
    }
}
