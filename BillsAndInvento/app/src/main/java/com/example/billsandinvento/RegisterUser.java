package com.example.billsandinvento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterUser extends AppCompatActivity{
    EditText email,password;
    Button btnreg;
    String emailPattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    ProgressDialog progressDialog;

    FirebaseAuth nAuth;
    FirebaseUser newUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.new_user_email);
        password=findViewById(R.id.new_user_password);
        progressDialog= new ProgressDialog(this);
        btnreg =findViewById(R.id.btnregister);
        nAuth= FirebaseAuth.getInstance();
        newUser= nAuth.getCurrentUser();

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                performAuth();
            }
        });
    }

    private void performAuth() {
        String conformEmail=email.getText().toString();
        String conformPassword=password.getText().toString();
        if(!conformEmail.matches(emailPattern)){
            email.setError("Input Invalid");
        } else if (conformPassword.isEmpty() || conformPassword.length()<6) {
            password.setError("Invalid password");
        }else{
            progressDialog.setMessage("Loading.....Please Wait");
            progressDialog.setTitle("Register New User");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            nAuth.createUserWithEmailAndPassword(conformEmail,conformPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        nextUserActivity();
                        Toast.makeText(RegisterUser.this, "New Admin Registered", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(RegisterUser.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            }

        }

    private void nextUserActivity() {
        Intent intent = new Intent(RegisterUser.this, DashBoard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}