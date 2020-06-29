package rs.ac.uns.ftn.findaroommate.service.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rs.ac.uns.ftn.findaroommate.model.Ad;

public interface AdItemsApi {

    @GET(ServiceUtils.AD_API)
    Call<List<Ad>> getAll();
}
