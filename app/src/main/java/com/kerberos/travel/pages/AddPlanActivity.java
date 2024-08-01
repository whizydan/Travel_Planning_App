package com.kerberos.travel.pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kerberos.travel.R;
import com.kerberos.travel.adapters.plansAdapter;
import com.kerberos.travel.models.PlansModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddPlanActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        ImageView back = findViewById(R.id.imageView38);
        TextInputLayout title = findViewById(R.id.title);
        TextInputLayout description = findViewById(R.id.description);
        MaterialButton save = findViewById(R.id.save);
        String idValue = getIntent().getStringExtra("id");
        String titleValue = getIntent().getStringExtra("title");
        String descriptionValue = getIntent().getStringExtra("desc");

        back.setOnClickListener(view ->{
            super.onBackPressed();
        });

        if(idValue != null){
            title.getEditText().setText(titleValue);
            description.getEditText().setText(descriptionValue);
        }

        save.setOnClickListener(view ->{
            if(TextUtils.isEmpty(title.getEditText().getText().toString())){
                title.setError("Title is required");
            }else if(TextUtils.isEmpty(description.getEditText().getText().toString())){
                description.setError("Description is required");
            }else{
                Date currentDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);

                PlansModel plan = new PlansModel();
                plan.setTitle(title.getEditText().getText().toString());
                plan.setDescription(description.getEditText().getText().toString());
                plan.setDate(sdf.format(currentDate));
                plan.setId(String.valueOf(System.currentTimeMillis()));
                if(idValue != null){
                    plan.setId(idValue);
                }

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("plans");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child(plan.getId())
                        .setValue(plan).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                AddPlanActivity.super.onBackPressed();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddPlanActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}