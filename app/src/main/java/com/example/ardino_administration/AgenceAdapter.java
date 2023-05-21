package com.example.ardino_administration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AgenceAdapter extends RecyclerView.Adapter<AgenceAdapter.AgenceHolder> {

    ArrayList<Agence> agences;
    Context context;
    private final AgenceOnItemClick agenceOnItemClick;

    public AgenceAdapter(ArrayList<Agence> agences, Context context , AgenceOnItemClick agenceOnItemClick) {
        this.agences = agences;
        this.context = context;
        this.agenceOnItemClick = agenceOnItemClick;
    }

    @NonNull
    @Override
    public AgenceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_agence_row , parent , false);
        return new AgenceHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AgenceHolder holder, int position) {

        Agence agence = agences.get(position);



        holder.tv_name.setText(agence.getName());
        Picasso.get().load(agence.getImg()).into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agenceOnItemClick.onItemClick(agence);
            }
        });
    }

    @Override
    public int getItemCount() {
        return agences.size();
    }

    static public class AgenceHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        CircleImageView imageView;


        public AgenceHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            imageView=(CircleImageView) itemView.findViewById(R.id.img_agence);


        }
    }


}
