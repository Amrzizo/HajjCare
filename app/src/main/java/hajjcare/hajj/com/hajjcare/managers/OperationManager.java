package hajjcare.hajj.com.hajjcare.managers;

import java.util.HashMap;
import java.util.Map;

import hajjcare.hajj.com.hajjcare.control.HajjCareApplicationClass;
import hajjcare.hajj.com.hajjcare.control.ResponseValidator;
import hajjcare.hajj.com.hajjcare.models.HelpMeResponse;
import hajjcare.hajj.com.hajjcare.models.LoginRequest;
import hajjcare.hajj.com.hajjcare.models.LoginResponse;
import hajjcare.hajj.com.hajjcare.models.network.HelpMeRequest;
import hajjcare.hajj.com.hajjcare.models.network.RawResponse;
import hajjcare.hajj.com.hajjcare.networklayer.AsynchronousCommunicator;
import hajjcare.hajj.com.hajjcare.networklayer.CallListener;
import hajjcare.hajj.com.hajjcare.networklayer.OnCallComplete;
import hajjcare.hajj.com.hajjcare.networklayer.RequestParams;

/**
 * Created by amra on 8/2/2018.
 */

public class OperationManager {
    private static OperationManager instance;

    public static OperationManager getInstance() {
        if (instance == null) {
            instance = new OperationManager();
        }
        return instance;
    }


    public void helpMe(final OnCallComplete onCallComplete, HelpMeRequest helpMeRequest) {

        RequestParams loginParams = new RequestParams();
        loginParams.setService(RequestParams.VALUE_SERVICE_HELP_ME);
        String path = HajjCareApplicationClass.getInstance().getBaseURL()+ AsynchronousCommunicator.SERVER_IP_EXTENTION+RequestParams.VALUE_SERVICE_HELP_ME;
        Map<String, String> header = new HashMap<>();
        header.put("Accept", "application/json");
        AsynchronousCommunicator.getInstance().startPOSTConnectionToServer(loginParams, helpMeRequest, header , new CallListener() {
            @Override
            public void onSuccess(RawResponse rawResponse) {

                ResponseValidator.handleOnSuccess(rawResponse, onCallComplete, HelpMeResponse.class);
            }

            @Override
            public void onFailure(Exception e) {

                onCallComplete.onFailure(e);
            }
        }, false, true);
    }

}
