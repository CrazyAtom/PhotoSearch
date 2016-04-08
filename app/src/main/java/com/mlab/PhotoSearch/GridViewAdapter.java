package com.mlab.PhotoSearch;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<ImageItem> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<ImageItem> data = new ArrayList<ImageItem>();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<ImageItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) convertView.findViewById(R.id.text);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageItem item = data.get(position);
        holder.imageTitle.setText(item.getTitle());
        holder.image.setImageURI(Uri.withAppendedPath(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, item.getThumbsID()));
        holder.image.setScaleType(ImageView.ScaleType.FIT_CENTER);

        return convertView;
    }

    private  static class ViewHolder {
        public  TextView imageTitle;
        public  ImageView image;
    }
}