package com.kerberos.travel.pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerberos.travel.R;
import com.kerberos.travel.adapters.AttractionsAdapter;
import com.kerberos.travel.adapters.DealsAdapter;
import com.kerberos.travel.models.AttractionsModel;

import java.util.ArrayList;

public class AttractionsActivity extends AppCompatActivity{
    private ArrayList<AttractionsModel> attractionsArrayList;

    private  AttractionsAdapter.OnAttractionClickListener itemClickListener = new AttractionsAdapter.OnAttractionClickListener() {
        @Override
        public void onAttractionClick(AttractionsModel attraction) {
            Intent intent = new Intent(AttractionsActivity.this, AttractionDetailActivity.class);
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
        setContentView(R.layout.activity_attractions);
        ImageView back = findViewById(R.id.imageView12);
        RecyclerView attractions = findViewById(R.id.attractions);
        attractionsArrayList = new ArrayList<>();
        TextInputLayout search = findViewById(R.id.textInputLayout);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("attractions");
        final AttractionsAdapter[] adapter = new AttractionsAdapter[1];

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    AttractionsModel attraction = childSnapshot.getValue(AttractionsModel.class);
                    attractionsArrayList.add(attraction);
                }
                adapter[0] = new AttractionsAdapter(AttractionsActivity.this,attractionsArrayList);
                adapter[0].setOnAttractionClickListener(itemClickListener);
                attractions.setLayoutManager(new LinearLayoutManager(AttractionsActivity.this,LinearLayoutManager.VERTICAL,false));
                attractions.setAdapter(adapter[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AttractionsActivity.this,error.getDetails(),Toast.LENGTH_LONG).show();
            }
        });

        search.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter[0].filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        back.setOnClickListener(v->{
            super.onBackPressed();
            finish();
        });
    }
}