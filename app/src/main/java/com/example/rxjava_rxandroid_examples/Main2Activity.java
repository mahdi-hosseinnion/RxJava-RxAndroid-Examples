package com.example.rxjava_rxandroid_examples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.rxjava_rxandroid_examples.models.Post;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("post")) {

            Post post = getIntent().getParcelableExtra("post");
            if (post != null)
                ((TextView) (findViewById(R.id.txt_title))).setText(post.toString());
            else
                Log.e(TAG, "getIncomingIntent: post is null");
        }
    }
}
