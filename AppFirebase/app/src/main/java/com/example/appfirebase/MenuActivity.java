package com.example.appfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MenuActivity extends AppCompatActivity {

    TextView toMainView;
    Button toAboutUs;
    MaterialButton toProducts, toSpecialOffers, toMyCart, toMyProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String epa = PrefConfig.loadEpasts(this);

        toMainView = (TextView) findViewById(R.id.toMainViewFromMenu);
        toMainView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        toAboutUs = (Button) findViewById(R.id.toAboutUsFromMenu);
        toAboutUs.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
            startActivity(intent);
        });

        toSpecialOffers = (MaterialButton) findViewById(R.id.toSpecialOffersFromMenu);
        toSpecialOffers.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SpecialOffersActivity.class);
            startActivity(intent);
        });

        toMyCart = (MaterialButton) findViewById(R.id.toMyCartFromMenu);
        toMyCart.setOnClickListener(view -> {
            if (epa.equals("Not Logged In") == true){
                Intent intent = new Intent(getApplicationContext(), HaventLoginActivity.class);
                startActivity(intent);
//                String nav = "To See Cart You Have To Login!";
//                Toast.makeText(MenuActivity.this, nav, Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });

        toMyProfile = (MaterialButton) findViewById(R.id.toMyProfileFromMenu);
        toMyProfile.setOnClickListener(view -> {
            if (epa.equals("Not Logged In") == true){
                Intent intent = new Intent(getApplicationContext(), HaventLoginActivity.class);
                startActivity(intent);
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        toProducts = (MaterialButton) findViewById(R.id.toProductsFromMenu);
        toProducts.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
//            intent.putExtra("user_email",  user_email);
            startActivity(intent);
        });
    }
}