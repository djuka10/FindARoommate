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
import rs.ac.uns.ftn.findaroommate.dto.AdFormDto;
import rs.ac.uns.ftn.findaroommate.dto.TagToSend;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.model.UserCharacteristic;

public interface ReviewerServiceApi {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST(ServiceUtils.ADD)
    Call<ResponseBody> add(@Body TagToSend tag);

    @GET(ServiceUtils.TEST)
    Call<ResponseBody> test();

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST(ServiceUtils.USER_CHARACTERISTIC_API)
    Call<ResponseBody> addUserChar(@Body UserCharacteristic tag);

    @GET(ServiceUtils.USER_CHARACTERISTIC_API)
    Call<ResponseBody> userCharacterisic();

    @GET(ServiceUtils.USER_CHARACTERISTIC_API)
    Call<List<UserCharacteristic>> userCharacterisics();

    @POST("ad")
    Call<AdFormDto> add(@Body AdFormDto ad);

    @Multipart
    @Headers({
            "User-Agent: Mobile-Android"
    })
    @POST("ad" + "/uploadAdPhoto")
    Call<ResourceRegistry> uploadPhoto(
            @Part MultipartBody.Part image,
            @Part MultipartBody.Part adId,
            @Part MultipartBody.Part user,
            @Part MultipartBody.Part profilePicture);

    @GET("resourceRegistry/ad/{adId}")
    Call<List<ResourceRegistry>> getAdImages(@Path("adId") String adId);

    @GET("test/{id}")
    Call<ResponseBody> testById(@Path("id") String id);
}
