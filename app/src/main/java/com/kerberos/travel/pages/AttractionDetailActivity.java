package com.kerberos.travel.pages;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerberos.travel.R;
import com.kerberos.travel.adapters.AttractionsAdapter;
import com.kerberos.travel.adapters.ReviewsAdapter;
import com.kerberos.travel.models.BookingsModel;
import com.kerberos.travel.models.ReviewsModel;
import com.kerberos.travel.models.SavesModel;
import com.kerberos.travel.models.TicketsModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AttractionDetailActivity extends AppCompatActivity{
    int price;
    TextInputLayout datePicker;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);
        ImageView image = findViewById(R.id.imageView5);
        TextView title = findViewById(R.id.textView10);
        TextView description = findViewById(R.id.textView12);
        ExtendedFloatingActionButton book = findViewById(R.id.button2);
        ExtendedFloatingActionButton addReview = findViewById(R.id.addReview);
        FloatingActionButton floatingActionButton = findViewById(R.id.save);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final ReviewsAdapter[] adapter = new ReviewsAdapter[1];
        ImageView back = findViewById(R.id.back);

        back.setOnClickListener(view->{
            super.onBackPressed();
        });

        String imageUrl = getIntent().getStringExtra("image");
        String name = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("description");
        price = getIntent().getIntExtra("price",0);
        String dest = getIntent().getStringExtra("destination");
        String id = getIntent().getStringExtra("id");

        SavesModel savesModel = new SavesModel();
        savesModel.setDescription(desc);
        savesModel.setName(name);
        savesModel.setImage(imageUrl);
        savesModel.setId(id);
        savesModel.setPrice(price);
        savesModel.setDestination(dest);

        addReview.setOnClickListener(view->{
            showSheet(id);
        });

        book.setOnClickListener(v->{
            BookingsModel bookingsModel = new BookingsModel();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = dateFormat.format(calendar.getTime());
            bookingsModel.setDate(formattedDate);
            bookingsModel.setFrom("");
            bookingsModel.setTo(savesModel.getDestination());
            bookingsModel.setName(savesModel.getName());
            bookingsModel.setPax("");
            bookingsModel.setPrice(String.valueOf(savesModel.getPrice()));
            bookingsModel.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            bookingsModel.setTicketType("Attraction");
            bookingsModel.setTime("");

            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra("date",bookingsModel.getDate());
            intent.putExtra("from",bookingsModel.getFrom());
            intent.putExtra("to",bookingsModel.getTo());
            intent.putExtra("name",bookingsModel.getName());
            intent.putExtra("userId",bookingsModel.getUserId());
            intent.putExtra("type",bookingsModel.getTicketType());
            intent.putExtra("time",bookingsModel.getTime());

            getDetails(intent);
        });

        DatabaseReference recent = FirebaseDatabase.getInstance().getReference().child("recent");
        recent.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(savesModel.getId())
                        .setValue(savesModel);

        description.setText(desc);
        title.setText(name);
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error_image)
                .into(image);

        floatingActionButton.setOnClickListener(v->{
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("saved");
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(savesModel.getId()).setValue(savesModel)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AttractionDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AttractionDetailActivity.this, "Attraction saved", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        reference = FirebaseDatabase.getInstance().getReference().child("rev").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ReviewsModel> reviews = new ArrayList<>();

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ReviewsModel review = childSnapshot.getValue(ReviewsModel.class);
                    if(review.getAttractionId().equals(id)){
                        reviews.add(review);
                    }
                }
                adapter[0] = new ReviewsAdapter(AttractionDetailActivity.this,reviews);
                recyclerView.setLayoutManager(new LinearLayoutManager(AttractionDetailActivity.this,LinearLayoutManager.VERTICAL,false));
                recyclerView.setAdapter(adapter[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AttractionDetailActivity.this,error.getDetails(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void showSheet(String id){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.add_review, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        MaterialButton submit = bottomSheetView.findViewById(R.id.submit);
        TextInputLayout title = bottomSheetView.findViewById(R.id.title);
        AppCompatRatingBar review = bottomSheetView.findViewById(R.id.review);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate = new Date();

        submit.setOnClickListener(v -> {
            if(TextUtils.isEmpty(title.getEditText().getText().toString())){
                title.setError("Title required");
            }else if(TextUtils.isEmpty(String.valueOf(review.getRating()))){
                Toast.makeText(AttractionDetailActivity.this,"enter rating", Toast.LENGTH_LONG).show();
            }else{
                ReviewsModel reviewsModel = new ReviewsModel();
                reviewsModel.setId(String.valueOf(System.currentTimeMillis()));
                reviewsModel.setAttractionId(id);
                reviewsModel.setDate(dateFormat.format(currentDate));
                reviewsModel.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                reviewsModel.setTitle(title.getEditText().getText().toString());
                reviewsModel.setMessage(String.valueOf(review.getRating()));
                saveReview(bottomSheetDialog, reviewsModel);
            }
        });

        bottomSheetDialog.show();
    }

    private void saveReview(BottomSheetDialog bottomSheetDialog, ReviewsModel review){
        DatabaseReference rev = FirebaseDatabase.getInstance().getReference().child("rev");
        rev.child(review.getAttractionId()).child(review.getId()).setValue(review).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AttractionDetailActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AttractionDetailActivity.this,"Review submitted",Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void getDetails(Intent intent){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.get_details, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        final int[] totalprice = {price};

        TextInputLayout pax = bottomSheetView.findViewById(R.id.textInputLayout4);
        datePicker = bottomSheetView.findViewById(R.id.textInputLayout6);
        TextView total = bottomSheetView.findViewById(R.id.textInputLayout7);
        MaterialButton proceed = bottomSheetView.findViewById(R.id.done);
        total.setText(totalprice[0] + " RM");

        datePicker.setEndIconOnClickListener(view->{
            showDatePicker();
        });

        pax.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    totalprice[0] = Integer.parseInt(s.toString()) * price;
                    total.setText(totalprice[0] + " RM");
                }catch(Exception ignored){}

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        proceed.setOnClickListener(view->{
            if(TextUtils.isEmpty(pax.getEditText().getText().toString())){
                pax.setError("At least 1 traveller should book");
            } else if (TextUtils.isEmpty(datePicker.getEditText().getText().toString())) {
                datePicker.setError("Date is required");
            }else{
                intent.putExtra("date",datePicker.getEditText().getText().toString());
                intent.putExtra("price",totalprice[0]);
                intent.putExtra("pax",Integer.parseInt(pax.getEditText().getText().toString()));
                startActivity(intent);
            }
        });

        bottomSheetDialog.show();
    }
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Handle date selection here
                        String selectedDate = dayOfMonth + "-" + monthOfYear + "-" + year;
                        Objects.requireNonNull(datePicker.getEditText()).setText(selectedDate);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }
}