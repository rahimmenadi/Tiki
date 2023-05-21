package com.example.ardino_administration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private String img;
    private String name;
    private float review;
    private String first_wilaya;
    private String second_wilaya;
    private String price;
    private String duration_min;
    private String duration_max;
    private String cell_phone;
    private String telephone;
    private String email;
    private String bus_number;
    FirebaseStorage mstrogae;

    private  static final  int Gallery_code=1;
    Uri imageUrl=null;













    ImageButton btn_finish;
    Button btn_edit;
    Button btn_delete;

    EditText agence_name_profile_tv;
    EditText first_wilaya_profile_tv;
    EditText second_wilaya_profile_tv;
    EditText price_profile_tv;
    EditText max_duration_profile_tv;
    EditText min_duration_profile_tv;
    EditText bus_number_profile_tv;
    EditText phone_profile_tv;
    EditText telephone_profile_tv;
    EditText email_profile_tv;
    ImageView imageView;
    Agence agence ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

      agence = (Agence) getIntent().getSerializableExtra("agence1");

        agence_name_profile_tv = findViewById(R.id.EditProfileAct_agence_name);
        first_wilaya_profile_tv = findViewById(R.id.EditProfileAct_first_wilaya);
        second_wilaya_profile_tv = findViewById(R.id.EditProfileAct_second_wilaya);
        price_profile_tv = findViewById(R.id.EditProfileAct_price);
        max_duration_profile_tv = findViewById(R.id.EditProfileAct_maximal_duration);
        min_duration_profile_tv=findViewById(R.id.EditProfileAct_minimal_duration);
        bus_number_profile_tv=findViewById(R.id.EditProfileAct_bus_number);
        phone_profile_tv=findViewById(R.id.EditProfileAct_phone);
        telephone_profile_tv=findViewById(R.id.EditProfileAct_telephone);
        email_profile_tv= findViewById(R.id.EditProfileAct_email);
        imageView=findViewById(R.id.EditProfileAct_imageView2);


        agence_name_profile_tv.setText(agence.getName());
        first_wilaya_profile_tv.setText(agence.getFirst_wilaya());
        second_wilaya_profile_tv.setText(agence.getSecond_wilaya());
        price_profile_tv.setText(agence.getPrice()+"");
        max_duration_profile_tv.setText((agence.getDuration_max()));
        min_duration_profile_tv.setText(agence.getDuration_min());
        bus_number_profile_tv.setText(agence.getBus_number()+"");
        phone_profile_tv.setText(agence.getCell_phone());
        telephone_profile_tv.setText(agence.getTelephone());
        email_profile_tv.setText(agence.getEmail());
        Picasso.get().load(agence.getImg()).into(imageView);










        btn_edit=findViewById(R.id.EditProfileAct_button1) ;
    btn_finish = findViewById(R.id.EditProfileAct_btn_finish);
    imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,Gallery_code);

        }
    });




        btn_delete=findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle("Are you sure");
                builder.setMessage("delete agence can be cause problem");
                builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase mdatabase=FirebaseDatabase.getInstance();
                        DatabaseReference mref=mdatabase.getReference().child("agences");
                        DatabaseReference item=mref.child(agence.getId());

                  item.removeValue();

                    }
                });
                builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(EditProfileActivity.this, "canclled", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();

            }
        });




        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==Gallery_code)&&(resultCode==RESULT_OK)){
            imageUrl=data.getData();
            Picasso.get().load(imageUrl).into(imageView);
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    agence.setName(agence_name_profile_tv.getText().toString());
                    agence.setBus_number(bus_number_profile_tv.getText().toString());
                    agence.setPrice(price_profile_tv.getText().toString());
                    agence.setEmail(email_profile_tv.getText().toString());
                    agence.setCell_phone(phone_profile_tv.getText().toString());
                    agence.setDuration_min(min_duration_profile_tv.getText().toString());
                    agence.setDuration_max(max_duration_profile_tv.getText().toString());
                    agence.setTelephone(telephone_profile_tv.getText().toString());

                    mstrogae=FirebaseStorage.getInstance();

                    StorageReference filepath=mstrogae.getReference().child("agences images").child(imageUrl.getLastPathSegment());
                    filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl=taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t=task.getResult().toString();
                                    agence.setImg(t);
                                }
                            });


                        }
                    });
                    FirebaseDatabase mdatabase=FirebaseDatabase.getInstance();
                    DatabaseReference mref=mdatabase.getReference().child("agences");
                    DatabaseReference item=mref.child(agence.getId());

                    item.setValue(agence);



                }
            });



}}}