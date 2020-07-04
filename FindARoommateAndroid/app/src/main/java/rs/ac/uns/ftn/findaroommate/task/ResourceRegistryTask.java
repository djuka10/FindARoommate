package rs.ac.uns.ftn.findaroommate.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.activity.RoomListActivity;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;

public class ResourceRegistryTask extends AsyncTask<Long,Void,Void> {

    private Context context;

    public static String RESULT_CODE = "RESULT_CODE";

    public ResourceRegistryTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Long... longs) {

        for (Ad ad: RoomListActivity.listOfAvaiable) {
            Call<List<ResourceRegistry>> r = ServiceUtils.adServiceApi.getAdImages(Integer.toString(ad.getEntityId()));

            r.enqueue(new Callback<List<ResourceRegistry>>() {
                @Override
                public void onResponse(Call<List<ResourceRegistry>> call, Response<List<ResourceRegistry>> response) {
                    List<ResourceRegistry> body = response.body();
                    for (ResourceRegistry rr: body) {
                        if(!RoomListActivity.listOfAvaiablePictures.contains(rr))
                            RoomListActivity.listOfAvaiablePictures.add(rr);
                    }

                }

                @Override
                public void onFailure(Call<List<ResourceRegistry>> call, Throwable t) {

                }
            });
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
        Log.i("Resource images registry task", "Succesfully get images");
    }
}
