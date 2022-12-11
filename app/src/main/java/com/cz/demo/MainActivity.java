package com.cz.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.cz.imagedownloader.ImageDownloader;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        // Set context for the image downloader.
        ImageDownloader.shared.context = this.getApplicationContext();

        // Display image with the `imageUrl`.
        String imageUrl = "https://d37t5b145o82az.cloudfront.net/pictures/14729eb660b3a409368f820a053ac319.jpg";
        ImageDownloader.shared.displayImage(imageUrl, imageView);
    }
}