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

public class Registration2Activity extends AppCompatActivity {

    private EditText firstName, lastName, registerEmail, registerPassword;
    private CardView registerButton;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();


        firebaseAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                {
                    //Upload data to the database
                    String user_email = registerEmail.getText().toString().trim();
                    String user_password = registerPassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(Registration2Activity.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registration2Activity.this, MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(Registration2Activity.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private void setupUIViews() {
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastNameText);
        registerEmail = (EditText) findViewById(R.id.registerEmailText);
        registerPassword = (EditText) findViewById(R.id.registerPasswordText);
        registerButton = (CardView) findViewById(R.id.nextCard);
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

//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }
}
