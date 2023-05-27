package com.example.appfirebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfirebase.Adapters.Cart;
import com.example.appfirebase.Adapters.CartsAdapter;
import com.example.appfirebase.Adapters.Order;
import com.example.appfirebase.Adapters.OrdersAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    RecyclerView recycler_search;
    LinearLayoutManager layoutManager;
    OrdersAdapter adapter;
    FirebaseFirestore db;

    MaterialButton logOut, settings;
    TextView toHome;
    ArrayList<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toHome = (TextView) findViewById(R.id.toHomeFromProfile);
        toHome.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        settings = (MaterialButton) findViewById(R.id.btnSettings);
        settings.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        });

        logOut = (MaterialButton) findViewById(R.id.btnLogout);
        logOut.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            PrefConfig.saveUserEmail(getApplicationContext(), "Not Logged In");
            startActivity(intent);
        });

//        recycler_search = (RecyclerView) findViewById(R.id.profile_recycle);
//        layoutManager = new LinearLayoutManager(this);
//        recycler_search.setLayoutManager(layoutManager);
//        recycler_search.setHasFixedSize(true);
//        recycler_search.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));
//
//        db = FirebaseFirestore.getInstance();
//
//
        recycler_search = (RecyclerView) findViewById(R.id.profile_recycle);
        layoutManager = new LinearLayoutManager(this);
        recycler_search.setHasFixedSize(true);
        recycler_search.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));

        recycler_search.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        orderList = new ArrayList<Order>();
//        adapter = new OrdersAdapter(CartActivity.this, orderList);
        adapter = new OrdersAdapter(orderList);

        recycler_search.setAdapter(adapter);


        getUserId();
    }

    private void getUserId(){
        String epa = PrefConfig.loadEpasts(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                if (epa.equals(String.valueOf(document.get("email")))){
                                    //pareiza parole
                                    int userId =  Integer.parseInt(String.valueOf(document.get("id")));

                                    getAllCart(userId);
                                   // break;
                                } else {
                                    //ievadiet pareizu paroli
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void getAllCart(int thisUserId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        orderList = new ArrayList<Order>();
        ArrayList<Order> orderArrayList = new ArrayList<Order>();

        db.collection("order")
//                .whereEqualTo("user", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                orderList.add(document.toObject(Order.class));

                            }
                            Iterator<Order> itr = orderList.iterator();
                            while(itr.hasNext()){

                                Order person = itr.next();
                                if (thisUserId != person.getUser()){
                                    itr.remove();
                                }else {

                                }

                            }


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                        adapter.notifyDataSetChanged();

                        //adapter.notifyDataSetChanged();
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent);

                    }
                });
    }

}