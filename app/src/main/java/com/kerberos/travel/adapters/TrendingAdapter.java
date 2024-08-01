package com.kerberos.travel.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kerberos.travel.R;
import com.kerberos.travel.models.TicketsModel;
import com.kerberos.travel.models.TrendingModel;
import com.kerberos.travel.pages.DestinationActivity;

import java.util.ArrayList;
import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder> {
    private List<TrendingModel> mData; // List to hold data for the adapter
    private List<TrendingModel> filteredList; // Filtered list for search results
    private LayoutInflater mInflater;
    private Context mContext;

    // Constructor to initialize the adapter with data
    public TrendingAdapter(Context context, List<TrendingModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.filteredList = new ArrayList<>(data); // Initialize filtered list with all items
        this.mContext = context;
    }

    // Method to inflate the layout file and create ViewHolder objects
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.trending, parent, false);
        return new ViewHolder(view);
    }

    // Method to bind data to ViewHolder objects
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrendingModel attraction = filteredList.get(position); // Use filtered list for display
        holder.title.setText(attraction.getTitle());
        holder.image.setImageResource(attraction.getImage());

        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(mContext, DestinationActivity.class);
            intent.putExtra("d", attraction.getD());
            mContext.startActivity(intent);
        });
    }

    // Method to get the total number of items in the data set
    @Override
    public int getItemCount() {
        return filteredList.size(); // Use filtered list size
    }

    // ViewHolder class to hold views for each item in the RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
        }
    }

    // Filter method to update the filtered list based on the search query
    public void filterList(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(mData); // If the query is empty, show all items
        } else {
            String filterPattern = query.toLowerCase().trim();
            for (TrendingModel item : mData) {
                if (item.getTitle().toLowerCase().contains(filterPattern)) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged(); // Notify RecyclerView of data change
    }
}
