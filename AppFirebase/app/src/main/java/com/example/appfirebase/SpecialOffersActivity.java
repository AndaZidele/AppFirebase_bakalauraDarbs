package com.example.appfirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.appfirebase.Adapters.Product;
import com.example.appfirebase.Adapters.ProductsAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SpecialOffersActivity extends AppCompatActivity {

    RecyclerView recycler_search;
    LinearLayoutManager layoutManager;
    //    ActivityProductsActivityBinding binding;
    ProductsAdapter adapter;
    TextView userEmail, toHome;
    FirebaseFirestore db;
    ArrayList<Product> productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_offers);
        recycler_search = (RecyclerView) findViewById(R.id.recycler_searchSp);

        toHome = (TextView) findViewById(R.id.spOffersToHome);
        toHome.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        layoutManager = new LinearLayoutManager(this);

        recycler_search.setHasFixedSize(true);
        recycler_search.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));
        recycler_search.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        productsList = new ArrayList<Product>();
        adapter = new ProductsAdapter(SpecialOffersActivity.this, productsList);
        recycler_search.setAdapter(adapter);
        getproducts();
    }

    private void getproducts(){

//        Intent category = getIntent();
//        String kategorija = category.getStringExtra("categoryName");

        db.collection("product")
                .whereEqualTo("special_offer", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Firestor Error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                productsList.add(dc.getDocument().toObject(Product.class));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}