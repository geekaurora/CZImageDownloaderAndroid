package com.cz.imagedownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageDownloader {
    public static ImageDownloader shared = new ImageDownloader();

    public Context context;

    private ImageCache imageCache;

    public ImageCache getImageCache() {
        if (imageCache == null) {
            imageCache = new ImageCache();
        }
        return imageCache;
    }

    // Background thread pool
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // Main queue handler
    private Handler mainLooper = new Handler(Looper.getMainLooper());

    public void displayImage(final String imageUrl, final ImageView imageView) {
        /** Fetch from cache */
        Bitmap cachedImage = getImageCache().get(imageUrl);
        if (cachedImage != null) {
            updateImageView(imageView, cachedImage);
            return;
        }

        /** Fetch from network */

        // Associate imageUrl with imageView
        imageView.setTag(imageUrl);

        // Run on background thread
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                // Download the image with imageUrl.
                Bitmap bitmap = downloadImage(imageUrl);
                if (bitmap == null) {
                    return;
                }
                // Save to cache
                getImageCache().put(imageUrl, bitmap);
                // Check associate `imageUrl`
                if (imageView.getTag().equals(imageUrl)) {
                    // Display image at the imageView on main thread
                    updateImageView(imageView, bitmap);
                }
            }
        });
    }

    public Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to download. Error - " + e);
            assert false: e;
        }
        return bitmap;
    }

    void updateImageView(final ImageView imageView, final Bitmap bmp) {
        mainLooper.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bmp);
            }
        });
    }
}
