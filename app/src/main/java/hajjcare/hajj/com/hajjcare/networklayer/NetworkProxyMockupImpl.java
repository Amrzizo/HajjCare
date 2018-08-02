package hajjcare.hajj.com.hajjcare.networklayer;

import java.io.IOException;
import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Amr Ahmed on 31/01/2018.
 */

public class NetworkProxyMockupImpl implements AsynchronousREPProxy {

    public Response getResponse(Request request) {
        return null;
    }

    private String bodyToString(final RequestBody requestBody) {
        try {
            final RequestBody copy = requestBody;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }


    @Override
    public Call<ResponseBody> startGETConnectionToServer(@Path(value = "path", encoded = true) String path,
                                                         @Query("lang") String Laguage,
                                                         @Query("type") String type,
                                                         @HeaderMap Map<String, String> headers) {
        return null;
    }

    @Override
    public Call<ResponseBody> startGETConnectionToServer(String path, String Language,
                                                         String type,
                                                         String Id,
                                                         Map<String, String> headers) {
        return null;
    }

    @GET("{path}")
    public Call<ResponseBody> startGETConnectionToServer(@Path(value = "path", encoded = true) String path,
                                                  @Query("lang") String Language,
                                                  @Query("type") String type,
                                                  @Query("Id") String page,
                                                  @Query("lat") String lat,
                                                  @Query("Long") String Long,
                                                  @HeaderMap Map<String, String> headers){
        return null;
    }

    @Multipart
    @POST("{path}")
    public Call<ResponseBody> startPOSTWithMultiPartConnectionToServer(@Path(value = "path", encoded = true) String path,
                                                                @Body Object requestStr,
                                                                @HeaderMap Map<String, String> headers,
                                                                @Part ("file") okhttp3.RequestBody file){
        return null;
    }

    @Override
    public Call<ResponseBody> startPOSTConnectionToServer(@Path(value = "path", encoded = true) String path,
                                                          @Body Object requestStr,
                                                          @HeaderMap Map<String, String> header) {
        return null;
    }



    @Override
    public Call<ResponseBody> startPutConnectionToServer(@Path(value = "path", encoded = true) String path,
                                                         @Body Object requestStr,
                                                         @HeaderMap Map<String, String> header) {
        return null;
    }
}
