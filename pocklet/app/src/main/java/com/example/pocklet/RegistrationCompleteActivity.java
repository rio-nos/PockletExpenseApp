package com.example.pocklet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationCompleteActivity extends AppCompatActivity {

    private EditText firstName, lastName;
    private CardView registerButton;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        firebaseAuth = FirebaseAuth.getInstance();

        firstName = (EditText) findViewById(R.id.firstNameText);
        lastName = (EditText) findViewById(R.id.lastNameText);
        registerButton = (CardView) findViewById(R.id.registerCard);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                {
                    //Upload data to the database
                    Intent intent = getIntent();
                    String user_email = intent.getStringExtra("email").trim();
                    String user_password = intent.getStringExtra("password").trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(RegistrationCompleteActivity.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationCompleteActivity.this, HomeActivity.class));
                            }
                            else
                            {
                                Toast.makeText(RegistrationCompleteActivity.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private Boolean validate() {

        Boolean result = false;
        String name = firstName.getText().toString();
        String last = lastName.getText().toString();

        if (name.isEmpty() || last.isEmpty())
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
