package hajjcare.hajj.com.hajjcare.networklayer;


import hajjcare.hajj.com.hajjcare.BuildConfig;
import hajjcare.hajj.com.hajjcare.R;
//import hajjcare.hajj.com.hajjcare.control.HajjCareApplicationClass;
//import hajjcare.hajj.com.hajjcare.models.RawResponse;
//import hajjcare.hajj.com.hajjcare.models.exceptios.ApplicationException;
//import hajjcare.hajj.com.hajjcare.models.requests.UpdateProfileRequest;
//import hajjcare.hajj.com.hajjcare.utils.HajjCareUIUtils;
//import hajjcare.hajj.com.hajjcare.views.UIController;
//import hajjcare.hajj.com.hajjcare.views.activities.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import hajjcare.hajj.com.hajjcare.control.HajjCareApplicationClass;
import hajjcare.hajj.com.hajjcare.control.UIController;
import hajjcare.hajj.com.hajjcare.models.exceptios.ApplicationException;
import hajjcare.hajj.com.hajjcare.models.network.RawResponse;
import hajjcare.hajj.com.hajjcare.utils.HajjCareUIUtils;
import hajjcare.hajj.com.hajjcare.views.activity.BaseActivity;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amra on 29/01/2018.
 */

public class AsynchronousCommunicator {

    public static String EMPTY_STRING = "";
    public static String FORWARD_SLASH = "/";
    public static AsynchronousCommunicator asynchronousCommunicator;
    public AsynchronousREPProxy asynchronousREPProxy;
    private String appLanguage;
    private static String configString;
    private static JSONObject configurationJsonObject;
    public static final String SERVER_IP_EXTENTION = "/api/";
    private String accountServiceCode;

    public static AsynchronousCommunicator getInstance() {
        if (asynchronousCommunicator == null) {

            asynchronousCommunicator = new AsynchronousCommunicator();
        }

        return asynchronousCommunicator;
    }

    private AsynchronousCommunicator() {

        initHttpClient();
//        configString = Constants.RETRIABLE_PROCESS;

        if (configString != null) {
            try {
                configurationJsonObject = new JSONObject(configString);
            } catch (JSONException ex) {
                //#debug info
                if (BuildConfig.DEBUG)
                    ex.printStackTrace();
            }

        }
    }

