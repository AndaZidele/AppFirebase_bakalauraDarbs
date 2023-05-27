package com.example.appfirebase.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfirebase.OneProductActivity;
import com.example.appfirebase.R;
import com.google.android.material.button.MaterialButton;
//import com.google.api.Context;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

//    Context context;
    Context context; //Android content
    ArrayList<Product> productList;
    public ProductsAdapter(Context context, ArrayList<Product> productList){
        this.context = context;
        this.productList = productList;
    }
//    private Context context;
//    List<Product> productList;
//    public ProductsAdapter(List<Product> productList) {this.productList = productList;}
//    public ProductsAdapter(List<DocumentSnapshot> context){
//        this.context = context;
//        productList = new ArrayList<>();
//    }
//    public void add(Product product){
//        productList.add(product);
//    }


    @NonNull
    @Override
    public ProductsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_table_view, viewGroup, false);

        View itemView = LayoutInflater.from(context).inflate(R.layout.products_table_view,viewGroup,false);

        //inflate(R.layout.products_table_view,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.name.setText(productList.get(i).getName());
//        String cen = productList.get(i).getPrice().toString();
//        String cenina = new DecimalFormat("####.##").format(cen);
        myViewHolder.price.setText(productList.get(i).getPrice());
        myViewHolder.description.setText(productList.get(i).getDescription());
        myViewHolder.itemView.setOnClickListener((v->{
            Intent cardViewsActivity = new Intent(myViewHolder.name.getContext(), OneProductActivity.class);
            //String str = myMovieDataList.getMovieName().toString();
            cardViewsActivity.putExtra("oneName",  productList.get(i).getName());
            cardViewsActivity.putExtra("onePrice",  productList.get(i).getPrice());
            cardViewsActivity.putExtra("oneDescr",  productList.get(i).getDescription());
            cardViewsActivity.putExtra("oneId",  productList.get(i).getId());
            myViewHolder.name.getContext().startActivity(cardViewsActivity);
        }));





    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
//        CardView root_view;
        TextView name, price, description; //vel pievienot visus
        MaterialButton btnEdt, btnDelete;
        //        String epastins = ProductsActivity.class.getName(userEmail);
        Layout userEmailLayout;
        TextView userEmail;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


//            root_view = (CardView) itemView.findViewById(R.id.root_view);
            name = (TextView) itemView.findViewById(R.id.txt_name);
            price = (TextView) itemView.findViewById(R.id.txt_price);
            description = (TextView) itemView.findViewById(R.id.txt_desc);

        }
    }
}
