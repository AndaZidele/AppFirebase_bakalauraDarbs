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
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    TextView name, email, phone, pass, address, toHome;
    Button btnN, btnE, btnPh, btnPass, btnA;
    MaterialButton delUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        db = FirebaseFirestore.getInstance();

        toHome = (TextView) findViewById(R.id.toHFromSet);
        toHome.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        name = (TextView) findViewById(R.id.settingsName);
        email = (TextView) findViewById(R.id.settingsEmail);
        phone = (TextView) findViewById(R.id.settingsPhone);
        pass = (TextView) findViewById(R.id.settingsPass);
        address = (TextView) findViewById(R.id.settingsAddress);

        getUsersData();

        delUser = (MaterialButton) findViewById(R.id.deleteAccount);

        delUser.setOnClickListener(view -> {
//            intent.putExtra("user_email",  user_email);
//            intent.putExtra("thisUsersEmail",  epastins);
//            String epa = PrefConfig.loadEpasts(this);
//            String email = String.valueOf(epa);
            deleteUser();

//            db.collection("user").document((String.valueOf(product))).delete();
            //String u = String.valueOf(db.collection("user").whereEqualTo("email", epa).get(Source.valueOf("id")));
//            String jobskill_query = db.collection("user").where('job_id','==',post.job_id);
//            jobskill_query.get().then(function(querySnapshot) {
//                querySnapshot.forEach(function(doc) {
//                    doc.ref.delete();
//                });
//            });


        });


    }

    private void deleteCarts(int user_id){
        db.collection("cart")
                .whereEqualTo("user", user_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double cenaVienam, cenaKopa = 0;
                            String produktuVirkne = "";

                            //int count = 0;
                            String userId = String.valueOf(user_id);
                            for (DocumentSnapshot document : task.getResult()) {
                                // count = count + 1;
                                String product = String.valueOf(document.get("prod"));
                                db.collection("cart").document((userId + product)).delete();
                            }
//                            deleteOrders(user_id);

                        }
                    }

                });

    }

    private void deleteOrders(int user_id){
        db.collection("order")
                .whereEqualTo("user", user_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);


                            String userId = String.valueOf(user_id);
                            for (DocumentSnapshot document : task.getResult()) {

                                String iddoc = document.getId();
                                // count = count + 1;
//                                String product = String.valueOf(document.get("prod"));
                                db.collection("order").document(iddoc).delete();
                            }

                            PrefConfig.saveUserEmail(getApplicationContext(), "Not Logged In");
                            startActivity(intent);

                        }
                    }

                });

    }

    private void deleteUser(){
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

//                                    getAllCart(userId);
                                    db.collection("user").document(String.valueOf(userId)).delete();


                                    Map<String, Object> lietotajs = new HashMap<>();
                                    lietotajs.put("id", userId);
                                    lietotajs.put("email", "");
                                    CollectionReference cr = db.collection("user");
                                    cr.document(String.valueOf(userId)).set(lietotajs);

                                    deleteCarts(userId);
                                    deleteOrders(userId);



                                    // break;
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void getUsersData(){
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
//                                    int userId =  Integer.parseInt(String.valueOf(document.get("id")));

//                                    getAllCart(userId);
                                    name.setText("Full Name: " + String.valueOf(document.get("name")));
                                    email.setText("Email: " +String.valueOf(document.get("email")));
                                    phone.setText("Phone: " +String.valueOf(document.get("phone")));
                                    pass.setText("Password: " +String.valueOf(document.get("password")));
                                    address.setText("Address: " +String.valueOf(document.get("address")));

                                    // break;
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}