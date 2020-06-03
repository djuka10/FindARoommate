package rs.ac.uns.ftn.findaroommate.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenManager;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.User;

public class SignUpHomeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    static final int GOOGLE_SIGN = 123;
    Button signInGoogle;
    TextView text;
    ImageView image;
    ProgressDialog loadingBar;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseUser firebaseUser;
    GoogleSignInAccount account;
    Person person;

    //facebook -- NE RADI TRENUTNO
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_home);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        loadingBar = new ProgressDialog(this);

        Button signUpGoogleButton = (Button) findViewById(R.id.btn_gooogle_signup);
        signUpGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sign up with Google", Toast.LENGTH_LONG).show();
                SignInGoogle();
            }
        });


        /*loginButton = findViewById(R.id.fb_login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());
                Intent intent = new Intent(SignUpHomeActivity.this, HomepageActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });*/
        Button signUpFbButton = (Button) findViewById(R.id.btn_fb_signup);
        signUpFbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sign up with Facebook -- work in progress", Toast.LENGTH_LONG).show();

            }
        });

        Button signUpEmailButton = (Button) findViewById(R.id.btn_email_signup);
        signUpEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpHomeActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        Button signInButton = (Button) findViewById(R.id.btn_login_action);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpHomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        Log.d("TAG", "handleFacebookAccessToken:" + accessToken);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Toast.makeText(getApplicationContext(), "Success from handleFacebook", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "signInWithCredential:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(SignUpHomeActivity.this, HomepageActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                });
    }

    void SignInGoogle() {
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, GOOGLE_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account != null)
                    firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            // Pass the activity result back to the Facebook SDK
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    //Token za facebook --> Ne radi trenutno
    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken == null) {
                Toast.makeText(getApplicationContext(), "Access token is null", Toast.LENGTH_SHORT);
            }
        }
    };

    private void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "firebaseAuthWithGoogle " + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);

        loadingBar.setTitle("Sign in with Google account");
        loadingBar.setMessage("Please wait");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();

        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()) {
                //progressBar.setVisibility(View.INVISIBLE);
                List<User> listUsers = User.getOneByEmail(task.getResult().getUser().getEmail());
                String s = task.getResult().getUser().getDisplayName();
                if(listUsers.isEmpty())
                    saveNewUser(task.getResult().getUser().getEmail());
                Toast.makeText(getApplicationContext(),"Sign in with google success", Toast.LENGTH_SHORT);
                Intent intent = new Intent(SignUpHomeActivity.this, HomepageActivity.class);
                startActivity(intent);
                loadingBar.dismiss();
            } else {
                //progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Sign in fail", Toast.LENGTH_SHORT);
                loadingBar.dismiss();
            }
        });
    }

    //Preko google kad treba da se kreira u internoj bazi
    private void saveNewUser(String email) {
        firebaseUser = mAuth.getCurrentUser();
        User user = new User().builder()
                .email(email)
                .firstName("DUMMY")
                .lastName("DUMMIC")
                .build();
//TODO Pokupiti nekako first i last name sa google account-a
        user.save();
    }
}
