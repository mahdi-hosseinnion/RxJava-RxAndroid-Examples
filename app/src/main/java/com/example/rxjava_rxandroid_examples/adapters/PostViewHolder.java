package com.example.rxjava_rxandroid_examples.adapters;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rxjava_rxandroid_examples.R;
import com.example.rxjava_rxandroid_examples.models.Post;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "PostViewHolder";
    TextView txt_title,txt_countOfComments;
    ProgressBar prb_comments;
    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_title=itemView.findViewById(R.id.txt_title_item);
        txt_countOfComments=itemView.findViewById(R.id.txt_countOfComments_item);
        prb_comments=itemView.findViewById(R.id.prb_comment_item);
    }
    public void bind(Post post){
        txt_title.setText(post.getTitle());
        if (post.getComments()!=null){
            prb_comments.setVisibility(View.INVISIBLE);
            txt_countOfComments.setVisibility(View.VISIBLE);
            txt_countOfComments.setText(String.valueOf(post.getComments().size()));
            Log.d(TAG, "bind: count of comments = "+post.getComments().size());
        }else{
            prb_comments.setVisibility(View.VISIBLE);
            txt_countOfComments.setVisibility(View.INVISIBLE);
        }

    }
}
