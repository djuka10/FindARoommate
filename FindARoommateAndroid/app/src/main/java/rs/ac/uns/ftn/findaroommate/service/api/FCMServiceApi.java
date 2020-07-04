package rs.ac.uns.ftn.findaroommate.service.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rs.ac.uns.ftn.findaroommate.dto.FCMRequest;
import rs.ac.uns.ftn.findaroommate.dto.FCMResponse;

public interface FCMServiceApi {

        @Headers(
                {
                        "Content-Type:application/json",
                        "Authorization:key=AAAAAzgKvgQ:APA91bHSHndiLyEp9Xfy5Xy9V5pDzZ0StMY4OdW-e2abwCZ63SFe1dY3aa0epIOSKWcSd7ai-exj72xcX9ekOGFCoXpATQz6ZL1lV7CplnvSONS7XWtleorJBIenjpUXTUkcD-5kwDEf"
                }
        )
        @POST("fcm/send")
        Call<FCMResponse> sendNotification(@Body FCMRequest body);

}
