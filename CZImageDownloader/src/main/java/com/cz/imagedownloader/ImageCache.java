package com.cz.imagedownloader;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
   Manager of mem cache and disk cache.

   ### Usage
   ```
    // 1.Set context for the image downloader.
    ImageDownloader.shared.context = this.getApplicationContext();

    // 2. Display image with the `imageUrl`.
    String imageUrl = "https://d37t5b145o82az.cloudfront.net/pictures/14729eb660b3a409368f820a053ac319.jpg";
    ImageDownloader.shared.displayImage(imageUrl, imageView);
   ```
 */
public class ImageCache {

    private LruCache<String, Bitmap> memCache;
    private DiskCache diskCache;

    public ImageCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 4;
        memCache = new LruCache<String, Bitmap>(cacheSize);
        diskCache = new DiskCache();
    }

    public void put(String url, Bitmap image) {
        String key = keyForUrl(url);
        diskCache.put(key, image);
        memCache.put(key, image);
    }

    public Bitmap get(String url) {
        String key = keyForUrl(url);
        // Read from memCache
        Bitmap memImage = memCache.get(key);
        if (memImage != null) {
            return memImage;
        }
        // Read from diskCache
        Bitmap diskImage = diskCache.get(key);
        if (diskImage != null) {
            memCache.put(key, diskImage);
        }
        return diskImage;
    }

    private String keyForUrl(String url) {
        return md5(url);
    }

    private int sizeOf(String key, Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
