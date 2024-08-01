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
import com.kerberos.travel.models.BookingsModel;
import com.kerberos.travel.models.TicketsModel;

import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {
    private List<BookingsModel> mData; // List to hold data for the adapter
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    // Constructor to initialize the adapter with data
    public BookingsAdapter(Context context, List<BookingsModel> data) {
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
        BookingsModel attraction = mData.get(position);
        holder.from.setText(attraction.getFrom());
        holder.to.setText(attraction.getTo());
        holder.price.setText("RM " + attraction.getPrice());
        holder.arrival.setText(attraction.getTicketType());
        holder.departure.setText(attraction.getTime());
        holder.date.setText(attraction.getDate());
        holder.date2.setText(attraction.getDate());
        holder.duration.setText(attraction.getPax());
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
