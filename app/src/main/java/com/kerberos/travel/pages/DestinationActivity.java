package com.kerberos.travel.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerberos.travel.R;
import com.kerberos.travel.adapters.AttractionsAdapter;
import com.kerberos.travel.adapters.CardsAdapter;
import com.kerberos.travel.fragments.HomeFragment;
import com.kerberos.travel.models.AttractionsModel;
import com.kerberos.travel.models.SavesModel;
import com.maxkeppeler.sheets.info.InfoSheet;

import java.util.ArrayList;
import java.util.Objects;

public class DestinationActivity extends AppCompatActivity{
    ArrayList<AttractionsModel> attractionsList;
    private  AttractionsAdapter.OnAttractionClickListener itemClickListener = new AttractionsAdapter.OnAttractionClickListener() {
        @Override
        public void onAttractionClick(AttractionsModel attraction) {
            Intent intent = new Intent(DestinationActivity.this, AttractionDetailActivity.class);
            intent.putExtra("image", attraction.getImage());
            intent.putExtra("title", attraction.getName());
            intent.putExtra("description", attraction.getDescription());
            intent.putExtra("price", attraction.getPrice());
            intent.putExtra("destination", attraction.getDestination());
            intent.putExtra("id", attraction.getId());
            startActivity(intent);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_destination);
        TextView description = findViewById(R.id.textView12);
        ImageView imageView = findViewById(R.id.imageView5);
        TextView title = findViewById(R.id.textView10);
        RecyclerView attractions = findViewById(R.id.attractions);
        ExtendedFloatingActionButton buyTicket = findViewById(R.id.buyTicket);
        attractionsList = new ArrayList<>();
        ImageView back = findViewById(R.id.imageView37);

        back.setOnClickListener(v ->{
            super.onBackPressed();
        });

        buyTicket.setOnClickListener(v->{
            startActivity(new Intent(this, TicketsActivity.class));
        });

        String destination = getIntent().getStringExtra("d");
        String destName = "bali";


        if(destination.equals("0")){
            //pulau
            description.setText("Pulau Redang, also known as Redang Island, is a popular tropical island located off the east " +
                    "coast of Malaysia in the state of Terengganu. It is part of the protected Terengganu Marine " +
                    "Park and is renowned for its crystal-clear waters, white sandy beaches, and vibrant marine life, " +
                    "making it a sought-after destination for snorkeling, diving, and beach vacations.");
            title.setText("Pulau Redang, Malaysia");
            imageView.setImageResource(R.drawable.pulau_thumbnail);
            destName = "pulau";
        }else if(destination.equals("1")){
            //phuket
            description.setText("\n" +
                    "Phuket is Thailand's largest island, renowned for its stunning beaches, " +
                    "clear waters, and vibrant tourism scene. It offers diverse activities like water sports, " +
                    "boat tours to nearby islands, and vibrant nightlife. " +
                    "The island also boasts a rich cultural heritage with colorful festivals, " +
                    "preserved architecture in Phuket City's Old Town, and a wide range of culinary experiences. " +
                    "Tourism is a major economic driver, supported by well-developed infrastructure and transportation " +
                    "options for visitors. Overall, Phuket is a top destination for beach enthusiasts, " +
                    "adventure seekers, and those interested in Thai culture.");
            title.setText("Phuket, Thailand");
            imageView.setImageResource(R.drawable.phuket_thumbnail);
            destName = "phuket";
        }else{
            //bali
            description.setText("a province and island located in the westernmost part of the Lesser Sunda Islands. It is one of the most popular tourist destinations in Southeast Asia, " +
                    "known for its stunning beaches, rich culture, vibrant arts scene, and lush landscapes");
            imageView.setImageResource(R.drawable.bali_humbnail);
        }
        final AttractionsAdapter[] attractionsAdapter = new AttractionsAdapter[1];
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("attractions");
        String finalDestName = destName;
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    AttractionsModel attractionsModel = childSnapshot.getValue(AttractionsModel.class);
                    if(finalDestName.equals(attractionsModel.getDestination())){
                        attractionsList.add(attractionsModel);
                    }
                }
                attractionsAdapter[0] = new AttractionsAdapter(DestinationActivity.this,attractionsList);
                attractions.setLayoutManager(new LinearLayoutManager(DestinationActivity.this,LinearLayoutManager.VERTICAL,false));
                attractionsAdapter[0].setOnAttractionClickListener(itemClickListener);
                attractions.setAdapter(attractionsAdapter[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                InfoSheet sheet = new InfoSheet();
                sheet.setWindowContext(DestinationActivity.this);
                sheet.title("Error getting attractions");
                sheet.content(error.getDetails());
                sheet.show();
            }
        });
    }
}