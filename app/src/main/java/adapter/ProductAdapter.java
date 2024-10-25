package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automate.LoginActivity;
import com.example.automate.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.Product;

/**
 * Adapter class for displaying products in a RecyclerView.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each product item
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Bind the data to the views for each product item
        Product product = productList.get(position);

        holder.textViewName.setText(product.getName());
        holder.textViewPrice.setText(String.format("LKR %.2f", product.getPrice()));
        Log.d("productImage", product.getProductImage());


        // Use Picasso to load the product image into the ImageView
        Picasso.get()
                .load(product.getProductImage())
                .placeholder(R.drawable.image_not_found)
                .error(R.drawable.image_not_found)
                .into(holder.imageViewProduct);

        // Handle item click to open ProductDetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("productId", product.getId());
            intent.putExtra("productName", product.getName());
            intent.putExtra("productPrice", product.getPrice());
            intent.putExtra("productCategory", product.getProductCategoryName());
            Log.d("productId", product.getId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewPrice;
        ImageView imageViewProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views for the product item
            textViewName = itemView.findViewById(R.id.textViewProductName);
            textViewPrice = itemView.findViewById(R.id.textViewProductPrice);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
        }
    }
}
