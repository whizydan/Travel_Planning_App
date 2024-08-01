package com.kerberos.travel.pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kerberos.travel.MainActivity;
import com.kerberos.travel.R;
import com.kerberos.travel.database.Database;
import com.kerberos.travel.models.UserModel;
import com.maxkeppeler.sheets.core.SheetStyle;
import com.maxkeppeler.sheets.info.InfoSheet;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MaterialButton register = findViewById(R.id.register);
        TextInputLayout name = findViewById(R.id.name);
        TextInputLayout email = findViewById(R.id.email);
        TextInputLayout username = findViewById(R.id.username);
        TextInputLayout password = findViewById(R.id.password);
        TextView signIn = findViewById(R.id.sign_in);
        TextView terms = findViewById(R.id.terms);
        TextInputLayout phone = findViewById(R.id.phone);

        terms.setOnClickListener(view ->{
            SpannableString conditions = new SpannableString(getResources().getText(R.string.terms));
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Terms & Conditions")
                    .setMessage(conditions)
                    .show();
        });


        register.setOnClickListener(view->{
            if(TextUtils.isEmpty(Objects.requireNonNull(name.getEditText()).getText())){
                name.setError("Enter name");
            }else if(TextUtils.isEmpty(Objects.requireNonNull(email.getEditText()).getText())){
                email.setError("Enter email");
            }else if(TextUtils.isEmpty(Objects.requireNonNull(username.getEditText()).getText())){
                username.setError("Enter username");
            }else if(TextUtils.isEmpty(Objects.requireNonNull(password.getEditText()).getText())){
                password.setError("Enter password");
            }else if(TextUtils.isEmpty(Objects.requireNonNull(phone.getEditText()).getText())){
                phone.setError("Enter phone");
            }else{
                name.setError("");
                email.setError("");
                username.setError("");
                password.setError("");
                phone.setError("");

                UserModel user = new UserModel();
                user.setName(name.getEditText().getText().toString());
                user.setEmail(email.getEditText().getText().toString());
                user.setUsername(username.getEditText().getText().toString());
                user.setPassword(password.getEditText().getText().toString());
                user.setPhone(phone.getEditText().getText().toString());

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("Could not register user",e.getMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        user.setId(mAuth.getCurrentUser().getUid());
                        Database db = new Database(RegisterActivity.this);

                        db.saveUser(user);
                    }
                });
            }
        });

        signIn.setOnClickListener(view->{
            super.onBackPressed();
            finish();
        });
    }
    public void showMessage(String title, String message){
        InfoSheet sheet = new InfoSheet();
        sheet.setWindowContext(this);
        sheet.title(title);
        sheet.content(message);
        sheet.style(SheetStyle.DIALOG);
        sheet.show();
    }
}