package rs.ac.uns.ftn.findaroommate.service.api;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rs.ac.uns.ftn.findaroommate.dto.EmailDto;
import rs.ac.uns.ftn.findaroommate.dto.PushNotificationRequest;
import rs.ac.uns.ftn.findaroommate.dto.UserDto;
import rs.ac.uns.ftn.findaroommate.model.Language;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.model.UserCharacteristic;

public interface NotificationServiceApi {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST(ServiceUtils.NOTIFICATION_API + "/review")
    Call<ResponseBody> pushRemoteReviewNotification(@Body PushNotificationRequest pushNotificationRequest);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST(ServiceUtils.NOTIFICATION_API + "/booking")
    Call<ResponseBody> pushRemoteBookingNotification(@Body PushNotificationRequest pushNotificationRequest);
}
