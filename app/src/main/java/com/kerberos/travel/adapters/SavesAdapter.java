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
import com.kerberos.travel.models.AttractionsModel;
import com.kerberos.travel.models.SavesModel;

import java.util.List;

public class SavesAdapter extends RecyclerView.Adapter<SavesAdapter.ViewHolder> {
    private List<SavesModel> mData; // List to hold data for the adapter
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    // Constructor to initialize the adapter with data
    public SavesAdapter(Context context, List<SavesModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
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
        SavesModel attraction = mData.get(position);
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
        return mData.size();
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
