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
import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.dto.AdDtoDto;
import rs.ac.uns.ftn.findaroommate.dto.AdFormDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;


public interface AdServiceApi {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST(ServiceUtils.AD_API)
    Call<ResponseBody> add(@Body Ad ad);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @POST(ServiceUtils.AD_API + "/book")
    Call<AdDtoDto> book(@Body AdDtoDto ad);

    @GET(ServiceUtils.AD_API)
    Call<List<Ad>> getAll();

    @POST(ServiceUtils.AD_API)
    Call<AdFormDto> add(@Body AdFormDto ad);

    @Multipart
    @Headers({
            "User-Agent: Mobile-Android"
    })
    @POST(ServiceUtils.AD_API + "/uploadAdPhoto")
    Call<ResourceRegistry> uploadPhoto(
            @Part MultipartBody.Part image,
            @Part MultipartBody.Part adId,
            @Part MultipartBody.Part user,
            @Part MultipartBody.Part profilePicture);

    @GET("resourceRegistry/ad/{adId}")
    Call<List<ResourceRegistry>> getAdImages(@Path("adId") String adId);
}
