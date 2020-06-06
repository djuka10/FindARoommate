package rs.ac.uns.ftn.findaroommate.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.provider.GenericFileProvider;
import rs.ac.uns.ftn.findaroommate.receiver.EditProfileReceiver;
import rs.ac.uns.ftn.findaroommate.service.EditProfileIntentService;
import rs.ac.uns.ftn.findaroommate.service.EditProfileService;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class ProfileFormActivity extends AppCompatActivity {

    User user;

    DatePickerDialog picker;
    TextInputEditText dateOfBirthEditText;
    TextInputEditText languageEditText;

    TextInputEditText firstNameEditText;
    TextInputEditText lastNameEditText;
    TextInputEditText occupationEditText;
    TextInputEditText descEditText;

    Spinner genderSpinner;
    Spinner studyLevelSpinner;
    Spinner workingStatusSpinner;
    ArrayAdapter<CharSequence> genderAdapter;
    ArrayAdapter<CharSequence> studyLevelAdapter;
    ArrayAdapter<CharSequence> workingStatusAdapter;

    ChipGroup personalityChipGroup;
    ChipGroup lifestyleChipGroup;
    ChipGroup musicChipGroup;
    ChipGroup filmChipGroup;
    ChipGroup sportChipGroup;


    Button saveButton;
    Button uploadOptionButton;
    Button galleryOptionButton;


    ImageButton personalityButton;
    ImageButton lifestyleButton;
    ImageButton sportButton;
    ImageButton filmButton;
    ImageButton musicButton;

    ImageButton uploadPhotoButton;
    private Uri file;
    ImageView photoView;

    AlertDialog alertDialog;

    private static final int LANGUAGE_CHOSER_ACTIVITY = 99;
    private static final int USER_ATTRIBUTES_ACTIVITY = 100;
    private static final int TAKE_PHOTO_ACTIVITY = 101;
    private static final int UPLOAD_PHOTO_ACTIVITY = 102;


    private ClickListener clickListener = new ClickListener();

    EditProfileReceiver editProfileReceiver;
    public static String EDIT_USER_PROFILE = "EDIT_USER_PROFILE";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == LANGUAGE_CHOSER_ACTIVITY) {
            if (data.hasExtra("selectedLanguages")) {
                CharSequence[] a = data.getExtras().getCharSequenceArray("selectedLanguages");
                StringBuilder builder = new StringBuilder();
                for(int i = 0; i < a.length; i++){
                    builder.append(a[i]);
                    if( i < a.length - 1)
                        builder.append(", ");
                }
                languageEditText.setText(builder.toString());
            }
        }

        if (resultCode == RESULT_OK && requestCode == USER_ATTRIBUTES_ACTIVITY) {
            CharSequence[] characteritics = data.getExtras().getCharSequenceArray("selectedPersonalities");
            personalityChipGroup.removeAllViews();
            createLanguageChips(characteritics, personalityChipGroup);

            characteritics = data.getExtras().getCharSequenceArray("selectedLifestyles");
            lifestyleChipGroup.removeAllViews();
            createLanguageChips(characteritics, lifestyleChipGroup);

            characteritics = data.getExtras().getCharSequenceArray("selectedMusics");
            musicChipGroup.removeAllViews();
            createLanguageChips(characteritics, musicChipGroup);

            characteritics = data.getExtras().getCharSequenceArray("selectedFilms");
            filmChipGroup.removeAllViews();
            createLanguageChips(characteritics, filmChipGroup);

            characteritics = data.getExtras().getCharSequenceArray("selectedSport");
            sportChipGroup.removeAllViews();
            createLanguageChips(characteritics, sportChipGroup);
        }

        if(resultCode == RESULT_OK && requestCode == TAKE_PHOTO_ACTIVITY){
            photoView.setImageURI(file);
        }

        if(resultCode == RESULT_OK && requestCode == UPLOAD_PHOTO_ACTIVITY){
            if(data!= null){
                Uri selectedImage = data.getData();
                photoView.setImageURI(selectedImage);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_form);

        user = AppTools.getLoggedUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_form_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        genderSpinner = (Spinner) findViewById(R.id.profile_form_gender_spinner);
        genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender_values, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        workingStatusSpinner = (Spinner) findViewById(R.id.profile_form_working_status_spinner);
        workingStatusAdapter = ArrayAdapter.createFromResource(this,
                R.array.working_status_values, android.R.layout.simple_spinner_item);
        workingStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workingStatusSpinner.setAdapter(workingStatusAdapter);

        studyLevelSpinner = (Spinner) findViewById(R.id.profile_form_study_level_spinner);
        studyLevelAdapter = ArrayAdapter.createFromResource(this,
                R.array.study_level_values, android.R.layout.simple_spinner_item);
        studyLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studyLevelSpinner.setAdapter(studyLevelAdapter);

        firstNameEditText = (TextInputEditText) findViewById(R.id.profile_form_first_name);
        lastNameEditText = (TextInputEditText) findViewById(R.id.profile_form_last_name);
        occupationEditText = (TextInputEditText) findViewById(R.id.profile_form_occupation);
        descEditText = (TextInputEditText) findViewById(R.id.profile_form_desc);

        dateOfBirthEditText= (TextInputEditText) findViewById(R.id.profile_form_date_of_birth_picker);
        dateOfBirthEditText.setInputType(InputType.TYPE_NULL);
        //dateOfBirthEditText.setOnClickListener(clickListener);
        dateOfBirthEditText.setOnFocusChangeListener(new FocusListener());

        languageEditText= (TextInputEditText) findViewById(R.id.profile_form_input_language);
        languageEditText.setOnFocusChangeListener(new FocusListener());
        languageEditText.setInputType(InputType.TYPE_NULL);

        saveButton = (Button) findViewById(R.id.profile_save_button);
        saveButton.setOnClickListener(clickListener);

        uploadPhotoButton = (ImageButton) findViewById(R.id.btn_upload_photo);
        uploadPhotoButton.setOnClickListener(clickListener);
        photoView = (ImageView) findViewById(R.id.profile_picture);

        personalityButton = (ImageButton) findViewById(R.id.btn_personality_select);
        lifestyleButton = (ImageButton) findViewById(R.id.btn_lifestyle_select);
        sportButton = (ImageButton) findViewById(R.id.btn_sport_select);
        filmButton = (ImageButton) findViewById(R.id.btn_film_select);
        musicButton = (ImageButton) findViewById(R.id.btn_music_select);

        personalityButton.setOnClickListener(clickListener);
        lifestyleButton.setOnClickListener(clickListener);
        sportButton.setOnClickListener(clickListener);
        filmButton.setOnClickListener(clickListener);
        musicButton.setOnClickListener(clickListener);


        personalityChipGroup = (ChipGroup) findViewById(R.id.personality_chips);
        lifestyleChipGroup = (ChipGroup) findViewById(R.id.lifestyle_chips);
        musicChipGroup = (ChipGroup) findViewById(R.id.music_chips);
        filmChipGroup = (ChipGroup) findViewById(R.id.film_chips);
        sportChipGroup = (ChipGroup) findViewById(R.id.sport_chips);

        setData();

        // ako nema u manifestu ovo zabrani na dugmetu da se slika...
        // nekad se otvori dijalog pa na osnovu odgovora se poziva listener ispod i interpretira
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            uploadPhotoButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        setUpReceiver();
    }

    @Override
    protected void onPause() {
        //osloboditi resurse
        if(editProfileReceiver != null){
            unregisterReceiver(editProfileReceiver);
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(EDIT_USER_PROFILE);
        registerReceiver(editProfileReceiver, filter);
    }

    private void setUpReceiver(){
        editProfileReceiver = new EditProfileReceiver();
    }

    private void setData(){
        firstNameEditText.setText(user.getFirstName());
        lastNameEditText.setText(user.getLastName());
        occupationEditText.setText(user.getOccupation());
        descEditText.setText(user.getAbout());

        // spinner set
        int spinnerPosition = genderAdapter.getPosition(user.getGender());
        genderSpinner.setSelection(spinnerPosition);

        spinnerPosition = workingStatusAdapter.getPosition(user.getWorkingStatus());
        workingStatusSpinner.setSelection(spinnerPosition);

        spinnerPosition = studyLevelAdapter.getPosition(user.getStudyLevel());
        studyLevelSpinner.setSelection(spinnerPosition);

        if(user.getBirthDay() != null){
            Calendar cldr = Calendar.getInstance();
            cldr.setTime(user.getBirthDay());
            dateOfBirthEditText.setText(cldr.get(Calendar.DAY_OF_MONTH) + "/" + (cldr.get(Calendar.MONTH) + 1) + "/" + cldr.get(Calendar.YEAR));
        }
    }

    private void proccessProfileEdit(){
        user.setFirstName(firstNameEditText.getText().toString());
        user.setLastName(lastNameEditText.getText().toString());
        user.setOccupation(occupationEditText.getText().toString());
        user.setAbout(descEditText.getText().toString());
        user.setGender(genderSpinner.getSelectedItem().toString());
        user.setWorkingStatus(workingStatusSpinner.getSelectedItem().toString());
        user.setStudyLevel(studyLevelSpinner.getSelectedItem().toString());

        user.save();

        // send to the server
        Intent editProfileIntent = new Intent(this, EditProfileService.class);
        editProfileIntent.putExtra("userId", user.getId());
        startService(editProfileIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            // ako su ove obe stvari u manifestu prikazi dugme, u suprotnom zadrzi onemoguceno
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                uploadPhotoButton.setEnabled(true);
            }
        }
    }

    private void createLanguageChips(CharSequence[] characteristics, ChipGroup chips){
        for(int i = 0; i < characteristics.length; i++){
            Chip c = (Chip) this.getLayoutInflater().inflate(R.layout.chip_tag, null, false);
            c.setText(characteristics[i]);
            chips.addView(c);
        }
    }

    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            boolean t = true;
            if(v == dateOfBirthEditText){
                TextInputEditText vo = (TextInputEditText)v;
                final Calendar cldr = Calendar.getInstance();

                if(user.getBirthDay() != null) {
                    cldr.setTime(user.getBirthDay());
                }

                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ProfileFormActivity.this, null, year, month, day);
                picker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateOfBirthEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                });
                picker.show();
            } else if(v == saveButton){
                proccessProfileEdit();
                Toast.makeText(ProfileFormActivity.this, getString(R.string.profile_form_success), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ProfileFormActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            } else if(v == uploadPhotoButton) {
                // dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileFormActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.upload_dialog, viewGroup, false);
                builder.setView(dialogView);
                alertDialog = builder.create();
                galleryOptionButton = dialogView.findViewById(R.id.btn_upload_option);
                uploadOptionButton = dialogView.findViewById(R.id.btn_take_photo_option);
                galleryOptionButton.setOnClickListener(new UploadDialogClick());
                uploadOptionButton.setOnClickListener(new UploadDialogClick());
                alertDialog.show();
            } else {
                Intent intent = new Intent(getApplicationContext(), UserAttributesActivity.class);
                intent.putExtra("key", "value");
                startActivityForResult(intent, USER_ATTRIBUTES_ACTIVITY);
            }
        }
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
                        file = FileProvider.getUriForFile(ProfileFormActivity.this, GenericFileProvider.MY_PROVIDER, outputMediaFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, file); //za skladistenje andoidovog serializable, tj parceable

                        startActivityForResult(intent, TAKE_PHOTO_ACTIVITY);
                    }
                    break;
            }
        }
    }

    class FocusListener implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(v == dateOfBirthEditText){
                if(hasFocus){
                    final Calendar cldr = Calendar.getInstance();

                    if(user.getBirthDay() != null) {
                        cldr.setTime(user.getBirthDay());
                    }

                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    picker = new DatePickerDialog(ProfileFormActivity.this, null, year, month, day);
                    picker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            dateOfBirthEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                            Calendar c = Calendar.getInstance();
                            c.set(year, month, dayOfMonth, 0, 0);

                            Date d = c.getTime();
                            user.setBirthDay(d);
                        }
                    });
                    picker.show();
                }
            } else if (v == languageEditText){
                if(hasFocus){
                    Intent intent = new Intent(getApplicationContext(), LanguageChooserActivity.class);
                    intent.putExtra("key", "value");
                    startActivityForResult(intent, LANGUAGE_CHOSER_ACTIVITY);
                }
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

}
