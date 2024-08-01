package com.kerberos.travel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kerberos.travel.R;
import com.kerberos.travel.models.HotelsModel;
import com.kerberos.travel.models.TicketsModel;
import com.kerberos.travel.models.TransportModel;

import java.util.ArrayList;
import java.util.List;

public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.ViewHolder> {
    private List<HotelsModel> mData; // List to hold data for the adapter
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private List<HotelsModel> filteredList;

    // Constructor to initialize the adapter with data
    public HotelsAdapter(Context context, List<HotelsModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
        this.filteredList = new ArrayList<>(data);
    }

    // Method to inflate the layout file and create ViewHolder objects
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.hotels, parent, false);
        return new ViewHolder(view);
    }

    // Method to bind data to ViewHolder objects
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HotelsModel attraction = filteredList.get(position);
        holder.name.setText(attraction.getName());
        holder.location.setText(attraction.getLocation());
        holder.price.setText("Rate per night per peron: " + attraction.getPrice() + " RM");
        Glide.with(mContext)
                .load(attraction.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error_image)
                .into(holder.image);
    }

    // Method to get the total number of items in the data set
    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    // ViewHolder class to hold views for each item in the RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, price, location;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            location = itemView.findViewById(R.id.location);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // Interface for click listener callback
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // Method to set click listener for items in the RecyclerView
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void filter(String location, String date, String rooms) {
        filteredList.clear();
        String searchLocation = location.toLowerCase().trim();
        String searchDate = date.toLowerCase().trim();
        String searchRooms = rooms.toLowerCase().trim();

        for (HotelsModel item : mData) {
            String itemLocation = item.getLocation().toLowerCase().trim();
            String itemDate = item.getDate().toLowerCase();
            String itemRooms = String.valueOf(item.getRooms());

            boolean fromLocation = itemLocation.contains(searchLocation);
            boolean dateMatch = itemDate.contains(searchDate);
            boolean roomsMatch = itemRooms.contains(searchRooms);

            if (fromLocation) {
                filteredList.add(item);
            }
        }

        notifyDataSetChanged();
    }
}
