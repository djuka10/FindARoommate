package rs.ac.uns.ftn.findaroommate.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.activity.ProfileFormActivity;
import rs.ac.uns.ftn.findaroommate.dto.UserDto;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class EditProfileTask extends AsyncTask<Long, Void, Void> {

    private Context context;

    public static String RESULT_CODE = "RESULT_CODE";

    public EditProfileTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Long... voids) {
        Log.i("REZ", "doInBackground");

        try {
            // User user = AppTools.getLoggedUser();
            User user = null;
            long id = -1;
            if(voids.length > 0){
                 id = voids[0];

            }
            if(id != -1){
                user = User.getOne(id);
            }

            UserDto userDto = new UserDto();
            userDto.convert(user);
            Call<UserDto> call = ServiceUtils.userServiceApi.add(userDto);
            call.enqueue(new Callback<UserDto>() {
                @Override
                public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Meesage recieved");
                        Log.i("fd", "Message received");

                        UserDto body = response.body();
                        User user = User.getOne(body.getEntityId());
                        user.setEntityId(body.getEntityId());
                        user.save();

//                        Intent intent = new Intent(ProfileFormActivity.EDIT_USER_PROFILE);
//                        context.sendBroadcast(intent);
                    } else {
                        Log.e("editProfileTask", "Error");

                        // TODO: HANDLE ERROR MECHANISM
                    }
                }

                @Override
                public void onFailure(Call<UserDto> call, Throwable t) {
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
