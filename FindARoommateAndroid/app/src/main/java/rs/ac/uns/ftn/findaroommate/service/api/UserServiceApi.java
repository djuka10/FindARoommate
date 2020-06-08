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
import rs.ac.uns.ftn.findaroommate.dto.TagToSend;
import rs.ac.uns.ftn.findaroommate.dto.UserDto;
import rs.ac.uns.ftn.findaroommate.model.Language;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.model.UserCharacteristic;

public interface UserServiceApi {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST(ServiceUtils.USER_API)
    Call<ResponseBody> add(@Body User user);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST(ServiceUtils.USER_API)
    Call<UserDto> add(@Body UserDto user);

    @Multipart
    @Headers({
            "User-Agent: Mobile-Android"
    })
    @POST(ServiceUtils.USER_API + "/uploadProfilePhoto")
    Call<ResourceRegistry> uploadPhoto(
            @Part MultipartBody.Part image,
            @Part MultipartBody.Part user,
            @Part MultipartBody.Part profilePicture);

    @GET(ServiceUtils.USER_API)
    Call<List<User>> getAll();

    @GET(ServiceUtils.USER_API + "/{id}/languages")
    Call<List<Language>> getUserLanguages(@Path("id") int id);

    @GET(ServiceUtils.USER_API + "/{id}/userCharacteristics")
    Call<List<UserCharacteristic>> getUserUserCharacteristic(@Path("id") int id);
}
