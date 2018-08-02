package hajjcare.hajj.com.hajjcare.networklayer;




import hajjcare.hajj.com.hajjcare.models.network.RawResponse;

/**
 * Created by Amr Ahmed on 02/02/2018.
 */

public interface CallListener {
    void onSuccess(RawResponse rawResponse);

    void onFailure(Exception e);
}
