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

    @GET(ServiceUtils.TEST)
    Call<ResponseBody> test();

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST(ServiceUtils.USER_CHARACTERISTIC_API)
    Call<ResponseBody> addUserChar(@Body UserCharacteristic tag);

    @GET("test/{id}")
    Call<ResponseBody> testById(@Path("id") String id);
}
