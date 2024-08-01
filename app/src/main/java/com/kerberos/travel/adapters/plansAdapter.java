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
import com.kerberos.travel.models.CouponsModel;
import com.kerberos.travel.models.PlansModel;
import com.kerberos.travel.pages.AddPlanActivity;

import java.util.ArrayList;
import java.util.List;

public class plansAdapter extends RecyclerView.Adapter<plansAdapter.ViewHolder> {
    private List<PlansModel> mData; // List to hold data for the adapter
    private List<PlansModel> filteredList; // List to hold filtered data
    private LayoutInflater mInflater;
    private Context mContext;

    // Constructor to initialize the adapter with data
    public plansAdapter(Context context, List<PlansModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.filteredList = new ArrayList<>(data); // Initialize filteredList with mData
        this.mContext = context;
    }

    // Method to inflate the layout file and create ViewHolder objects
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.plans, parent, false);
        return new ViewHolder(view);
    }

    // Method to bind data to ViewHolder objects
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlansModel attraction = filteredList.get(position); // Use filteredList instead of mData
        holder.title.setText(attraction.getTitle());
        holder.date.setText(attraction.getDate());

        holder.itemView.setOnClickListener(v ->{
            Intent intent = new Intent(mContext, AddPlanActivity.class);
            intent.putExtra("id",attraction.getId());
            intent.putExtra("title",attraction.getTitle());
            intent.putExtra("desc",attraction.getDescription());
            mContext.startActivity(intent);
        });
    }

    // Method to get the total number of items in the data set
    @Override
    public int getItemCount() {
        return filteredList.size(); // Return size of filteredList
    }

    // ViewHolder class to hold views for each item in the RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);

        }

    }


    // Method to filter the data based on a search query
    public void filter(String query) {
        filteredList.clear();
        if (TextUtils.isEmpty(query)) {
            filteredList.addAll(mData); // If query is empty, show all data
        } else {
            String searchQuery = query.toLowerCase().trim();
            for (PlansModel item : mData) {
                if (item.getTitle().toLowerCase().contains(searchQuery)) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged(); // Notify adapter that data set has changed
    }
}
