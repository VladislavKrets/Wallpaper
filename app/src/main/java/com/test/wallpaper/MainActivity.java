package com.test.wallpaper;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.test.walpaper.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar topToolBar = findViewById(R.id.toolbar);
        topToolBar.setTitleTextColor(Color.WHITE);
        topToolBar.setSubtitleTextColor(Color.WHITE);

        setSupportActionBar(topToolBar);


        gridView = findViewById(R.id.albumsGridView);
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setHorizontalSpacing(5);
        gridView.setVerticalSpacing(5);
        gridView.setColumnWidth(370);
        new AssetsTask().execute();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (requestCode) {
                case 1: {
                    Uri uri = data.getData();
                    Intent intent = new Intent(MainActivity.this, PhotoViewActivity.class);
                    intent.putExtra("filePath", uri);
                    startActivity(intent);
                }
            }
        }
    }

    private class AssetsTask extends AsyncTask<Void, Void, Void> {
        private PhotoAdapter adapter;
        @Override
        protected Void doInBackground(Void... voids) {
            List<PhotoEntity> photoEntities = new ArrayList<>();
            AssetManager assetManager = getAssets();
            try {
                photoEntities.add(new PhotoEntity(assetManager.open("cam.png"), "own picture"));
                String[] folders = assetManager.list("wallpapers");
                for (String folderName : folders) {
                    photoEntities.add(new PhotoEntity(assetManager.open("wallpapers/" + folderName + "/1.jpg"),
                            folderName));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            adapter = new PhotoAdapter(MainActivity.this, photoEntities, R.layout.main_activity_item);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    switch (position) {
                        case 0:{
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

                            break;
                        }
                        default: {
                            Intent intent = new Intent(MainActivity.this, AlbumActivity.class);
                            intent.putExtra("folderName", ((PhotoEntity)adapterView.getAdapter().getItem(position)).getName());
                            startActivity(intent);
                            break;
                        }
                    }
                }
            });
        }
    }


}
