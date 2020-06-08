package rs.ac.uns.ftn.findaroommate.service;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

import com.google.android.gms.common.util.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.activity.ProfileFormActivity;
import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.dto.UserDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;
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
public class UploadImagesService extends IntentService {

    public UploadImagesService() {
        super("UploadImagesService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ArrayList<String> images = intent.getStringArrayListExtra("images");
            int addId = intent.getIntExtra("addId", -1);
            int userId = -1;

            User user = AppTools.getLoggedUser();
            if(user != null){
                userId = user.getEntityId();
            }

            for(String imgUri: images){
                sendImageToServer(Uri.parse(imgUri), addId, userId);
            }
        }
    }

    private void sendImageToServer(Uri selectedImage, int addId, int userId){
        InputStream inputStream = null;
        byte[] image = null;
        String fileName = "";

        try {
            // ne radi za gallery, za photo je okej....
            inputStream = getContentResolver().openInputStream(selectedImage);
            fileName = getFileName(selectedImage);
            try {
                image = IOUtils.toByteArray(inputStream);
                System.out.println("jkfd");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e){

        }

        //new UploadProfileTask(getApplicationContext()).execute(image, fileName, user.getEntityId(), true);
        RequestBody body = MultipartBody.create(MediaType.parse("image/jpeg"), image);

        Call<ResourceRegistry> c = ServiceUtils.reviewerServiceApi.uploadPhoto(
                MultipartBody.Part.createFormData("image", fileName, body),
                MultipartBody.Part.createFormData("addId", Integer.toString(addId)),
                MultipartBody.Part.createFormData("user", Integer.toString(userId)),
                MultipartBody.Part.createFormData("profilePicture", Boolean.toString(false)));
        c.enqueue(new Callback<ResourceRegistry>() {
            @Override
            public void onResponse(Call<ResourceRegistry> call, Response<ResourceRegistry> response) {
                if(response.isSuccessful()){
                    ResourceRegistry body = response.body();
                    System.out.println("super");

                }
            }


            @Override
            public void onFailure(Call<ResourceRegistry> call, Throwable t) {
                System.out.println("Error");
            }
        });
    }

    public String getFileName(Uri selectedImage){
        Cursor cursor = null;
        String fileName = "";
        cursor = getContentResolver().query(selectedImage, null, null, null, null);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            cursor.moveToFirst();
            fileName = cursor.getString(nameIndex);
            cursor.close();
        }
        return fileName;
    }

}
