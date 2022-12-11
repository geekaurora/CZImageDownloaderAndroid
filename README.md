### Usage

**MainActivity.java**

```
ImageDownloader.shared.context = this.getApplicationContext();
ImageDownloader.shared.displayImage(imageUrl, imageView);
```

**AndroidManifest.xml**

```
  <uses-permission android:name="android.permission.INTERNET" />  
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```