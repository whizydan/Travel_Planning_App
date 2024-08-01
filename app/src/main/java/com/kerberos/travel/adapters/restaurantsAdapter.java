package com.kerberos.travel.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerberos.travel.R;
import com.kerberos.travel.models.RestaurantModel;
import com.kerberos.travel.models.TicketsModel;

import java.util.ArrayList;
import java.util.List;

public class restaurantsAdapter extends RecyclerView.Adapter<restaurantsAdapter.ViewHolder> {
    private List<RestaurantModel> mData; // List to hold data for the adapter
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    // Constructor to initialize the adapter with data
    public restaurantsAdapter(Context context, List<RestaurantModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    // Method to inflate the layout file and create ViewHolder objects
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.restaurants, parent, false);
        return new ViewHolder(view);
    }

    // Method to bind data to ViewHolder objects
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestaurantModel restaurant = mData.get(position);
        holder.name.setText(restaurant.getName());
        holder.location.setText(restaurant.getLocation());
        holder.ratingBar.setRating(restaurant.getStars());
        Glide.with(mContext)
                .load(restaurant.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error_image)
                .into(holder.image);


        holder.itemView.setOnClickListener(v->{
            showRestaurantInfo(restaurant);
        });
    }

    private void showRestaurantInfo(RestaurantModel restaurant) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        View bottomSheetView = LayoutInflater.from(mContext).inflate(R.layout.hotel_info, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        TextView hotelName = bottomSheetView.findViewById(R.id.hotel_name);
        TextView hotelTime = bottomSheetView.findViewById(R.id.time);
        TextView hotelLocation = bottomSheetView.findViewById(R.id.location);
        TextView hotelDescription = bottomSheetView.findViewById(R.id.description);
        TextView hotelContact = bottomSheetView.findViewById(R.id.contact);
        MaterialButton submit = bottomSheetView.findViewById(R.id.submit);
        TextInputLayout feedback = bottomSheetView.findViewById(R.id.feedback);
        RecyclerView recyclerView = bottomSheetView.findViewById(R.id.recyclerView);
        ArrayList<String> feedbacks = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("resfeed").child(restaurant.getId());

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                feedbacks.clear();
                                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                    String feedback = snapshot1.getValue(String.class);
                                    feedbacks.add(feedback);
                                }

                                if(feedbacks.isEmpty()){
                                    recyclerView.setVisibility(View.GONE);
                                }else{
                                    recyclerView.setAdapter(new RestaurantReviewsAdapter(mContext,feedbacks));
                                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(mContext,error.getDetails(),Toast.LENGTH_LONG).show();
                            }
                        });

        submit.setOnClickListener(v->{
            if(TextUtils.isEmpty(feedback.getEditText().getText())){
                feedback.setError("Enter feedback");
            }else{
                reference.child(String.valueOf(System.currentTimeMillis())).setValue(feedback.getEditText().getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(mContext,"Review has been added",Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        hotelName.setText(restaurant.getName());
        hotelTime.setText(restaurant.getTime());
        hotelLocation.setText(restaurant.getLocation());
        hotelDescription.setText(restaurant.getDescription());
        hotelContact.setText(restaurant.getContact());

        bottomSheetDialog.show();
    }

    // Method to get the total number of items in the data set
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // ViewHolder class to hold views for each item in the RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, location;
        RatingBar ratingBar;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            image = itemView.findViewById(R.id.image);
            //itemView.setOnClickListener(this);
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
