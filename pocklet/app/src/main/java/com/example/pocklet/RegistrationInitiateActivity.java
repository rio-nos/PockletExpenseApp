package com.example.pocklet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class RegistrationInitiateActivity extends AppCompatActivity {

    private EditText registerEmail, registerPassword;
    private CardView nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registerEmail = (EditText) findViewById(R.id.registerEmailText);
        registerPassword = (EditText) findViewById(R.id.registerPasswordText);
        nextButton = (CardView) findViewById(R.id.nextCard);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                {
                    Intent intent = new Intent(RegistrationInitiateActivity.this, RegistrationCompleteActivity.class);
                    intent.putExtra("email", registerEmail.getText().toString());
                    intent.putExtra("password", registerPassword.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }


    private Boolean validate() {

        Boolean result = false;
        String email = registerEmail.getText().toString();
        String pass = registerPassword.getText().toString();

        if (email.isEmpty() || pass.isEmpty())
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
