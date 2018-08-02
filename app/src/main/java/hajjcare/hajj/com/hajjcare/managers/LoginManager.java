package hajjcare.hajj.com.hajjcare.managers;



import java.util.HashMap;
import java.util.Map;

import hajjcare.hajj.com.hajjcare.control.HajjCareApplicationClass;
import hajjcare.hajj.com.hajjcare.control.ResponseValidator;
import hajjcare.hajj.com.hajjcare.models.LoginRequest;
import hajjcare.hajj.com.hajjcare.models.LoginResponse;
import hajjcare.hajj.com.hajjcare.models.network.RawResponse;
import hajjcare.hajj.com.hajjcare.networklayer.AsynchronousCommunicator;
import hajjcare.hajj.com.hajjcare.networklayer.CallListener;
import hajjcare.hajj.com.hajjcare.networklayer.OnCallComplete;
import hajjcare.hajj.com.hajjcare.networklayer.RequestParams;

/**
 * Created by amra on 2/3/2018.
 */

public class LoginManager {

    private static LoginManager instance;

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }

    public void doLogin(final OnCallComplete onCallComplete, LoginRequest loginRequest) {

        RequestParams loginParams = new RequestParams();
        loginParams.setService(RequestParams.VALUE_SERVICE_LOGIN);
        String path = HajjCareApplicationClass.getInstance().getBaseURL()+AsynchronousCommunicator.SERVER_IP_EXTENTION+RequestParams.VALUE_SERVICE_LOGIN;
        Map<String, String> header = new HashMap<>();
        header.put("Accept", "application/json");
        AsynchronousCommunicator.getInstance().startPOSTConnectionToServer(loginParams, loginRequest, header , new CallListener() {
            @Override
            public void onSuccess(RawResponse rawResponse) {

                ResponseValidator.handleOnSuccess(rawResponse, onCallComplete, LoginResponse.class);
            }

            @Override
            public void onFailure(Exception e) {

                onCallComplete.onFailure(e);
            }
        }, false, true);
    }

//    public void signUp(final OnCallComplete onCallComplete, RegisterRequest registerRequest) {
//
//        RequestParams signUpParams = new RequestParams();
//        signUpParams.setService(RequestParams.VALUE_SERVICE_SIGN_UP);
//        AsynchronousCommunicator.getInstance().startPOSTConnectionToServer(signUpParams, registerRequest, null, new CallListener() {
//            @Override
//            public void onSuccess(RawResponse rawResponse) {
//
//                ResponseValidator.handleOnSuccess(rawResponse, onCallComplete, RegisterResponse.class);
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//                onCallComplete.onFailure(e);
//            }
//        }, false, true);
//    }
//
//    public void getProfileData(final OnCallComplete onCallComplete) {
//
//        RequestParams profileParams = new RequestParams();
//        profileParams.setService(RequestParams.VALUE_SERVICE_PROFILE);
//
//        ProfileRequest profileRequest = new ProfileRequest(StarSmilesApplicationClass.getToken());
//
//        Map<String, String> header = new HashMap<>();
//        header.put("Accept", "application/json");
//        header.put("Authorization", "Bearer " + StarSmilesApplicationClass.getToken());
//        AsynchronousCommunicator.getInstance().startPOSTConnectionToServer(profileParams, profileRequest, header, new CallListener() {
//            @Override
//            public void onSuccess(RawResponse rawResponse) {
//
//                ResponseValidator.handleOnSuccess(rawResponse, onCallComplete, ProfileResponse.class);
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//                onCallComplete.onFailure(e);
//            }
//        }, false, true);
//    }
//
//    public void getPosts(final OnCallComplete onCallComplete, String language, int page) {
//
//        String path = AsynchronousCommunicator.SERVER_IP_EXTENTION+RequestParams.VALUE_SERVICE_POSTS ;
//
//
//        Map<String, String> header = new HashMap<>();
//        header.put("Accept", "application/json");
//        header.put("Authorization", "Bearer " + StarSmilesApplicationClass.getToken());
//        AsynchronousCommunicator.getInstance().startGETConnectionToServer(null, header, path,language,RequestParams.VALUE_TYPE_SLIDES,page, new CallListener() {
//            @Override
//            public void onSuccess(RawResponse rawResponse) {
//
//                ResponseValidator.handleOnSuccess(rawResponse, onCallComplete, PostsResponse.class);
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//                onCallComplete.onFailure(e);
//            }
//        }, false, true,true);
//    }
}
