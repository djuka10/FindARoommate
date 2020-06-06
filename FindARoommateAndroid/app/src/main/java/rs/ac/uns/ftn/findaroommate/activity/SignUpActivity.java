package rs.ac.uns.ftn.findaroommate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.EditProfileService;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText userMail, userPassword, userFirstName, userLastName;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userMail =  (EditText) findViewById(R.id.sign_up_email);
        userPassword = (EditText) findViewById(R.id.sign_up_password);
        userFirstName = (EditText) findViewById(R.id.sign_up_first_name);
        userLastName = (EditText) findViewById(R.id.sign_up_last_name);

        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        Button signUpButton = (Button) findViewById(R.id.btn_signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateNewAccount();
                /*Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);*/
            }
        });
    }

    private void CreateNewAccount() {

        final String email = userMail.getText().toString();
        final String password = userPassword.getText().toString();
        final String firstName = userFirstName.getText().toString();
        final String lastName = userLastName.getText().toString();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter the mail", Toast.LENGTH_SHORT);
        } else if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT);
        } else {

            loadingBar.setTitle("Creating new account");
            loadingBar.setMessage("Please wait");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()) {
                        User user = User.builder()
                                .firstName(firstName)
                                .lastName(lastName)
                                .email(email)
                                .activeSince(new Date())
                                .password(password).build();
                        user.save();
                        sendToServer(user);
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(SignUpActivity.this, "Account create successfully...", Toast.LENGTH_SHORT);
                        loadingBar.dismiss();
                    } else {
                        String message = task.getException().toString();
                        Toast.makeText(SignUpActivity.this, "Error: " + message, Toast.LENGTH_SHORT);
                        loadingBar.dismiss();
                    }


                }
            });
        }

    }

    private void sendToServer(User user){
        // send to the server
        Intent editProfileIntent = new Intent(this, EditProfileService.class);
        editProfileIntent.putExtra("userId", user.getId());
        startService(editProfileIntent);
    }
}
