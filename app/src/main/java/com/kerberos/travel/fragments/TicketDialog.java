package com.kerberos.travel.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.WriterException;
import com.kerberos.travel.R;
import com.kerberos.travel.utils.QRCodeGenerator;

public class TicketDialog extends Dialog {
    private String priceValue, dateValue, typeValue;
    public TicketDialog(@NonNull Context context, String price, String date, String type) {
        super(context);
        this.priceValue = price;
        this.dateValue = date;
        this.typeValue = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_view);

        getWindow().getDecorView().setBackground(null);

        TextView email = findViewById(R.id.email);
        TextView price = findViewById(R.id.price);
        TextView date = findViewById(R.id.date);
        TextView type = findViewById(R.id.type);
        ImageView imageView = findViewById(R.id.imageView);

        String data = "{'price':'"+priceValue+"','date':'"+dateValue+"','type':'"+typeValue+"'}";
        int width = 500;
        int height = 500;

        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
        try {
            Bitmap qrCodeBitmap = qrCodeGenerator.generateQRCode(data, width, height);
            imageView.setImageBitmap(qrCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        price.setText(priceValue);
        date.setText(dateValue);
        type.setText(typeValue);
    }
}
