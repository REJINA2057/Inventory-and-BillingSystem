package com.example.billsandinvento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText adminEmail,adminPassword;
    Button btnlogin;
    String emailPattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    ProgressDialog progressDialog;

    FirebaseAuth nAuth;
    FirebaseUser newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.grey)));
        getSupportActionBar().hide();
        adminEmail=findViewById(R.id.email);
        adminPassword=findViewById(R.id.password);
        progressDialog= new ProgressDialog(this);
        btnlogin =findViewById(R.id.btnlogin);
        nAuth= FirebaseAuth.getInstance();
        newUser= nAuth.getCurrentUser();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String conformEmail=adminEmail.getText().toString();
        String conformPassword=adminPassword.getText().toString();
        if(!conformEmail.matches(emailPattern)){
            adminEmail.setError("Input Invalid");
        } else if (conformPassword.isEmpty() || conformPassword.length()<6) {
            adminPassword.setError("Invalid password");
        }else{
            progressDialog.setMessage("Loading.....Please Wait");
            progressDialog.setTitle("Admin Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            nAuth.signInWithEmailAndPassword(conformEmail,conformPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        nextUserActivity();
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
    private void nextUserActivity() {
        Intent intent = new Intent(Login.this,DashBoard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}