package com.example.appfirebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.perf.FirebasePerformance;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    MaterialButton toLog, toCart;
    TextView toMenuView;

    Button toProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String epa = PrefConfig.loadEpasts(this);

//        FirebasePerformance.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


//        Query query = db.collection("user");
//        AggregateQuery countQuery = query.count();
//        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    // Count fetched successfully
//                    AggregateQuerySnapshot snapshot = task.getResult();
//                    Toast.makeText(MainActivity.this, "Here " + snapshot.getCount(), Toast.LENGTH_SHORT).show();
//
////                    Log.d(TAG, "Count: " + snapshot.getCount());
//                } else {
//                    Log.d(TAG, "Count failed: ", task.getException());
//                }
//            }
//        });

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // User is signed in
////            Toast.makeText(MainActivity.this, "Here " + FirebaseAuth.getInstance().getCurrentUser(), Toast.LENGTH_SHORT).show();
//
//        } else {
//            // No user is signed in
//        }
//        if (FirebaseAuth.getInstance().getCurrentUser() != null){
//            Toast.makeText(MainActivity.this, "Here " + FirebaseAuth.getInstance().getCurrentUser(), Toast.LENGTH_SHORT).show();
//        }
        /*
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(apiClient);*/

        toLog = (MaterialButton) findViewById(R.id.mainToLogin);
        toLog.setOnClickListener(view -> {
            if (epa.equals("Not Logged In") == true){
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//            intent.putExtra("user_email",  user_email);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
//                intent.putExtra("thisUsersEmail",  epastins);
                startActivity(intent);
            }
        });

        toCart = (MaterialButton) findViewById(R.id.mainToCart);
        toCart.setOnClickListener(view -> {
            if (epa.equals("Not Logged In") == true){
//                String nav = "To See Cart You Have To Login!";
//                Toast.makeText(MainActivity.this, nav, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), HaventLoginActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });


     /*    toLog.setOnClickListener(view -> {
            //pagaidam te bus pievienosanas funkcija!!!
            for (int i=1; i<51; i++) {//sakuma no 151 lisz 1101 un tad no 1101 lidz 10101
                int prod_id = (100 + i);
                String prod_n = "Women's Product No. " + String.valueOf(i);
                boolean boole;
                String prod_price;
                if (i < 26) {
                    prod_price = String.valueOf(Float.parseFloat("24.99") + i);
                    boole = true;
                } else {
                    prod_price = String.valueOf(Float.parseFloat("99.49") - i);
                    boole = false;
                }
                String prod_desc = "Women's Product No. " + String.valueOf(i) + " is very comfortable and soft.";
                String prod_img = "Women" + String.valueOf(i) + ".png";
                Map<String, Object> produktins = new HashMap<>();
                produktins.put("id", prod_id);
                produktins.put("name", prod_n);
                produktins.put("special_offer", boole);
                produktins.put("price", prod_price);
                produktins.put("description", prod_desc);
                produktins.put("image", prod_img);
                produktins.put("category", "women");
                db.collection("product")
                        .add(produktins)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
            }
             for (int i=51; i<101; i++) {//sakuma no 151 lisz 1101 un tad no 1101 lidz 10101
                 int prod_id = (100 + i);
                 String prod_n = "Men's Product No. " + String.valueOf(i);
                 boolean boole;
                 String prod_price;
                 if (i < 76) {
                     prod_price = String.valueOf(Float.parseFloat("24.99") + i);
                     boole = true;
                 } else {
                     prod_price = String.valueOf(Float.parseFloat("199.49") - i);
                     boole = false;
                 }
                 String prod_desc = "Men's Product No. " + String.valueOf(i) + " is very comfortable and soft.";
                 String prod_img = "Men" + String.valueOf(i) + ".png";
                 Map<String, Object> produktins = new HashMap<>();
                 produktins.put("id", prod_id);
                 produktins.put("name", prod_n);
                 produktins.put("special_offer", boole);
                 produktins.put("price", prod_price);
                 produktins.put("description", prod_desc);
                 produktins.put("image", prod_img);
                 produktins.put("category", "men");
                 db.collection("product")
                         .add(produktins)
                         .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                             @Override
                             public void onSuccess(DocumentReference documentReference) {

                             }
                         })
                         .addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                             }
                         });
             }
             for (int i=101; i<151; i++) {//sakuma no 151 lisz 1101 un tad no 1101 lidz 10101
                 int prod_id = (100 + i);
                 String prod_n = "Children's Product No. " + String.valueOf(i);
                 boolean boole;
                 String prod_price;
                 if (i < 126) {
                     prod_price = String.valueOf(Float.parseFloat("24.99") + i);
                     boole = true;
                 } else {
                     prod_price = String.valueOf(Float.parseFloat("199.49") - i);
                     boole = false;
                 }
                 String prod_desc = "Children's Product No. " + String.valueOf(i) + " is very comfortable and soft.";
                 String prod_img = "Children" + String.valueOf(i) + ".png";
                 Map<String, Object> produktins = new HashMap<>();
                 produktins.put("id", prod_id);
                 produktins.put("name", prod_n);
                 produktins.put("special_offer", boole);
                 produktins.put("price", prod_price);
                 produktins.put("description", prod_desc);
                 produktins.put("image", prod_img);
                 produktins.put("category", "children");
                 db.collection("product")
                         .add(produktins)
                         .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                             @Override
                             public void onSuccess(DocumentReference documentReference) {

                             }
                         })
                         .addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                             }
                         });
             }

        });
*/



        toMenuView = (TextView) findViewById(R.id.toMenu);
        toMenuView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
//            intent.putExtra("user_email",  user_email);
            startActivity(intent);
        });

        toProd = (Button) findViewById(R.id.mainToProducts);
        toProd.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
//            intent.putExtra("user_email",  user_email);
            startActivity(intent);
//            SendMail mail = new SendMail()
        });

    }
}