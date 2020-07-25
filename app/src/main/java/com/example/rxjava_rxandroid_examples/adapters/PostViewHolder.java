package com.example.rxjava_rxandroid_examples.adapters;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rxjava_rxandroid_examples.R;
import com.example.rxjava_rxandroid_examples.models.Post;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private static final String TAG = "PostViewHolder";
    private OnPostClickListener mOnPostClickListener;
    TextView txt_title;
    public PostViewHolder(@NonNull View itemView, OnPostClickListener onPostClickListener) {
        super(itemView);
        txt_title=itemView.findViewById(R.id.txt_title_item);
        this.mOnPostClickListener=onPostClickListener;
        itemView.setOnClickListener(this);
    }

    public void bind(Post post){
        txt_title.setText(post.getTitle());
    }

    @Override
    public void onClick(View v) {
        mOnPostClickListener.onPostClick(getAdapterPosition());
    }
}
