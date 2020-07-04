package rs.ac.uns.ftn.findaroommate.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.activity.ProfileFormActivity;
import rs.ac.uns.ftn.findaroommate.dto.UserDto;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;
import rs.ac.uns.ftn.findaroommate.task.EditProfileTask;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class EditProfileIntentService extends IntentService {

    public EditProfileIntentService() {
        super("EditProfileIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.i("EditProfileService", "onStartCommand");
            long userId = intent.getLongExtra("userId", -1l);

            int status = AppTools.getConnectivityStatus(getApplicationContext());

            if(status == AppTools.TYPE_NOT_CONNECTED){
                Log.e("EditProfileService", "The connection is not established");
            }

            Log.i("REZ", "doInBackground");

            try {
                // User user = AppTools.getLoggedUser();
                User user = null;

                if(userId != -1){
                    user = User.getOne(userId);
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

                            Intent intent = new Intent(ProfileFormActivity.EDIT_USER_PROFILE);
                            sendBroadcast(intent);
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

        }
    }

}
