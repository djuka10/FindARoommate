package rs.ac.uns.ftn.findaroommate.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.RoomListActivity;
import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.dto.AdDtoDto;
import rs.ac.uns.ftn.findaroommate.dto.TagToSend;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.Language;
import rs.ac.uns.ftn.findaroommate.model.Review;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;
import rs.ac.uns.ftn.findaroommate.utils.AdStatus;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;


/**
 * Created by milossimic on 4/6/16.
 */
public class DemoTask extends AsyncTask<Void, Void, Void> {

    private Context context;

    public static String RESULT_CODE = "RESULT_CODE";

    public DemoTask(Context context)
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
                            if(User.getOneGlobal(user.getEntityId()) == null){
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
                        User user = null;
                        User owner = null;
                        for (Ad ad: RoomListActivity.adsList) {
                            if(Ad.getOne(ad.getEntityId()) == ad) {
                                //proveram kako poredi
                            }
                            if(Ad.getOne(ad.getEntityId()) == null) {
                                if(ad.getUserId() != null) {
                                    user = User.getOne(ad.getUserId().getEntityId());
                                }
                                if(ad.getOwnerId() != null) {
                                    owner = User.getOne(ad.getOwnerId().getEntityId());
                                }

                                if(ad.getAdStatus() != null) {
                                    if(ad.getAdStatus().equals(AdStatus.IDLE)) {

                                    } else if(ad.getAdStatus().equals(AdStatus.PENDING)) {

                                    } else if(ad.getAdStatus().equals(AdStatus.APPROVE)) {

                                    } else {
                                        //denied
                                    }
                                }

                                ad.setUserId(user);
                                ad.setOwnerId(owner);
                                ad.save();
                            }

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
                Intent intent = new Intent(context, SyncService.class);
                context.startService(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void result) {
        Log.i("REZ", "onPostExecute");

        Intent ints = new Intent("fd");
        int status = AppTools.getConnectivityStatus(context);
        ints.putExtra(RESULT_CODE, status);
        //context.sendBroadcast(ints);
    }

    private void serverErrorHandling(String action){
        String serverErrorPattenr = context.getString(R.string.server_error_msg);

        Intent intent = new Intent(MainActivity.SERVER_ERROR);
        intent.putExtra("error_message",
                serverErrorPattenr.replace("%ACTION%", action));
        context.sendBroadcast(intent);
    }
}
