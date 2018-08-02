package hajjcare.hajj.com.hajjcare.models.network;

/**
 * Created by Amr Ahmed on 31/01/2018.
 */

public class RawResponse {
    public String response ;
    public int code;
    public RawResponse(String response, int code){
        this.response = response;
        this.code = code;
    }

    @Override
    public String toString() {
        if(response != null){
            return response;
        }else {
            return "There is no Response from Server";
        }
    }
}
