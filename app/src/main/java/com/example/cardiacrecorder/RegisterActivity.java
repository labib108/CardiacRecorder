package com.example.cardiacrecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    TextView alreadyhaveanaccount;
    EditText username,email,phone,password,repassword;
    MaterialButton signupbtn;
    String emailPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    /**
     *
     * @param savedInstanceState
     */
    @SuppressLint("MissingInflatedId")
    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        alreadyhaveanaccount=findViewById(R.id.alreadyhaveanaccount);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        repassword=findViewById(R.id.repassword);
        signupbtn=findViewById(R.id.signupbtn);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        alreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                PerforAuth();
            }

            private void PerforAuth() {
                String mail=email.getText().toString();
                String uname=username.getText().toString();
                String phoneNo=phone.getText().toString();
                String passcode=password.getText().toString();
                String repasscode=repassword.getText().toString();
                if(!mail.matches(emailPattern))
                {
                    email.setError("Enter Correct Email");
                    email.requestFocus();
                }
                else if(passcode.isEmpty() || passcode.length()<6)
                {
                    password.setError("Enter Proper Password");
                }
                else if(!passcode.equals(repasscode))
                {
                    repassword.setError("Password doesn't match for both field");
                }
                else
                {
                    progressDialog.setMessage("Please Wait While Registration....");
                    progressDialog.setTitle("Registration");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.createUserWithEmailAndPassword(mail,passcode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
                                Map<String,String> map = new HashMap<>();
                                map.put("username",uname);
                                map.put("phone",phoneNo);
                                map.put("email",mail);

                                String uid = mAuth.getUid();
                                ref.child(uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    /**
                                     *
                                     * @param task
                                     */
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progressDialog.dismiss();
                                            sendUserToNextActivity();
                                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(RegisterActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        private void sendUserToNextActivity() {
                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}