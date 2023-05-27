package com.example.appfirebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfirebase.Adapters.Cart;
import com.example.appfirebase.Adapters.CartsAdapter;
import com.example.appfirebase.Adapters.Product;
import com.example.appfirebase.Adapters.ProductsAdapter;
import com.example.appfirebase.Adapters.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartActivity extends AppCompatActivity {


    RecyclerView recycler_search;
    LinearLayoutManager layoutManager;
    //    ActivityProductsActivityBinding binding;
    CartsAdapter adapter;
    TextView userEmail;
    FirebaseFirestore db;
    ArrayList<Cart> cartList;

    TextView totalCena, toHome;

    MaterialButton btnOrder;

//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recycler_search = (RecyclerView) findViewById(R.id.productListInCart);
        layoutManager = new LinearLayoutManager(this);
//        recycler_search.setLayoutManager(layoutManager);
//        recycler_search.setHasFixedSize(true);
//        recycler_search.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));

//        recycler_search.setLayoutManager(layoutManager);
        recycler_search.setHasFixedSize(true);
        recycler_search.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));

        //Te pievienot list!!!

//        adapter=new ProductsAdapter((this);)


//        recycler_search.setAdapter(adapter);
        recycler_search.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        cartList = new ArrayList<Cart>();
        adapter = new CartsAdapter(CartActivity.this, cartList);

        recycler_search.setAdapter(adapter);

       // getCarts();

        getUserId();

        btnOrder = (MaterialButton) findViewById(R.id.btnMakeOrder);
        btnOrder.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
//            intent.putExtra("user_email",  user_email);
            startActivity(intent);
        });

        toHome = (TextView) findViewById(R.id.toHomeFromCart);
        toHome.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            intent.putExtra("user_email",  user_email);
            startActivity(intent);
        });

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
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                if (epa.equals(String.valueOf(document.get("email")))){
                                    //pareiza parole
                                    int userId =  Integer.parseInt(String.valueOf(document.get("id")));
                                    getCarts(userId);
                                    break;
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

    private void getCarts(int userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

//        Toast.makeText(CartActivity.this, "HIIIIIIII", Toast.LENGTH_SHORT).show();


//        ArrayList<User> productsList = new ArrayList<User>();

        ArrayList<Cart> cartArrayList = new ArrayList<Cart>();

        db.collection("cart")
                .whereEqualTo("user", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double cenaVienam, cenaKopa = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               // Log.d(TAG, document.getId() + " => " + document.getData());
                                cenaVienam = 0;
                                cartList.add(document.toObject(Cart.class));


                                cenaVienam = Integer.parseInt(String.valueOf(document.get("amount"))) * Double.parseDouble(String.valueOf(document.get("price")));

                                cenaKopa = cenaKopa + cenaVienam;

                                //   cartArrayList.add(document.toObject(Cart.class));
                            }
//                            Iterator<Cart> itr = cartList.iterator();
//                            while(itr.hasNext()) {
//                                cenaVienam = 0;
//                                Cart person = itr.next();
//                                cenaVienam = person.getAmount() * person.getPrice();
//
//
//
//                            }

                            String cenina = new DecimalFormat("####.##").format(cenaKopa);


                            totalCena = (TextView) findViewById(R.id.total_id);
//                            totalCena.setText(new DecimalFormat("####.##").format(cenaKopa));

                            //        totalCena.setText(new DecimalFormat("####.##").format(cenaKopa));

                            totalCena.setText("Price: " + cenina + " EUR");
//                            productsNames.setText(produktuVirkne);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        adapter.notifyDataSetChanged();



                    }
                });

      /*  db.collection("cart")
                .whereEqualTo("user_id", userId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Firestor Error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
//                                Cart c = dc.toObject(Cart.class);
                                cartList.add(dc.getDocument().toObject(Cart.class));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });*/

        //////////////////Te
      /*  db.collection("cart")
                .whereEqualTo("user_id", userId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Log.e("Firestor Error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                cartList.add(dc.getDocument().toObject(Cart.class));
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                });*/
                        /*
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
//                                cartList.add(dc.getDocument().toObject(User.class));
                                db.collection("cart")
                                        .whereEqualTo("user_id", dc.getDocument().toObject(User.class).getId())
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                                if (error != null) {
                                                    Log.e("Firestor Error", error.getMessage());
                                                    return;
                                                }

                                                for (DocumentChange dc : value.getDocumentChanges()) {
                                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                                        cartList.add(dc.getDocument().toObject(Cart.class));
                                                    }
                                                }

                                                adapter.notifyDataSetChanged();
                                            }
                                        });
                            } else {
                                Toast.makeText(CartActivity.this, "You Haven't Logged In!", Toast.LENGTH_SHORT).show();
                            }
                        }*/


    }






       /* //te jadabon lietotaja id
        db.collection("cart")
                .whereEqualTo("id", uid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){
                            Log.e("Firestor Error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                productsList.add(dc.getDocument().toObject(User.class));


                                db.collection("cart")
                                        .whereEqualTo("user_id", dc.getDocument().toObject(User.class).getId())
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                                if (error != null) {
                                                    Log.e("Firestor Error", error.getMessage());
                                                    return;
                                                }

                                                for (DocumentChange dc : value.getDocumentChanges()) {
                                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                                        cartList.add(dc.getDocument().toObject(Cart.class));
                                                    }
                                                }

                                                adapter.notifyDataSetChanged();
                                            }
                                        });
                            } else {
                                Toast.makeText(CartActivity.this, "You Haven't Logged In!", Toast.LENGTH_SHORT).show();
                            }
                        }
    }
});*/

}