package hajjcare.hajj.com.hajjcare.networklayer;



import java.util.Map;

import hajjcare.hajj.com.hajjcare.control.HajjCareApplicationClass;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by amra on 28/01/2018.
 */

public interface AsynchronousREPProxy {


    @GET("{path}")
    Call<ResponseBody> startGETConnectionToServer(@Path(value = "path", encoded = true) String path,
                                                  @Query("lang") String Laguage,
                                                  @Query("type") String type,
                                                  @HeaderMap Map<String, String> headers);


    @GET("{path}")
    Call<ResponseBody> startGETConnectionToServer(@Path(value = "path", encoded = true) String path,
                                                  @Query("lang") String Language,
                                                  @Query("type") String type,
                                                  @Query("Id") String page,
                                                  @HeaderMap Map<String, String> headers);
    @GET("{path}")
    Call<ResponseBody> startGETConnectionToServer(@Path(value = "path", encoded = true) String path,
                                                  @Query("lang") String Language,
                                                  @Query("type") String type,
                                                  @Query("Id") String page,
                                                  @Query("lat") String lat,
                                                  @Query("Long") String Long,
                                                  @HeaderMap Map<String, String> headers);


    @POST("{path}")
    Call<ResponseBody> startPOSTConnectionToServer(@Path(value = "path", encoded = true) String path,
                                                   @Body Object requestStr,
                                                   @HeaderMap Map<String, String> headers);
//    @Multipart
//    @Headers({"Content-Type:multipart/form-data; boundary=AaB03x"})
    @POST("{path}")
    Call<ResponseBody> startPOSTWithMultiPartConnectionToServer(@Path(value = "path", encoded = true) String path,
                                                                @Body Object requestStr,
                                                                @HeaderMap Map<String, String> headers,
                                                                @Part("file") okhttp3.RequestBody file);

//    @Part("name") okhttp3.RequestBody name,
//    @Part("email") okhttp3.RequestBody email,
//    @Part("phone") okhttp3.RequestBody phone,
//    @Part("device_id") okhttp3.RequestBody device_id,

    @PUT("{path}")
    Call<ResponseBody> startPutConnectionToServer(@Path(value = "path", encoded = true) String path,
                                                  @Body Object requestStr,
                                                  @HeaderMap Map<String, String> headers);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HajjCareApplicationClass.getInstance().getBaseURL() + AsynchronousCommunicator.SERVER_IP_EXTENTION)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
