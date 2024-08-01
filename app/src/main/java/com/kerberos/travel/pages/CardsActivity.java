package com.kerberos.travel.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerberos.travel.R;
import com.kerberos.travel.adapters.AttractionsAdapter;
import com.kerberos.travel.adapters.CardsAdapter;
import com.kerberos.travel.models.AttractionsModel;
import com.kerberos.travel.models.CardModel;

import java.util.ArrayList;

public class CardsActivity extends AppCompatActivity implements CardsAdapter.ItemClickListener{
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    private ArrayList<CardModel> cardsArrayList;
    FloatingActionButton fab;
    LinearLayout section;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
        ImageView back = findViewById(R.id.imageView13);
        MaterialButton add = findViewById(R.id.add);
        section = findViewById(R.id.section);
        recyclerView = findViewById(R.id.cards);
        fab = findViewById(R.id.fab);
        cardsArrayList = new ArrayList<>();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("cards").child(uid);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    CardModel card = childSnapshot.getValue(CardModel.class);
                    cardsArrayList.add(card);
                }

                if(!cardsArrayList.isEmpty()){
                    section.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(CardsActivity.this,LinearLayoutManager.VERTICAL,false));
                recyclerView.setAdapter(new CardsAdapter(CardsActivity.this,cardsArrayList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CardsActivity.this,error.getDetails(),Toast.LENGTH_LONG).show();
            }
        });

        add.setOnClickListener(v->{
            startActivity(new Intent(this,AddCardActivity.class));
        });
        fab.setOnClickListener(v->{
            startActivity(new Intent(this,AddCardActivity.class));
        });

        back.setOnClickListener(v->{
            super.onBackPressed();
            finish();
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        CardModel selectedItem = cardsArrayList.get(position);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cardsArrayList.clear();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    CardModel card = childSnapshot.getValue(CardModel.class);
                    cardsArrayList.add(card);
                }

                if (!cardsArrayList.isEmpty()) {
                    section.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(CardsActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(new CardsAdapter(CardsActivity.this, cardsArrayList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CardsActivity.this, error.getDetails(), Toast.LENGTH_LONG).show();
            }
        });
    }
}