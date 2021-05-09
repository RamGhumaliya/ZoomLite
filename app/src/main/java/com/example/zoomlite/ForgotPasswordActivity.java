package com.example.zoomlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    TextView textViewCancel;
    EditText editTextEmail;
    Button btnEmail;
    FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        mAuth= FirebaseAuth.getInstance();
        textViewCancel=findViewById(R.id.textViewCancel);
        editTextEmail=findViewById(R.id.editTextEmail);
        btnEmail=findViewById(R.id.btnEmail);
        mLoadingBar=new ProgressDialog(this);

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=editTextEmail.getText().toString();
                if(email.isEmpty() || !email.matches(emailPattern))
                {
                    Toast.makeText(ForgotPasswordActivity.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                    editTextEmail.requestFocus();
                }
                else
                {
                    mLoadingBar.setTitle("Sending Email");
                    mLoadingBar.setMessage("Please Wait...");
                    mLoadingBar.setCanceledOnTouchOutside(false);
                    mLoadingBar.show();
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                mLoadingBar.dismiss();
                                Toast.makeText(ForgotPasswordActivity.this, "Please Check Email", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                mLoadingBar.dismiss();
                                Toast.makeText(ForgotPasswordActivity.this, "Email Not Found Please Enter Correct Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}