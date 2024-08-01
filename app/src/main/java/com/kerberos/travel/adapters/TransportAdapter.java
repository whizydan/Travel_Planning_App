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
import com.google.firebase.auth.FirebaseAuth;
import com.kerberos.travel.R;
import com.kerberos.travel.models.BookingsModel;
import com.kerberos.travel.models.CouponsModel;
import com.kerberos.travel.models.TicketsModel;
import com.kerberos.travel.models.TransportModel;
import com.kerberos.travel.pages.PaymentActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TransportAdapter extends RecyclerView.Adapter<TransportAdapter.ViewHolder> {
    private List<TransportModel> mData; // List to hold data for the adapter
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private List<TransportModel> filteredList;

    // Constructor to initialize the adapter with data
    public TransportAdapter(Context context, List<TransportModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
        this.filteredList = new ArrayList<>(data);
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
        TransportModel attraction = filteredList.get(position);;
        holder.from.setText(attraction.getFrom());
        holder.to.setText(attraction.getTo());
        holder.price.setText("RM " + attraction.getPrice());
        if(attraction.isMultiCity()){
            holder.duration.setText("has stopover");
        }else{
            holder.duration.setText("Direct");
        }
        if(attraction.getType().equals("bus")){
            holder.vehicle.setImageResource(R.drawable.bus);
        } else if (attraction.getType().equals("car")) {
            holder.vehicle.setImageResource(R.drawable.car);
        } else if (attraction.getType().equals("train")) {
            holder.vehicle.setImageResource(R.drawable.train);
        }else if (attraction.getType().equals("flight")) {
            holder.vehicle.setImageResource(R.drawable.flight);
        }else{
            holder.vehicle.setImageResource(R.drawable.boat);
        }
        holder.arrival.setText("");
        holder.departure.setText("");
        holder.date.setText("");
        holder.date2.setText("");


    }

    // Method to get the total number of items in the data set
    @Override
    public int getItemCount() {
        return filteredList.size(); // Return size of filteredList
    }

    // ViewHolder class to hold views for each item in the RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView from, to, price, arrival, departure, date, duration, date2;
        ImageView image, vehicle;

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
            vehicle = itemView.findViewById(R.id.imageView17);
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

    public void filter(String fromQuery, String toQuery, String dateQuery, String personsQuery, String multiCityQuery) {
        filteredList.clear();
        String searchFrom = fromQuery.toLowerCase().trim();
        String searchTo = toQuery.toLowerCase().trim();
        String searchPersons = personsQuery.toLowerCase().trim();
        boolean searchMultiCity = Boolean.parseBoolean(multiCityQuery);

        for (TransportModel item : mData) {
            String itemFrom = item.getFrom().toLowerCase().trim();
            String itemTo = item.getTo().toLowerCase().trim();
            String itemPersons = String.valueOf(item.getPax()).toLowerCase(); // Assuming getPax() returns int

            boolean itemMultiCity = item.isMultiCity();

            boolean fromMatch = itemFrom.contains(searchFrom);
            boolean toMatch = itemTo.contains(searchTo);
            boolean personsMatch = itemPersons.contains(searchPersons);
            boolean multiCityMatch = itemMultiCity == searchMultiCity;

            if (fromMatch && toMatch) {
                filteredList.add(item);
            }
        }

        notifyDataSetChanged();
    }

    public void filterCar(String fromQuery, String toQuery, String dateQuery, String personsQuery, String multiCityQuery) {
        filteredList.clear();
        String searchFrom = fromQuery.toLowerCase().trim();
        String searchTo = toQuery.toLowerCase().trim();
        String searchPersons = personsQuery.toLowerCase().trim();
        boolean searchMultiCity = Boolean.parseBoolean(multiCityQuery);

        for (TransportModel item : mData) {
            String itemFrom = item.getFrom().toLowerCase().trim();
            String itemTo = item.getTo().toLowerCase().trim();
            String itemPersons = String.valueOf(item.getPax()).toLowerCase(); // Assuming getPax() returns int

            boolean itemMultiCity = item.isMultiCity();

            boolean fromMatch = itemFrom.contains(searchFrom);
            boolean toMatch = itemTo.contains(searchTo);
            boolean personsMatch = itemPersons.contains(searchPersons);
            boolean multiCityMatch = itemMultiCity == searchMultiCity;

            if (fromMatch && toMatch) {
                filteredList.add(item);
            }
        }

        notifyDataSetChanged();
    }

    public void filterBySeats(String pax) {
        filteredList.clear();
        String searchText = pax.toLowerCase().trim();

        for (TransportModel item : mData) {
            String itemPax = String.valueOf(item.getPax()).toLowerCase();

            boolean fromPax = itemPax.contains(searchText);

            if (fromPax) {
                filteredList.add(item);
            }
        }

        notifyDataSetChanged();
    }
}
