package com.example.automate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automate.R;

import java.util.List;

import com.example.automate.model.ProductCommentData;

/**
 * Adapter class for displaying product comments in a RecyclerView.
 */
public class ProductCommentAdapter extends RecyclerView.Adapter<ProductCommentAdapter.CommentViewHolder> {

    private Context context;
    private List<ProductCommentData> comments;
    private int currentCommentIndex = 0;

    public ProductCommentAdapter(Context context, List<ProductCommentData> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each comment item
        View view = LayoutInflater.from(context).inflate(com.example.automate.R.layout.product_comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        // Bind the data for the current comment
        ProductCommentData item = comments.get(currentCommentIndex);
        holder.commentText.setText(item.getCommentText());
        holder.commentUserText.setText(item.getUsername());
        holder.ratingBarInput.setRating(item.getRating());
        holder.arrowLeft.setOnClickListener(v -> showPreviousComment());
        holder.arrowRight.setOnClickListener(v -> showNextComment());
    }

    private void showPreviousComment() {
        // Show the previous comment if available
        if (currentCommentIndex > 0) {
            currentCommentIndex--;
            notifyDataSetChanged();
        }
    }

    private void showNextComment() {
        // Show the next comment if available
        if (currentCommentIndex < comments.size() - 1) {
            currentCommentIndex++;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView commentText;
        public TextView commentUserText;
        public ImageButton arrowLeft;
        public ImageButton arrowRight;
        public RatingBar ratingBarInput;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views for the comment item
            commentText = itemView.findViewById(R.id.commentText);
            commentUserText = itemView.findViewById(R.id.commentUserText);
            arrowLeft = itemView.findViewById(R.id.arrowLeft);
            arrowRight = itemView.findViewById(R.id.arrowRight);
            ratingBarInput = itemView.findViewById(R.id.ratingBarInput);
        }
    }
}
