package hajjcare.hajj.com.hajjcare.networklayer;


/**
 * Created by Amr Ahmed on 01/02/2018.
 */

public interface OnCallComplete {
    void onSuccess(Object object);

    void onFailure(Exception appException);
}

