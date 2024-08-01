package com.kerberos.travel.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.kerberos.travel.R;
import com.kerberos.travel.models.AttractionsModel;
import com.kerberos.travel.pages.AttractionDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class AttractionsAdapter extends RecyclerView.Adapter<AttractionsAdapter.ViewHolder> {
    private List<AttractionsModel> mData; // List to hold data for the adapter
    private LayoutInflater mInflater;
    private Context mContext;
    private List<AttractionsModel> filteredList;
    private OnAttractionClickListener listener; // Interface variable

    // Constructor to initialize the adapter with data
    public AttractionsAdapter(Context context, List<AttractionsModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
        this.filteredList = new ArrayList<>(data);
    }

    // Interface for item click
    public interface OnAttractionClickListener {
        void onAttractionClick(AttractionsModel attraction);
    }

    // Method to inflate the layout file and create ViewHolder objects
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.attractions, parent, false);
        return new ViewHolder(view);
    }

    // Method to bind data to ViewHolder objects
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AttractionsModel attraction = filteredList.get(position);
        holder.title.setText(attraction.getName());
        holder.description.setText(attraction.getDescription());
        holder.price.setText("$ " + attraction.getPrice());
        Glide.with(mContext)
                .load(attraction.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error_image)
                .into(holder.image);

    }

    // Method to get the total number of items in the data set
    @Override
    public int getItemCount() {
        return filteredList.size(); // Return size of filteredList
    }

    // ViewHolder class to hold views for each item in the RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, description;
        MaterialButton price;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView24);
            description = itemView.findViewById(R.id.textView26);
            price = itemView.findViewById(R.id.materialButton2);
            image = itemView.findViewById(R.id.imageView15);

            itemView.setOnClickListener(this); // Set click listener on itemView
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                listener.onAttractionClick(filteredList.get(position)); // Notify interface
            }
        }
    }

    public void setOnAttractionClickListener(OnAttractionClickListener listener) {
        this.listener = listener;
    }

    public void filter(String query) {
        filteredList.clear();
        if (TextUtils.isEmpty(query)) {
            filteredList.addAll(mData); // If query is empty, show all data
        } else {
            String searchQuery = query.toLowerCase().trim();
            for (AttractionsModel item : mData) {
                if (item.getName().toLowerCase().contains(searchQuery)) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged(); // Notify adapter that data set has changed
    }
}
