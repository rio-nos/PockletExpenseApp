package com.example.pocklet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText firstName, lastName, registerEmail, registerPassword;
    private CardView nextButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (validate())
                {
                    startActivity(new Intent(RegistrationActivity.this, Registration2Activity.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }

    private void setupUIViews() {
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastNameText);
        registerEmail = (EditText) findViewById(R.id.registerEmailText);
        registerPassword = (EditText) findViewById(R.id.registerPasswordText);
        nextButton = (CardView) findViewById(R.id.nextCard);
    }

    private Boolean validate() {

        Boolean result = false;
        String name = firstName.getText().toString();
        String last = lastName.getText().toString();
        String email = registerEmail.getText().toString();
        String pass = registerPassword.getText().toString();

        if (name.isEmpty() || last.isEmpty() || email.isEmpty() || pass.isEmpty())
        {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else
        {
            result = true;
        }
        return result;
    }
}
