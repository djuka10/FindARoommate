package rs.ac.uns.ftn.findaroommate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.util.IOUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.dto.AdFormDto;
import rs.ac.uns.ftn.findaroommate.dto.EmailDto;
import rs.ac.uns.ftn.findaroommate.fragment.FragmentTransition;
import rs.ac.uns.ftn.findaroommate.fragment.NewAdFinalFragment;
import rs.ac.uns.ftn.findaroommate.fragment.NewAdFragmentFactory;
import rs.ac.uns.ftn.findaroommate.fragment.NewAdFragmentAbstact;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.AdItem;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.service.EditAdService;
import rs.ac.uns.ftn.findaroommate.service.EditProfileService;
import rs.ac.uns.ftn.findaroommate.service.UploadImagesService;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;
import rs.ac.uns.ftn.findaroommate.utils.AdStatus;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class NewAdActivity extends AppCompatActivity {

    public Toolbar getToolbar() {
        return toolbar;
    }

    Toolbar toolbar;
    ActionBar actionBar;

    AdDto ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ad);

        toolbar = (Toolbar) findViewById(R.id.ad_form_toolbar);
        toolbar.setTitle("New Add");
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button button = (Button) findViewById(R.id.ad_save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewAdFragmentAbstact activeFragment = (NewAdFragmentAbstact) FragmentTransition.findActiveFragment(NewAdActivity.this);
                int dialogNum = activeFragment.getDialogNum();

                if(dialogNum == 6){
                    Fragment lastFragment = FragmentTransition.findActiveFragment(NewAdActivity.this);
                    if(lastFragment instanceof NewAdFinalFragment){
                        ((NewAdFinalFragment)lastFragment).pickData();
                    }

                    //save
                    proccessAdEdit();

                    Toast.makeText(NewAdActivity.this, getString(R.string.ad_form_success), Toast.LENGTH_LONG).show();
                    Intent homepageIntent = new Intent(NewAdActivity.this, HomepageActivity.class);
                    startActivity(homepageIntent);
                    finish();
                } else {
                    //Fragment f = FragmentTransition.getCurrentFragment(NewAdActivity.this);
                    int nextDialogNum = ++dialogNum;
                    NewAdFragmentAbstact nextFragment = NewAdFragmentFactory.getNewAdFragment(nextDialogNum, ad);
                    Bundle bundle = new Bundle();
                    bundle.putString("title",  getString(R.string.ad_form_header)+ " ");
                    bundle.putInt("dialogNum", nextDialogNum);
                    nextFragment.setArguments(bundle);
                    FragmentTransition.to(nextFragment, NewAdActivity.this, true, "Dialog" + nextDialogNum);

                }
            }
        });

        ad = AdDto.builder()
                .ad(new Ad())
                .adItems(new ArrayList<AdItem>())
                .images(new ArrayList<ResourceRegistry>())
                .prefsFilm(new ArrayList<UserCharacteristic>())
                .prefsLifestyle(new ArrayList<UserCharacteristic>())
                .prefsMusic(new ArrayList<UserCharacteristic>())
                .prefsPersonality(new ArrayList<UserCharacteristic>())
                .prefsSport(new ArrayList<UserCharacteristic>())
                .adItemsIds(new ArrayList<Integer>())
                .prefsId(new ArrayList<Integer>())
                .build();

        if(savedInstanceState == null){
            NewAdFragmentAbstact nextFragment = NewAdFragmentFactory.getNewAdFragment(1, ad);
            Bundle bundle = new Bundle();
            bundle.putString("title", getString(R.string.ad_form_header));
            bundle.putInt("dialogNum", 1);
            nextFragment.setArguments(bundle);
            FragmentTransition.to(nextFragment, NewAdActivity.this, false, "Dialog1");
        }

        // ako nema u manifestu ovo zabrani na dugmetu da se slika...
        // nekad se otvori dijalog pa na osnovu odgovora se poziva listener ispod i interpretira
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            // ako su ove obe stvari u manifestu prikazi dugme, u suprotnom zadrzi onemoguceno
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            NewAdFragmentAbstact activeFragment = (NewAdFragmentAbstact) FragmentTransition.findActiveFragment(NewAdActivity.this);
            int dialogNum = activeFragment.getDialogNum();

            if(dialogNum == 1){
                //back from dialog
                //finish();
                return super.onOptionsItemSelected(item);
            }

            int nextDialogNum = --dialogNum;
            NewAdFragmentAbstact nextFragment = NewAdFragmentFactory.getNewAdFragment(nextDialogNum, ad);
            Bundle bundle = new Bundle();
            bundle.putString("title", getString(R.string.ad_form_header));
            bundle.putInt("dialogNum", nextDialogNum);
            nextFragment.setArguments(bundle);
            FragmentTransition.to(nextFragment, NewAdActivity.this, false);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void proccessAdEdit(){

        Ad a = ad.getAd();
        User user = AppTools.getLoggedUser();
        a.setOwnerId(user);
        a.setAdStatus(AdStatus.IDLE);
        a.save();

        // send to the server
//        Intent editAdtent = new Intent(this, EditAdService.class);
//        editAdtent.putExtra("userId", -1);
//        editAdtent.putExtra("ad", ad);
//        startService(editAdtent);

        AdFormDto adFormDto = AdFormDto.builder()
                .adType(ad.getAd().getAdType())
                .boysNum(ad.getAd().getBoysNum())
                .costsIncluded(ad.getAd().isCostsIncluded())
                .deposit(ad.getAd().getDeposit())
                .description(ad.getAd().getDescription())
                .flatM2(ad.getAd().getFlatM2())
                .ladiesNum(ad.getAd().getLadiesNum())
                .latitude(ad.getAd().getLatitude())
                .longitude(ad.getAd().getLongitude())
                .maxDays(ad.getAd().getMaxDays())
                .minDays(ad.getAd().getMinDays())
                .maxPerson(ad.getAd().getMaxPerson())
                .price(ad.getAd().getPrice())
                .roomM2(ad.getAd().getRoomM2())
                .title(ad.getAd().getTitle())
                .adItemsId(ad.getAdItemsIds())
                .roommatePrefsId(ad.getPrefsId())
                .adStatus(AdStatus.IDLE)
                .adOwnerId(user.getEntityId())
                .build();

        if(ad.getAd().getAvailableFrom() != null){
            adFormDto.setAvailableFrom(AppTools.getSimpleDateFormat().format(ad.getAd().getAvailableFrom()));
        }

        if(ad.getAd().getAvailableUntil() != null){
            adFormDto.setAvailableUntil(AppTools.getSimpleDateFormat().format(ad.getAd().getAvailableUntil()));
        }

        Call<AdFormDto> call = ServiceUtils.adServiceApi.add(adFormDto);
        call.enqueue(new Callback<AdFormDto>() {
            @Override
            public void onResponse(Call<AdFormDto> call, Response<AdFormDto> response) {
                if (response.isSuccessful()) {
                    System.out.println("Meesage recieved");
                    Log.i("fd", "Message received");

                    AdFormDto body = response.body();
                    a.setEntityId(body.getEntityId());
                    a.save();

                    uploadImagesToServer(body.getEntityId());
                    sendNotificationMail();
                } else {
                    Log.e("editProfileTask", "Error");

                    // TODO: HANDLE ERROR MECHANISM
                    Intent intent = new Intent(MainActivity.SERVER_ERROR);
                    intent.putExtra("error_message",
                            "Server error while adding new ad. Please try again.");
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onFailure(Call<AdFormDto> call, Throwable t) {
                System.out.println("Error!");
                Log.e("error", t.getMessage());

                Intent intent = new Intent(MainActivity.SERVER_ERROR);
                intent.putExtra("error_message",
                        "Server error while adding new ad. Please try again.");
                sendBroadcast(intent);
            }
        });

    }

    private void sendNotificationMail(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean shouldNewAdNotification = sharedPreferences.getBoolean("should_new_ad", false);
        if(shouldNewAdNotification){
            User user = AppTools.getLoggedUser();
            String contentPattern = getString(R.string.ad_form_mail_content);
            EmailDto emailDto = new EmailDto(
                    getString(R.string.ad_form_mail_subject),
                    contentPattern.replace("%USER_NAME%", user.getFirstName() + " " + user.getLastName()),
                    getString(R.string.ad_form_mail_additional_info),
                    user.getEmail());
            Call<ResponseBody> call = ServiceUtils.userServiceApi.sendNotificationMail(emailDto);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Meesage recieved");
                        Log.i("fd", "Message received");

                    } else {
                        Log.e("error", "Error");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println("Error!");
                    Log.e("error", t.getMessage());
                }
            });
        }
    }

    private void uploadImagesToServer(int adId){
//        Intent uploadImagesIntent = new Intent(NewAdActivity.this, UploadImagesService.class);
//        List<String> uriList = ad.getImages().stream().map(ResourceRegistry::getUri).collect(Collectors.toList());
//        ArrayList<String> uriArrayList = (ArrayList<String>)uriList;
//        uploadImagesIntent.putStringArrayListExtra("images", uriArrayList);
//        uploadImagesIntent.putExtra("addId", adId);
//
//        startService(uploadImagesIntent);


        //***********************************TESTING*******************************************
        int addId = adId;
        int userId = -1;

        User user = AppTools.getLoggedUser();
        if(user != null){
            userId = user.getEntityId();
        }

        for(ResourceRegistry resourceRegistry: ad.getImages()){
            sendImageToServer(Uri.parse(resourceRegistry.getUri()), addId, userId);
        }

    }

    private void sendImageToServer(Uri selectedImage, int addId, int userId){
        InputStream inputStream = null;
        byte[] image = null;
        String fileName = "";

        try {
            // ne radi za gallery
            inputStream = getContentResolver().openInputStream(selectedImage);
            fileName = getFileName(selectedImage);
            //fileName = AppTools.createFileName();
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

        Call<ResourceRegistry> c = ServiceUtils.adServiceApi.uploadPhoto(
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
