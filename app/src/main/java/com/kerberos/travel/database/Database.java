package com.kerberos.travel.database;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerberos.travel.MainActivity;
import com.kerberos.travel.models.UserModel;
import com.kerberos.travel.pages.LoginActivity;
import com.kerberos.travel.pages.OtpActivity;
import com.kerberos.travel.pages.RegisterActivity;
import com.maxkeppeler.sheets.core.SheetStyle;
import com.maxkeppeler.sheets.info.InfoSheet;

import java.util.Objects;

public class Database {

    private final Activity activity;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    public Database(Activity activity){
        this.activity = activity;
    }

    public UserModel getUserById(){
        String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        final UserModel[] user = new UserModel[1];
        reference.child("users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user[0] = snapshot.getValue(UserModel.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showMessage("Error getting user details", error.getDetails());
            }
        });
        return user[0];
    }

    public void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showMessage("Failed to login",e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                activity.startActivity(new Intent(activity, OtpActivity.class));
                activity.finish();
            }
        });
    }

    public void resetPassword(String email){
        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                showMessage("Success","An email with instructions on how to reset your password \n has been sent to " +
                        email);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showMessage("Failed to send an email", e.getMessage());
            }
        });
    }

    public void saveUser(UserModel user){
        reference.child("users").child(user.getId()).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        activity.startActivity(new Intent(activity, MainActivity.class));
                        activity.finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("Could not save user data", e.getMessage());
                    }
                });
    }

    public void showMessage(String title, String message){
        InfoSheet sheet = new InfoSheet();
        sheet.setWindowContext(activity);
        sheet.title(title);
        sheet.content(message);
        sheet.style(SheetStyle.DIALOG);
        sheet.show();
    }
}
