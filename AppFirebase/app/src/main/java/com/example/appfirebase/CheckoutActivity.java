package com.example.appfirebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfirebase.Adapters.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    TextView productsNames, totalCena, toHome;
    EditText userN, userE, userPh, userA;
    MaterialButton btnMakeOrder;

    FirebaseFirestore db;
    ArrayList<Cart> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        toHome = (TextView) findViewById(R.id.toHFromCheckout);
        toHome.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        db = FirebaseFirestore.getInstance();
        cartList = new ArrayList<Cart>();

        getUserId();

        btnMakeOrder = (MaterialButton) findViewById(R.id.btnMakeOrder);
        btnMakeOrder.setOnClickListener(view -> {
            getUserIdOrderam();
            //1)Order: userId, userName, userEmail, userPhone, userAddress, productsName(String virkne), orderPrice, datums, statuss(true/false=piegadats/nepiegadats)

//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
        });
    }

    private void getUserId(){
        String epa = PrefConfig.loadEpasts(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userN = (EditText) findViewById(R.id.changeName);
        userE = (EditText) findViewById(R.id.changeEmail);
        userPh = (EditText) findViewById(R.id.changePhone);
        userA = (EditText) findViewById(R.id.changeAddress);

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
                                    userN.setText(String.valueOf(document.get("name")));
                                    userE.setText(String.valueOf(document.get("email")));
                                    userPh.setText(String.valueOf(document.get("phone")));
                                    userA.setText(String.valueOf(document.get("address")));

                                    getAllCart(userId);
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

    private void getAllCart(int userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        productsNames = (TextView) findViewById(R.id.checkout_txt_name);
        totalCena = (TextView) findViewById(R.id.checkout_txt_price);

//        ArrayList<Cart> cartArrayList = new ArrayList<Cart>();

        db.collection("cart")
                .whereEqualTo("user", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            float cenaVienam, cenaKopa = 0;
                            String produktuVirkne = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                cenaVienam = 0;

                                Log.d(TAG, document.getId() + " => " + document.getData());
                                cartList.add(document.toObject(Cart.class));

                                //String.valueOf(document.get("name"))
                                if (produktuVirkne.equals("")) {
                                    if (Integer.parseInt(String.valueOf(document.get("amount")))==1) {
                                        produktuVirkne = produktuVirkne + String.valueOf(document.get("name"));
                                    } else {
                                        produktuVirkne = produktuVirkne + String.valueOf(document.get("name")) + " (" + String.valueOf(Integer.parseInt(String.valueOf(document.get("amount")))) +")";

                                    }
                                } else {
                                    if (Integer.parseInt(String.valueOf(document.get("amount")))==1) {
                                        produktuVirkne = produktuVirkne + "; " + String.valueOf(document.get("name"));
                                    } else {
                                        produktuVirkne = produktuVirkne + "; " + String.valueOf(document.get("name")) + " (" + String.valueOf(Integer.parseInt(String.valueOf(document.get("amount")))) +")";

                                    }

                                }
                                cenaVienam = Integer.parseInt(String.valueOf(document.get("amount"))) * Float.parseFloat(String.valueOf(document.get("price")));

                                cenaKopa = cenaKopa + cenaVienam;

                                //   cartArrayList.add(document.toObject(Cart.class));
                            }

                            String cenina = new DecimalFormat("####.##").format(cenaKopa);
                            totalCena.setText("Price: " + cenina + " EUR");
                            productsNames.setText(produktuVirkne);


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        //adapter.notifyDataSetChanged();

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

    private void getUserIdOrderam(){//String user_email){
        String epa = PrefConfig.loadEpasts(this);
        userN = (EditText) findViewById(R.id.changeName);
        userE = (EditText) findViewById(R.id.changeEmail);
        userPh = (EditText) findViewById(R.id.changePhone);
        userA = (EditText) findViewById(R.id.changeAddress);

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
//                                    userN.setText(String.valueOf(document.get("name")));
//                                    userE.setText(String.valueOf(document.get("email")));
//                                    userPh.setText(String.valueOf(document.get("phone")));
//                                    userA.setText(String.valueOf(document.get("address")));

                                    //getAllCart(userId);
                                    makeProdIds(userId);//te vjg datus iekavas
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

    private void makeProdIds(int user_id){
        db.collection("cart")
                .whereEqualTo("user", user_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double cenaVienam, cenaKopa = 0;
                            String produktuVirkne = "";

                            for (DocumentSnapshot document : task.getResult()) {
                                cenaVienam = 0;
                               // Log.d(TAG, String.valueOf(document.get("user")));
                               // int produktaUsers = Integer.parseInt(String.valueOf(document.get("user")));
//                                if (produktaUsers != user_id) {
//
//                                } else {
                                    if (produktuVirkne.equals("")) {
                                        if (Integer.parseInt(String.valueOf(document.get("amount")))==1) {
                                            produktuVirkne = produktuVirkne + String.valueOf(document.get("prod"));
                                        } else {
                                            produktuVirkne = produktuVirkne + String.valueOf(document.get("prod")) + " ( x " + String.valueOf(document.get("amount")) +")";

                                        }
                                    } else {
                                        if (Integer.parseInt(String.valueOf(document.get("amount")))==1) {
                                            produktuVirkne = produktuVirkne + "; " + String.valueOf(document.get("prod"));
                                        } else {
                                            produktuVirkne = produktuVirkne + "; " + String.valueOf(document.get("prod")) + " ( x " + String.valueOf(document.get("amount")) +")";

                                        }

                                    }
//                                Log.d(TAG, "Price: ");
//                                    Log.d(TAG, String.valueOf(document.get("price"))+String.valueOf(document.get("amount")));
//                                  Toast.makeText(CheckoutActivity.this, String.valueOf(document.get("price")) + String.valueOf(document.get("amount")), Toast.LENGTH_SHORT).show();
                                    cenaVienam = Integer.parseInt(String.valueOf(document.get("amount"))) * Double.parseDouble(String.valueOf(document.get("price")));

//                                }
                                cenaKopa = cenaKopa + cenaVienam;
                                // count=count + 1;
                                //ieprieks dabut count un tad kad abi vienadi tad iet uz addProd, citadi inc!!!
                               // if (String.valueOf(strId).equals(String.valueOf(document.get("prod")))) {

                                //}
                            }
                            String cenina = new DecimalFormat("####.##").format(cenaKopa);


//                                   Intent intent = new Intent(getApplicationContext(), OrderHasBeenMadeActivity.class);
//                                   intent.putExtra("forOrderId", user_id);
//                                   intent.putExtra("forOrderVi", produktuVirkne);
//                                   intent.putExtra("forOrderCe", cenina);
                            userA = (EditText) findViewById(R.id.changeAddress);
                            String address = String.valueOf(userA.getText());
                            if (address.equals("")){
                                Toast.makeText(CheckoutActivity.this, "Please Write Your Address", Toast.LENGTH_SHORT).show();
                            } else {
                                makeOrder(user_id, produktuVirkne, cenina);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }

                });


    }
    private void makeOrder(int user_id, String prod_ids, String cena){
        userN = (EditText) findViewById(R.id.changeName);
        userE = (EditText) findViewById(R.id.changeEmail);
        userPh = (EditText) findViewById(R.id.changePhone);
        userA = (EditText) findViewById(R.id.changeAddress);
        String email = String.valueOf(userE.getText());
        String phone = String.valueOf(userPh.getText());
        String address = String.valueOf(userA.getText());
        String name = String.valueOf(userN.getText());
        productsNames = (TextView) findViewById(R.id.checkout_txt_name);
        String prod_names = String.valueOf(productsNames.getText());
        String userId = String.valueOf(user_id);

        LocalDate dat = LocalDate.now();
        String datu = String.valueOf(dat);

        Date laiks = Calendar.getInstance().getTime();
        String laik = String.valueOf(laiks);
        String orderid = String.valueOf(user_id)+"";

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Map<String, Object> lietotajs = new HashMap<>();
        lietotajs.put("user", user_id);//("id", (skaits[0] + 10000));
        lietotajs.put("name", name);
        lietotajs.put("email", email);
        lietotajs.put("phone", phone);
        lietotajs.put("address", address);
        lietotajs.put("productsnames", prod_names);
        lietotajs.put("prodids", prod_ids);
        lietotajs.put("productsprice", cena);
        lietotajs.put("datums", datu);


//        ApiFuture<DocumentReference> addedDocRef = db.collection("cities").add(data);
        CollectionReference cr = db.collection("order");
        cr.document().set(lietotajs);

        deleteCarts(user_id);



    }
//    private void deleteProduct(int product, int userId) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("cart").document((String.valueOf(userId) + String.valueOf(product))).delete();
//    }

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

                        }
                    }

                });

    }




}