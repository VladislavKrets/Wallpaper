package com.test.wallpaper;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.test.walpaper.R;

import java.io.IOException;
import java.io.InputStream;

public class PhotoViewActivity extends AppCompatActivity {
    
    private ImageView imageView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        Toolbar topToolBar = findViewById(R.id.toolbar);
        topToolBar.setTitleTextColor(Color.WHITE);
        topToolBar.setSubtitleTextColor(Color.WHITE);

        setSupportActionBar(topToolBar);

        imageView = findViewById(R.id.photoView);
        button = findViewById(R.id.setButton);

        Bitmap bitmap = null;
        String isInput = getIntent().getStringExtra("input");
        if (isInput == null) { //null means we are choosing photo from gallery
            Uri uri = (Uri) getIntent().getParcelableExtra("filePath");;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else { //from assets
            bitmap = BitmapFactory.decodeStream(UtilsInputStream.getInstance().getInputStream());
        }
        imageView.setImageBitmap(bitmap);
        final Bitmap finalBitmap = bitmap;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new WallpaperTask().execute(finalBitmap);
            }
        });
    }
    private class WallpaperTask extends AsyncTask<Bitmap, Void, Void> {
        private ProgressBar progressBar;

        @Override
        protected void onPreExecute() {
            progressBar = new ProgressBar(PhotoViewActivity.this,null,android.R.attr.progressBarStyleLarge);
            //building progress bar
            RelativeLayout layout = findViewById(R.id.relativeLayout);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300,300);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            layout.addView(progressBar,params);
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected Void doInBackground(Bitmap... bitmaps) {
            WallpaperManager wallpaperManager
                    = WallpaperManager.getInstance(getApplicationContext());
            try {
                wallpaperManager.setBitmap(bitmaps[0]); //only one bitmap instance
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setVisibility(View.GONE);
            finish();
        }
    }
}
