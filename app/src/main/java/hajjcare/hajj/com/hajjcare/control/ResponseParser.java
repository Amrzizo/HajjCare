package hajjcare.hajj.com.hajjcare.control;


import com.google.gson.Gson;


import java.io.IOException;

import hajjcare.hajj.com.hajjcare.models.network.RawResponse;

/**
 * Created by Amr Ahmed on 04/02/2017.
 */

public class ResponseParser {

    public static Object parseResponse(RawResponse rawResponse, Class clazz) throws IOException {
        Gson gson = new Gson();
        Object responseObject = gson.fromJson(rawResponse.response, clazz);
        return responseObject;
    }

}
