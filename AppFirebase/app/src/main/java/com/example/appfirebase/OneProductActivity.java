package com.example.appfirebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfirebase.Adapters.Cart;
import com.example.appfirebase.Adapters.Product;
import com.example.appfirebase.Adapters.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.rpc.context.AttributeContext;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OneProductActivity extends AppCompatActivity {


    TextView description, price, name, toHome;
    ImageView image;
    MaterialButton addTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_product);

        toHome = (TextView) findViewById(R.id.oneProductToMenu);
        toHome.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        name = (TextView) findViewById(R.id.productName);
        price = (TextView) findViewById(R.id.productPrice);
        image = (ImageView) findViewById(R.id.productImage);
        description = (TextView) findViewById(R.id.productDescription);
        addTo = (MaterialButton) findViewById(R.id.btnAddToCart);
        //lietotājam bus jauns pasutijums ar id, kam bus produktu saraksts ar to id

        Intent oneProduct = getIntent();

        String user_email = oneProduct.getStringExtra("user_email");// lietotaja epasts
        String strName = oneProduct.getStringExtra("oneName");
        String strPrice = oneProduct.getStringExtra("onePrice");
//        String strImage = oneProduct.getStringExtra("oneImage");
        String strDescr = oneProduct.getStringExtra("oneDescr");
       // String strId = oneProduct.getStringExtra("oneId");
//
        int strIdInt = oneProduct.getIntExtra("oneId", 0);

//        Toast.makeText(OneProductActivity.this, ""+ intId, Toast.LENGTH_SHORT).show();
//        if (strName != null && strPrice != null && strDescr != null) { //&& strImage!=null
            name.setText(strName);
            price.setText(strPrice);//new DecimalFormat("####.##").format(strPrice) + " EUR");//strPrice + " EUR");
//        totalCena.setText(new DecimalFormat("####.##").format(cenaKopa));

//            image.setImageResource(Integer.parseInt(strImage));
            description.setText(strDescr);
//        }

        addTo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                addToCart();
//                Toast.makeText(OneProductActivity.this, "Hii from AddTo func", Toast.LENGTH_SHORT).show();


//                    Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
//                intent.putExtra("user_email",  userLE.getText().toString());
//                startActivity(intent);

                String epa = PrefConfig.loadEpasts(OneProductActivity.this);
                if (epa.equals("Not Logged In") == true){
                    String nav = "To Add Product To Cart You Have To Login!";
                    Toast.makeText(OneProductActivity.this, nav, Toast.LENGTH_LONG).show();
                } else {
                    getUserId(strIdInt, strPrice);

//                    getUsersId();//prodId, 1, strName, pri, user_email);

                }
            }
        });
            /*
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                String uid = "0";
                final long[] id = new long[1];
                int uidInt = 0;

                    //////
                            break;


                                          /*  Map<String, Object> grozs = new HashMap<>();
                                            grozs.put("prod_id", intId);
                                            grozs.put("user_id", prIdd);
                                            grozs.put("price", strPrice);
                                            grozs.put("prod_name", strName);
                                            grozs.put("amount", (ska[0]+8));

                                            CollectionReference cr = db.collection("cart");
                                            cr.document(String.valueOf((skaitsCart[0]+1000000)))
                                                    .set(grozs);*/


