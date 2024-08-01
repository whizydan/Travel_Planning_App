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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerberos.travel.R;
import com.kerberos.travel.adapters.SavesAdapter;
import com.kerberos.travel.models.SavesModel;
import com.kerberos.travel.models.TicketsModel;

import java.util.ArrayList;

public class SavedActivity extends AppCompatActivity implements SavesAdapter.ItemClickListener{

    ArrayList<SavesModel> ticketsModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        ImageView back = findViewById(R.id.back);
        RecyclerView recyclerView = findViewById(R.id.savedItems);
        LinearLayout hide = findViewById(R.id.saved);
        ticketsModelArrayList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("saved");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    SavesModel ticket = childSnapshot.getValue(SavesModel.class);
                    ticketsModelArrayList.add(ticket);
                }

                if(!ticketsModelArrayList.isEmpty()){
                    hide.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                recyclerView.setAdapter(new SavesAdapter(SavedActivity.this,ticketsModelArrayList));
                recyclerView.setLayoutManager(new LinearLayoutManager(SavedActivity.this,LinearLayoutManager.VERTICAL,false));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SavedActivity.this, error.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(v->{
            super.onBackPressed();
            finish();
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        SavesModel attraction = ticketsModelArrayList.get(position);
        Intent intent = new Intent(this, AttractionDetailActivity.class);
        intent.putExtra("image",attraction.getImage());
        intent.putExtra("title",attraction.getName());
        intent.putExtra("description",attraction.getDescription());
        intent.putExtra("price",attraction.getPrice());
        intent.putExtra("destination",attraction.getDestination());
        intent.putExtra("id",attraction.getId());
        startActivity(intent);
    }
}