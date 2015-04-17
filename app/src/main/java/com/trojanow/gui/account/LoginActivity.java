package com.trojanow.gui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.trojanow.R;

import java.net.HttpURLConnection;

public class LoginActivity extends ActionBarActivity {

    private Button buttonSignIn;
    private Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(v);
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(v);
            }
        });
    }

    public void signIn(View v) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivityForResult(intent, 0);
    }

    public void signUp(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == HttpURLConnection.HTTP_OK) {
            finish();
        }
    }
}
