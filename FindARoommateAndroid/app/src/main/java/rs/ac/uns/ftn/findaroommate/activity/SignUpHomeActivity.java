package rs.ac.uns.ftn.findaroommate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import rs.ac.uns.ftn.findaroommate.R;

public class SignUpHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_home);

        Button signUpGoogleButton = (Button) findViewById(R.id.btn_gooogle_signup);
        signUpGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sign up with Google", Toast.LENGTH_LONG).show();

            }
        });

        Button signUpFbButton = (Button) findViewById(R.id.btn_fb_signup);
        signUpFbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sign up with Facebook", Toast.LENGTH_LONG).show();

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
}
