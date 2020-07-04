package rs.ac.uns.ftn.findaroommate.task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.preference.PreferenceManager;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.RoomListActivity;
import rs.ac.uns.ftn.findaroommate.dto.UserSettings;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.Review;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.SyncService;
import rs.ac.uns.ftn.findaroommate.service.UpcomingStayService;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;
import rs.ac.uns.ftn.findaroommate.utils.AdStatus;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;


/**
 * Created by milossimic on 4/6/16.
 */
public class SyncTask extends AsyncTask<Void, Void, Void> {

    private Context context;

    public static String RESULT_CODE = "RESULT_CODE";

    public SyncTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        //postaviti parametre, pre pokretanja zadatka ako je potrebno
    }

    @Override
    protected Void doInBackground(Void... params) {
        //simulacija posla koji se obavlja u pozadini i traje duze vreme
        Log.i("REZ", "doInBackground");

        try {

            Call<List<User>> c = ServiceUtils.userServiceApi.getAll();
            c.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Meesage recieved");
                        Log.i("fd", "Message received");
                        List<User> users = response.body();
                        for (User user: users) {
                            if(User.getOneByEmailSingle(user.getEmail()) == null){
                                user.save();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    System.out.println("Error!");
                    Log.e("error", t.getMessage());
                }
            });

            Call<List<Ad>> a = ServiceUtils.adServiceApi.getAll();
            a.enqueue(new Callback<List<Ad>>() {
                @Override
                public void onResponse(Call<List<Ad>> call, Response<List<Ad>> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Meesage recieved");
                        Log.i("fd", "Message received");

                        Ad.deleteAll();

                        RoomListActivity.adsList = response.body();

                        for (Ad ad: RoomListActivity.adsList) {
                            ad.save();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Ad>> call, Throwable t) {
                    System.out.println("Error!");
                    Log.e("error", t.getMessage());
                }
            });



            User loggedUser = AppTools.getLoggedUser();
            if(loggedUser != null){
                Call<List<Review>> reviewsCall = ServiceUtils.reviewServiceApi.getAll();
                reviewsCall.enqueue(new Callback<List<Review>>() {
                    @Override
                    public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                        if(response.isSuccessful()){
                            System.out.println("Meesage recieved");
                            List<Review> reviews = response.body();

                            List<Review> deleted = Review.deleteAll();
                            for(Review review : reviews){
                                review.save();
                            }

                            Log.i("fd", "Message received");
                        } else {
                            serverErrorHandling("Reviews");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Review>> call, Throwable t) {
                        serverErrorHandling("Reviews");
                    }
                });
                Call<UserSettings> settingsCall = ServiceUtils.userServiceApi.getUserSettings(loggedUser.getEntityId());
                settingsCall.enqueue(new Callback<UserSettings>() {
                    @Override
                    public void onResponse(Call<UserSettings> call, Response<UserSettings> response) {
                        if(response.isSuccessful()){
                            UserSettings settings = response.body();
                            if(settings.getEntityId() != null){
                                updateSettings(settings);
                            }
                        } else {
                            serverErrorHandling("gettings user settings");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserSettings> call, Throwable t) {
                        serverErrorHandling("gettings user settings");
                    }
                });
            } {
                Log.i("login", "user Should be logged");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void result) {
        Log.i("REZ", "onPostExecute");

    }

    private void updateSettings(UserSettings settings){
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(context);
        s.edit()
                //app:EntryValues
                .putString(context.getString(R.string.prefs_lang), settings.getLanguage())
                .putString(context.getString(R.string.prefs_unit), settings.getDistance())
                .putString(context.getString(R.string.prefs_remind_day), settings.getRemindDay())

                .putBoolean(context.getString(R.string.prefs_should_remind), settings.getShouldRemind())


                .putBoolean(context.getString(R.string.prefs_stay_notif), settings.getStayNotif())
                .putBoolean(context.getString(R.string.prefs_new_message_notif), settings.getNewMessageNotif())
                .putBoolean(context.getString(R.string.prefs_new_review_notif), settings.getNewReviewNotif())

                .putBoolean(context.getString(R.string.prefs_should_request_mail), settings.getShouldRequestMail())
                .putBoolean(context.getString(R.string.prefs_should_new_ad_mail), settings.getShouldNewAdMail())
                .putBoolean(context.getString(R.string.prefs_should_confirm_mail), settings.getShouldConfirmMail())
                .apply();
    }

    private void serverErrorHandling(String action){
        String serverErrorPattenr = context.getString(R.string.server_error_msg);

        String c = Locale.getDefault().getCountry();

        Intent intent = new Intent(MainActivity.SERVER_ERROR);
        intent.putExtra("error_message",
                serverErrorPattenr.replace("%ACTION%", action));
        context.sendBroadcast(intent);
    }
}
