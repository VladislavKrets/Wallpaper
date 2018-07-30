package com.test.wallpaper;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.test.walpaper.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {
    private GridView gridView;

    private String folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        folder = getIntent().getStringExtra("folderName");
        Toolbar topToolBar = findViewById(R.id.toolbar);
        topToolBar.setTitleTextColor(Color.WHITE);
        topToolBar.setSubtitleTextColor(Color.WHITE);

        setSupportActionBar(topToolBar);

        gridView = findViewById(R.id.albumGridView);
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setHorizontalSpacing(5);
        gridView.setVerticalSpacing(5);
        gridView.setColumnWidth(370);

        new AssetsTask().execute();
    }

    private class AssetsTask extends AsyncTask<Void, Void, Void> {
        private PhotoAdapter adapter;

        @Override
        protected Void doInBackground(Void... voids) {
            List<PhotoEntity> photoEntities = new ArrayList<>();
            AssetManager assetManager = getAssets();
            try {
                String[] files = assetManager.list("wallpapers/" + folder);

                for (String fileName : files) {
                    photoEntities.add(new PhotoEntity(assetManager.open("wallpapers/" + folder + "/" + fileName),
                            ""));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            adapter = new PhotoAdapter(AlbumActivity.this, photoEntities, R.layout.album_activity_item);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(AlbumActivity.this, PhotoViewActivity.class);
                    InputStream inputStream = ((PhotoEntity)adapterView.getAdapter()
                            .getItem(position)).getFilePath();
                    UtilsInputStream.getInstance().setInputStream(inputStream);
                    intent.putExtra("input", "InputStream");
                    startActivity(intent);

                }
            });
        }
    }
}
