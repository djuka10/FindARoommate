package rs.ac.uns.ftn.findaroommate.task;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.activity.ProfileFormActivity;
import rs.ac.uns.ftn.findaroommate.dto.FirebaseUserDto;
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
            long userId = -1;
            if(voids.length > 0){
                userId = voids[0];

            }
            if(userId != -1){
                user = User.getOne(userId);
            }

            UserDto userDto = new UserDto();
            userDto.convert(user);
            String deviceId = AppTools.getDeviceId();
            userDto.setDeviceId(deviceId);
            Call<UserDto> call = ServiceUtils.userServiceApi.add(userDto);
            call.enqueue(new Callback<UserDto>() {
                @Override
                public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Meesage recieved");
                        Log.i("fd", "Message received");

                        UserDto body = response.body();

                        List<User> users = User.getOneByEmail(body.getEmail());
                        if(!users.isEmpty()){
                            User user = users.get(0);
                            user.setEntityId(body.getEntityId());
                            user.save();
                            updateUser(body.getEntityId());
                        }

                    } else {
                        Log.e("editProfileTask", "Error");
                        Intent intent = new Intent(MainActivity.SERVER_ERROR);
                        intent.putExtra("error_message",
                                "Server error while editing user info. Please try again.");
                        context.sendBroadcast(intent);
                    }
                }

                @Override
                public void onFailure(Call<UserDto> call, Throwable t) {
                    System.out.println("Error!");
                    Log.e("error", t.getMessage());

                    Intent intent = new Intent(MainActivity.SERVER_ERROR);
                    intent.putExtra("error_message",
                            "Server error while editing user info. Please try again.");
                    context.sendBroadcast(intent);
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

    }

    private void updateUser(int loggedUserId){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUserDto user = new FirebaseUserDto(loggedUserId, AppTools.getDeviceId());

        ref.child(String.valueOf(loggedUserId)).setValue(user);
    }
}
