package rs.ac.uns.ftn.findaroommate.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.RoomListActivity;
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
                            if(Ad.getOneGlobal(ad.getEntityId()) == ad) {
                                //proveram kako poredi
                            }

                            User user = null;
                            User owner = null;

                            if(Ad.getOneGlobal(ad.getEntityId()) == null) {
                                if(ad.getUserId() != null) {
                                    user = User.getOneGlobal(ad.getUserId().getEntityId());
                                }
                                if(ad.getOwnerId() != null) {
                                    owner = User.getOneGlobal(ad.getOwnerId().getEntityId());
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
                Call<List<Review>> reviewsCall = ServiceUtils.reviewServiceApi.getUserReview(loggedUser.getEntityId());
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

    private void serverErrorHandling(String action){
        String serverErrorPattenr = context.getString(R.string.server_error_msg);

        String c = Locale.getDefault().getCountry();

        Intent intent = new Intent(MainActivity.SERVER_ERROR);
        intent.putExtra("error_message",
                serverErrorPattenr.replace("%ACTION%", action));
        context.sendBroadcast(intent);
    }
}
