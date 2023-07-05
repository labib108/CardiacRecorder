package com.example.cardiacrecorder;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    public static String PREFS_NAME="MyPrefsFile";
    private EditText ed1,ed2,ed3;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://cardiacrecorder-eb8d3-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed1=findViewById(R.id.username);
        ed2=findViewById(R.id.password);
        ed3=findViewById(R.id.Contact);
        TextView btn=findViewById(R.id.signup);
        TextView admin=findViewById(R.id.admin);
        Button login=findViewById(R.id.login);
        TextView btn2=findViewById(R.id.fgtpassword);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Passwordreset.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username=ed1.getText().toString().trim();
                String password=ed2.getText().toString().trim();
                String mobile=ed3.getText().toString().trim();
                if(username.isEmpty() || password.isEmpty() || mobile.isEmpty()) {
                    Toast.makeText(Login.this,"Please enter username,mobile and password ",Toast.LENGTH_SHORT).show();
                }
                else if(mobile.isEmpty()){
                    Toast.makeText(Login.this,"Please enter mobile number ",Toast.LENGTH_SHORT).show();
                }
                else if(mobile.length()<11){
                    Toast.makeText(Login.this,"Contact no should be 11 digit",Toast.LENGTH_SHORT).show();
                }

                else{
                    databaseReference.child("userinfo").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // check if mobile number is not registered
                            if(snapshot.hasChild(mobile)){
                                String getPassword=snapshot.child(mobile).child("password").getValue(String.class);
                                String getUsername=snapshot.child(mobile).child("username").getValue(String.class);

                                if(getPassword.equals(password) && getUsername.equals(username)){
                                    SharedPreferences sharedPreferences=getSharedPreferences(Login.PREFS_NAME,0);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    SharedPreferences sharedPreferences1=getSharedPreferences(PREFS_NAME,0);
                                    editor.putString("hasLoggedIn2",String.valueOf(mobile));
                                    editor.putBoolean("hasLoggedIn",true);
                                    editor.commit();
                                    String num=String.valueOf(mobile);
                                    Toast.makeText(Login.this, "Log in successful!!", Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(Login.this,TestLogin.class);
                                    intent.putExtra("var",num);
                                    startActivity(intent);
                                    finish();

                                }
                                else{
                                    Toast.makeText(Login.this, "Wrong password or username", Toast.LENGTH_SHORT).show();
                                }

                            }

                            else{
                                Toast.makeText(Login.this, "Please SignUp", Toast.LENGTH_SHORT).show();


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
                finish();

            }
        });
    }

}
