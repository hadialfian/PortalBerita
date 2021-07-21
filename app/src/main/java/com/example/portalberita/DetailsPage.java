package com.example.portalberita;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        Context myContext = this;
        TextView title = findViewById(R.id.title);
        TextView publisedAt = findViewById(R.id.publishAt);
        TextView publisher = findViewById(R.id.publiser);
        TextView description = findViewById(R.id.description);
        Button readMore = findViewById(R.id.readMore);
        TextView author = findViewById(R.id.author);

        Bundle data = getIntent().getExtras();

        //Get ukurun layan device
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int deviceHeight = metrics.heightPixels;
        int deviceWidth = metrics.widthPixels;

        //Gambar berita
        String imageUrl = data.getString("imageUrl");
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.get()
                .load(imageUrl)
                .resize(deviceWidth, deviceHeight/3)
                .centerCrop()
                .into(imageView);

        title.setText(data.getString("title"));
        publisedAt.setText(data.getString("publishedAt"));
        publisher.setText(data.getString("publisher"));
        description.setText(data.getString("description"));
        author.setText("Author: " + data.getString("author"));

        readMore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                WebView myWebView = new WebView(myContext);
                setContentView(myWebView);
                myWebView.loadUrl(data.getString("url"));
            }
        });
    }
}