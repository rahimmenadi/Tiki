package com.example.ardino_administration;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class activity_search_result extends AppCompatActivity implements RecycleViewInterface {

    ImageButton btn_finish ;
    TextView filter_btn;
    TextView agencname;
    Dialog dialog;
    int hour,minute;
    String[] destinations;
    ShowDate showDateFilter;
    RecyclerView recyclerView;
    bus_recycleViewAdapter adapter;
    DatabaseReference database;
    RecyclerView.LayoutManager  layoutManager;

    AgenceAdapter agenceAdapter;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference reference=firebaseDatabase.getReference();
    DatabaseReference childreference=reference.child(
            "imageurl");
    CircleImageView img_agence;
    TextView image_url;
   DatabaseReference database1;
    String price;
    String imageurl ;
    busModel vol;



    ShowDate showDate;
    ArrayList<busModel> busModelis = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_result);
        btn_finish = findViewById(R.id.tripResultAct_btn_finish);
        recyclerView = findViewById(R.id.myrecycle);

        destinations = getResources().getStringArray(R.array.destinations);

        filter_btn=findViewById(R.id.tripResultAct_btn_filter);
        showDateFilter = new ShowDate(filter_btn , activity_search_result.this , true);
        showDateFilter.initdate();
        img_agence=findViewById(R.id.img_agence);
        agencname=findViewById(R.id.tv_agence_name);

        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateFilter.datePickerDialog.show();
            }
        });
        database = FirebaseDatabase.getInstance().getReference("vols");
        database1=FirebaseDatabase.getInstance().getReference("agences");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                busModelis.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    System.out.println(dataSnapshot.getValue().toString());
                    String a =dataSnapshot.getValue().toString();
                    busModel vol = new busModel();
                    vol.setName(dataSnapshot.child("name").getValue().toString());
                    vol.setId(dataSnapshot.getKey());
                    vol.setDate(dataSnapshot.child("date").getValue().toString());
                    vol.setLeaveTime(dataSnapshot.child("leaveTime").getValue().toString());
                    vol.setPrice(dataSnapshot.child("price").getValue().toString());
                    vol.setImage(dataSnapshot.child("image").getValue().toString());



















                    busModelis.add(vol);


                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });




        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        // Display Code Over Here Using Array List (busModelis)

        setAdapter();


    }




    public void setAdapter(){
        layoutManager = new LinearLayoutManager(this);

        database = FirebaseDatabase.getInstance().getReference("vols");

        adapter = new bus_recycleViewAdapter(this,busModelis,this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }







    @Override
    public void onitemClick(int position) {

        vol=busModelis.get(position);

        showDialog();
    }




    public void showDialog(){

        dialog = new Dialog(activity_search_result.this);
        dialog.setContentView(R.layout.custom_dialogue_modified);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        TextView tv_date = dialog.findViewById(R.id.date_tv);



        Button save_btn = dialog.findViewById(R.id.btn_save);
        Button cancel_btn = dialog.findViewById(R.id.btn_cancel);
        Button delete_btn = dialog.findViewById(R.id.btn_delete);
        AutoCompleteTextView dropDownText_depart = dialog.findViewById(R.id.dropdown_text_from);
        AutoCompleteTextView dropDownText_arrive = dialog.findViewById(R.id.dropdown_text_to);
        TextView tv_time = dialog.findViewById(R.id.time_tv);
        AutoCompleteTextView dropDownText = dialog.findViewById(R.id.dropdown_text_agence);
        save_btn.setEnabled(false);



        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }

        });


        save_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String agence=dropDownText.getText().toString().trim();
                String depart=dropDownText_depart.getText().toString().trim();
                String arivé=dropDownText_arrive.getText().toString().trim();
                String leavetime=tv_time.getText().toString().trim();
                String leavedate=tv_date.getText().toString().trim();
                vol.setPrice(price);
                vol.setLeaveTime(leavetime);
                vol.setFrom(depart);
                vol.setName(agence);
                vol.setTo(arivé);
                vol.setDate(leavedate);
                FirebaseDatabase mdatabase=FirebaseDatabase.getInstance();
                DatabaseReference mref=mdatabase.getReference().child("vols");
                DatabaseReference item=mref.child(vol.getId());

                item.setValue(vol);
                // SAVING CODE HERE
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(activity_search_result.this);
                builder.setTitle("Are you sure");
                builder.setMessage("delete vol can be cause problem");
                builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase mdatabase=FirebaseDatabase.getInstance();
                        DatabaseReference mref=mdatabase.getReference().child("vols");
                        DatabaseReference item=mref.child(vol.getId());

                        item.removeValue();

                    }
                });
                builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(activity_search_result.this, "canclled", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
                // DELETE CODE HERE
            }
        });








        ArrayList<String> items = new ArrayList<>();
        ArrayList<Agence> items2= new ArrayList<>();
        ArrayAdapter<String> agence_adapter = new ArrayAdapter<>(getBaseContext(), R.layout.drop_down_item, items);
        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                agence_adapter.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    System.out.println(dataSnapshot.getValue().toString());
                    String a =dataSnapshot.getValue().toString();
                    Agence agence = new Agence();
                    agence.setName(dataSnapshot.child("name").getValue().toString());
                    agence.setFirst_wilaya(dataSnapshot.child("first_wilaya").getValue().toString());

                    agence.setSecond_wilaya(dataSnapshot.child("second_wilaya").getValue().toString());
                    agence.setPrice(dataSnapshot.child(("price")).getValue().toString());

                    agence.setImg(dataSnapshot.child("img").getValue().toString());



                    // Agence agence = dataSnapshot.getValue(Agence.class);
                    items.add(agence.getName());
                    items2.add(agence);

                }

                agence_adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        dropDownText.setAdapter(agence_adapter);
        //end input text ++++++++++++++++=

        ArrayAdapter<String> depart_adapter = new ArrayAdapter<>(activity_search_result.this, R.layout.drop_down_item, destinations);


        dropDownText_depart.setAdapter(depart_adapter);
        dropDownText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String depart =null;
                String arrive=null;



                for (Agence item:items2) {
                    if (item.getName().equals(dropDownText.getText().toString())){
                        depart=item.getFirst_wilaya();
                        arrive=item.getSecond_wilaya();
                        price = item.getPrice();
                        imageurl=item.getImg();



                    }


                }
                dropDownText_depart.setText(depart);
                dropDownText_arrive.setText(arrive);

                if (dropDownText.getText().toString().isEmpty()||tv_time.getText().toString().equals("Leave time")){
                    save_btn.setEnabled(false);

                }
                else {
                    save_btn.setEnabled(true);
                }


            }


            @Override
            public void afterTextChanged(Editable s) {

            }


        });

        //end input text 2 ================




        // end input text 3 ===============








        showDate = new ShowDate(tv_date , this , true);
        showDate.initdate();
        tv_date.setText(ShowDate.getTodayDate());
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate.datePickerDialog.show();
            }
        });


        //time picker ++++++++++++++========================


        tv_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(tv_time.getText().toString().equals("Leave time")||dropDownText.getText().toString().isEmpty()){
                    save_btn.setEnabled(false);
                }
                else
                {
                    save_btn.setEnabled(true);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        hour = i;
                        minute = i1;
                        tv_time.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity_search_result.this,onTimeSetListener,hour,minute,true);

                timePickerDialog.show();
            }

        });

        dialog.show();



        //end dialog ******************************************************

    }
}