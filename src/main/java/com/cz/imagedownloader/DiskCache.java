package com.cz.imagedownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DiskCache {

    File cacheDir;

    DiskCache() {
        cacheDir = ImageDownloader.shared.context.getCacheDir();
    }

    public Bitmap get(String key) {
        File file = new File(cacheDir, key);
        String path = file.getAbsolutePath();
        return BitmapFactory.decodeFile(path);
    }

    public void put(String key, Bitmap bmp) {
        FileOutputStream fileOutputStream = null;

        try {
            File file = new File(cacheDir, key);
            fileOutputStream = new FileOutputStream(file);
            // Save image file to disk with PNG format.
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
