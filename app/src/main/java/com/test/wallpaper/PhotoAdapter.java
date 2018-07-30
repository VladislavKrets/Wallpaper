package com.test.wallpaper;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.walpaper.R;

import java.util.List;

public class PhotoAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater inflater;
    private List<PhotoEntity> photoEntities;
    private int viewId;

    public PhotoAdapter(Context context, List<PhotoEntity> photoEntities, int viewId) {
        this.context = context;
        this.photoEntities = photoEntities;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.viewId = viewId;
    }

    @Override
    public int getCount() {
        return photoEntities.size();
    }

    @Override
    public PhotoEntity getItem(int index) {
        return photoEntities.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(viewId, viewGroup, false);
        }
        view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 620));
        PhotoEntity photoEntity = getItem(position);
        ImageView photoView = view.findViewById(R.id.photoView);
        TextView photoName = view.findViewById(R.id.photoName);

        photoView.setImageBitmap(BitmapFactory.decodeStream(photoEntity.getFilePath()));
        if (photoName != null) photoName.setText(photoEntity.getName());

        return view;
    }
}