//                                            Toast.makeText(OneProductActivity.this, ""+ prIdd, Toast.LENGTH_SHORT).show();

            //Te vispirms japarbauda vai groza jau nav lietotajam sads produkts!!!
                                           /* final boolean[] b = {true};
                                            db.collection("cart")
                                                    .whereEqualTo("user_id", prIdd)
                                                    .whereEqualTo("prod_id", intId)
                                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                                            if (error != null){
                                                                Log.e("Firestore Error", error.getMessage());
                                                                return;
                                                            }

                                                            boolean boole = false;
                                                            for (DocumentChange dc : value.getDocumentChanges()){
                                                                if (dc.getType() == DocumentChange.Type.ADDED){
                                                                    cartArrayList.add(dc.getDocument().toObject(Cart.class));
//
                                                                }
                                                            }
//                                                            adapter.notifyDataSetChanged();
                                                        }

                                                    });//Te jabub *+/

//                                            Toast.makeText(OneProductActivity.this, "" + cartArrayList.get(0).getName(), Toast.LENGTH_SHORT).show();
                                            //ja ir =>
//                                            if (b[0]==true){
//                                                Toast.makeText(OneProductActivity.this, "Nav Sada Produkta", Toast.LENGTH_SHORT).show();
//                                                //return;
//                                            } else {
//                                                Toast.makeText(OneProductActivity.this, "IR IR IR", Toast.LENGTH_SHORT).show();
////                                                                return;
//                                            }


                                            //...
                                            //ja nav =>


//                                    adapter.notifyDataSetChanged();
                                }
                            });


        */



    }

    private void incCart(String strPrice, int user_id, int prod_id, int amount) {

      //  Toast.makeText(OneProductActivity.this, "Hii from Increment Cart!!!!!!", Toast.LENGTH_SHORT).show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("cart").document((String.valueOf(user_id)+String.valueOf(prod_id))).update("amount", amount);

//        Intent intent = new Intent(getApplicationContext(), DoneActivity.class);
//        startActivity(intent);
    }

    private void addToCart(String strPrice, int user_id, int prod_id) {
     //   Toast.makeText(OneProductActivity.this, "Hii from Add To Cart!!!!!!", Toast.LENGTH_SHORT).show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent oneProduct = getIntent();

//        String user_email = oneProduct.getStringExtra("user_email");// lietotaja epasts
        String strName = oneProduct.getStringExtra("oneName");

          Map<String, Object> grozs = new HashMap<>();
                                            grozs.put("prod", prod_id);
                                            grozs.put("user", user_id);
                                            grozs.put("price", strPrice);
                                            grozs.put("name", strName);
                                            grozs.put("amount", 1);

                                            CollectionReference cr = db.collection("cart");
                                            cr.document((String.valueOf(user_id)+String.valueOf(prod_id))).set(grozs);

//        Intent intent = new Intent(getApplicationContext(), DoneActivity.class);
//        startActivity(intent);
    }

        private void getUserId(int strId, String strPrice){//String user_email){
        String epa = PrefConfig.loadEpasts(this);

          //  Toast.makeText(OneProductActivity.this, epa, Toast.LENGTH_SHORT).show();

            //Cursor cursor = myDB.getDataUserId();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                // count++;
//                                Toast.makeText(OneProductActivity.this, "Hii TEEEEE" + " "+ String.valueOf(document.get("email")), Toast.LENGTH_SHORT).show();

                                if (epa.equals(String.valueOf(document.get("email")))){
//                                    Toast.makeText(OneProductActivity.this, "Hii TEEEEE", Toast.LENGTH_SHORT).show();

                                    //pareiza parole
                                    int userId =  Integer.parseInt(String.valueOf(document.get("id")));


                                    cartCount(strPrice, userId, strId);
//                                    PrefConfig.saveUserEmail(getApplicationContext(), epasts);

                                 //   sendUserToNextView();
                                } else {
                                    //ievadiet pareizu paroli
                                }
                            }
//                                if (count>0){
//                                   // addUser(-1, id, vards, epasts, parole, telefons, addrese);
//                                } else {
//                                    //lietotajs ar sadu epastu nav registrets
//                                   // addUser(0, id, vards, epasts, parole, telefons, addrese);
//                                }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



    }

    private void cartCount(String strPrice, int userId, int strId){
        final int[] skaits = new int[1];
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("cart")
                .whereEqualTo("user", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                          //  int count = id;
                            int c = 0;
                          //  long idsFir = id;
                                for (DocumentSnapshot document : task.getResult()) {
//                                    if document.get("id")
                                    c++;
//                                    if (String.valueOf(idsFir).equals (String.valueOf(document.get("id")))) {
//                                        //pareiza parole
//                                        idsFir = idsFir + 1;
//
//                                        //  sendUserToNextView();
//                                    }

                            }
                            getIncOrAdd(c,strPrice, userId, strId);

                        }
                    }
                });
