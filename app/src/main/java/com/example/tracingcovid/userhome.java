package com.example.tracingcovid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class userhome extends AppCompatActivity {
    Button add,submit,scan;
    LinearLayout li;
   public static EditText Cphone;
   EditText Cname,Caddress;
    TextView tv;
    DatabaseReference Customerref,shophistory;
    ProgressDialog loadingBar;
    String shopId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        add=findViewById(R.id.add);
        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null)
        {
            shopId= getIntent().getExtras().get("ShopId").toString();
        }
        submit=findViewById(R.id.submit);
        li=findViewById(R.id.info);
        tv=findViewById(R.id.desc);
        loadingBar = new ProgressDialog(this);
        Customerref= FirebaseDatabase.getInstance().getReference().child("Customer");
        shophistory = FirebaseDatabase.getInstance().getReference().child("ShopHistory");
        Cname=findViewById(R.id.Cname);
        Cphone=findViewById(R.id.Cphone);
        Caddress=findViewById(R.id.Caddress);
        li.setVisibility(View.GONE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                li.setVisibility(View.VISIBLE);
                add.setVisibility(View.GONE);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> customerMap = new HashMap<>();
                        customerMap.put("Id",Cphone.getText().toString());
                        customerMap.put("Name",Cname.getText().toString());
                        customerMap.put("Phone",Cphone.getText().toString());
                        customerMap.put("Address",Caddress.getText().toString());

                        Customerref.child(Cphone.getText().toString()).updateChildren(customerMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        { loadingBar.setTitle("Add New customer");
                                            loadingBar.setMessage("Dear Admin, please wait while we are adding the new Coustomer.");
                                            loadingBar.setCanceledOnTouchOutside(false);
                                            loadingBar.show();
                                            add.setVisibility(View.VISIBLE);
                                            loadingBar.dismiss();
                                            Toast.makeText(userhome.this,"Customer is added",Toast.LENGTH_LONG).show();
                                           getttingshop();

                                        }
                                    }
                                });
                    }
                });

            }
        });
        scan=findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2= new Intent(userhome.this,scan.class);
                startActivity(intent2);
                fetchingqrinfo();
            }
        });



    }

    public void fetchingqrinfo() {

        if(!Cphone.getText().toString().isEmpty())
        {
            Customerref.child(Cphone.getText().toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String cname=  dataSnapshot.child("Name").getValue().toString();
                    String caddress = dataSnapshot.child("Address").getValue().toString();
                   Cname.setText(cname);
                   Caddress.setText(caddress);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void getttingshop()
    {
        DatabaseReference Userref= FirebaseDatabase.getInstance().getReference().child("User");
        Userref.child(shopId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String Shopname=  dataSnapshot.child("name").getValue().toString();
                    String Shopaddress = dataSnapshot.child("address").getValue().toString();
                    String Shopphone = dataSnapshot.child("phone").getValue().toString();
                    HashMap<String,Object> shophistory1= new HashMap<>();
                    shophistory1.put("ShopName",Shopname);
                    shophistory1.put("Shopaddress",Shopaddress);
                    shophistory1.put("Shopphone",Shopphone);
                    shophistory.child(Cphone.getText().toString()).child(shopId).updateChildren(shophistory1);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
