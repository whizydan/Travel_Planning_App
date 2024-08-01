package com.kerberos.travel.pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerberos.travel.R;
import com.kerberos.travel.models.TicketsModel;
import com.kerberos.travel.models.UserModel;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        MaterialButton save = findViewById(R.id.materialButton);
        TextInputLayout name = findViewById(R.id.name);
        TextInputLayout userName = findViewById(R.id.username);
        TextInputLayout phone = findViewById(R.id.phone);
        final UserModel[] userModel = new UserModel[1];
        ImageView back = findViewById(R.id.imageView39);

        back.setOnClickListener(v->{
            super.onBackPressed();
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    UserModel user = childSnapshot.getValue(UserModel.class);
                    if(childSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        name.getEditText().setText(user.getName());
                        userName.getEditText().setText(user.getUsername());
                        phone.getEditText().setText(user.getPhone());
                        userModel[0] = user;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, error.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(view -> {
            if(TextUtils.isEmpty(name.getEditText().getText())){
                name.setError("Enter name");
            }else if(TextUtils.isEmpty(userName.getEditText().getText())){
                userName.setError("Enter username");
            }else{
                userModel[0].setUsername(userName.getEditText().getText().toString());
                userModel[0].setName(name.getEditText().getText().toString());
                userModel[0].setPhone(phone.getEditText().getText().toString());
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userModel[0])
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ProfileActivity.this, "Profile update successful", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
