package rs.ac.uns.ftn.findaroommate.service.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.dto.AdDtoDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;


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

    @POST(ServiceUtils.AD_API)
    Call<AdDtoDto> add(@Body AdDtoDto ad);

    @GET(ServiceUtils.AD_API)
    Call<List<Ad>> getAll();
}
