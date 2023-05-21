package com.example.ardino_administration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContentInfo;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import com.google.android.material.textfield.TextInputEditText;


public class CreateAgenceActivity extends AppCompatActivity {

    Agence obj ;
    Button btn_to ;
    private  static final  int Gallery_code=1;
    ImageButton btn_finish;
    TextInputEditText agence_name_ft;
    TextInputEditText first_wilaya_ft;
    TextInputEditText second_wilaya_ft;
    TextInputEditText price_ft;
    TextInputEditText minimal_duration_ft;
    TextInputEditText maximal_duration_ft;
    TextInputEditText cellphone_ft;
    TextInputEditText telephone_ft;
    TextInputEditText email_ft;
    TextInputEditText bus_number_ft;
    Button chose_image_btn;
    Button btn_save;
    FirebaseDatabase mdatabase;
    DatabaseReference mref;
    FirebaseStorage mstrogae;
    Uri imageUrl=null;
    ImageView img_agence;
    ProgressDialog progressDialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_agence);


        // btn_to= findViewById(R.id.button1);

        chose_image_btn = findViewById(R.id.button_chose_image);
        btn_save = findViewById(R.id.save_btn);
        agence_name_ft = findViewById(R.id.ft_agence_name);
        first_wilaya_ft = findViewById(R.id.ft_first_wilaya);
        second_wilaya_ft = findViewById(R.id.ft_second_wilaya);
        price_ft =findViewById(R.id.ft_price);
        minimal_duration_ft = findViewById(R.id.ft_minimal_duration);
        maximal_duration_ft = findViewById(R.id.ft_maximal_duration);
        cellphone_ft = findViewById(R.id.ft_phone);
        telephone_ft = findViewById(R.id.ft_telephone);
        email_ft = findViewById(R.id.ft_email);
        bus_number_ft = findViewById(R.id.ft_bus_number);
        btn_finish = findViewById(R.id.createAct_btn_finish);
        img_agence = findViewById(R.id.agence_img);
        progressDialog=new ProgressDialog(this);




        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mdatabase=FirebaseDatabase.getInstance();
        mref=mdatabase.getReference().child("agences");
        mstrogae=FirebaseStorage.getInstance();
        chose_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_code);

            }


        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==Gallery_code)&&(resultCode==RESULT_OK)){
            imageUrl=data.getData();
            Picasso.get().load(imageUrl).into(img_agence);
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name=agence_name_ft.getText().toString().trim();


                    String first_wilaya=first_wilaya_ft.getText().toString().trim();
                    String second_wilaya=second_wilaya_ft.getText().toString().trim();
                    String price= price_ft.getText().toString().trim();
                    String duration_min=minimal_duration_ft.getText().toString().trim();
                    String duration_max=maximal_duration_ft.getText().toString().trim();
                    String cell_phone=cellphone_ft.getText().toString().trim();
                    String telephone=telephone_ft.getText().toString().trim();
                    String email=email_ft.getText().toString().trim();
                    String bus_number=bus_number_ft.getText().toString().trim() ;
                    if (!(name.isEmpty()&& first_wilaya.isEmpty()&&second_wilaya.isEmpty()&&price.isEmpty()&&duration_max.isEmpty()&&duration_min.isEmpty()&&cell_phone.isEmpty()&&telephone.isEmpty()&&email.isEmpty()&&bus_number.isEmpty()&&imageUrl==null)){


                        progressDialog.setTitle("UPLOADING...");
                        progressDialog.show();
                        StorageReference filepath=mstrogae.getReference().child("agences images").child(imageUrl.getLastPathSegment());
                        filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> downloadUrl=taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        String t=task.getResult().toString();
                                    String id =mref.push().getKey();



                                        mref.child(id).child("name").setValue(name);
                                        mref.child(id).child("first_wilaya").setValue(first_wilaya);
                                        mref.child(id).child("second_wilaya").setValue(second_wilaya);
                                        mref.child(id).child("price").setValue(price);
                                        mref.child(id).child("duration_max").setValue(duration_max);
                                        mref.child(id).child("duration_min").setValue(duration_min);
                                        mref.child(id).child("cell_phone").setValue(cell_phone);
                                        mref.child(id).child("telephone").setValue(telephone);
                                        mref.child(id).child("email").setValue(email);
                                        mref.child(id).child("bus_number").setValue(bus_number);
                                        mref.child(id).child("img").setValue(task.getResult().toString());
                                        System.out.println("washhh");


                                        progressDialog.dismiss();


                                    }
                                });
                            }
                        });


                    }

                }
            });

        }












    }
}