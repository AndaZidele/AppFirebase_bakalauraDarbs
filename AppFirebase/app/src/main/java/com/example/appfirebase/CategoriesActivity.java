package com.example.appfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

public class CategoriesActivity extends AppCompatActivity {

    MaterialButton toM, toW, toCh, toMyProfile, toMyCart;
    TextView toHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        String epa = PrefConfig.loadEpasts(this);

        toHome = (TextView) findViewById(R.id.toHomeFromCategories);
        toHome.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });


        toM = (MaterialButton) findViewById(R.id.toMen);
        toM.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
//            intent.putExtra("user_email",  user_email);
            intent.putExtra("categoryName",  "men");

            startActivity(intent);
        });
        toW = (MaterialButton) findViewById(R.id.toWomen);
        toW.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
//            intent.putExtra("user_email",  user_email);
            intent.putExtra("categoryName",  "women");
            startActivity(intent);
        });

        toCh = (MaterialButton) findViewById(R.id.toChildren);
        toCh.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
//            intent.putExtra("user_email",  user_email);

            intent.putExtra("categoryName",  "children");
            startActivity(intent);
        });


    }
}