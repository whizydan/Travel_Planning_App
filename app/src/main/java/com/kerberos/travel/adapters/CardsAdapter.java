package com.kerberos.travel.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kerberos.travel.R;
import com.kerberos.travel.models.CardModel;
import com.kerberos.travel.models.CouponsModel;
import com.kerberos.travel.pages.EditCardActivity;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
    private List<CardModel> mData; // List to hold data for the adapter
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    // Constructor to initialize the adapter with data
    public CardsAdapter(Context context, List<CardModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    // Method to inflate the layout file and create ViewHolder objects
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card, parent, false);
        return new ViewHolder(view);
    }

    // Method to bind data to ViewHolder objects
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardModel card = mData.get(position);
        holder.cardExpiry.setText("EXP: " + card.getMonth() + "/" + card.getYear());
        holder.cardNumber.setText(card.getCardNumber());

        holder.itemView.setOnLongClickListener(v ->{
            new MaterialAlertDialogBuilder(mContext)
                    .setTitle("Card: " + card.getCardNumber())
                    .setCancelable(false)
                    .setPositiveButton("Edit", (dialog, which) -> {
                        Intent intent = new Intent(mContext, EditCardActivity.class);
                        intent.putExtra("id",card.getId());
                        intent.putExtra("no",card.getCardNumber());
                        intent.putExtra("month",card.getMonth());
                        intent.putExtra("year",card.getYear());
                        intent.putExtra("cvc",card.getCvc());
                        intent.putExtra("name",card.getName());
                        mContext.startActivity(intent);
                    }).setNegativeButton("Delete", (dialog, which) ->{
                        FirebaseDatabase.getInstance().getReference().child("cards")
                                .child(card.getUserId()).child(card.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(mContext,"Card has been deleted",Toast.LENGTH_LONG).show();
                                    }
                                });
                    })
                    .show();
            return false;
        });
    }

    // Method to get the total number of items in the data set
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // ViewHolder class to hold views for each item in the RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cardNumber, cardExpiry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardExpiry = itemView.findViewById(R.id.expiryDate);
            cardNumber = itemView.findViewById(R.id.cardNumber);
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
