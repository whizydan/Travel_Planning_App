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
import com.google.android.material.button.MaterialButton;
import com.kerberos.travel.R;
import com.kerberos.travel.models.CouponsModel;
import com.kerberos.travel.models.TicketsModel;

import java.util.List;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.ViewHolder> {
    private List<TicketsModel> mData; // List to hold data for the adapter
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    // Constructor to initialize the adapter with data
    public TicketsAdapter(Context context, List<TicketsModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    // Method to inflate the layout file and create ViewHolder objects
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tickets, parent, false);
        return new ViewHolder(view);
    }

    // Method to bind data to ViewHolder objects
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TicketsModel attraction = mData.get(position);
        holder.from.setText(attraction.getFrom());
        holder.to.setText(attraction.getTo());
        holder.price.setText("RM " + attraction.getPrice());
        holder.arrival.setText(attraction.getArrival());
        holder.departure.setText(attraction.getDeparture());
        holder.date.setText(attraction.getDate());
        holder.date2.setText(attraction.getDate());
        holder.duration.setText(attraction.getDuration());
        Glide.with(mContext)
                .load(attraction.getAirline())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error_image)
                .into(holder.image);
    }

    // Method to get the total number of items in the data set
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // ViewHolder class to hold views for each item in the RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView from, to, price, arrival, departure, date, duration, date2;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.textView19);
            to = itemView.findViewById(R.id.textView31);
            price = itemView.findViewById(R.id.textView17);
            arrival = itemView.findViewById(R.id.textView32);
            departure = itemView.findViewById(R.id.textView21);
            date = itemView.findViewById(R.id.textView30);
            date2 = itemView.findViewById(R.id.textView33);
            duration = itemView.findViewById(R.id.textView34);
            image = itemView.findViewById(R.id.imageView11);
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
}
