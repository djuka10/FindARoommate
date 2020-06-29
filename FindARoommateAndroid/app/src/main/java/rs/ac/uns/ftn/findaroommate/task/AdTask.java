package rs.ac.uns.ftn.findaroommate.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.activity.RoomListActivity;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;
import rs.ac.uns.ftn.findaroommate.utils.AdStatus;

public class AdTask extends AsyncTask<Long,Void,Void> {

    private Context context;

    public static String RESULT_CODE = "RESULT_CODE";

    public AdTask(Context context)
    {
        this.context = context;
    }
    @Override
    protected Void doInBackground(Long... longs) {

        Call<List<Ad>> a = ServiceUtils.adServiceApi.getAll();
        a.enqueue(new Callback<List<Ad>>() {
            @Override
            public void onResponse(Call<List<Ad>> call, Response<List<Ad>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Meesage recieved");
                    Log.i("fd", "Message received");

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

        return null;
    }
}
