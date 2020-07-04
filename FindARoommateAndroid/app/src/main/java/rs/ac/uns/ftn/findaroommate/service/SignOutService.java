package rs.ac.uns.ftn.findaroommate.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.dto.UserDto;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SignOutService extends IntentService {


    public SignOutService() {
        super("SignOutService");
    }





    @Override
    protected void onHandleIntent(Intent intent) {
        removeUserDevice();
        removePrefs();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        AppTools.setLoggedUser(null);
        AppTools.setFirebaseUser(null);

    }

    private void removeUserDevice(){
        User loggedUser = AppTools.getLoggedUser();
        UserDto userDto = UserDto.builder().entityId(loggedUser.getEntityId()).deviceId(AppTools.getDeviceId()).build();
        Call<ResponseBody> call = ServiceUtils.userServiceApi.signOut(userDto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println("Meesage recieved");
                    Log.i("fd", "Message received");

                } else {
                    Log.e("editProfileTask", "Error");
                    Intent intent = new Intent(MainActivity.SERVER_ERROR);
                    intent.putExtra("error_message",
                            "Server error while user signout.");
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("editProfileTask", "Error");
                Intent intent = new Intent(MainActivity.SERVER_ERROR);
                intent.putExtra("error_message",
                        "Server error while user signout.");
                sendBroadcast(intent);
            }
        });
    }

    private void removePrefs(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().clear().apply();
    }

}
