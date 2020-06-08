package rs.ac.uns.ftn.findaroommate.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.dto.UserDto;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;

public class UploadProfileTask extends AsyncTask<Object, Void, Void> {

    private Context context;

    public static String RESULT_CODE = "RESULT_CODE";

    public UploadProfileTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Object... voids) {
        Log.i("REZ", "doInBackground");

        byte[] image = (byte[]) voids[0];
        String fileName = (String) voids[1];
        int user = (int) voids[2];
        boolean profilePicture = (boolean) voids[3];

        RequestBody body = MultipartBody.create(MediaType.parse("image/jpeg"), image);

        Call<ResourceRegistry> c = ServiceUtils.userServiceApi.uploadPhoto(
                MultipartBody.Part.createFormData("image", fileName, body),
                MultipartBody.Part.createFormData("user", Integer.toString(user)),
                MultipartBody.Part.createFormData("profilePicture", Boolean.toString(profilePicture)));
        c.enqueue(new Callback<ResourceRegistry>() {
            @Override
            public void onResponse(Call<ResourceRegistry> call, Response<ResourceRegistry> response) {
                if(response.isSuccessful()){
                    System.out.println("super");
                }
            }


            @Override
            public void onFailure(Call<ResourceRegistry> call, Throwable t) {
                System.out.println("Error");
            }
        });
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //super.onPostExecute(aVoid);

    }
}
