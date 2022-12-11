package com.example.testmp4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

// The module of CZImageDownloader.
import com.cz.imagedownloader.ImageDownloader;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ImageView imageView;
    private String imageUrl = "https://d37t5b145o82az.cloudfront.net/pictures/14729eb660b3a409368f820a053ac319.jpg";

    ImageButton playButton;
    MediaController mediaController;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        imageView = (ImageView) findViewById(R.id.imageView);

        //Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_home_black_24dp);
        //Bitmap bmp = BitmapFactory.decodeFile("android.resource://com.example.testmp4/" + "test.jpg");
        //imageView.setImageBitmap(bmp);
        //imageView.setImageResource(R.drawable.ic_home_black_24dp);

        //final String imageUrl = "https://stackify.com/wp-content/uploads/2018/09/types_of_exceptions_in_java_image1.jpg";
        // Set context for the image downloader.
        ImageDownloader.shared.context = this.getApplicationContext();
        // Display image with the `imageUrl`.
        ImageDownloader.shared.displayImage(imageUrl, imageView);
    }

    public void playVideo(View view) {
        ImageDownloader.shared.displayImage(imageUrl, imageView);
    }
}
