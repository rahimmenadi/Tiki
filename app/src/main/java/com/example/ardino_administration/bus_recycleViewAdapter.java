package com.example.ardino_administration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class bus_recycleViewAdapter extends RecyclerView.Adapter<bus_recycleViewAdapter.MyViewHolder> {
    private final RecycleViewInterface recycleViewInterface;
    Context context;
    ArrayList<busModel> busModelis;

    public bus_recycleViewAdapter(Context context , ArrayList<busModel> busModelis , RecycleViewInterface recycleViewInterface){
        this.busModelis=busModelis;
        this.context=context;
        this.recycleViewInterface = recycleViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_raw,parent,false);
        return new MyViewHolder(view,recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        busModel vols= busModelis.get(position);
        holder.tvDuration.setText(busModelis.get(position).getDuration());
        holder.name.setText(busModelis.get(position).getName());

        holder.tvPrice.setText(busModelis.get(position).getPrice());
        holder.tvSeat.setText(busModelis.get(position).getSeatsRemaining());
        holder.tvDate.setText(busModelis.get(position).getDate());
        holder.tvLeave.setText(busModelis.get(position).getLeaveTime());


        Picasso.get().load(vols.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return busModelis.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvDuration,tvLeave,tvDate,tvSeat,tvPrice,name;
        Button this_btn;

        public MyViewHolder(@NonNull View itemView,RecycleViewInterface recycleViewInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.agence_image);
            tvDuration =itemView.findViewById(R.id.tv_duration);
            tvLeave = itemView.findViewById(R.id.tv_leavetime);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvSeat = itemView.findViewById(R.id.tv_seats);
            tvPrice = itemView.findViewById(R.id.tv_price);
            this_btn = itemView.findViewById(R.id.btn_this);
           name=itemView.findViewById(R.id.tv_agence_name);


            this_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recycleViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recycleViewInterface.onitemClick(pos);
                        }
                    }
                }
            });

        }
    }

}
