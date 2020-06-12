package rs.ac.uns.ftn.findaroommate.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.dto.AdFormDto;
import rs.ac.uns.ftn.findaroommate.dto.UserDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;

public class EditAdTask extends AsyncTask<Long, Void, Void> {

    private Context context;

    public static String RESULT_CODE = "RESULT_CODE";

    public EditAdTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Long... voids) {
        Log.i("REZ", "doInBackground");

        try {
            // User user = AppTools.getLoggedUser();
            Ad ad = null;
            long adId = -1;
            if(voids.length > 0){
                adId = voids[0];

            }
//            if(adId != -1){
//                user = User.getOne(adId);
//            }

            AdFormDto adFormDto = new AdFormDto();

            Call<AdFormDto> call = ServiceUtils.adServiceApi.add(adFormDto);
            call.enqueue(new Callback<AdFormDto>() {
                @Override
                public void onResponse(Call<AdFormDto> call, Response<AdFormDto> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Meesage recieved");
                        Log.i("fd", "Message received");

                        AdFormDto body = response.body();

//                        List<User> users = User.getOneByEmail(body.getEmail());
//                        if(!users.isEmpty()){
//                            User user = users.get(0);
//                            user.setEntityId(body.getEntityId());
//                            user.save();
//                        }

                    } else {
                        Log.e("editProfileTask", "Error");

                        // TODO: HANDLE ERROR MECHANISM
                    }
                }

                @Override
                public void onFailure(Call<AdFormDto> call, Throwable t) {
                    System.out.println("Error!");
                    Log.e("error", t.getMessage());
                }
            });
        } catch (Exception e) {
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

    }
}
