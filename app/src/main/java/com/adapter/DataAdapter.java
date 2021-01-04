package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.api_call_struct_retrofit.R;
import com.model.Details;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Context context;

    private ArrayList<Details> data;

    public DataAdapter(Context context, ArrayList<Details> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.tvFirstName.setText(data.get(i).getFirst_name());
        holder.tvLastName.setText(data.get(i).getLast_name());
        holder.id.setText(data.get(i).getId());
        holder.email.setText(data.get(i).getEmail());
        String imgUrl = data.get(i).getAvatar();
        Glide.with(context)
                .load(imgUrl)
                .thumbnail(0.5f)
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFirstName, tvLastName, id, email;
        public ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.first_name);
            tvLastName = itemView.findViewById(R.id.last_name);
            id = itemView.findViewById(R.id.id);
            email = itemView.findViewById(R.id.email);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }
}
