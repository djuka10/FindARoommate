package rs.ac.uns.ftn.findaroommate.task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.HomepageActivity;
import rs.ac.uns.ftn.findaroommate.activity.RoomListActivity;
import rs.ac.uns.ftn.findaroommate.dto.AdDtoDto;
import rs.ac.uns.ftn.findaroommate.dto.EmailDto;
import rs.ac.uns.ftn.findaroommate.dto.PushNotificationRequest;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

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
            Call<AdDtoDto> call = ServiceUtils.adServiceApi.book(adDtoDto);
            call.enqueue(new Callback<AdDtoDto>() {
                @Override
                public void onResponse(Call<AdDtoDto> call, Response<AdDtoDto> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Meesage recieved");
                        Log.i("fd", "Message received");

                        AdDtoDto body = response.body();
                        Ad ad = Ad.getOneGlobal(body.getEntityId());
                        User user = AppTools.getLoggedUser();

                        // interested user edited ad
                        if(ad.getOwnerId() != user.getEntityId()){
                            ad.setUserId(user.getEntityId());
                        }
                        ad.setAdStatus(ad.getAdStatus());
                        ad.save();

                        sendNotification(ad, user);

                        Log.i("fd", "PROSLO SVE!");
                    } else {
                        Log.e("editBookTask", "Error");

                        serverErrorHandling("confirming booking");
                    }
                }

                @Override
                public void onFailure(Call<AdDtoDto> call, Throwable t) {
                    System.out.println("Error!");
                    Log.e("error", t.getMessage());

                    serverErrorHandling("confirming booking");
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

    private void sendNotification(Ad ad, User user){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String adOwnerShip = "";
        if(ad.getOwnerId() == user.getEntityId()){
            adOwnerShip = "ad";
        } else {
            adOwnerShip = "booking";
        }

        if(preferences.getBoolean("stay_notif", false)){
            Intent intent = new Intent(MainActivity.BOOKING);
            intent.putExtra("adOwner",
                    adOwnerShip);
            intent.putExtra("adStatus",
                    ad.getAdStatus().toString());
            intent.putExtra("adTitle",
                    ad.getTitle());
            context.sendBroadcast(intent);
        }

        sendNotificationMail(ad, user);
        sendNotificationRemote(ad, user, adOwnerShip);

    }

    private void sendNotificationRemote(Ad ad, User user, String adOwnerShip){
        String adOwnerShipRemote = "";
        Integer userId = null;
        if(adOwnerShip.equals("ad")){
            adOwnerShipRemote = "booking";
            if(ad.getUserId() != 0){
                userId = ad.getUserId();
            }
        } else {
            adOwnerShipRemote = "ad";
            userId = ad.getOwnerId();
        }

        String notif_topic = context.getString(R.string.notif_booking_topic);
        String notif_title = context.getString(R.string.notif_booking_title);
        String notif_text = context.getString(R.string.notif_booking_text)
                .replace("%AD%", adOwnerShipRemote)
                .replace("%STATUS%", ad.getAdStatus().toString())
                .replace("%TITLE%", ad.getTitle())
                ;

        PushNotificationRequest pushNotificationRequest = PushNotificationRequest.builder()
                .topic(notif_topic)
                .title(notif_title)
                .message(notif_text)
                .userId(userId)
                .adId(ad.getEntityId())
                .build();

        Call<ResponseBody> call = ServiceUtils.notificationServiceApi.pushRemoteBookingNotification(pushNotificationRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println("Meesage recieved");
                    Log.i("fd", "Message received");

                } else {
                    Log.e("editBookTask", "Error");

                    serverErrorHandling("sending remote notification");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                serverErrorHandling("sending remote notification");
            }
        });
    }

    private void sendNotificationMail(Ad ad, User user){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean should_request = preferences.getBoolean("should_request", false);
        boolean should_confirm = preferences.getBoolean("should_confirm", false);

        EmailDto emailDto1;
        EmailDto emailDto2;

        String adOwnerShip = "";
        if(ad.getOwnerId() == user.getEntityId()){
            // owner of ad is editing

            // notificationEmail: ad owner booking confirmation is checked
            if(should_confirm){
                adOwnerShip = "ad";
                emailDto1 = prepareMail(ad, user, adOwnerShip);

                Call<ResponseBody> call = ServiceUtils.userServiceApi.sendNotificationMail(emailDto1);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            System.out.println("Meesage recieved");
                            Log.i("fd", "Message received");

                        } else {
                            Log.e("editBookTask", "Error");

                            serverErrorHandling("sending notification mail");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        serverErrorHandling("sending notification mail");
                    }
                });
            }

            // send to user mail if it is not null
            if(ad.getUserId() != 0){
                User u = User.getOneGlobal(ad.getUserId());
                emailDto2 = prepareMail(ad, u, "booking");

                // send to user mail
                Call<ResponseBody> call = ServiceUtils.userServiceApi.sendNotificationMail(emailDto2, ad.getEntityId());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            System.out.println("Meesage recieved");
                            Log.i("fd", "Message received");

                        } else {
                            Log.e("editBookTask", "Error");

                            serverErrorHandling("sending notification mail");
                        }                }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        serverErrorHandling("sending notification mail");
                    }
                });
            }


        } else {
            // user would like to book ad
            User own = User.getOneGlobal(ad.getOwnerId());
            emailDto2 = prepareMail(ad, own, "ad");
            List<EmailDto> emails = new ArrayList<EmailDto>(Arrays.asList(new EmailDto[]{emailDto2}));

            // notificationEmail: user booking request is checked
            if(should_request){
                adOwnerShip = "booking";
                emailDto1 = prepareMail(ad, user, adOwnerShip);
                emails.add(emailDto1);
            }

            Call<ResponseBody> call = ServiceUtils.userServiceApi.sendNotificationMailBatch(emails);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Meesage recieved");
                        Log.i("fd", "Message received");

                    } else {
                        Log.e("editBookTask", "Error");

                        serverErrorHandling("sending notification mail");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    serverErrorHandling("sending notification mail");
                }
            });
        }
    }

    private EmailDto prepareMail(Ad ad, User user, String adOwnerShip){
        String title = context.getString(R.string.notif_booking_title);
        String messagePattern = context.getString(R.string.notif_booking_text);
        String message = messagePattern
                .replace("%AD%", adOwnerShip)
                .replace("%STATUS%", ad.getAdStatus().toString())
                .replace("%TITLE%", ad.getTitle())
                ;

        String sendTo = "";
        if(user.getEmail() != null){
             sendTo = user.getEmail();
        }

        EmailDto emailDto = new EmailDto(title, title, message, sendTo);

        return emailDto;
    }

    private void serverErrorHandling(String action){
        String serverErrorPattenr = context.getString(R.string.server_error_msg);

        Intent intent = new Intent(MainActivity.SERVER_ERROR);
        intent.putExtra("error_message",
                serverErrorPattenr.replace("%ACTION%", action));
        context.sendBroadcast(intent);
    }
}
