package rs.ac.uns.ftn.findaroommate.service.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rs.ac.uns.ftn.findaroommate.dto.TagToSend;
import rs.ac.uns.ftn.findaroommate.dto.UserDto;
import rs.ac.uns.ftn.findaroommate.model.User;

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

    @GET(ServiceUtils.USER_API)
    Call<List<User>> getAll();
}
