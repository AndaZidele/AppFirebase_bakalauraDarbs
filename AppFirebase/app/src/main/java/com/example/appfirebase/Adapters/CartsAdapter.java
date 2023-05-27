package com.example.appfirebase.Adapters;


import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfirebase.CartActivity;
import com.example.appfirebase.OneProductActivity;
import com.example.appfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.MyViewHolderCart>{

    CartsAdapter adapter;

    //    List<Cart> cartList;
    Context context; //Android content
    ArrayList<Cart> cartList;

    public CartsAdapter(Context context, ArrayList<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

//    CompositeDisposable compositeDisposable = new CompositeDisposable();
//    UserAPI myAPI;

    @NonNull
    @Override
    public MyViewHolderCart onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_table_view, viewGroup, false);// LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_table_view, viewGroup, false);

        return new MyViewHolderCart(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCart myViewHolder, int i) {
        myViewHolder.name.setText(cartList.get(i).getName());

        String cenina = cartList.get(i).getPrice();//new DecimalFormat("####.##").format((cartList.get(i).getPrice()).toString());
        myViewHolder.price.setText(cenina);

        myViewHolder.amount.setText(String.valueOf(String.valueOf(cartList.get(i).getAmount())));
      //  myViewHolder.userid.setText(String.valueOf(cartList.get(i).getUser()));
       // myViewHolder.prodid.setText(String.valueOf(cartList.get(i).getProd()));


        int userId =  Integer.parseInt(String.valueOf(cartList.get(i).getUser()));
        int product = Integer.parseInt(String.valueOf(cartList.get(i).getProd()));
        int amo =   Integer.parseInt(String.valueOf(myViewHolder.amount.getText())) + 1;//Integer.parseInt(String.valueOf(cartList.get(i).getAmount() + 1));

        myViewHolder.btnDel.setOnClickListener((v->{
            Intent cardViewsActivity = new Intent(myViewHolder.name.getContext(), CartActivity.class);
            deleteProduct(cartList.get(i).getProd(), cartList.get(i).getUser());
            myViewHolder.name.getContext().startActivity(cardViewsActivity);
        }));

        myViewHolder.btnPlus.setOnClickListener((v->{
            Intent cardViewsActivity = new Intent(myViewHolder.name.getContext(), CartActivity.class);

            //funkcija kur skaitu palielina pa 1
            incProduct(cartList.get(i).getProd(), cartList.get(i).getUser(), cartList.get(i).getAmount());


            //vajadzetu reloadoties sim skatam ja nesanak, tad tikai ka seit pamaina skaitu un kopejo cenu
            myViewHolder.name.getContext().startActivity(cardViewsActivity);
        }));

        myViewHolder.btnMinus.setOnClickListener((v->{
            Intent cardViewsActivity = new Intent(myViewHolder.name.getContext(), CartActivity.class);

            if ((cartList.get(i).getAmount()) == 1){
                deleteProduct(cartList.get(i).getProd(), cartList.get(i).getUser());
            }
//            if ((cartList.get(i).getAmount()) == 1){
//            deleteProduct( "8", "1");
//            } else {

            else {
                //funkcija kur skaitu samazina pa 1
                decProduct(cartList.get(i).getProd(), cartList.get(i).getUser(), cartList.get(i).getAmount());

//            }
            }



            // bet ja amount jau ir 1 tad paradas pazinjojuma logs vai
            // velaties dzest so produktu no groza un ja ja
            //tad aiziet uz deleteProduct funkciju

//            cardViewsActivity.putExtra("name",  productList.get(i).getName());
//            String iToS = Integer.toString(productList.get(i).getId());
//            cardViewsActivity.putExtra("id", iToS);
            //vajadzetu reloadoties sim skatam ja nesanak, tad tikai ka seit pamaina skaitu un kopejo cenu
            myViewHolder.name.getContext().startActivity(cardViewsActivity);
        }));


//            myViewHolder.btnEdt.setOnClickListener((v->{
//            Intent cardViewsActivity = new Intent(myViewHolder.name.getContext(), OneProductActivity.class);
//            //String str = myMovieDataList.getMovieName().toString();
//            cardViewsActivity.putExtra("name",  productList.get(i).getName());
//            String iToS = Integer.toString(productList.get(i).getId());
//            cardViewsActivity.putExtra("id", iToS);
//            myViewHolder.name.getContext().startActivity(cardViewsActivity);
//        }));

//        myViewHolder.btnDelete.setOnClickListener((v->{
//            Intent cardViewsActivity = new Intent(myViewHolder.name.getContext(), DeleteActivity.class);
//            String iToS = Integer.toString(productList.get(i).getId());
//            cardViewsActivity.putExtra("id", iToS);
//            cardViewsActivity.putExtra("name",  productList.get(i).getName());
//            myViewHolder.name.getContext().startActivity(cardViewsActivity);
//        }));



    }

    private void updateData(){

//        int uId =  user.getText().getUsers_id();
//        int product = cartList.get(i).getId();
//        int amo = cartList.get(i).getAmount();

    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

//    public void startListening() {
//    }
//
//    public void stopListening() {
//    }


    public class MyViewHolderCart extends RecyclerView.ViewHolder {
        CardView c_root_view;
        TextView name, price, amount, userid, prodid; //vel pievienot visus
        MaterialButton btnPlus, btnMinus, btnDel;
        //
        public MyViewHolderCart(@NonNull View itemView) {
            super(itemView);

//            userid = (TextView) itemView.findViewById(R.id.userId);
//            prodid = (TextView) itemView.findViewById(R.id.prodId);


//            c_root_view = (CardView) itemView.findViewById(R.id.cart_root_view);
            name = (TextView) itemView.findViewById(R.id.cart_product_name);
            price = (TextView) itemView.findViewById(R.id.cart_product_price);
            amount = (TextView) itemView.findViewById(R.id.cart_product_amount);

            btnMinus = (MaterialButton) itemView.findViewById(R.id.btn_minuss);
            btnPlus = (MaterialButton) itemView.findViewById(R.id.btn_plus);
            btnDel = (MaterialButton) itemView.findViewById(R.id.btn_delete_product);

//            Retrofit retrofitUser = RetrofitUser.getInstance();
//            myAPI = retrofitUser.create(UserAPI.class);
        }
    }

    private void updateCart(int user_id, int prod_id, int amount) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cart").document((String.valueOf(user_id)+String.valueOf(prod_id))).update("amount", amount);

//        adapter.notifyDataSetChanged();

    }

    private void incProduct(int product, int userId, int amo){
//        Toast.makeText(CartActivity.class, "Inc Inc Inc", Toast.LENGTH_SHORT).show();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cart")
                .whereEqualTo("user", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                if (String.valueOf(product).equals(String.valueOf(document.get("prod")))) {
                                    int am = amo + 1;
                                    int strId = Integer.parseInt(String.valueOf(document.get("prod")));
                                    db.collection("cart").document((String.valueOf(userId)+String.valueOf(strId))).update("amount", am);
                                    break;

//                                        updateCart(userId, strId, am);
                                    }
                                }
                            }
                        }
                });
    }

    private void decProduct(int product, int userId, int amo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cart")
                .whereEqualTo("user", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                //ieprieks dabut count un tad kad abi vienadi tad iet uz addProd, citadi inc!!!
                                if (String.valueOf(product).equals(String.valueOf(document.get("prod")))) {
                                    int am = amo - 1;//Integer.parseInt(String.valueOf(document.get("amount"))) - 1;
                                    // String strPrice = String.valueOf(document.get("price"));
                                   // int strId = Integer.parseInt(String.valueOf(document.get("prod")));
                                    db.collection("cart").document((String.valueOf(userId)+String.valueOf(product))).update("amount", am);

//                                    updateCart(userId, product, am);
                                    break;

                                    //inProdAmount
                                }
                            }
                        }
                    }
                });
    }

    private void deleteProduct(int product, int userId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cart").document((String.valueOf(userId)+String.valueOf(product))).delete();

//        mFirebaseDatabaseInstance!!.collection("users").document(userId!!).delete()



//        compositeDisposable.add(myAPI.deleteProduct(product, user)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
////                        Toast.makeText(CartsAdapter.this, ""+s, Toast.LENGTH_SHORT).show();
//                    }
//                }));


    }

//    public void deleteItem(int position){
//        this.cartList.remove(position);
//        notifyItemRemoved(position);
//    }

}