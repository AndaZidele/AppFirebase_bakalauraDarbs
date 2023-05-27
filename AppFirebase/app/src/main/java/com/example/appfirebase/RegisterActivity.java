package com.example.appfirebase;

import static android.content.ContentValues.TAG;

import static java.lang.String.valueOf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfirebase.Adapters.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class RegisterActivity extends AppCompatActivity {

    TextView toMenu, toLogin, toH;
    EditText fullName, email, phone, pass, confPass;
    MaterialButton btnReg;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    private Trace mTrace;
    private String STARTUP_TRACE_NAME = "startup_trace";
    private String REQUESTS_COUNTER_NAME = "requests sent";
    private String FILE_SIZE_COUNTER_NAME = "file size";
    private CountDownLatch mNumStartupTasks = new CountDownLatch(2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toLogin = (TextView) findViewById(R.id.userGoToLoginPage);
        toLogin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

        toH = (TextView) findViewById(R.id.toRegisterHome);
        toH.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        mAuth= FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        fullName = (EditText) findViewById(R.id.userFullName);
        email = (EditText) findViewById(R.id.userEmail);
        phone = (EditText) findViewById(R.id.userPhone);
        pass = (EditText) findViewById(R.id.userPassword);
        confPass = (EditText) findViewById(R.id.userConfirmPassword);


        btnReg = (MaterialButton) findViewById(R.id.btnRegister);
        btnReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Te performance:
//                mTrace = FirebasePerformance.getInstance().newTrace(STARTUP_TRACE_NAME);
//                Log.d(TAG, "Starting trace");
//                mTrace.start();

                registerUser();

                // Wait for app startup tasks to complete asynchronously and stop the trace.
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            mNumStartupTasks.await();
//                        } catch (InterruptedException e) {
//                            Log.e(TAG, "Unable to wait for startup task completion.");
//                        } finally {
//                            Log.d(TAG, "Stopping trace");
//                            mTrace.stop();
//                            RegisterActivity.this.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(RegisterActivity.this, "Trace completed",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    }
//                }).start();
            }
        });

    }

    private void registerUser(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final int[] skaits = new int[1];
        String epasts = email.getText().toString();
        String parole = pass.getText().toString();
        String confParole = confPass.getText().toString();
        String telefons = phone.getText().toString();
        String vards = fullName.getText().toString();
        if(TextUtils.isEmpty(epasts)){
            Toast.makeText(this, "Email cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(parole)){
            Toast.makeText(this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(confParole)){
            Toast.makeText(this, "Confirm Password cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(telefons)){
            Toast.makeText(this, "Phone cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(vards)){
            Toast.makeText(this, "Name cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }


        //iegūstam id vertibu!!!
        Query query = db.collection("user");
        AggregateQuery countQuery = query.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Count fetched successfully
                    AggregateQuerySnapshot snapshot = task.getResult();
//                    Toast.makeText(RegisterActivity.this, "Here " + snapshot.getCount(), Toast.LENGTH_SHORT).show();
                    skaits[0] = (int) snapshot.getCount();
//                    int idLietotajam = snapshot.get
//                    Log.d(TAG, "Count: " + snapshot.getCount());
                    //nakama rinda jaatkomente:
                    checkEmail((skaits[0]+1), vards, epasts, parole, telefons,"");
                } else {
                    Log.d(TAG, "Count failed: ", task.getException());
                }
            }
        });


//        checkEmail(1001, vards, epasts, parole, telefons,"");
      /*  int id = 1010;
        String addrese = "";
        db.collection("user")
                .whereEqualTo("email", epasts)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;

                            }
                            if (count>0){
//                                addUser(-1, id, vards, epasts, parole, telefons, addrese);
                            } else {
//                                addUser(0, id, vards, epasts, parole, telefons, addrese);
                                addUser1(0, id, vards, epasts, parole, telefons, addrese);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });*/


    }

    private void addUser(int eksisteLietotajs, int id, String vards, String epasts, String parole, String telefons, String addrese) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = id;
                            int c = 0;
                            long idsFir = id;
                            loop:
                            while (true) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    c++;
                                    if (String.valueOf(idsFir).equals (String.valueOf(document.get("id")))) {
                                        //pareiza parole
                                        idsFir = idsFir + 1;
                                        continue loop;

                                        //  sendUserToNextView();
                                    }
                                    if (count == c) {
                                        addUser1(-1, idsFir, vards, epasts, parole, telefons, addrese);
                                        break loop;
                                    }
                                }
                            }
                        }
                    }
                });


    }
    private void addUser1(int eksisteLietotajs, long id, String vards, String epasts, String parole, String telefons, String addrese){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> lietotajs = new HashMap<>();
        lietotajs.put("id", id);//("id", (skaits[0] + 10000));
        lietotajs.put("name", vards);
        lietotajs.put("email", epasts);
        lietotajs.put("password", parole);
        lietotajs.put("phone", telefons);
        lietotajs.put("address", "");
        CollectionReference cr = db.collection("user");
        cr.document(String.valueOf(id)).set(lietotajs);


//        mTrace = FirebasePerformance.getInstance().newTrace(STARTUP_TRACE_NAME);
//        Log.d(TAG, "Starting trace");
//        mTrace.start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mNumStartupTasks.await();
                } catch (InterruptedException e) {
                    Log.e(TAG, "Unable to wait for startup task completion.");
                } finally {
                    Log.d(TAG, "Stopping trace");
                    mTrace.stop();
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "Trace completed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
//                    sendUserToNextView();
        PrefConfig.saveUserEmail(getApplicationContext(), epasts);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            intent.putExtra("user_email",  user_email);
        startActivity(intent);
    }

    private void checkEmail(int id, String vards, String epasts, String parole, String telefons, String addrese){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user")
                .whereEqualTo("email", epasts)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;

                            }
                            if (count>0){
//                                addUser(-1, id, vards, epasts, parole, telefons, addrese);
                            } else {
//                                addUser(0, id, vards, epasts, parole, telefons, addrese);
                                addUser1(0, id, vards, epasts, parole, telefons, addrese);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    /*
    private void funkcija1( int id, String vards, String epasts, String parole, String telefons, String addrese){

//                mAuth.createUserWithEmailAndPassword( (id + "@m.com"),parole).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth.createUserWithEmailAndPassword(epasts,parole).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String, Object> lietotajs = new HashMap<>();
                    lietotajs.put("id", id);//("id", (skaits[0] + 10000));
                    lietotajs.put("name", vards);
                    lietotajs.put("email", epasts);
                    lietotajs.put("password", parole);
                    lietotajs.put("phone", telefons);
                    lietotajs.put("address", "");
                    CollectionReference cr = db.collection("user");
                    cr.document(String.valueOf(id)).set(lietotajs);

//                    sendUserToNextView();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            intent.putExtra("user_email",  user_email);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();


                }
            }
        });

    }

    private void sendUserToNextView() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }*/
}