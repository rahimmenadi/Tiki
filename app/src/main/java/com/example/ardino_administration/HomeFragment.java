package com.example.ardino_administration;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;


public class HomeFragment extends Fragment {
    TextInputEditText firstwilaya;
    TextInputEditText secondwilaya;

    AutoCompleteTextView from_list;
    AutoCompleteTextView to_list;
    Button btn_search;
    String[] destinations;
    AutoCompleteTextView ed_date;
    ShowDate showDate;
    ImageButton btn_switch;
    CardView cv_addAgence;
    Dialog dialog;
    int hour,minute;
    ExtendedFloatingActionButton btn_addTrip;
    DatabaseReference database;
    String price;
    String imageurl;
    final  String nmbrofseats="00000000000000000000000000000";



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        destinations = getResources().getStringArray(R.array.destinations);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.dropdowen_item, destinations);
        from_list.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.dropdowen_item, newTable());
        to_list.setAdapter(adapter2);
        from_list.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = to_list.getText().toString();
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.dropdowen_item, newTable());
                to_list.setAdapter(adapter2);
                if (s.equals(from_list.getText().toString())) to_list.setText("");
            }
        });
        to_list.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (to_list.getText().toString().equals("")) {
                    btn_search.setEnabled(false);

                } else {
                    btn_search.setEnabled(true);

                }
            }
        });

        btn_addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        to_list = v.findViewById(R.id.HomeFragment_to_spinir);
        from_list = v.findViewById(R.id.HomeFragment_from_spinir);
        btn_switch = v.findViewById(R.id.HomeFragment_btn_switch);
        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_switch();
            }
        });

        btn_search = v.findViewById(R.id.HomeFragment_btn_search);
        btn_addTrip = v.findViewById(R.id.HomeFragment_floatBtn_addTrip);



        ed_date = v.findViewById(R.id.HomeFragment_date_spinir);
        showDate = new ShowDate(ed_date, getActivity(), true);
        showDate.initdate();
        ed_date.setText(ShowDate.getTodayDate());
        ed_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate.datePickerDialog.show();
            }
        });
        ed_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) showDate.datePickerDialog.show();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity() , activity_search_result.class));
            }
        });

        cv_addAgence = v.findViewById(R.id.HomeFragment_layout_addAgence);

        cv_addAgence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity() , CreateAgenceActivity.class));
            }
        });

        return v;
    }

    public String[] newTable() {
        String s = from_list.getText().toString();
        String[] table = new String[destinations.length - 1];
        int j = 0;
        for (String destination : destinations) {
            if (!(destination.equals(s))) {
                table[j] = destination;
                j++;
            }
        }
        return table;
    }

    public void btn_switch() {
        String from_text = from_list.getText().toString();
        String to_text = to_list.getText().toString();
        if (!to_text.equals("")) {
            from_list.setText(to_text);
            to_list.setText(from_text);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.dropdowen_item, destinations);
            from_list.setAdapter(adapter);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.dropdowen_item, newTable());
            to_list.setAdapter(adapter2);
        }
    }


    public void showDialog(){


        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialogue_modified);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;


        Button save_btn = dialog.findViewById(R.id.btn_save);
        Button cancel_btn = dialog.findViewById(R.id.btn_cancel);
        Button btn_delete = dialog.findViewById(R.id.btn_delete);
        save_btn.setEnabled(false);



        btn_delete.setVisibility(View.GONE);
        database = FirebaseDatabase.getInstance().getReference("agences");
        AutoCompleteTextView dropDownText_depart = dialog.findViewById(R.id.dropdown_text_from);
        AutoCompleteTextView dropDownText_arrive = dialog.findViewById(R.id.dropdown_text_to);
        FirebaseDatabase mdatabase;
        DatabaseReference mref;
        TextView tv_time = dialog.findViewById(R.id.time_tv);
        ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(getActivity());









        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        //agence text input layout ***************


        //       TextInputLayout textInputLayout_agence = dialog.findViewById(R.id.text_input_layout_agence);

//        textInputLayout.setHelperText("hello there");
//        textInputLayout.setHelperTextEnabled(false);


            ArrayList<String> items = new ArrayList<>();
            ArrayList<Agence> items2= new ArrayList<>();
        ArrayAdapter<String> agence_adapter = new ArrayAdapter<>(getActivity(), R.layout.drop_down_item, items);
        database.addValueEventListener(new ValueEventListener() {
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





        AutoCompleteTextView dropDownText = dialog.findViewById(R.id.dropdown_text_agence);

        dropDownText.setAdapter(agence_adapter);
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
                        price=item.getPrice();
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



        //end input text ++++++++++++++++=





        //end input text 2 ================





        // end input text 3 ===============







        TextView tv_date = dialog.findViewById(R.id.date_tv);
        showDate = new ShowDate(tv_date , getActivity() , true);
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),onTimeSetListener,hour,minute,true);

                timePickerDialog.show();
            }

        });
        mdatabase=FirebaseDatabase.getInstance();
        mref=mdatabase.getReference().child("vols");

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String agence=dropDownText.getText().toString().trim();
                String depart=dropDownText_depart.getText().toString().trim();
                String arivé=dropDownText_arrive.getText().toString().trim();
                String leavetime=tv_time.getText().toString().trim();
                String leavedate=tv_date.getText().toString().trim();


                if (!(agence.isEmpty()&&depart.isEmpty()&&arivé.isEmpty()&&leavedate.isEmpty()&&leavetime.isEmpty())){
                    progressDialog.setTitle("PLEASE WAIT");
                    progressDialog.show();
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    },1500);

                    String id =mref.push().getKey();

                    mref.child(id).child("name").setValue(agence);
                    mref.child(id).child("from").setValue(depart);
                    mref.child(id).child("to").setValue(arivé);
                    mref.child(id).child("leaveTime").setValue(leavetime);
                    mref.child(id).child("date").setValue(leavedate);
                    mref.child(id).child("price").setValue((price));
                    mref.child(id).child(("image")).setValue(imageurl);
                    mref.child(id).child("seats").setValue(nmbrofseats);





                }
                dialog.dismiss();

            }

        });

        dialog.show();


    }

}