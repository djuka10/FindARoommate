package rs.ac.uns.ftn.findaroommate.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.EditProfileService;
import rs.ac.uns.ftn.findaroommate.service.SyncService;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

import static java.util.Objects.nonNull;

public class SignUpHomeActivity extends AppCompatActivity {

    static final int GOOGLE_SIGN = 123;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //List of providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        if(!isUserAuthenticated()) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setLogo(R.mipmap.ic_logo_round)
                            .setTheme(R.style.AppTheme)
                            .build(),
                    GOOGLE_SIGN);
        } else {
            Intent intent = new Intent(SignUpHomeActivity.this, HomepageActivity.class);
            startActivity(intent);
        }

    }

    private boolean isUserAuthenticated() {
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        boolean isUserAuthenticated = nonNull(firebaseAuth) && nonNull(firebaseAuth.getCurrentUser());
        return isUserAuthenticated;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_SIGN && resultCode == RESULT_OK) {
            saveUser();
            startSync();
            Intent intent = new Intent(SignUpHomeActivity.this, HomepageActivity.class);
            startActivity(intent);
        }
    }


    //Preko google kad treba da se kreira u internoj bazi
    private void saveUser() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String[] credentials;
        String firstName = "";
        String lastName = "";
        if(firebaseUser.getDisplayName() != null){
           credentials = firebaseUser.getDisplayName().split(" ");
           firstName = credentials[0];
           lastName = credentials[1];
        }

        String profileUrl = "";
        if(firebaseUser.getPhotoUrl() != null) {
            profileUrl = firebaseUser.getPhotoUrl().toString();
        }
        List<User> users = User.getOneByEmail(firebaseUser.getEmail());
        User user = null;
        if(!users.isEmpty())
            user = users.get(0);

        if(user == null) {
            User user2 = new User().builder()
                    .email(firebaseUser.getEmail())
                    .firstName(firstName)
                    .lastName(lastName)
                    .activeSince(new Date())
                    .build();
            if(!profileUrl.isEmpty()){
                user2.setUrlProfile(profileUrl);
            }
            user2.save();
            sendToServer(user2);
        } else {

            if (user.getUrlProfile() == null){
                if(!profileUrl.isEmpty()){
                    user.setUrlProfile(profileUrl);
                }
            }
//            user.setFirstName(user.getFirstName());
//            user.setLastName(user.getLastName());
//            user.setActiveSince(user.getActiveSince());
            user.save();
            sendToServer(user);
        }
    }

    private void sendToServer(User user){
        // send to the server
        Intent editProfileIntent = new Intent(this, EditProfileService.class);
        editProfileIntent.putExtra("userId", user.getId());
        startService(editProfileIntent);
    }

    private void startSync(){
        Intent intent = new Intent(this, SyncService.class);
        startService(intent);
    }

}
