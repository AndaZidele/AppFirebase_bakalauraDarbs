package com.example.appfirebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    TextView toH, toRegister;
    EditText userLE, userLP;
    MaterialButton btnLog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toH = (TextView) findViewById(R.id.loginToHome);
        toH.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        userLE = (EditText) findViewById(R.id.userLoginEmail);
        userLP = (EditText) findViewById(R.id.userLoginPassword);

        mAuth= FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        toRegister = (TextView) findViewById(R.id.userGoToRegisterPage);
        toRegister.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
//            intent.putExtra("user_email",  user_email);
            startActivity(intent);
        });


        btnLog = (MaterialButton) findViewById(R.id.btnLogin);
        btnLog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loginUser();
//                loginUser(userLE.getText().toString(),
//                        userLP.getText().toString());


//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.putExtra("user_email",  userLE.getText().toString());
//                startActivity(intent);
            }
        });

    }

    private void loginUser(){
        String epasts = userLE.getText().toString();
        String parole = userLP.getText().toString();

        if(TextUtils.isEmpty(epasts)){
            Toast.makeText(this, "Email cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(parole)){
            Toast.makeText(this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        } else {

            //parbaudit epastu un iegut ta paroli

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
                                   // count++;
                                    if (parole.equals(document.get("password"))){
                                        //pareiza parole

                                        PrefConfig.saveUserEmail(getApplicationContext(), epasts);

                                        sendUserToNextView();
                                    } else {
                                        //ievadiet pareizu paroli
                                        Toast.makeText(LoginActivity.this, "Password Not Correct!", Toast.LENGTH_SHORT).show();
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
            //ja paroles sakrit - ir ielogojies


//            mAuth.signInWithEmailAndPassword(epasts,parole).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//
//                        sendUserToNextView();
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Login not Successful", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            });

        }

    }


    private void sendUserToNextView() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

//    private void loginUser(String email, String password){
//        if(TextUtils.isEmpty(email)){
//            Toast.makeText(this, "Email cannot be empty!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(TextUtils.isEmpty(password)){
//            Toast.makeText(this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        compositeDisposable.add(myAPI.loginUser(email,password)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String response) throws Exception {
//                        Toast.makeText(LoginActivity.this, "Login was successful! "+response, Toast.LENGTH_SHORT).show();
//
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        intent.putExtra("user_email",  userLE.getText().toString());
//                        startActivity(intent);
//                    }
//                }));

//    }
}