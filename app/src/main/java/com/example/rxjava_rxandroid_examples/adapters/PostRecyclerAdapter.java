package com.example.rxjava_rxandroid_examples.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rxjava_rxandroid_examples.R;
import com.example.rxjava_rxandroid_examples.models.Post;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.http.POST;

public class PostRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Post> mPosts=new ArrayList<>();
    private OnPostClickListener mOnPostClickListener;

    public PostRecyclerAdapter(OnPostClickListener mOnPostClickListener) {
        this.mOnPostClickListener = mOnPostClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_list_item,parent,false);
        return new PostViewHolder(view,mOnPostClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PostViewHolder) holder).bind(mPosts.get(position));
    }

    @Override
    public int getItemCount() {
        if (mPosts!=null){
            return mPosts.size();
        }
        return 0;
    }
    public void setRecipes(List<Post> posts){
        mPosts = posts;
        notifyDataSetChanged();
    }
    public void updatePost(Post post){
        mPosts.set(mPosts.indexOf(post), post);
        notifyItemChanged(mPosts.indexOf(post));
    }

    public List<Post> getPosts() {
        return mPosts;
    }
}
