package hajjcare.hajj.com.hajjcare.networklayer;


import java.io.IOException;

import hajjcare.hajj.com.hajjcare.control.HajjCareApplicationClass;
import hajjcare.hajj.com.hajjcare.utils.HajjCareUtils;
import okhttp3.*;
import okio.Buffer;

/**
 * Created by Amr Ahmed on 01/08/2018.
 */

public class AsynchronousProxyInterceptor implements Interceptor {


    /*
    Updated by amra @ 29-01-2018
    * to provide mocking data for each request  by request operation id
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;

        if (HajjCareApplicationClass.getInstance().isEnabledMockingData()) {

            NetworkProxyMockupImpl networkProxyMockupImpl = new NetworkProxyMockupImpl();

            response = networkProxyMockupImpl.getResponse(chain.request());

            okhttp3.RequestBody requestBody = chain.request().body();
            chain.request().url().toString().replaceAll("%3F", "?");
            String path = chain.request().url().toString();
            String fileName = path.substring(path.lastIndexOf('/') + 1) + ".txt";


            String responseBodyString = HajjCareUtils.ReadStringFromfile(fileName);
            response = new Response.Builder()
                    .code(200)
                    .message("back with mock data")
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), responseBodyString.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();
        } else {

//            chain.request().url().toString().replaceAll("%3F", "?");
                response = chain.proceed(chain.request());
            }

        return response;
    }

    private String bodyToString(final okhttp3.RequestBody requestBody) {
        try {
            final okhttp3.RequestBody copy = requestBody;
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
}
