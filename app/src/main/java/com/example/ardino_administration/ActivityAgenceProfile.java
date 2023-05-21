package com.example.ardino_administration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ActivityAgenceProfile extends AppCompatActivity {

    TextView agence_name_profile_tv;
    TextView first_wilaya_profile_tv;
    TextView second_wilaya_profile_tv;
    TextView price_profile_tv;
    TextView duration_profile_tv;
    TextView bus_number_profile_tv;
    TextView phone_profile_tv;
    TextView telephone_profile_tv;
    TextView email_profile_tv;
    ImageButton btn_finish , btn_edit;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_agence_profile);

        agence_name_profile_tv = findViewById(R.id.tv_pf_agence_name);
        first_wilaya_profile_tv = findViewById(R.id.tv_profile_first_wilaya);
        second_wilaya_profile_tv = findViewById(R.id.tv_profile_second_wilaya);
        price_profile_tv = findViewById(R.id.tv_profile_price);
        duration_profile_tv = findViewById(R.id.tv_profile_duration);
        bus_number_profile_tv = findViewById(R.id.tv_profile_bus_number);
        phone_profile_tv = findViewById(R.id.tv_profile_phone);
        telephone_profile_tv = findViewById(R.id.tv_profile_telephone);
        email_profile_tv = findViewById(R.id.tv_profile_email);
        btn_finish = findViewById(R.id.profileAct_btn_finish);
        btn_edit = findViewById(R.id.profileAct_btn_edit);
        imageView=findViewById(R.id.agence_img);


        Agence agence = (Agence) getIntent().getSerializableExtra("agence");



       // Agence agence = getIntent().getParcelableExtra("agence");
        agence_name_profile_tv.setText(agence.getName());
        first_wilaya_profile_tv.setText(agence.getFirst_wilaya());
        second_wilaya_profile_tv.setText(agence.getSecond_wilaya());
        price_profile_tv.setText(agence.getPrice()+"");
        duration_profile_tv.setText(agence.getDuration_min()+"/"+agence.getDuration_max());
        bus_number_profile_tv.setText(agence.getBus_number()+"");
        phone_profile_tv.setText(agence.getCell_phone());
        telephone_profile_tv.setText(agence.getTelephone());
        email_profile_tv.setText(agence.getEmail());
        Picasso.get().load(agence.getImg()).into(imageView);


        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(),EditProfileActivity.class);
                intent.putExtra("agence1" ,agence);

                startActivity(intent);



            }
        });



    }
}