    private void initHttpClient() {
        // http logger interceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        // set level of logging to be on body level.
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = getUnsafeOkHttpClient().addInterceptor(interceptor)
                .addInterceptor(new AsynchronousProxyInterceptor()).readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).build();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl((HajjCareApplicationClass.getInstance()
                .getBaseURL() + SERVER_IP_EXTENTION)).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(okHttpClient).build();
        asynchronousREPProxy = retrofit.create(AsynchronousREPProxy.class);
    }

    public void startGETConnectionToServer(RequestParams requestParams,
                                           Map<String, String> header,
                                           String path,
                                           String language,
                                           String type,
                                           String page,
                                           final CallListener callListener,
                                           boolean isHubRequest,
                                           final boolean showDialog,
                                           boolean withPage) {


        if (showDialog) {
            showLoadingDialog();

        }
        if (header == null) {
            header = getDefaultHeader();
        }

        if (path == null) {
            path = EMPTY_STRING;

            path = FORWARD_SLASH + requestParams.getService() + FORWARD_SLASH + requestParams.getOperationType() + FORWARD_SLASH + requestParams.getOperationId();


        }
        Call<ResponseBody> call;
        if (withPage) {
            call = asynchronousREPProxy.startGETConnectionToServer(path, language, type, page, header);
        } else {
            call = asynchronousREPProxy.startGETConnectionToServer(path, language, type, page, language,type,  header);
        }


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (showDialog)
                    hideDialog();
                try {
                    if (response != null && response.body() != null)
                        callListener.onSuccess(new RawResponse(response.body().string(), response.code()));
                    else if (showDialog) {

                        if (response.message() == null) {
                            showErrorMessage(new ApplicationException(HajjCareApplicationClass.getInstance().getCurrentShowenActivity().getString(R.string.STR_RETROFIT_NO_RESPONSE_TXT)));

                        } else {
                            showErrorMessage(new ApplicationException(response.message()));

                        }
                    } else {

                        callListener.onFailure(new ApplicationException(HajjCareApplicationClass.getInstance().getCurrentShowenActivity().getString(R.string.STR_RETROFIT_NO_RESPONSE_TXT)));
                    }
                } catch (final IOException e) {

                    if (showDialog) {
                        showErrorMessage(e);
                    }
                    callListener.onFailure(e);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, final Throwable t) {

                if (showDialog) {
                    showErrorMessage(t);
                }
                callListener.onFailure((Exception) t);
            }
        });
    }


    public void startPOSTConnectionToServer(RequestParams requestParams,
                                            Object body,
                                            Map<String, String> header,
                                            final CallListener callListener,
                                            boolean isHubRequest,
                                            final boolean showDialog) {
        if (showDialog) {
            showLoadingDialog();
        }

        if (header == null) {
            header = getDefaultHeader();
        }


        String path = EMPTY_STRING;

        try {

            Object JsonBody = null;
            path = AsynchronousCommunicator.SERVER_IP_EXTENTION + requestParams.getService();
            JsonBody = body;


            Call<ResponseBody> call = asynchronousREPProxy.startPOSTConnectionToServer(path, JsonBody, header);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (showDialog) {
                        hideDialog();
                    }

                    try {
                        if (response != null && response.body() != null)
                            callListener.onSuccess(new RawResponse(response.body().string(), response.code()));
                        else if (showDialog) {

                            showErrorMessage(new ApplicationException(response.message()));
                        } else {

                            callListener.onFailure(new ApplicationException(HajjCareApplicationClass.getInstance().getCurrentShowenActivity().getString(R.string.STR_RETROFIT_NO_RESPONSE_TXT)));
                        }
                    } catch (final IOException e) {
                        if (showDialog) {

                            showErrorMessage(e);

                        }
                        callListener.onFailure(e);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, final Throwable t) {

                    if (showDialog) {

                        showErrorMessage(t);
                    }
//                    callListener.onFailure((Exception) t);
                }
            });

        } catch (ApplicationException ex) {

            if (showDialog)
                showErrorMessage(ex);
            else
                callListener.onFailure(ex);
        }
    }

    public void startPOSTWithMultiPartConnectionToServer(RequestParams requestParams,
                                                         Object body,
                                                         Map<String, String> header,
                                                         final CallListener callListener,
                                                         boolean isHubRequest,
                                                         final boolean showDialog,
                                                         okhttp3.RequestBody file) {
        if (showDialog) {
            showLoadingDialog();
        }

        if (header == null) {
            header = getMultiPartHeader();
        }


        String path = EMPTY_STRING;

        try {

            path = AsynchronousCommunicator.SERVER_IP_EXTENTION + requestParams.getService();


//            okhttp3.RequestBody nameBody = okhttp3.RequestBody.create(okhttp3.MultipartBody.FORM, ((UpdateProfileRequest) body).getName());
//            okhttp3.RequestBody emailBody = okhttp3.RequestBody.create(okhttp3.MultipartBody.FORM, ((UpdateProfileRequest) body).getEmail());
//            okhttp3.RequestBody phoneBody = okhttp3.RequestBody.create(okhttp3.MultipartBody.FORM, ((UpdateProfileRequest) body).getPhone());
//            okhttp3.RequestBody deviceIdBody = okhttp3.RequestBody.create(okhttp3.MultipartBody.FORM, ((UpdateProfileRequest) body).getDevice_id());
            Object JsonBody = null;
            path = AsynchronousCommunicator.SERVER_IP_EXTENTION + requestParams.getService();
            JsonBody = body;

            Call<ResponseBody> call = asynchronousREPProxy.startPOSTWithMultiPartConnectionToServer(path, JsonBody, header, file);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (showDialog) {
                        hideDialog();
                    }

                    try {
                        if (response != null && response.body() != null)
                            callListener.onSuccess(new RawResponse(response.body().string(), response.code()));
                        else if (showDialog) {

                            showErrorMessage(new ApplicationException(response.message()));
                        } else {

                            callListener.onFailure(new ApplicationException(HajjCareApplicationClass.getInstance().getCurrentShowenActivity().getString(R.string.STR_RETROFIT_NO_RESPONSE_TXT)));
                        }
                    } catch (final IOException e) {
                        if (showDialog) {

                            showErrorMessage(e);

                        }
                        callListener.onFailure(e);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, final Throwable t) {

                    if (showDialog) {

                        showErrorMessage(t);
                    }
//                    callListener.onFailure((Exception) t);
                }
            });

        } catch (ApplicationException ex) {

            if (showDialog)
                showErrorMessage(ex);
            else
                callListener.onFailure(ex);
        }
    }


    private void showLoadingDialog() {
        HajjCareApplicationClass.getInstance().getCurrentShowenActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HajjCareUIUtils.showLoading("", HajjCareApplicationClass.getInstance().getCurrentShowenActivity().getString(R.string.alert_loading_contents), false);

            }
        });
    }

    public void StartPutConnectionToServer(RequestParams requestParams,
                                           Object body,
                                           Map<String, String> header,
                                           final CallListener callListener,
                                           boolean isHubRequest,
                                           final boolean showDialog) {

        if (showDialog) {
            showLoadingDialog();
        }

        if (header == null) {
            header = getDefaultHeader();
        }

        String path = EMPTY_STRING;
        Object jsonBody = null;
        if (isHubRequest) {
            // set path for request
            path = FORWARD_SLASH + requestParams.getService() + FORWARD_SLASH + requestParams.getOperationId() + FORWARD_SLASH + requestParams.getOperationType();

            jsonBody = body;
        }


        Call<ResponseBody> call = asynchronousREPProxy.startPutConnectionToServer(path, jsonBody, header);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (showDialog)
                    hideDialog();

                try {
                    if (response != null && response.body() != null)
                        callListener.onSuccess(new RawResponse(response.body().string(), response.code()));

                    else if (showDialog) {

                        showErrorMessage(new ApplicationException(HajjCareApplicationClass.getInstance().getCurrentShowenActivity().getString(R.string.STR_RETROFIT_NO_RESPONSE_TXT)));
                    } else {

                        callListener.onFailure(new ApplicationException(HajjCareApplicationClass.getInstance().getCurrentShowenActivity().getString(R.string.STR_RETROFIT_NO_RESPONSE_TXT)));
                    }

                } catch (final IOException e) {

                    if (showDialog) {
                        showErrorMessage(e);
                    } else {
                        callListener.onFailure(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, final Throwable t) {

                if (showDialog) {
                    showErrorMessage((ApplicationException) t);

                } else {
                    callListener.onFailure((Exception) t);
                }
            }
        });
    }


    private void showErrorMessage(final Throwable t) {
        ((BaseActivity) HajjCareApplicationClass.getInstance().getCurrentShowenActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HajjCareUIUtils.hideLoading();
                UIController.handleActivtyResponseError(t, HajjCareApplicationClass.getInstance().getCurrentShowenActivity(), false, true);

            }
        });
    }

    private void hideDialog() {
        ((BaseActivity) HajjCareApplicationClass.getInstance().getCurrentShowenActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HajjCareUIUtils.hideLoading();
            }
        });
    }


    private OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

//            OkHttpClient okHttpClient = builder.build();
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getDefaultHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        return header;
    }

    private Map<String, String> getMultiPartHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "text/plain");
        return header;
    }


}
