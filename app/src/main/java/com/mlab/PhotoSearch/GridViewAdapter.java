package com.mlab.PhotoSearch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
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
    private String searchWord = "";

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
//        holder.imageTitle.setText(item.getDate());
        setTextViewColorPartial(holder.imageTitle, item.getDate(), searchWord, 0xffff7011);
        holder.image.setImageURI(Uri.withAppendedPath(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, item.getThumbsID()));
        holder.image.setScaleType(ImageView.ScaleType.FIT_CENTER);

        return convertView;
    }

    public void setData(ArrayList<ImageItem> data) {
        this.data.clear();
        this.data = data;
 //       setNotifyOnChange(true);
        // notifyDataSetChanged();

//        ((MainActivity)context).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                notifyDataSetChanged();
//            }
//        });
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public static void setTextViewColorPartial(TextView view, String fullText, String subText, int color) {
        view.setText(fullText, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int idxMatching = fullText.indexOf(subText);
        str.setSpan(new ForegroundColorSpan(color), idxMatching, idxMatching + subText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private  static class ViewHolder {
        public  TextView imageTitle;
        public  ImageView image;
    }
}