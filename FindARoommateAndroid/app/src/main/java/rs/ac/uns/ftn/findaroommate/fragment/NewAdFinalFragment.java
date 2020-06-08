package rs.ac.uns.ftn.findaroommate.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.NewAdActivity;
import rs.ac.uns.ftn.findaroommate.adapter.DisplayImageAdapter;
import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.provider.GenericFileProvider;
import rs.ac.uns.ftn.findaroommate.service.DemoService;
import rs.ac.uns.ftn.findaroommate.service.UploadImagesService;

public class NewAdFinalFragment extends NewAdFragmentAbstact {

    ImageButton uploadPhotoButton;
    ImageView photoView;

    AlertDialog alertDialog;

    Button uploadOptionButton;
    Button galleryOptionButton;

    ViewPager viewPager;
    DisplayImageAdapter imageAdapter;

    TextInputEditText titleEditText;
    TextInputEditText descEditText;

    private Uri takenPhotoTemp;


    private static final int TAKE_PHOTO_ACTIVITY = 101;
    private static final int UPLOAD_PHOTO_ACTIVITY = 102;

    private static final String APP_NAME = "FindARoommate";

    public NewAdFinalFragment(AdDto ad) {
        super(ad);
    }

    public static NewAdFinalFragment newInstance(AdDto ad) {
        NewAdFinalFragment mpf = new NewAdFinalFragment(ad);

        return mpf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_new_add_final, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            title = bundle.getString("title", "New ad");
            dialogNum = bundle.getInt("dialogNum", 1);
        }

        TextView titleText = (TextView) view.findViewById(R.id.dialog_title);
        titleText.setText(title + " (Final)");

        uploadPhotoButton = (ImageButton) view.findViewById(R.id.btn_upload_photo);
        uploadPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                ViewGroup viewGroup = view.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.upload_dialog, viewGroup, false);
                builder.setView(dialogView);
                alertDialog = builder.create();
                galleryOptionButton = dialogView.findViewById(R.id.btn_upload_option);
                uploadOptionButton = dialogView.findViewById(R.id.btn_take_photo_option);
                galleryOptionButton.setOnClickListener(new NewAdFinalFragment.UploadDialogClick());
                uploadOptionButton.setOnClickListener(new NewAdFinalFragment.UploadDialogClick());
                alertDialog.show();
            }
        });

        viewPager = (ViewPager) view.findViewById(R.id.slider_view_pager);

        imageAdapter = new DisplayImageAdapter(getContext(), this, ad.getImages());
        viewPager.setAdapter(imageAdapter);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            uploadPhotoButton.setEnabled(false);
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        titleEditText = (TextInputEditText) view.findViewById(R.id.ad_form_title);
        descEditText = (TextInputEditText) view.findViewById(R.id.ad_form_desc);

        //temp
        String adTitle = ad.getAd().getTitle();
        String adDesc = ad.getAd().getDescription();

        if(adTitle != null){
            titleEditText.setText(ad.getAd().getTitle());
        }

        if(adDesc != null){
            descEditText.setText(ad.getAd().getDescription());
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        NewAdActivity act = (NewAdActivity) getActivity();
        act.getToolbar().setTitle("New ad " + dialogNum + " of 6");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("title", "Gladiator");

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    class UploadDialogClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();

            switch (id){
                case R.id.btn_upload_option:
                    alertDialog.cancel();
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, UPLOAD_PHOTO_ACTIVITY);

                    break;
                case R.id.btn_take_photo_option:
                    alertDialog.cancel();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    File outputMediaFile = getOutputMediaFile();
                    if(outputMediaFile != null){
                        takenPhotoTemp = FileProvider.getUriForFile(getContext(), GenericFileProvider.MY_PROVIDER, outputMediaFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, takenPhotoTemp); //za skladistenje andoidovog serializable, tj parceable

                        startActivityForResult(intent, TAKE_PHOTO_ACTIVITY);
                    }
                    break;
            }
        }
    }

    private static File getOutputMediaFile(){

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "FindARoommate");

        String path = mediaStorageDir.getPath();

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdir()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == getActivity().RESULT_OK && requestCode == TAKE_PHOTO_ACTIVITY){
            if(isExternalStorageWritable()){
                if (takenPhotoTemp != null){
                    String uriPath = takenPhotoTemp.toString();
                    takenPhotoTemp.getPath();

                    ResourceRegistry newImage  = ResourceRegistry.builder().uri(uriPath).profilePicture(false).build();
                    addAdImage(newImage);
                }
            } else {
                Log.e(APP_NAME, "Dont have permissions to write");
            }
        }

        if(resultCode == getActivity().RESULT_OK && requestCode == UPLOAD_PHOTO_ACTIVITY){
            if(data!= null){
                if(isExternalStorageReadable()){
                    Uri selectedImage = data.getData();
                    String uriPath = selectedImage.toString();

                    ResourceRegistry newImage  = ResourceRegistry.builder().uri(uriPath).profilePicture(false).build();
                    addAdImage(newImage);
                } else {
                    Log.e(APP_NAME, "Dont have permissions to write");
                }
            }
        }
    }

    public void addAdImage(ResourceRegistry newIamge){
        ad.getImages().add(newIamge);
        imageAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(ad.getImages().size()-1);
    }

    public void removeImage(ResourceRegistry image, int nextPosition){
        viewPager.setAdapter(null);
        ad.getImages().remove(image);
        imageAdapter = new DisplayImageAdapter(getContext(), this, ad.getImages());
        viewPager.setAdapter(imageAdapter);

        imageAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(nextPosition);

        Toast.makeText(getContext(), "Image deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                uploadPhotoButton.setEnabled(true);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /* Checks if external storage is available to write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        //pickData();

        super.onDestroyView();
    }

    public void pickData(){
        ad.getAd().setTitle(titleEditText.getText().toString());
        ad.getAd().setDescription(descEditText.getText().toString());

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
