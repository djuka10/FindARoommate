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
import rs.ac.uns.ftn.findaroommate.model.Review;
import rs.ac.uns.ftn.findaroommate.model.UserCharacteristic;

public interface ReviewServiceApi {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST(ServiceUtils.REVIEW_API)
    Call<Review> addReview(@Body Review review);

    @GET(ServiceUtils.REVIEW_API)
    Call<List<Review>> getAll();

    @GET(ServiceUtils.REVIEW_API + "/{userId}")
    Call<List<Review>> getUserReview(@Path("userId") int userId);
}
