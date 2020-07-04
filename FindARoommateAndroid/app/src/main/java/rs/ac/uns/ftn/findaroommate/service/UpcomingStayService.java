package rs.ac.uns.ftn.findaroommate.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.dto.UserDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;


//public class UpcomingStayService extends Service {
//
//    public static String RESULT_CODE = "RESULT_CODE";
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.i("REZ", "onStartCommand");
//
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        if(prefs.getBoolean("should_remind", false)){
//            String reminder_days_str = prefs.getString("reminder_day", null);
//            if(reminder_days_str != null){
//                int reminder_days = Integer.parseInt(reminder_days_str);
//                int status = AppTools.getConnectivityStatus(getApplicationContext());
//
//                //ima konekcije ka netu skini sta je potrebno i sinhronizuj bazu
//                if(status != AppTools.TYPE_NOT_CONNECTED){
//                    new UpcomingStayTask(getApplicationContext()).execute(reminder_days);
//                }
//            }
//        }
//
//        stopSelf();
//
//        return START_NOT_STICKY;
//    }
//
//    @Override
//    public IBinder onBind(Intent arg0) {
//        return null;
//    }
//
//}

public class UpcomingStayService extends IntentService {

    public UpcomingStayService() {
        super("UpcomingStayService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("should_remind", false)){
            String reminder_days_str = prefs.getString("reminder_day", null);
            if(reminder_days_str != null){
                int daysBefore = Integer.parseInt(reminder_days_str);
                int status = AppTools.getConnectivityStatus(getApplicationContext());

                //ima konekcije ka netu skini sta je potrebno i sinhronizuj bazu
                if(status != AppTools.TYPE_NOT_CONNECTED){
                    User loggedUser = AppTools.getLoggedUser();
                    UserDto loggedUserDto = UserDto.builder().entityId(loggedUser.getEntityId()).email(loggedUser.getEmail()).build();

                    Call<Integer> c = ServiceUtils.userServiceApi.getUpcomingStays(loggedUserDto, daysBefore);
                    c.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.isSuccessful()) {
                                Integer body = response.body();
                                if(body > 0){
                                    Intent ints = new Intent(MainActivity.UPCOMING_STAY);
                                    ints.putExtra("reminder_days", reminder_days_str);
                                    sendBroadcast(ints);
                                }

                            } else {
                                serverErrorHandling("upcoming stays");
                            }

                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            System.out.println("Error!");
                            Log.e("error", t.getMessage());

                            serverErrorHandling("upcoming stays");
                        }
                    });
                }
            }
        }

//        Intent ints = new Intent(MainActivity.UPCOMING_STAY);
//        getApplicationContext().sendBroadcast(ints);
    }

    private void serverErrorHandling(String action){
        String serverErrorPattenr = getApplicationContext().getString(R.string.server_error_msg);

        Intent intent = new Intent(MainActivity.SERVER_ERROR);
        intent.putExtra("error_message",
                serverErrorPattenr.replace("%ACTION%", action));
        sendBroadcast(intent);
    }


}