//        Query query = db.collection("cart");
//        AggregateQuery countQuery = query.count();
//        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    // Count fetched successfully
//                    AggregateQuerySnapshot snapshot = task.getResult();
////                    Toast.makeText(RegisterActivity.this, "Here " + snapshot.getCount(), Toast.LENGTH_SHORT).show();
//                    skaits[0] = (int) snapshot.getCount();
////                    int idLietotajam = snapshot.get
////                    Log.d(TAG, "Count: " + snapshot.getCount());
//                    getIncOrAdd(skaits[0],strPrice, userId, strId);
//                    //checkEmail((skaits[0]+1), vards, epasts, parole, telefons,"");
//                } else {
//                    Log.d(TAG, "Count failed: ", task.getException());
//                }
//            }
//        });
    }

    private void getIncOrAdd(int c,String strPrice, int userId, int strId){//String user_email){
//        String epa = PrefConfig.loadEpasts(this);
        //Cursor cursor = myDB.getDataUserId();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cart")
                .whereEqualTo("user", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                          //  Toast.makeText(OneProductActivity.this, "SSSSSSSSSSSSSSSS  " + c, Toast.LENGTH_SHORT).show();

                            if (c==0){
                                addToCart(strPrice, userId, strId);

                            } else {

                                for (DocumentSnapshot document : task.getResult()) {
                                    count=count + 1;
                                    //ieprieks dabut count un tad kad abi vienadi tad iet uz addProd, citadi inc!!!
                                    if (String.valueOf(strId).equals(String.valueOf(document.get("prod")))) {

                                      //  Toast.makeText(OneProductActivity.this, "KKKK", Toast.LENGTH_SHORT).show();

                                        int am = Integer.parseInt(String.valueOf(document.get("amount"))) + 1;
                                        incCart(strPrice, userId, strId, am);
                                        break;

                                        //inProdAmount
                                    } else {
                                        //ievadiet pareizu
                                        if (c == count) {
                                         //   Toast.makeText(OneProductActivity.this, c +  "  OOO  " + count, Toast.LENGTH_SHORT).show();

//                                            int am = (int) document.get("amount");
//                                            incCart(strPrice, userId, strId, am);
                                            addToCart(strPrice, userId, strId);
                                            break;
                                        }
//                                        Toast.makeText(OneProductActivity.this, "OOO  " + count, Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }

//                                if (count>0){
//                                   // addUser(-1, id, vards, epasts, parole, telefons, addrese);
//                                } else {
//                                    //lietotajs ar sadu epastu nav registrets
//                                   // addUser(0, id, vards, epasts, parole, telefons, addrese);
//                                }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



    }





    private void funkcija1(int skaitins,  int prod_id, String user_id, String price, String name, int amount) {

        int userIdInt = Integer.parseInt(user_id);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Toast.makeText(OneProductActivity.this, "Te: "+prod_id + " "+userIdInt, Toast.LENGTH_SHORT).show();
///////////
        ArrayList<Cart> cartList = new ArrayList<Cart>();
        //te jadabon lietotaja id
        db.collection("user")
//                .whereEqualTo("email", uid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){
                            Log.e("Firestor Error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                cartList.add(dc.getDocument().toObject(Cart.class));
//                                int prIdd = (dc.getDocument().toObject(User.class).getId());
                                //esam dabujusi lietotaja id

        /////////////

//                        for (DocumentChange dc : value.getDocumentChanges()) {
//                            if (dc.getType() == DocumentChange.Type.ADDED) {
//                                Toast.makeText(OneProductActivity.this, prod_id, Toast.LENGTH_SHORT).show();

//                                ArrayList<Cart> carList = new ArrayList<Cart>();

//                                carList.add(dc.getDocument().toObject(Cart.class));
                                int userId = (dc.getDocument().toObject(Cart.class).getUser());//getUsers_id());
                                int prodId = (dc.getDocument().toObject(Cart.class).getProd());
//                                            Toast.makeText(OneProductActivity.this, userId, Toast.LENGTH_SHORT).show();

//                                int cartListSkaits = cartList.lastIndexOf(Cart.class);

//                                int userId = cartList.get(cartListSkaits).getUsers_id();

                                if ((userIdInt == userIdInt) && (prodId == prod_id)) {
                                  //  Toast.makeText(OneProductActivity.this, "Te palielinasim daudzumu: " + userIdInt + " "+ prodId, Toast.LENGTH_SHORT).show();
                                    //break;
                                }
                                else {
                                   // Toast.makeText(OneProductActivity.this, "Te pievienosim produktu: " + userIdInt + " "+ prodId, Toast.LENGTH_SHORT).show();
                                }

                                              /*  Map<String, Object> grozs = new HashMap<>();
                                                grozs.put("prod_id", intId);
                                                grozs.put("user_id", resId2);
                                                grozs.put("price", strPrice);
                                                grozs.put("prod_name", strName);
                                                grozs.put("amount", 1);

//                                                CollectionReference cr = db.collection("cart");
//                                                cr.document()
//                                                        .set(grozs);

                                                db.collection("cart")
                                                        .add(grozs)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
//                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
//                                Log.w(TAG, "Error adding document", e);
                                                            }
                                                        });
                                                //String.valueOf((skaitsCart[0]+1000000))
                                            }
*/

//                                            if (userId == ){
//                                                ska[0] = dc.getDocument().toObject(Cart.class).getAmount();
//                                                Toast.makeText(OneProductActivity.this, userId, Toast.LENGTH_SHORT).show();

//                                            }

                            }
                        }
                    }
                });

    }
}