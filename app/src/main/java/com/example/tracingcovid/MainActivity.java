package com.example.tracingcovid;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
      private Button signup,signin,Detail;
    private FirebaseAuth mAuth;
    private DatabaseReference user;
    private EditText nameInput,phoneInput,passwordInput,addressInput,emailInput;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signin=findViewById(R.id.sigin);
        signup= findViewById(R.id.signup);
        Detail= findViewById(R.id.datail);
        nameInput= findViewById(R.id.name);
        phoneInput=findViewById(R.id.phone);
        user=FirebaseDatabase.getInstance().getReference().child("User");
        passwordInput=findViewById(R.id.password);
        emailInput=findViewById(R.id.Email);
        addressInput=findViewById(R.id.address);
        loadingBar = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,userlogin.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSeller();
            }
        });
        Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,customerDetail.class);
                startActivity(intent);
            }
        });
    }
    public void registerSeller()
    {

        final String name= nameInput.getText().toString();
        final String phone= phoneInput.getText().toString();
        final String email= emailInput.getText().toString();
        String password= passwordInput.getText().toString();
        final String address= addressInput.getText().toString();
        if(!name.equals("")&& !phone.equals("")&& !email.equals("")&& !password.equals("")&& !address.equals(""))
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {


                                final String sid = mAuth.getCurrentUser().getUid();
                                HashMap<String, Object> sellermap= new HashMap<>();
                                sellermap.put("id",sid);
                                sellermap.put("phone",phone);
                                sellermap.put("email",email);
                                sellermap.put("address",address);
                                sellermap.put("name",name);
                                user.child(sid).updateChildren(sellermap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                loadingBar.dismiss();
                                                Toast.makeText(MainActivity.this,"You are registered",Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(MainActivity.this, userhome.class);
                                                intent.putExtra("ShopId",sid);
                                                intent.addFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                                startActivity(intent);
                                                finish();
                                            }
                                        });


                            }
                        }
                    });

        }
        else
        {
            Toast.makeText(MainActivity.this,"All fields are mandatory ",Toast.LENGTH_LONG).show();
        }
    }
}
