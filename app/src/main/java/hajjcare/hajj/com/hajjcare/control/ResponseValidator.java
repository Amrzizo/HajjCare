package hajjcare.hajj.com.hajjcare.control;

import com.google.gson.Gson;

import hajjcare.hajj.com.hajjcare.BuildConfig;
import hajjcare.hajj.com.hajjcare.models.exceptios.ApplicationException;
import hajjcare.hajj.com.hajjcare.models.network.RawResponse;
import hajjcare.hajj.com.hajjcare.models.network.ServerBaseResponse;
import hajjcare.hajj.com.hajjcare.networklayer.OnCallComplete;
import hajjcare.hajj.com.hajjcare.utils.HajjCareUIUtils;
import hajjcare.hajj.com.hajjcare.views.activity.BaseActivity;

/**
 * Created by Amr Ahmed on 02/02/2018.
 */

public class ResponseValidator {
    public final static int RESPONSE_SUCCESS = 200;


    public ResponseValidator() {
    }

    public static boolean validateResponse(RawResponse rawResponse, Class clazz) throws ApplicationException {
        try {
            String bodyString = new String(rawResponse.response);
            if (bodyString != null && !bodyString.isEmpty()) {
                ServerBaseResponse serverBaseResponse = (ServerBaseResponse) new Gson().fromJson(bodyString, clazz);
                if (rawResponse.code== RESPONSE_SUCCESS) {
                    return true;
                } else {

                    final ApplicationException finalApplicationContextException = new ApplicationException(rawResponse.response);
                    ((BaseActivity) HajjCareApplicationClass.getInstance().getCurrentShowenActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HajjCareUIUtils.hideLoading();
                            UIController.handleActivtyResponseError(finalApplicationContextException, HajjCareApplicationClass.getInstance().getCurrentShowenActivity(), false, true);

                        }
                    });
                }
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return false;
    }


    /*
    By amra @ 26-11-2017
     */
    public static void handleOnSuccess(RawResponse response,
                                       final OnCallComplete onCallComplete, Class clazz) {
        try {
            if (ResponseValidator.validateResponse(response, clazz)) {

                onCallComplete.onSuccess(ResponseParser.parseResponse(response, clazz));
            }
        } catch (ApplicationException ae) {
            if (BuildConfig.DEBUG)
                ae.printStackTrace();
            onCallComplete.onFailure(ae);
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
            onCallComplete.onFailure(new ApplicationException(e.getMessage()));
        }
    }


}
