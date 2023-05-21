package com.example.ardino_administration;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class AgenceFragment extends Fragment implements  AgenceOnItemClick {

    ArrayList<Agence> agences = new ArrayList();
    RecyclerView recyclerView;
    AgenceAdapter AgenceAdapter;
    RecyclerView.LayoutManager  layoutManager;
    ExtendedFloatingActionButton btn_addAgence;
    TextInputEditText et_agenceName , et_destination;

    DatabaseReference database;

    AgenceAdapter agenceAdapter;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference reference=firebaseDatabase.getReference();
    DatabaseReference childreference=reference.child("imageurl");
    CircleImageView img_agence;
    TextView image_url;
    Button btn_delete;


    public AgenceFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_agence, container, false);


        recyclerView = v.findViewById(R.id.AgenceFragment_rv);
        img_agence=(CircleImageView) v.findViewById(R.id.img_agence);



        setAdapter();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {  agences.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){


                    System.out.println(dataSnapshot.getValue().toString());
                    String a =dataSnapshot.getValue().toString();
                    Agence agence = new Agence();
//                    agence.setId(dataSnapshot.getKey());
//                    agence.setName(dataSnapshot.child("agence name").getValue().toString());
//
//                    agence.setImg(dataSnapshot.child("imageurl").getValue().toString());
//                    agence.setBus_number(dataSnapshot.child("bus number").getValue().toString());
//
//                  agence.setDuration_min(dataSnapshot.child("duration min").getValue().toString());
//
//                    agence.setTelephone(dataSnapshot.child("cell phone").getValue().toString());
//
//
//                    agence.setPrice(dataSnapshot.child("price").getValue().toString());
//                    agence.setDuration_max(dataSnapshot.child("duration max").getValue().toString());
//                   agence.setFirst_wilaya(dataSnapshot.child("first wilaya").getValue().toString());
//                    agence.setSecond_wilaya(dataSnapshot.child("second wilaya").getValue().toString());
//                    agence.setEmail(dataSnapshot.child("email").getValue().toString());


agence= dataSnapshot.getValue(Agence.class);
agence.setId(dataSnapshot.getKey());
















                    // Agence agence = dataSnapshot.getValue(Agence.class);
                    agences.add(agence);

                }
                agenceAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        btn_delete=v.findViewById(R.id.btn_delete);



        btn_addAgence = v.findViewById(R.id.AgenceFragment_btn_addAgence);
        et_agenceName = v.findViewById(R.id.AgenceFragment_et_agenceName);
        et_destination = v.findViewById(R.id.AgenceFragment_et_destination);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > dx +10 && btn_addAgence.isExtended()){
                    btn_addAgence.shrink();
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(et_agenceName.getWindowToken(), 0);
                    mgr.hideSoftInputFromWindow(et_destination.getWindowToken(), 0);
                }
                if (dy < dx - 10 && !btn_addAgence.isExtended()){
                    btn_addAgence.extend();
                }
            }
        });
  ;




        btn_addAgence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity() , CreateAgenceActivity.class));
            }
        });




        return v;
    }



    private void setAdapter() {
        layoutManager = new LinearLayoutManager(getActivity());

        database = FirebaseDatabase.getInstance().getReference("agences");

        agenceAdapter = new AgenceAdapter(agences, getActivity(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(agenceAdapter);
    }


    @Override
    public void onItemClick(Agence agence) {
        Intent intent = new Intent(getActivity() ,ActivityAgenceProfile.class);
        intent.putExtra("agence" ,agence);
        startActivity(intent);

    }








}
