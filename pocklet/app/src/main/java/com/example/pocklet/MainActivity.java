package com.example.pocklet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText Username;
    private EditText Password;
    private CardView Login;
    private int counter = 5;
    private TextView Message;
    private TextView Registration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = (EditText) findViewById(R.id.loginUsernameText);
        Password = (EditText) findViewById(R.id.loginPasswordText);
        Login = (CardView) findViewById(R.id.loginCard);
        Message = (TextView) findViewById(R.id.messageView);
        Registration = (TextView) findViewById(R.id.createView);

        Message.setText("");

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        if(user != null) {
            finish();
            //Change to home page of Pocklet
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Username.getText().toString(), Password.getText().toString());
            }
        });

        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void validate(String user_name, String user_password) {
        progressDialog.setMessage("Validating.");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(user_name, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()) {
                  progressDialog.dismiss();
                  Toast.makeText(MainActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                  startActivity(new Intent(MainActivity.this, HomeActivity.class));
              } else {
                  Toast.makeText(MainActivity.this, "Login Failed. Number of attempts remaining: " + String.valueOf(counter) + ".", Toast.LENGTH_SHORT).show();
                  counter--;
                  if(counter == 0)
                  {
                      Login.setEnabled(false);
                  }
              }
           }
       });
    }
}
