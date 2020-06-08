package rs.ac.uns.ftn.findaroommate.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.activity.HomepageActivity;
import rs.ac.uns.ftn.findaroommate.activity.RoomListActivity;
import rs.ac.uns.ftn.findaroommate.dto.AdDtoDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;

public class BookTask extends AsyncTask<Long,Void,Void> {

    private Context context;

    public static String RESULT_CODE = "RESULT_CODE";

    public BookTask(Context context)
    {
        this.context = context;
    }
    @Override
    protected Void doInBackground(Long... voids) {
        Log.i("REZ", "doInBackground");

        try {
            Ad ad = null;
            long id = -1;
            if(voids.length > 0) {
                id = voids[0];
            }

            if(id != -1) {
                ad = Ad.getOne(id);
            }

            AdDtoDto adDtoDto = new AdDtoDto();
            adDtoDto.convert(ad);
            Call<AdDtoDto> call = ServiceUtils.adServiceApi.add(adDtoDto);
            call.enqueue(new Callback<AdDtoDto>() {
                @Override
                public void onResponse(Call<AdDtoDto> call, Response<AdDtoDto> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Meesage recieved");
                        Log.i("fd", "Message received");

                        AdDtoDto body = response.body();
                        Ad ad = Ad.getOne(body.getEntityId());
                        User user = User.getOne(body.getUserId().getEntityId());
                        ad.setUserId(user);
                        ad.setAdStatus(ad.getAdStatus());
                        ad.save();

                        Intent intent = new Intent(HomepageActivity.HOME_PAGE);
                        context.startActivity(intent);

                        /*Intent intent = new Intent(HomepageActivity.HOME_PAGE);
                        context.sendBroadcast(intent);*/

                        Log.i("fd", "PROSLO SVE!");
                    } else {
                        Log.e("editProfileTask", "Error");

                        // TODO: HANDLE ERROR MECHANISM
                    }
                }

                @Override
                public void onFailure(Call<AdDtoDto> call, Throwable t) {
                    System.out.println("Error!");
                    Log.e("error", t.getMessage());
                }
            });

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //super.onPostExecute(aVoid);
        Toast.makeText(context, "Succesfully!!", Toast.LENGTH_SHORT).show();

    }
}
